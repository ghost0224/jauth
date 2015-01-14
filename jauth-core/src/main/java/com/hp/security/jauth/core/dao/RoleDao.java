/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.dao;

import java.util.List;

import com.hp.security.jauth.core.model.Role;


/**
 * @author huangyiq
 *
 */
public interface RoleDao {

    List<Role> findAll();
    
    Role findById(long roleId);

    void save(Role role);

    void update(Role role);

    void delete(long roleId);

}
