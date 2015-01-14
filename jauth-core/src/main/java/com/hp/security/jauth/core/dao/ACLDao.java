/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hp.security.jauth.core.model.ACL;

/**
 * @author huangyiq
 *
 */
public interface ACLDao {

    void save(ACL acl);

    void update(ACL acl);

    void delete(@Param("principalId") long principalId, @Param("principalType") int principalType,
            @Param("controllerId") long controllerId);

    void deleteByController(long controllerId);

    void deleteByPrincipal(@Param("principalId") long principalId, @Param("principalType") int principalType);

    List<ACL> findByPrincipal(@Param("principalId") long principalId, @Param("principalType") int principalType);

    ACL findByPrincipalAndController(@Param("principalId") long principalId, @Param("principalType") int principalType,
            @Param("controllerId") long controllerId);

    List<?> findDetailStatus(String sql);

    List<ACL> findByController(long controllerId);

}
