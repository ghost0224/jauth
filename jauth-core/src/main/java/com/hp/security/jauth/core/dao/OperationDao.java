/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hp.security.jauth.core.model.Operation;

/**
 * @author huangyiq
 *
 */
public interface OperationDao {

    Operation findById(@Param("controllerId") long controllerId, @Param("operationId") int operationId);

    List<Operation> findByControllerId(long controllerId);

    int findQuantityByPrincipalId(@Param("principalId") long principalId, @Param("principalType") int principalType);

    int findQuantityByPrincipalIdAndControllerId(@Param("principalId") long principalId,
            @Param("principalType") int principalType, @Param("controllerId") long controllerId);

    void save(Operation operation);

    void update(Operation operation);

    void deleteByControllerId(long controllerId);

    void delete(@Param("controllerId") long controllerId, @Param("operationId") int operationId);

}
