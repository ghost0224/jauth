/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.dao.ACLDao;
import com.hp.security.jauth.core.dao.AssociateUserDao;
import com.hp.security.jauth.core.dao.UserGroupDao;
import com.hp.security.jauth.core.model.AssociateUser;
import com.hp.security.jauth.core.model.UserGroup;
import com.hp.security.jauth.core.service.AssociateUserService;


/**
 * @author huangyiq
 *
 */
@Service("authUserService")
public class AssociateUserServiceImpl implements AssociateUserService {

    private AssociateUserDao userDao;
    private UserGroupDao userGroupDao;
    private ACLDao aclDao;

    /**
     * @return the userDao
     */
    public AssociateUserDao getUserDao() {
        return userDao;
    }

    /**
     * @param userDao
     *            the userDao to set
     */
    @Autowired
    public void setUserDao(AssociateUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * @return the userGroupDao
     */
    public UserGroupDao getUserGroupDao() {
        return userGroupDao;
    }

    /**
     * @param userGroupDao
     *            the userGroupDao to set
     */
    @Autowired
    public void setUserGroupDao(UserGroupDao userGroupDao) {
        this.userGroupDao = userGroupDao;
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

    public List<AssociateUser> findAll() {
        return userDao.findAll();
    }

    public AssociateUser findById(long associateUserId) {
        return userDao.findById(associateUserId);
    }

    public AssociateUser findByUserId(String userId) {
        return userDao.findByUserId(userId);
    }

    public AssociateUser findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public void save(AssociateUser user) {
        userDao.save(user);
        List<UserGroup> userGroups = user.getUserGroups();
        for (UserGroup ug : userGroups) {
            userGroupDao.save(ug);
        }
    }

    public void update(AssociateUser user) {
        userGroupDao.deleteByUserId(user.getAssociateUserId());
        List<UserGroup> userGroups = user.getUserGroups();
        for (UserGroup ug : userGroups) {
            userGroupDao.save(ug);
        }
        userDao.update(user);
    }

    public void delete(long userId) {
        aclDao.deleteByPrincipal(userId, 1);
        userGroupDao.deleteByUserId(userId);
        userDao.delete(userId);
    }

}
