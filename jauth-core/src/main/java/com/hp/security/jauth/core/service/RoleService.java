/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service;

import java.util.List;

import com.hp.security.jauth.core.model.Role;

/**
 * @author huangyiq
 *
 */
public interface RoleService {

    void save(Role role);

    List<Role> findAll();

    Role findById(long roleId);

    void delete(long roleId);

    void update(Role role);

}
