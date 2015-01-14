/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.wms.dao;

import com.hp.wms.model.User;

/**
 * @author huangyiq
 *
 */
public interface UserDao {

    User findByEmail(String email, String pwd);

}
