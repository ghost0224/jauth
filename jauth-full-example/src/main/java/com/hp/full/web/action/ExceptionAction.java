/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.full.web.action;

import com.hp.full.web.constant.Constant;


/**
 * @author huangyiq
 *
 */
public class ExceptionAction extends BaseAction {

	public String execute() {
		System.out.println("ex");
    	return Constant.DONE_PAGE;
    }

}
