/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.dao;

import java.util.List;

import com.hp.security.jauth.core.model.GroupRole;

/**
 * @author huangyiq
 *
 */
public interface GroupRoleDao {

    List<GroupRole> findByRoleId(long roleId);

    List<GroupRole> findByGroupId(long groupId);

    void deleteByRoleId(long roleId);

    void deleteByGroupId(long groupId);

    void save(GroupRole groupRole);

}
