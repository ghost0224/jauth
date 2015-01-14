package com.hp.security.jauth.admin.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.security.jauth.core.model.ACL;
import com.hp.security.jauth.core.model.Controller;
import com.hp.security.jauth.core.model.Operation;
import com.hp.security.jauth.core.model.Role;
import com.hp.security.jauth.core.service.ACLService;
import com.hp.security.jauth.core.service.ControllerService;
import com.hp.security.jauth.core.service.RoleService;

@Service
public class AdminUtil {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	private RoleService roleService;
	private ACLService aclService;
	private ControllerService controllerService;
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Autowired
	public void setAclService(ACLService aclService) {
		this.aclService = aclService;
	}

	@Autowired
	public void setControllerService(ControllerService controllerService) {
		this.controllerService = controllerService;
	}

	public List<String> setAllPermission(int roleId) {
		List<String> result = new ArrayList<String>();
		Role role = roleService.findById(roleId);
		List<Controller> controllers = controllerService.findAll();
		logger.info("all controllers amount: " + controllers.size());
		for(Controller controller : controllers) {
			ACL acl = new ACL();
			acl.setAclState(0);
			acl.setControllerId(controller.getControllerId());
			acl.setPrincipalId(role.getRoleId());
			acl.setPrincipalType(0);
			List<Operation> operations = controller.getOperations();
			for(Operation operation : operations) {
				acl.setState(operation.getOperationId(), true);
			}
			aclService.saveOrUpdate(acl);
			logger.info("update: " + acl);
		}
		return result;
	}
	
}
