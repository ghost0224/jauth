/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.wms.interceptor;

import java.util.Map;

import com.hp.wms.constant.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author huangyiq
 *
 */
public class SecurityInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     * @see
     * com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2
     * .ActionInvocation)
     */
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext ctx = invocation.getInvocationContext();
        if (!"login".equals(ctx.getName())) {
            Map<String, Object> session = ctx.getSession();
            Object user = session.get(Constant.LOGIN_USER);
            if (null == user) {
                return "loginPage";
            }
        }
        return invocation.invoke();
    }

}
