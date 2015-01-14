/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service;

import java.util.List;
import java.util.Map;

import com.hp.security.jauth.core.model.ACL;
import com.hp.security.jauth.core.model.AssociateUser;
import com.hp.security.jauth.core.model.Controller;


/**
 * @author huangyiq
 *
 */
public interface ACLService {

    void saveOrUpdate(long principalId, int principalType, long controllerId, int operationSize, String operations);
    
    void saveOrUpdate(ACL acl);

    @Deprecated
    void saveOrUpdate(long principalId, int principalType, long controllerId, int operationId, boolean yes);

    void delete(long principalId, int principalType, long controllerId);

    Map<String, ACL> findAllByUser(long associateUserId);

    List<Controller> findViewPageController(Map<String, ACL> aclMap, List<Controller> allControllerList);

    boolean findPermissionByControllerAndOperation(Map<String, ACL> aclMap, String controllerMapping, String operationName);
    
    @Deprecated
    boolean findPermissionByControllerAndOperation(AssociateUser associateUser,
            Map<String, Controller> allControllerMap, String controllerMapping, String operationName);

    List<ACL> findByRole(long roleId);

    List<ACL> findByUser(long associateUserId);

    ACL findByRoleAndController(long roleId, long controllerId);

    ACL findByUserAndController(long associateUserId, long controllerId);

    ACL findByPrincipalAndController(long principalId, int principalType, long controllerId);

    @Deprecated
    List<?> findDetailStatusByPrincipal(long principalId, int principalType);

    @Deprecated
    List<?> findDetailStatusByPrincipalAndController(long principalId, int principalType, long controllerId);

}
