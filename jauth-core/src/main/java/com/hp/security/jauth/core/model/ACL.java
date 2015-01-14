package com.hp.security.jauth.core.model;

import java.io.Serializable;

public class ACL implements Serializable {
 
	private static final long serialVersionUID = -3669712335882594780L;

	private long principalId = 0; /* 'prinsep(e)l */

    private int principalType = 0; //0=role, 1=user

    private long controllerId = 0;
	 
    private long aclState = 0;


    /**
     * @return the principalId
     */
    public long getPrincipalId() {
        return principalId;
    }


    /**
     * @param principalId
     *            the principalId to set
     */
    public void setPrincipalId(long principalId) {
        this.principalId = principalId;
    }


    /**
     * @return the controllerId
     */
    public long getControllerId() {
        return controllerId;
    }


    /**
     * @param controllerId
     *            the controllerId to set
     */
    public void setControllerId(long controllerId) {
        this.controllerId = controllerId;
    }


    /**
     * @return the aclState
     */
    public long getAclState() {
        return aclState;
    }


    /**
     * @param aclState
     *            the aclState to set
     */
    public void setAclState(long aclState) {
        this.aclState = aclState;
    }


    /**
     * @return the principalType
     */
    public int getPrincipalType() {
        return principalType;
    }


    /**
     * @param principalType
     *            the principalType to set
     */
    public void setPrincipalType(int principalType) {
        this.principalType = principalType;
    }


    /**
     * set the current acl's operation authority
     * 
     * @param operationId
     * @param yes
     */
    public void setState(int operationId, boolean yes) {
        int temp = 1;
        temp = temp << operationId;
        if (yes) {
            aclState |= temp;
        } else {
            aclState &= ~temp;
        }
    }

    /**
     * get the current acl's authority by operationId
     * 
     * @param operationId
     * @return
     */
    public boolean getState(int operationId) {
        int temp = 1;
        temp = temp << operationId;
        temp &= aclState;
        return temp != 0;
    }


	@Override
	public String toString() {
		return "principalId: " + principalId + ", principalType: " + principalType + ", controllerId: " + controllerId + ", aclState: " + aclState;
	}
    
}
