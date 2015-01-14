/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.wms.action;

import com.hp.wms.constant.Constant;


/**
 * @author huangyiq
 *
 */
public class ExceptionAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		System.out.println("ex");
    	return Constant.DONE_PAGE;
    }

}
