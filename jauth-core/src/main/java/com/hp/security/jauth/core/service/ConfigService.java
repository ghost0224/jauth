/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service;

import java.util.List;

import com.hp.security.jauth.core.model.Config;

/**
 * @author huangyiq
 *
 */
public interface ConfigService {

    List<Config> findAll();

    Config findByKey(String key);

    void save(Config config);

    void update(Config config);

}
