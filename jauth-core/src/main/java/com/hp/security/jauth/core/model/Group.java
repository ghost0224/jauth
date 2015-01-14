package com.hp.security.jauth.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonBackReference;

public class Group implements Serializable {
 
	private static final long serialVersionUID = -8753973506283024366L;

	private long groupId;
	 
	private String name;
	
	@JsonBackReference
    private List<GroupRole> groupRoles = new ArrayList<GroupRole>();
	
	@JsonBackReference
    private List<UserGroup> userGroups = new ArrayList<UserGroup>();

    /**
     * @return the groupId
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     *            the groupId to set
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the groupRoles
     */
    public List<GroupRole> getGroupRoles() {
        return groupRoles;
    }


    /**
     * @param groupRoles
     *            the groupRoles to set
     */
    public void setGroupRoles(List<GroupRole> groupRoles) {
        this.groupRoles = groupRoles;
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
 
