/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.wms.dao.impl;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Repository;

import com.hp.wms.dao.UserDao;
import com.hp.wms.model.User;

/**
 * @author huangyiq
 *
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {
	
	static Properties prop = new Properties();
	
	static {
		ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();
		Resource r = resolver.getResource("users.properties");
		try {
			prop.load(r.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public User findByEmail(String email, String pwd) {
    	if(prop.get(email).equals(pwd)) {
    		User u = new User();
    		u.setEmail(email);
    		u.setUserName(email);
    		return u;
    	}
    	return null;
    }

}
