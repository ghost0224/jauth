package com.hp.security.jauth.core.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonBackReference;

public class UserGroup implements Serializable {

	private static final long serialVersionUID = -3380348675847772579L;

	@JsonBackReference
    private AssociateUser user;
	 
    private Group group;
	 
	private int priority;


    /**
     * @return the user
     */
    public AssociateUser getUser() {
        return user;
    }


    /**
     * @param user
     *            the user to set
     */
    public void setUser(AssociateUser user) {
        this.user = user;
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
 
