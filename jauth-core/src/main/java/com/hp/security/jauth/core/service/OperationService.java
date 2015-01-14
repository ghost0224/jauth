/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service;

import java.util.List;

import com.hp.security.jauth.core.model.Operation;

/**
 * @author huangyiq
 *
 */
public interface OperationService {

    Operation findById(long controllerId, int operationId);

    List<Operation> findByControllerId(long controllerId);

    int findQuantityByRoleId(long roleId);

    int findQuantityByRoleIdAndController(long roleId, long controllerId);

    int findNextOperationId(long controllerId);

    void save(Operation operation);

    void update(Operation operation);

    void delete(long controllerId, int operationId);

}
