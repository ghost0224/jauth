package com.hp.security.jauth.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AssociateUser implements Serializable {

	private static final long serialVersionUID = -1441615297194566476L;

	private long associateUserId;
 
	private String userId;
	 
	private String email;
	
	private String password;
	 
    private String activate;

    private List<UserGroup> userGroups = new ArrayList<UserGroup>();

    /**
     * @return the associateUserId
     */
    public long getAssociateUserId() {
        return associateUserId;
    }

    /**
     * @param associateUserId
     *            the associateUserId to set
     */
    public void setAssociateUserId(long associateUserId) {
        this.associateUserId = associateUserId;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
     * @return the activate
     */
    public String getActivate() {
        return activate;
    }


    /**
     * @param activate
     *            the activate to set
     */
    public void setActivate(String activate) {
        this.activate = activate;
    }

    /**
     * @return the userGroups
     */
    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    /**
     * @param userGroups
     *            the userGroups to set
     */
    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }
	 
}
 
