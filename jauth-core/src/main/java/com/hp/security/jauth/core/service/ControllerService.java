/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service;

import java.util.List;

import com.hp.security.jauth.core.model.Controller;

/**
 * @author huangyiq
 *
 */
public interface ControllerService {

    void save(Controller controller);

    void update(Controller controller);

    void delete(long controllerId);

    List<Controller> findAll();

    Controller findById(Long controllerId);
    
    Controller findByMapping(String mapping);

}
