/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;




//import com.hp.security.jauth.controller.LogicalController;
import com.hp.security.jauth.core.dao.ACLDao;
import com.hp.security.jauth.core.dao.AssociateUserDao;
import com.hp.security.jauth.core.dao.ConfigDao;
import com.hp.security.jauth.core.dao.OperationDao;
import com.hp.security.jauth.core.exception.AuthException;
import com.hp.security.jauth.core.filter.SystemInit;
import com.hp.security.jauth.core.model.ACL;
import com.hp.security.jauth.core.model.AssociateUser;
import com.hp.security.jauth.core.model.Controller;
import com.hp.security.jauth.core.model.GroupRole;
import com.hp.security.jauth.core.model.Operation;
import com.hp.security.jauth.core.model.Role;
import com.hp.security.jauth.core.model.UserGroup;
import com.hp.security.jauth.core.service.ACLService;
import com.hp.security.jauth.core.util.Constants;
import com.hp.security.jauth.core.util.MessageContext;


/**
 * @author huangyiq
 *
 */
@Service("authACLService")
public class ACLServiceImpl implements ACLService {

    private ACLDao aclDao;
    private ConfigDao configDao;
    private AssociateUserDao userDao;
    private OperationDao operationDao;
    private Logger log = Logger.getLogger(this.getClass().getName());
    @Value("${databaseType}")
    private String databaseType;
    
	/**
     * @param aclDao
     *            the aclDao to set
     */
    @Autowired
    public void setAclDao(ACLDao aclDao) {
        this.aclDao = aclDao;
    }

    /**
     * @param userDao
     *            the userDao to set
     */
    @Autowired
    public void setUserDao(AssociateUserDao userDao) {
        this.userDao = userDao;
    }
    
	/**
     * @param operationDao
     *            the operationDao to set
     */
    @Autowired
    public void setOperationDao(OperationDao operationDao) {
        this.operationDao = operationDao;
    }
    
    /**
     * @param configDao
     *            the configDao to set
     */
    @Autowired
    public void setConfigDao(ConfigDao configDao) {
        this.configDao = configDao;
    }

    @Deprecated
    public void saveOrUpdate(long principalId, int principalType, long controllerId, int operationId, boolean yes) {
        ACL acl = aclDao.findByPrincipalAndController(principalId, principalType, controllerId);
        if (null != acl) {
            acl.setState(operationId, yes);
            aclDao.update(acl);
        } else {
            acl = new ACL();
            acl.setPrincipalId(principalId);
            acl.setPrincipalType(principalType);
            acl.setControllerId(controllerId);
            acl.setState(operationId, yes);
            aclDao.save(acl);
        }
    }
    
	public void saveOrUpdate(ACL acl) {
    	ACL acldb = aclDao.findByPrincipalAndController(acl.getPrincipalId(), acl.getPrincipalType(), acl.getControllerId());
        if (null != acldb) {
        	aclDao.update(acl);
        } else {
        	aclDao.save(acl);
        }
	}

    public void saveOrUpdate(long principalId, int principalType, long controllerId, int operationSize,
            String operations) {
        ACL acl = aclDao.findByPrincipalAndController(principalId, principalType, controllerId);
        if (null != acl) {
            if (operations != null && operations.split(",").length > 0) {
                String[] operation = operations.split(",");
                for (int i = 0, j = 0; i < operationSize; i++) {
                    if (j < operation.length) {
                        int operationIndex = Integer.parseInt(operation[j]);
                        if (operationIndex == i) {
                            acl.setState(operationIndex, true);
                            j++;
                        } else {
                            acl.setState(i, false);
                        }
                    } else {
                        acl.setState(i, false);
                    }
                }
            } else {
                acl.setAclState(0);
            }
            log.debug("update: " + acl.getAclState());
            aclDao.update(acl);
        } else {
            acl = new ACL();
            acl.setControllerId(controllerId);
            acl.setPrincipalId(principalId);
            acl.setPrincipalType(principalType);
            if (operations != null && operations.split(",").length > 0) {
                String[] operation = operations.split(",");
                for (int i = 0, j = 0; i < operationSize; i++) {
                    if (j < operation.length) {
                        int operationIndex = Integer.parseInt(operation[j]);
                        if (operationIndex == i) {
                            acl.setState(operationIndex, true);
                            j++;
                        } else {
                            acl.setState(i, false);
                        }
                    } else {
                        acl.setState(i, false);
                    }
                }
            } else {
                acl.setAclState(0);
            }
            log.debug("insert: " + acl.getAclState());
            aclDao.save(acl);
        }

    }

    public void delete(long principalId, int principalType, long controllerId) {
        aclDao.delete(principalId, principalType, controllerId);
    }

    public Map<String, ACL> findAllByUser(long associateUserId) {
        AssociateUser user = userDao.findById(associateUserId);
        if (null == user) {
            return null;
        }
        Map<String, ACL> result = new HashMap<String, ACL>();
        int arithmeticIndex = Integer.parseInt(configDao.findByKey(Constants.CONFIG_KEY_ARITHMETIC).getValue());
        if (arithmeticIndex == 0) {
            /**
             * Merge Arithmetic
             */
            // 1.get current user's groups
            List<UserGroup> userGroups = user.getUserGroups();
            for (int i = 0; i < userGroups.size(); i++) {
                // 2.get each group's roles
                List<GroupRole> groupRoles = userGroups.get(i).getGroup().getGroupRoles();
                for (int j = 0; j < groupRoles.size(); j++) {
                    // 4.get each role's access-control-list
                    List<ACL> acls = aclDao.findByPrincipal(groupRoles.get(j).getRole().getRoleId(), 0);
                    for (ACL a : acls) {
                        Controller controller = SystemInit.allControllerIdMap.get(a.getControllerId());
                        if (null != controller) {
                            ACL acl = result.get(controller.getMapping());
                            if (acl != null) {
                                long resultState = acl.getAclState() | a.getAclState();
                                acl.setAclState(resultState);
                            } else {
                                acl = a;
                            }
                            result.put(controller.getMapping(), acl);
                        }
                    }
                }
            }
            // 1.get current user's acls
            List<ACL> acls = aclDao.findByPrincipal(user.getAssociateUserId(), 1);
            for (ACL a : acls) {
                Controller controller = SystemInit.allControllerIdMap.get(a.getControllerId());
                if (null != controller) {
                    ACL acl = result.get(controller.getMapping());
                    if (acl != null) {
                        long resultState = acl.getAclState() | a.getAclState();
                        acl.setAclState(resultState);
                    } else {
                        acl = a;
                    }
                    result.put(controller.getMapping(), acl);
                }
            }
        } else {
            /**
             * Priority Arithmetic
             */
            // 1.get current user's groups by priority "DESC"
            List<UserGroup> userGroups = user.getUserGroups();
            // 2.since the userGroups in the user is sort by priority "DESC", here should start with
            // the last element.
            for (int i = userGroups.size() - 1; i >= 0; i--) {
                // 2.get each group's roles by priority asc
                List<GroupRole> groupRoles = userGroups.get(i).getGroup().getGroupRoles();
                // 3.since the groupRoles in the userGroup is sort by priority "DESC", here should
                // start with the last element.
                for (int j = groupRoles.size() - 1; j >= 0; j--) {
                    // 4.get each role's access-control-list
                    List<ACL> acls = aclDao.findByPrincipal(groupRoles.get(j).getRole().getRoleId(), 0);
                    for (ACL acl : acls) {
                        /*
                         * 5.moduleId as a MapKey, put "acl" into a map. the lower "acl" will be put
                         * first, higher later. if there are more than one "acl" defined a same
                         * module, the higher priority role's "acl" will cover the lower role's
                         * "acl".
                         */
                        Controller controller = SystemInit.allControllerIdMap.get(acl.getControllerId());
                        if (null != controller) {
                            result.put(controller.getMapping(), acl);
                        }
                    }
                }
            }
            // 6.get user's access-control-list
            List<ACL> acls = aclDao.findByPrincipal(associateUserId, 1);
            for (ACL acl : acls) {
                /*
                 * 7.moduleId as a MapKey, put "acl" into a map. if there is a same moduleId in the
                 * map, it will be covered by current "acl". because the acl of user have a higher
                 * priority than the acl of this user's groups.
                 */
                Controller controller = SystemInit.allControllerIdMap.get(acl.getControllerId());
                if (null != controller) {
                    result.put(controller.getMapping(), acl);
                }
            }
        }
        return result;
    }

    public List<Controller> findViewPageController(Map<String, ACL> aclMap, List<Controller> allControllerList) {
        List<Controller> result = new ArrayList<Controller>();
        for (Controller c : allControllerList) {
            ACL acl = aclMap.get(c.getMapping());
            if (null != acl) {
                log.debug("Controller: " + c.getMapping() + ", ACL State: " + acl.getAclState() + ", ViewPage: "
                        + acl.getState(0));
                if (acl.getState(0)) {
                    result.add(c);
                }
            }
        }
        return result;
    }

    public boolean findPermissionByControllerAndOperation(Map<String, ACL> aclMap, String controllerMapping, String operationName) {
        ACL acl = aclMap.get(controllerMapping);
        if(null == acl) {
        	controllerMapping = controllerMapping.substring(controllerMapping.indexOf("/")+1);
        	acl = aclMap.get(controllerMapping);
        }
        if (null != acl) {
            Controller controller = SystemInit.allControllerMappingMap.get(controllerMapping);
            if (null != controller) {
                if (!controller.getActivate().equals("Y")) {
                	String message = MessageContext.getMessage("AUTH_ERROR_1", new String[]{"mapping", controller.getMapping()});
                    throw new AuthException("AUTH_ERROR_1", message);
                }
                Operation operation = null;
                List<Operation> operations = controller.getOperations();
                for (Operation o : operations) {
                    if (o.getName().equals(operationName)) {
                        operation = o;
                        break;
                    }
                }
                if (null != operation) {
                    return acl.getState(operation.getOperationId());
                } else {
                	String message = MessageContext.getMessage("AUTH_ERROR_14", new String[]{"operation", operationName}, new String[]{"controller", controller.getMapping()});
                	throw new AuthException("AUTH_ERROR_14", message);
                }
            } else {
            	String message = MessageContext.getMessage("AUTH_ERROR_15", new String[]{"controller", controllerMapping});
            	throw new AuthException("AUTH_ERROR_15", message);
            }
        }
        return false;
    }
    
    public boolean findPermissionByControllerAndOperation(AssociateUser associateUser,
            Map<String, Controller> allControllerMap, String controllerMapping, String operationName) {
    	Controller controller = allControllerMap.get(controllerMapping);
		if (null != controller) {
            if (!controller.getActivate().equals("Y")) {
            	String message = MessageContext.getMessage("AUTH_ERROR_1", new String[]{"mapping", controller.getMapping()});
                throw new AuthException("AUTH_ERROR_1", message);
            }
            Operation operation = null;
            List<Operation> operations = controller.getOperations();
            for (Operation o : operations) {
                if (o.getName().equals(operationName)) {
                    operation = o;
                    break;
                }
            }
            if(null != operation) {
            	ACL acl = aclDao.findByPrincipalAndController(associateUser.getAssociateUserId(), 1, controller.getControllerId());
            	if(null != acl) {
            		return acl.getState(operation.getOperationId());
            	} else {
            		List<UserGroup> userGroups = associateUser.getUserGroups();
            		for(UserGroup ug : userGroups) {
            			List<GroupRole> groupRoles = ug.getGroup().getGroupRoles();
            			for(GroupRole gr : groupRoles) {
            				Role r = gr.getRole();
            				acl = aclDao.findByPrincipalAndController(r.getRoleId(), 0, controller.getControllerId());
            				if(null != acl) {
            					return acl.getState(operation.getOperationId());
            				}
            			}
            		}
            	}
            }
    	}
        return false;
    }


    public List<ACL> findByRole(long roleId) {
        return aclDao.findByPrincipal(roleId, 0);
    }

    public List<ACL> findByUser(long associateUserId) {
        return aclDao.findByPrincipal(associateUserId, 1);
    }

    public ACL findByRoleAndController(long roleId, long controllerId) {
        return aclDao.findByPrincipalAndController(roleId, 0, controllerId);
    }

    public ACL findByUserAndController(long associateUserId, long controllerId) {
        return aclDao.findByPrincipalAndController(associateUserId, 1, controllerId);
    }

    public ACL findByPrincipalAndController(long principalId, int principalType, long controllerId) {
        return aclDao.findByPrincipalAndController(principalId, principalType, controllerId);
    }

    public List<?> findDetailStatusByPrincipal(long principalId, int principalType) {
        int topQuantity = operationDao.findQuantityByPrincipalId(principalId, principalType);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT CONTROLLER_ID R0");
        if(databaseType.equalsIgnoreCase("mysql")) {
        	for (int i = 0, temp = 1; i < topQuantity; temp *= 2) {
                sql.append(", (ACL_STATE&");
                sql.append(temp);
                sql.append(") R");
                sql.append(++i);
            }
        } else if(databaseType.equalsIgnoreCase("oracle")) {
        	for (int i = 0, temp = 1; i < topQuantity; temp *= 2) {
                sql.append(", BITAND(ACL_STATE,");
                sql.append(temp);
                sql.append(") R");
                sql.append(++i);
            }
        }
        sql.append(" FROM ATH_ACSS_CTRL_LST WHERE PRINCIPAL_ID = ");
        sql.append(principalId);
        sql.append(" AND PRINCIPAL_TYPE = ");
        sql.append(principalType);
        sql.append(" ORDER BY CONTROLLER_ID ASC");
        List<?> acls = aclDao.findDetailStatus(sql.toString());
        return acls;
    }

    public List<?> findDetailStatusByPrincipalAndController(long principalId, int principalType, long controllerId) {
        int topQuantity = operationDao.findQuantityByPrincipalIdAndControllerId(principalId, principalType,
                controllerId);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT CONTROLLER_ID R0");
        if(databaseType.equalsIgnoreCase("mysql")) {
        	for (int i = 0, temp = 1; i < topQuantity; temp *= 2) {
                sql.append(", (ACL_STATE&");
                sql.append(temp);
                sql.append(") R");
                sql.append(++i);
            }
        } else if(databaseType.equalsIgnoreCase("oracle")) {
        	for (int i = 0, temp = 1; i < topQuantity; temp *= 2) {
                sql.append(", BITAND(ACL_STATE,");
                sql.append(temp);
                sql.append(") R");
                sql.append(++i);
            }
        }
        sql.append(" FROM ATH_ACSS_CTRL_LST WHERE PRINCIPAL_ID = ");
        sql.append(principalId);
        sql.append(" AND PRINCIPAL_TYPE = ");
        sql.append(principalType);
        sql.append(" AND CONTROLLER_ID = ");
        sql.append(controllerId);
        List acls = aclDao.findDetailStatus(sql.toString());
        return acls;
    }

}
