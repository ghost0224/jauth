/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hp.security.jauth.core.model.AssociateUser;
import com.hp.security.jauth.core.model.UserGroup;
import com.hp.security.jauth.core.service.AssociateUserService;
import com.hp.security.jauth.core.service.RoleService;

/**
 * @author huangyiq
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jauth-core-applicationContext.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "jauthTransactionManager", defaultRollback = true)
public class UserServiceTest {

    private RoleService roleService;
    private AssociateUserService userService;
    
    /**
     * @return the userService
     */
    public AssociateUserService getUserService() {
        return userService;
    }

    /**
     * @param userService
     *            the userService to set
     */
    @Autowired
    public void setUserService(AssociateUserService userService) {
        this.userService = userService;
    }

    /**
     * @return the roleService
     */
    public RoleService getRoleService() {
        return roleService;
    }

    @Autowired
    /**
     * @param roleService the roleService to set
     */
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @Test
    public void testSave() {
        AssociateUser user = new AssociateUser();
        user.setUserId("ttt2");
        user.setEmail("e2@hp.com");
        user.setActivate("Y");
        userService.save(user);
    }

    @Test
    public void testFind() {
        List<AssociateUser> users = userService.findAll();
        for (AssociateUser u : users) {
            List<UserGroup> ugs = u.getUserGroups();
            for (UserGroup ug : ugs) {
                System.out.println(ug.getGroup());
            }
        }
    }

}
