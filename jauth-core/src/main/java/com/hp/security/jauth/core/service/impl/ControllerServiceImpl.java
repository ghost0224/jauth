/**
 * Copyright 2010 Hewlett-Packard. All rights reserved. <br>
 * HP Confidential. Use is subject to license terms.
 */
package com.hp.security.jauth.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.dao.ACLDao;
import com.hp.security.jauth.core.dao.ControllerDao;
import com.hp.security.jauth.core.dao.OperationDao;
import com.hp.security.jauth.core.model.Controller;
import com.hp.security.jauth.core.model.Operation;
import com.hp.security.jauth.core.service.ControllerService;
import com.hp.security.jauth.core.util.Constants;


/**
 * @author huangyiq
 *
 */
@Service("authControllerService")
public class ControllerServiceImpl implements ControllerService {

    private ControllerDao controllerDao;
    private OperationDao operationDao;
    private ACLDao aclDao;

    /**
     * @param controllerDao
     *            the controllerDao to set
     */
    @Autowired
    public void setControllerDao(ControllerDao controllerDao) {
        this.controllerDao = controllerDao;
    }

    /**
     * @param operationDao
     *            the operationDao to set
     */
    @Autowired
    public void setOperationDao(OperationDao operationDao) {
        this.operationDao = operationDao;
    }

    /**
     * @param aclDao
     *            the aclDao to set
     */
    @Autowired
    public void setAclDao(ACLDao aclDao) {
        this.aclDao = aclDao;
    }

    public void save(Controller controller) {
        controllerDao.save(controller);
        Operation operation = new Operation();
        operation.setController(controller);
        operation.setName(Constants.DEFAULT_OPERATION);
        operationDao.save(operation);
    }

    public List<Controller> findAll() {
        return controllerDao.findAll();
    }

    public Controller findById(long controllerId) {
        return controllerDao.findById(controllerId);
    }

    public void update(Controller controller) {
        controllerDao.update(controller);
    }

    public void delete(long controllerId) {
        aclDao.deleteByController(controllerId);
        operationDao.deleteByControllerId(controllerId);
        controllerDao.delete(controllerId);
    }

    public Controller findById(Long controllerId) {
        return controllerDao.findById(controllerId);
    }

	public Controller findByMapping(String mapping) {
		return controllerDao.findByMapping(mapping);
	}

}
