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

import com.hp.security.jauth.core.model.Group;
import com.hp.security.jauth.core.service.GroupService;
import com.hp.security.jauth.core.service.RoleService;

/**
 * @author huangyiq
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/jauth-core-applicationContext.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "jauthTransactionManager", defaultRollback = true)
public class GroupServiceTest {

    private RoleService roleService;
    private GroupService groupService;

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
    
    /**
     * @return the groupService
     */
    public GroupService getGroupService() {
        return groupService;
    }

    /**
     * @param groupService
     *            the groupService to set
     */
    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Test
    public void testSave() {
        Group group = new Group();
        group.setName("myGroup");
        groupService.save(group);
    }

    @Test
    public void testUpdate() {
        Group group = new Group();
        group.setName("myGroup3");
        group.setGroupId(4);
        groupService.update(group);
    }

    @Test
    public void testDelete() {
        groupService.delete(2);
    }

    @Test
    public void testFind() {
        List<Group> groups = groupService.findAll();
        for (Group g : groups) {
            g.getUserGroups();
            g.getGroupRoles();
        }
    }

}
