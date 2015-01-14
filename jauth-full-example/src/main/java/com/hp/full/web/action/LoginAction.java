/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.full.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.hp.full.web.constant.Constant;
import com.hp.full.web.model.User;
import com.hp.full.web.service.UserService;


/**
 * @author huangyiq
 *
 */
public class LoginAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    private UserService userService;
    private String email;
    private String password;

    public String execute() {
        User user = userService.findByEmail(email, password);
        if (null != user) {
            super.getSession().put(Constant.LOGIN_USER, user.getEmail());
            return SUCCESS;
        } else {
            return ERROR;
        }
    }

    public String loginPage() {
        return "loginPage";
    }

    /**
     * @return the userService
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * @param userService
     *            the userService to set
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
