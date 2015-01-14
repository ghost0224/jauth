/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.dao;

import java.util.List;

import com.hp.security.jauth.core.model.Config;

/**
 * @author huangyiq
 *
 */
public interface ConfigDao {

    void save(Config config);

    void update(Config config);

    List<Config> findAll();

    Config findByKey(String key);

}
