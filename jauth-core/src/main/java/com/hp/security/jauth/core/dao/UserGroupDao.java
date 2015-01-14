/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.dao;

import java.util.List;

import com.hp.security.jauth.core.model.UserGroup;

/**
 * @author huangyiq
 *
 */
public interface UserGroupDao {

    List<UserGroup> findByUserId(long userId);

    List<UserGroup> findByGroupId(long groupId);

    void deleteByUserId(long userId);

    void deleteByGroupId(long groupId);

    void save(UserGroup userGroup);

}
