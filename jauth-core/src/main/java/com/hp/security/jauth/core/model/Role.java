package com.hp.security.jauth.core.model;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {
 
	private static final long serialVersionUID = -907015022987930310L;

	private long roleId;
	 
	private String name;

    private List<GroupRole> roleGroups;

    /**
     * @return the roleId
     */
    public long getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     *            the roleId to set
     */
    public void setRoleId(long roleId) {
        this.roleId = roleId;
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
     * @return the roleGroups
     */
    public List<GroupRole> getRoleGroups() {
        return roleGroups;
    }

    /**
     * @param roleGroups
     *            the roleGroups to set
     */
    public void setRoleGroups(List<GroupRole> roleGroups) {
        this.roleGroups = roleGroups;
    }
	 
}
 
