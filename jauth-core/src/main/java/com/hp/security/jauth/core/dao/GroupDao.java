/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.dao;

import java.util.List;

import com.hp.security.jauth.core.model.Group;

/**
 * @author huangyiq
 *
 */
public interface GroupDao {

    List<Group> findAll();

    Group findById(long groupId);

    void save(Group group);

    void update(Group group);

    void delete(long groupId);

}
