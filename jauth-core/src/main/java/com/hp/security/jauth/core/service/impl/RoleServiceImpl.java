/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.dao.ACLDao;
import com.hp.security.jauth.core.dao.GroupRoleDao;
import com.hp.security.jauth.core.dao.RoleDao;
import com.hp.security.jauth.core.model.Role;
import com.hp.security.jauth.core.service.RoleService;


/**
 * @author huangyiq
 *
 */
@Service("authRoleService")
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;
    private GroupRoleDao groupRoleDao;
    private ACLDao aclDao;

    /**
     * @return the roleDao
     */
    public RoleDao getRoleDao() {
        return roleDao;
    }

    @Autowired
    /**
     * @param roleDao the roleDao to set
     */
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
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

    /**
     * @return the aclDao
     */
    public ACLDao getAclDao() {
        return aclDao;
    }

    /**
     * @param aclDao
     *            the aclDao to set
     */
    @Autowired
    public void setAclDao(ACLDao aclDao) {
        this.aclDao = aclDao;
    }

    public void save(Role role) {
        roleDao.save(role);
    }

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    public Role findById(long roleId) {
        return roleDao.findById(roleId);
    }

    public void update(Role role) {
        roleDao.update(role);
    }

    public void delete(long roleId) {
        groupRoleDao.deleteByRoleId(roleId);
        aclDao.deleteByPrincipal(roleId, 0);
        roleDao.delete(roleId);
    }

}
