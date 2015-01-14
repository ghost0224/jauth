/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.dao.GroupDao;
import com.hp.security.jauth.core.dao.GroupRoleDao;
import com.hp.security.jauth.core.dao.UserGroupDao;
import com.hp.security.jauth.core.model.Group;
import com.hp.security.jauth.core.model.GroupRole;
import com.hp.security.jauth.core.model.UserGroup;
import com.hp.security.jauth.core.service.GroupService;


/**
 * @author huangyiq
 *
 */
@Service("authGroupService")
public class GroupServiceImpl implements GroupService {

    private GroupDao groupDao;
    private UserGroupDao userGroupDao;
    private GroupRoleDao groupRoleDao;

    /**
     * @return the groupDao
     */
    public GroupDao getGroupDao() {
        return groupDao;
    }

    /**
     * @return the userGroupDao
     */
    public UserGroupDao getUserGroupDao() {
        return userGroupDao;
    }

    @Autowired
    /**
     * @param userGroupDao the userGroupDao to set
     */
    public void setUserGroupDao(UserGroupDao userGroupDao) {
        this.userGroupDao = userGroupDao;
    }

    /**
     * @param groupDao
     *            the groupDao to set
     */
    @Autowired
    public void setGroupDao(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    /**
     * @return the groupRoleDao
     */
    public GroupRoleDao getGroupRoleDao() {
        return groupRoleDao;
    }

    /**
     * @param groupRoleDao
     *            the groupRoleDao to set
     */
    @Autowired
    public void setGroupRoleDao(GroupRoleDao groupRoleDao) {
        this.groupRoleDao = groupRoleDao;
    }

    public List<Group> findAll() {
        return groupDao.findAll();
    }

    public Group findById(long groupId) {
        return groupDao.findById(groupId);
    }

    public List<UserGroup> findByUserId(long userId) {
        return userGroupDao.findByUserId(userId);
    }

    public void save(Group group) {
        groupDao.save(group);
        List<GroupRole> groupRoles = group.getGroupRoles();
        for (GroupRole gr : groupRoles) {
            groupRoleDao.save(gr);
        }
    }

    public void update(Group group) {
        groupRoleDao.deleteByGroupId(group.getGroupId());
        List<GroupRole> groupRoles = group.getGroupRoles();
        for (GroupRole gr : groupRoles) {
            groupRoleDao.save(gr);
        }
        groupDao.update(group);
    }

    public void delete(long groupId) {
        userGroupDao.deleteByGroupId(groupId);
        groupRoleDao.deleteByGroupId(groupId);
        groupDao.delete(groupId);
    }

}
