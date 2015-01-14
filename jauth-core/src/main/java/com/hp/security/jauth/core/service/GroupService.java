/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service;

import java.util.List;

import com.hp.security.jauth.core.model.Group;
import com.hp.security.jauth.core.model.UserGroup;


/**
 * @author huangyiq
 *
 */
public interface GroupService {

    List<Group> findAll();

    Group findById(long groupId);
    
    List<UserGroup> findByUserId(long userId);

    void save(Group group);

    void update(Group group);

    void delete(long groupId);

}
