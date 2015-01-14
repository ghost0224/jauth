/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.dao;

import java.util.List;

import com.hp.security.jauth.core.model.Controller;

/**
 * @author huangyiq
 *
 */
public interface ControllerDao {

    Controller findById(long controllerId);

    void save(Controller controller);

    List<Controller> findAll();

    void update(Controller controller);

    void delete(long controllerId);

	Controller findByMapping(String mapping);

}
