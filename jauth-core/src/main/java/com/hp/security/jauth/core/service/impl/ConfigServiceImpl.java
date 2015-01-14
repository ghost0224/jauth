/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.dao.ConfigDao;
import com.hp.security.jauth.core.model.Config;
import com.hp.security.jauth.core.service.ConfigService;


/**
 * @author huangyiq
 *
 */
@Service("authConfigService")
public class ConfigServiceImpl implements ConfigService {

    private ConfigDao configDao;

    /**
     * @return the configDao
     */
    public ConfigDao getConfigDao() {
        return configDao;
    }

    /**
     * @param configDao
     *            the configDao to set
     */
    @Autowired
    public void setConfigDao(ConfigDao configDao) {
        this.configDao = configDao;
    }

    public List<Config> findAll() {
        return configDao.findAll();
    }

    public Config findByKey(String key) {
        return configDao.findByKey(key);
    }

    public void save(Config config) {
        configDao.save(config);
    }

    public void update(Config config) {
        configDao.update(config);
    }

}
