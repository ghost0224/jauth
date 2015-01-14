package com.hp.security.jauth.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Controller implements Serializable {
 
	private static final long serialVersionUID = -227385379897567474L;

	private long controllerId;
	 
	private String mapping;
	 
    private String moduleName;
	 
    private String activate;
    
    private String business;
    
    private Application application;

    private List<Operation> operations = new ArrayList<Operation>();


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
     * @return the mapping
     */
    public String getMapping() {
        return mapping;
    }

    /**
     * @param mapping
     *            the mapping to set
     */
    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    /**
     * @return the moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName
     *            the moduleName to set
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
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
     * @return the operations
     */
    public List<Operation> getOperations() {
        return operations;
    }

    /**
     * @param operations
     *            the operations to set
     */
    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}
	
	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	
}