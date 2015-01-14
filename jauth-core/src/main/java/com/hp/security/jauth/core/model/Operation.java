package com.hp.security.jauth.core.model;

import java.io.Serializable;

public class Operation implements Serializable {
 
	private static final long serialVersionUID = 6732849282379784618L;

	private int operationId;
	 
	private String name;

    private Controller controller;

    /**
     * @return the operationId
     */
    public int getOperationId() {
        return operationId;
    }

    /**
     * @param operationId
     *            the operationId to set
     */
    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }


    /**
     * @return the controller
     */
    public Controller getController() {
        return controller;
    }


    /**
     * @param controller
     *            the controller to set
     */
    public void setController(Controller controller) {
        this.controller = controller;
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
	 
	 
}
 
