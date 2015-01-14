---- ATH_APP -----
----------------------------------------------------------
CREATE TABLE ATH_APP
(
  APP_ID  	  NUMBER                         NOT NULL,
  MAPPING     VARCHAR2(100 BYTE)             NOT NULL,
  APP_NAME    VARCHAR2(100 BYTE)             NOT NULL
);
ALTER TABLE ATH_APP ADD (CONSTRAINT ATH_APP_PK PRIMARY KEY (APP_ID), CONSTRAINT ATH_APP_AK UNIQUE (MAPPING));

---- ATH_CTRLR -----
-----------------------------------------------------------
CREATE TABLE ATH_CTRLR
(
  CONTROLLER_ID  NUMBER                         NOT NULL,
  MAPPING        VARCHAR2(100 BYTE)             NOT NULL,
  ACTIVATE       CHAR(1 BYTE)                   DEFAULT 'Y'                   NOT NULL,
  MODULE_NAME    VARCHAR2(100 BYTE)             NOT NULL,
  BUSINESS       CHAR(1 BYTE)                   DEFAULT 'Y'                   NOT NULL,
  APP_ID		 NUMBER
);
ALTER TABLE ATH_CTRLR ADD (CONSTRAINT ATH_CTRLR_PK PRIMARY KEY (CONTROLLER_ID));
ALTER TABLE ATH_CTRLR ADD (CONSTRAINT ATH_CTRLR_AK UNIQUE (MAPPING, APP_ID));
ALTER TABLE ATH_CTRLR ADD (CONSTRAINT ATH_CTRLR_FK FOREIGN KEY (APP_ID) REFERENCES ATH_APP (APP_ID));

---- ATH_OPRN -----
-----------------------------------------------------------
CREATE TABLE ATH_OPRN
(
  CONTROLLER_ID  NUMBER                         NOT NULL,
  OPERATION_ID   NUMBER                         NOT NULL,
  NAME           VARCHAR2(100 BYTE)             NOT NULL
);
ALTER TABLE ATH_OPRN ADD (CONSTRAINT ATH_OPRN_PK PRIMARY KEY (CONTROLLER_ID, OPERATION_ID));

---- ATH_ASSC_USR -----
----------------------------------------------------------
CREATE TABLE ATH_ASSC_USR
(
  ASSOCIATE_USER_ID  NUMBER                     NOT NULL,
  USER_ID            VARCHAR2(100 BYTE)         NOT NULL,
  EMAIL              VARCHAR2(100 BYTE),
  PASSWORD           VARCHAR2(50 BYTE),
  ACTIVATE           CHAR(1 BYTE)               DEFAULT 'Y'                   NOT NULL
);
ALTER TABLE ATH_ASSC_USR ADD (CONSTRAINT ATH_ASSC_USR_PK PRIMARY KEY (ASSOCIATE_USER_ID), CONSTRAINT ATH_ASSC_USR_AK UNIQUE (USER_ID));

---- ATH_ROL -----
-----------------------------------------------------------
CREATE TABLE ATH_ROL
(
  ROLE_ID  NUMBER                               NOT NULL,
  NAME     VARCHAR2(100 BYTE)                   NOT NULL
);

ALTER TABLE ATH_ROL ADD (CONSTRAINT ATH_ROL_PK PRIMARY KEY (ROLE_ID), CONSTRAINT ATH_ROL_AK UNIQUE (NAME));

---- ATH_GRP -----
-----------------------------------------------------------
CREATE TABLE ATH_GRP
(
  GROUP_ID  NUMBER                              NOT NULL,
  NAME      VARCHAR2(100 BYTE)                  NOT NULL
);
ALTER TABLE ATH_GRP ADD (CONSTRAINT ATH_GRP_PK PRIMARY KEY (GROUP_ID), CONSTRAINT ATH_GRP_AK2 UNIQUE (NAME));

---- ATH_GRP_ROL -----
-----------------------------------------------------------
CREATE TABLE ATH_GRP_ROL
(
  GROUP_ID  NUMBER                              NOT NULL,
  ROLE_ID   NUMBER                              NOT NULL,
  PRIORITY  NUMBER                              DEFAULT 0                     NOT NULL
);
ALTER TABLE ATH_GRP_ROL ADD (CONSTRAINT ATH_GRP_ROL_PK PRIMARY KEY(GROUP_ID, ROLE_ID));
ALTER TABLE ATH_GRP_ROL ADD (CONSTRAINT ATH_GRP_ROL_FK FOREIGN KEY (GROUP_ID) REFERENCES ATH_GRP (GROUP_ID));
ALTER TABLE ATH_GRP_ROL ADD (CONSTRAINT ATH_GRP_ROL_FK2 FOREIGN KEY (ROLE_ID) REFERENCES ATH_ROL (ROLE_ID));

---- ATH_USR_GRP -----
-----------------------------------------------------------
CREATE TABLE ATH_USR_GRP
(
  USER_ID   NUMBER                              NOT NULL,
  GROUP_ID  NUMBER                              NOT NULL,
  PRIORITY  NUMBER                              DEFAULT 0                     NOT NULL
);
ALTER TABLE ATH_USR_GRP ADD (CONSTRAINT ATH_USR_GRP_PK PRIMARY KEY (USER_ID, GROUP_ID));
ALTER TABLE ATH_USR_GRP ADD (CONSTRAINT ATH_USR_GRP_FK FOREIGN KEY (GROUP_ID) REFERENCES ATH_GRP (GROUP_ID));
ALTER TABLE ATH_USR_GRP ADD (CONSTRAINT ATH_USR_GRP_FK2 FOREIGN KEY (USER_ID) REFERENCES ATH_ASSC_USR (ASSOCIATE_USER_ID));

---- ATH_ACSS_CTRL_LST -----
-----------------------------------------------------------
CREATE TABLE ATH_ACSS_CTRL_LST
(
  PRINCIPAL_ID    NUMBER                        NOT NULL,
  PRINCIPAL_TYPE  NUMBER                        NOT NULL,
  CONTROLLER_ID   NUMBER                        NOT NULL,
  ACL_STATE       NUMBER                        DEFAULT 0                     NOT NULL
);
ALTER TABLE ATH_ACSS_CTRL_LST ADD (CONSTRAINT ATH_ACSS_CTRL_LST_PK PRIMARY KEY (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID));
ALTER TABLE ATH_ACSS_CTRL_LST ADD (CONSTRAINT ATH_ACSS_CTRL_LST_FK FOREIGN KEY (CONTROLLER_ID) REFERENCES ATH_CTRLR (CONTROLLER_ID));

---- ATH_CNFG -----
-----------------------------------------------------------
CREATE TABLE ATH_CNFG
(
  KEY    VARCHAR2(100 BYTE)                     NOT NULL,
  VALUE  VARCHAR2(255 BYTE)                     DEFAULT ''
);
ALTER TABLE ATH_CNFG ADD (CONSTRAINT ATH_CNFG_PK PRIMARY KEY (KEY));

---- ATH_LOG -----
-----------------------------------------------------------
CREATE TABLE ATH_LOG
(
  LOG_ID        NUMBER                          NOT NULL,
  USER_ID       VARCHAR2(100 BYTE)              NOT NULL,
  APP_ID        VARCHAR2(100 BYTE),
  CONTROLLER    VARCHAR2(100 BYTE),
  OPERATION     VARCHAR2(100 BYTE),
  NOTES     	VARCHAR2(255 BYTE),
  INSERT_DATE   DATE                            NOT NULL,
  RESULT        VARCHAR2(50 BYTE)               NOT NULL,
  JAUTH_COST     NUMBER                         NOT NULL,
  OVERALL_COST  NUMBER
);
ALTER TABLE ATH_LOG ADD (CONSTRAINT ATH_LOG_PK PRIMARY KEY (LOG_ID));

--Sequence 
CREATE SEQUENCE ATH_APP_SEQ START WITH 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1;

CREATE SEQUENCE ATH_CTRLR_SEQ START WITH 100 MAXVALUE 9999999999999999999999999999 MINVALUE 100;

CREATE SEQUENCE ATH_USR_SEQ START WITH 100 MAXVALUE 9999999999999999999999999999 MINVALUE 100;

CREATE SEQUENCE ATH_ROL_SEQ START WITH 100 MAXVALUE 9999999999999999999999999999 MINVALUE 100;

CREATE SEQUENCE ATH_GRP_SEQ START WITH 100 MAXVALUE 9999999999999999999999999999 MINVALUE 100;

CREATE SEQUENCE ATH_LOG_SEQ START WITH 1 MAXVALUE 9999999999999999999999999999 MINVALUE 1;
  
--Data
  Insert into ATH_ASSC_USR
   (ASSOCIATE_USER_ID, USER_ID, EMAIL, PASSWORD, ACTIVATE)
 Values
   (0, 'AuthAdmin', 'admin@auth.com', '7FEF6171469E80D32C0559F88B377245', 'Y');
   
Insert into ATH_ROL
   (ROLE_ID, NAME)
 Values
   (0, 'Auth Admin');
   
Insert into ATH_GRP
   (GROUP_ID, NAME)
 Values
   (0, 'Auth Admin Group');
   
Insert into ATH_GRP_ROL
   (GROUP_ID, ROLE_ID, PRIORITY)
 Values
   (0, 0, 0);
   
Insert into ATH_USR_GRP
   (USER_ID, GROUP_ID, PRIORITY)
 Values
   (0, 0, 0);
   
Insert into ATH_CTRLR
   (CONTROLLER_ID, MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   (0, 'jauth', 'Auth System', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (CONTROLLER_ID, MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   (1, 'jauth/user', 'Auth User', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (CONTROLLER_ID, MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   (2, 'jauth/application', 'Auth Application', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (CONTROLLER_ID, MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   (3, 'jauth/group', 'Auth Group', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (CONTROLLER_ID, MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   (4, 'jauth/role', 'Auth Role', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (CONTROLLER_ID, MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   (5, 'jauth/controller', 'Auth Module', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (CONTROLLER_ID, MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   (6, 'jauth/operation', 'Auth Operation', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (CONTROLLER_ID, MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   (7, 'jauth/ACL', 'Auth ACL', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (CONTROLLER_ID, MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   (8, 'jauth/config', 'Auth Config', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (CONTROLLER_ID, MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   (9, 'jauth/report', 'Auth Report', 'Y', 'N', NULL);
Insert into ATH_CTRLR
   (CONTROLLER_ID, MAPPING, MODULE_NAME, ACTIVATE, BUSINESS, APP_ID)
 Values
   (10, 'jauth/importer', 'Auth Importer', 'Y', 'N', NULL);
   
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (0, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (0, 1, 'index');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (1, 4, 'save');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (1, 3, 'update');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (1, 2, 'delete');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (1, 1, 'detail');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (1, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (2, 0, 'view');
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
   (3, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (4, 0, 'view');
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
   (5, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (6, 0, 'view');
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
   (7, 1, 'operationList');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (7, 2, 'saveOrUpdate');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (7, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (8, 2, 'update');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (8, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (8, 1, 'detail');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (8, 3, 'save');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (9, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (9, 1, 'pieWithModule');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (9, 2, 'trendLineWithAccess');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (10, 0, 'view');
Insert into ATH_OPRN
   (CONTROLLER_ID, OPERATION_ID, NAME)
 Values
   (10, 1, 'importWSDL');
   
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (0, 0, 0, 3);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (0, 0, 1, 31);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (0, 0, 2, 31);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (0, 0, 3, 31);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (0, 0, 4, 31);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (0, 0, 5, 31);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (0, 0, 6, 31);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (0, 0, 7, 7);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (0, 0, 8, 15);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (0, 0, 9, 7);
Insert into ATH_ACSS_CTRL_LST
   (PRINCIPAL_ID, PRINCIPAL_TYPE, CONTROLLER_ID, ACL_STATE)
 Values
   (0, 0, 10, 3);
   
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('authActive', 'Y');
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('authorizationCheck', 'Y');
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('rules', '*/*/{authO},*/*/*/{authO},*/*/{authO}.*,*/*/*/{authO}.*');
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('sessionID', 'USER_ID');
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('sessionACL', 'ACL_MAP');
Insert into ATH_CNFG
   (`KEY`, VALUE)
 Values
   ('soapEnabled', 'N');
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('protectWSDL', 'N');
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('loginPage', '/jauth/login');
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('exceptionPage', '/jauth/exception');
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('arithmeticIndex', '0');
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('accessiblePage', '[\\w\\W]*/jauth/getResource,[\\w\\W]*/jauth/login,[\\w\\W]*/jauth/loginProcess');
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('accessibleHost', '127.0.0.1');
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('dbLogEnabled', 'N');
Insert into ATH_CNFG
   (KEY, VALUE)
 Values
   ('logPath', '/jauth/');
COMMIT;
