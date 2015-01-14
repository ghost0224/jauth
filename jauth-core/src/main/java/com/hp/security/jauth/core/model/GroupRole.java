package com.hp.security.jauth.core.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonBackReference;

public class GroupRole implements Serializable {
 
	private static final long serialVersionUID = 5273162941311756814L;

	@JsonBackReference
    private Role role;
	 
    private Group group;
	 
	private int priority;


    /**
     * @return the role
     */
    public Role getRole() {
        return role;
    }


    /**
     * @param role
     *            the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }


    /**
     * @return the group
     */
    public Group getGroup() {
        return group;
    }


    /**
     * @param group
     *            the group to set
     */
    public void setGroup(Group group) {
        this.group = group;
    }


    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }


    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
	 
}
 
