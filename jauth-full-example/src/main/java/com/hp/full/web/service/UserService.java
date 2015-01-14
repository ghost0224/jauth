/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.full.web.service;

import com.hp.full.web.model.User;

/**
 * @author huangyiq
 *
 */
public interface UserService {

    User findByEmail(String email, String pwd);

}
