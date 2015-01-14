/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.dao;

import java.util.List;

import com.hp.security.jauth.core.model.AssociateUser;

/**
 * @author huangyiq
 *
 */
public interface AssociateUserDao {

    AssociateUser findById(long associateUserId);

    AssociateUser findByUserId(String userId);

    AssociateUser findByEmail(String email);

    List<AssociateUser> findAll();

    void save(AssociateUser user);

    void update(AssociateUser user);

    void delete(long userId);

}
