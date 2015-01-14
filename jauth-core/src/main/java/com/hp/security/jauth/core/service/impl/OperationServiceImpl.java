/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.dao.ACLDao;
import com.hp.security.jauth.core.dao.OperationDao;
import com.hp.security.jauth.core.model.ACL;
import com.hp.security.jauth.core.model.Operation;
import com.hp.security.jauth.core.service.OperationService;


/**
 * @author huangyiq
 *
 */
@Service("authOperationService")
public class OperationServiceImpl implements OperationService {

    private OperationDao operationDao;
    private ACLDao aclDao;

    /**
     * @param operationDao
     *            the operationDao to set
     */
    @Autowired
    public void setOperationDao(OperationDao operationDao) {
        this.operationDao = operationDao;
    }

    /**
     * @param aclDao
     *            the aclDao to set
     */
    @Autowired
    public void setAclDao(ACLDao aclDao) {
        this.aclDao = aclDao;
    }

    public Operation findById(long controllerId, int operationId) {
        return operationDao.findById(controllerId, operationId);
    }

    public List<Operation> findByControllerId(long controllerId) {
        return operationDao.findByControllerId(controllerId);
    }

    public int findQuantityByRoleId(long roleId) {
        return operationDao.findQuantityByPrincipalId(roleId, 0);
    }

    public int findQuantityByRoleIdAndController(long roleId, long controllerId) {
        return operationDao.findQuantityByPrincipalIdAndControllerId(roleId, 0, controllerId);
    }

    public int findNextOperationId(long controllerId) {
        List<Operation> operations = operationDao.findByControllerId(controllerId);
        int result = 0;
        int index = 0;
        while (true) {
            if (index < operations.size()) {
                if (operations.get(index).getOperationId() > result) {
                    return result;
                } else if (operations.get(index).getOperationId() == result) {
                    result++;
                    index++;
                }
            } else {
                return operations.size();
            }
        }
    }

    public void save(Operation operation) {
        int operationId = findNextOperationId(operation.getController().getControllerId());
        operation.setOperationId(operationId);
        operationDao.save(operation);
    }

    public void update(Operation operation) {
        operationDao.update(operation);
    }

    public void delete(long controllerId, int operationId) {
        List<ACL> acls = aclDao.findByController(controllerId);
        for (ACL acl : acls) {
            acl.setState(operationId, false);
            aclDao.update(acl);
        }
        operationDao.delete(controllerId, operationId);
    }

}
