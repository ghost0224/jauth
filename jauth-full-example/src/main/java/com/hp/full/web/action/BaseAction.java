package com.hp.full.web.action;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Class BaseAction.java
 * 
 * @author Ethan Huang (yiqingh@hp.com)
 * @since Aug 7, 2012
 */
public class BaseAction extends ActionSupport implements SessionAware, ApplicationAware, ServletRequestAware {

    /** serialVersionUID */
    private static final long serialVersionUID = 3925678722522497889L;
    private Map<String, Object> session = new HashMap<String, Object>();
    private Map<String, Object> application = new HashMap<String, Object>();
    private HttpServletRequest request;
    
    public String execute() {
        return SUCCESS;
    }
    
    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public Map<String, Object> getApplication() {
        return application;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setApplication(Map<String, Object> application) {
        this.application = application;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}
