-- ATH_APP--
----------------
CREATE TABLE `ath_app` (
  `APP_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MAPPING` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `APP_NAME` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`APP_ID`),
  UNIQUE KEY `APP_ID_UNIQUE` (`APP_ID`),
  UNIQUE KEY `MAPPING_UNIQUE` (`MAPPING`),
  UNIQUE KEY `APP_NAME_UNIQUE` (`APP_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ATH_CTRLR--
----------------
CREATE TABLE `ath_ctrlr` (
  `CONTROLLER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MAPPING` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `MODULE_NAME` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `BUSINESS` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Y',
  `APP_ID` int(11) DEFAULT NULL,
  `ACTIVATE` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`CONTROLLER_ID`),
  UNIQUE KEY `CONTROLLER_ID_UNIQUE` (`CONTROLLER_ID`),
  UNIQUE KEY `MAPPING_UNIQUE` (`MAPPING`,`APP_ID`),
  UNIQUE KEY `MODULE_NAME_UNIQUE` (`MODULE_NAME`),
  KEY `FK_APP_ID_idx` (`APP_ID`),
  CONSTRAINT `FK_APP_ID` FOREIGN KEY (`APP_ID`) REFERENCES `ath_app` (`APP_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ATH_OPRN--
----------------
CREATE TABLE `ath_oprn` (
  `CONTROLLER_ID` int(11) NOT NULL,
  `OPERATION_ID` int(11) NOT NULL,
  `NAME` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`CONTROLLER_ID`,`OPERATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ATH_ROL--
----------------
CREATE TABLE `ath_rol` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ROLE_ID`),
  UNIQUE KEY `NAME_UNIQUE` (`NAME`),
  UNIQUE KEY `ROLE_ID_UNIQUE` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ATH_GRP--
----------------
CREATE TABLE `ath_grp` (
  `GROUP_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`GROUP_ID`),
  UNIQUE KEY `NAME_UNIQUE` (`NAME`),
  UNIQUE KEY `GROUP_ID_UNIQUE` (`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ATH_GRP_ROL--
----------------
CREATE TABLE `ath_grp_rol` (
  `GROUP_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  `PRIORITY` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`GROUP_ID`,`ROLE_ID`),
  KEY `FK_ROLE_ID_idx` (`ROLE_ID`),
  CONSTRAINT `FK_GROUP_ID` FOREIGN KEY (`GROUP_ID`) REFERENCES `ath_grp` (`GROUP_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ROLE_ID` FOREIGN KEY (`ROLE_ID`) REFERENCES `ath_rol` (`ROLE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ATH_ASSC_USR--
-------------------
CREATE TABLE `ath_assc_usr` (
  `ASSOCIATE_USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `EMAIL` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PASSWORD` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ACTIVATE` char(1) COLLATE utf8_unicode_ci DEFAULT 'Y',
  PRIMARY KEY (`ASSOCIATE_USER_ID`),
  UNIQUE KEY `ASSOCIATE_USER_ID_UNIQUE` (`ASSOCIATE_USER_ID`),
  UNIQUE KEY `USER_ID_UNIQUE` (`USER_ID`),
  UNIQUE KEY `EMAIL_UNIQUE` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ATH_USR_GRP--
------------------
CREATE TABLE `ath_usr_grp` (
  `USER_ID` int(11) NOT NULL,
  `GROUP_ID` int(11) NOT NULL,
  `PRIORITY` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`USER_ID`,`GROUP_ID`),
  KEY `FK_GROUP_ID_idx` (`GROUP_ID`),
  CONSTRAINT `FK_GROUP_ID2` FOREIGN KEY (`GROUP_ID`) REFERENCES `ath_grp` (`GROUP_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_USER_ID` FOREIGN KEY (`USER_ID`) REFERENCES `ath_assc_usr` (`ASSOCIATE_USER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ATH_ACSS_CTRL_LST--
--------------------------
CREATE TABLE `ath_acss_ctrl_lst` (
  `PRINCIPAL_ID` int(11) NOT NULL,
  `PRINCIPAL_TYPE` int(11) NOT NULL,
  `CONTROLLER_ID` int(11) NOT NULL,
  `ACL_STATE` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`PRINCIPAL_ID`,`PRINCIPAL_TYPE`,`CONTROLLER_ID`),
  KEY `FK_CONTROLLER_ID_idx` (`CONTROLLER_ID`),
  CONSTRAINT `FK_CONTROLLER_ID` FOREIGN KEY (`CONTROLLER_ID`) REFERENCES `ath_ctrlr` (`CONTROLLER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ATH_CNFG--
----------------
CREATE TABLE `ath_cnfg` (
  `KEY` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `VALUE` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`KEY`),
  UNIQUE KEY `KEY_UNIQUE` (`KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ATH_LOG--
----------------
CREATE TABLE `ath_log` (
  `LOG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `APP_ID` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CONTROLLER` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `OPERATION` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NOTES` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `INSERT_DATE` date NOT NULL,
  `RESULT` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `JAUTH_COST` int(11) NOT NULL,
  `OVERALL_COST` int(11) DEFAULT NULL,
  PRIMARY KEY (`LOG_ID`),
  UNIQUE KEY `LOG_ID_UNIQUE` (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Data
 Insert into ATH_ASSC_USR
   (USER_ID, EMAIL, PASSWORD, ACTIVATE)
 Values
   ('AuthAdmin', 'admin@auth.com', '7FEF6171469E80D32C0559F88B377245', 'Y');
   
Insert into ATH_ROL
   (NAME)
 Values
   ('Auth Admin');
   
Insert into ATH_GRP
   (NAME)
 Values
   ('Auth Admin Group');
   
Insert into ATH_GRP_ROL
   (GROUP_ID, ROLE_ID, PRIORITY)
 Values
   (1, 1, 0);
   
Insert into ATH_USR_GRP
   (USER_ID, GROUP_ID, PRIORITY)
 Values
   (1, 1, 0);
   
Insert into ATH_CTRLR
   (MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   ('jauth', 'Auth System', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   ('jauth/user', 'Auth User', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   ('jauth/application', 'Auth Application', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   ('jauth/group', 'Auth Group', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   ('jauth/role', 'Auth Role', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   ('jauth/controller', 'Auth Module', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   ('jauth/operation', 'Auth Operation', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   ('jauth/ACL', 'Auth ACL', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   ('jauth/config', 'Auth Config', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   ('jauth/report', 'Auth Report', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   ('jauth/importer', 'Auth Importer', 'Y', 'N', NULL);
   
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (1, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (1, 1, 'index');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (2, 4, 'save');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (2, 3, 'update');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (2, 2, 'delete');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (2, 1, 'detail');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (2, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (3, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (3, 4, 'save');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (3, 3, 'update');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (3, 2, 'delete');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (3, 1, 'detail');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (4, 4, 'save');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (4, 3, 'update');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (4, 2, 'delete');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (4, 1, 'detail');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (4, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (5, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (5, 4, 'save');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (5, 3, 'update');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (5, 2, 'delete');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (5, 1, 'detail');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (6, 4, 'save');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (6, 3, 'update');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (6, 2, 'delete');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (6, 1, 'detail');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (6, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (7, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (7, 4, 'save');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (7, 3, 'update');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (7, 2, 'delete');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (7, 1, 'detail');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (8, 1, 'operationList');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (8, 2, 'saveOrUpdate');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (8, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (9, 2, 'update');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (9, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (9, 1, 'detail');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (9, 3, 'save');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (10, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (10, 1, 'pieWithModule');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (10, 2, 'trendLineWithAccess');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (11, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (11, 1, 'importWSDL');
   
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (1, 0, 1, 3);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (1, 0, 2, 31);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (1, 0, 3, 31);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (1, 0, 4, 31);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (1, 0, 5, 31);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (1, 0, 6, 31);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (1, 0, 7, 31);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (1, 0, 8, 7);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (1, 0, 9, 15);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (1, 0, 10, 7);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (1, 0, 11, 3);
   
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('authActive', 'Y');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('authorizationCheck', 'Y');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('rules', '*/*/{authO},*/*/*/{authO},*/*/{authO}.*,*/*/*/{authO}.*');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('sessionID', 'USER_ID');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('sessionACL', 'ACL_MAP');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('soapEnabled', 'N');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('protectWSDL', 'N');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('loginPage', '/jauth/login');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('exceptionPage', '/jauth/exception');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('arithmeticIndex', '0');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('accessiblePage', '[\\w\\W]*/jauth/getResource,[\\w\\W]*/jauth/login,[\\w\\W]*/jauth/loginProcess');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('accessibleHost', '127.0.0.1');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('dbLogEnabled', 'N');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('logPath', '/jauth/');
COMMIT;
