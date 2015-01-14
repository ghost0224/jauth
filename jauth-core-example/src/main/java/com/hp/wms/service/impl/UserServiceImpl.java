/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.wms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.wms.dao.UserDao;
import com.hp.wms.model.User;
import com.hp.wms.service.UserService;


/**
 * @author huangyiq
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    /* (non-Javadoc)
     * @see com.hp.cmu.wizard.service.UserProfileService#findByEmail()
     */
    @Override
    public User findByEmail(String email, String pwd) {
        return userDao.findByEmail(email, pwd);
    }

    /**
     * @return the userDao
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * @param userDao
     *            the userDao to set
     */
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
