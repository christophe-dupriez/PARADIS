SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.auditmachine CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.auditnetwork CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.auditgraph CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.auditevent CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.auditgroup CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.namemeasure CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.measure CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.subtype CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.auditscaling CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.namemachine CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.rawscaling CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.audituser CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.graphmachine CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.usergroup CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.scalingline CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.auditalert CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.audittype CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.audit CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.alertMessage CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.nameevent CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.testedmachine CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.machine CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.testcondition CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.namenetwork CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.alert CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.event CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.user CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.rawdata1700 CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.graphtype CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.scaling CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.namegroup CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.nametype CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.namegraph CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.network CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.groupofusers CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.datatype CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

SET FOREIGN_KEY_CHECKS=0
;

DROP TABLE IF EXISTS labodata.graphconfig CASCADE
;

SET FOREIGN_KEY_CHECKS=1
;

CREATE TABLE labodata.graphconfig (id INT NOT NULL, individualmachine BOOL NULL, individualtype BOOL NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.datatype (active BOOL NULL, color TINYINT NULL, color0 TINYINT NULL, color1 TINYINT NULL, color2 TINYINT NULL, color3 TINYINT NULL, color4 TINYINT NULL, color5 TINYINT NULL, color6 TINYINT NULL, color7 TINYINT NULL, color8 TINYINT NULL, color9 TINYINT NULL, defaulttimespan INT NULL, description VARCHAR(127) NULL, id INT NOT NULL, max DOUBLE NULL, min DOUBLE NULL, min1 DOUBLE NULL, min2 DOUBLE NULL, min3 DOUBLE NULL, min4 DOUBLE NULL, min5 DOUBLE NULL, min6 DOUBLE NULL, min7 DOUBLE NULL, min8 DOUBLE NULL, min9 DOUBLE NULL, nbcategories TINYINT NULL, symbol TINYINT NULL, unit VARCHAR(20) NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.groupofusers (active BOOL NULL, email VARCHAR(255) NULL, gsm VARCHAR(23) NULL, id INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.network (basetempo TINYINT NULL, beatgsm SMALLINT NULL, beatmeasure SMALLINT NULL, id INT NOT NULL, sleeptime INT NULL, timeslice SMALLINT NULL, warmup SMALLINT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.namegraph (id INT NOT NULL, language CHAR(2) NULL, name VARCHAR(99) NULL, owner INT NOT NULL, since DATETIME NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.nametype (id INT NOT NULL, language CHAR(2) NULL, name VARCHAR(99) NULL, owner INT NOT NULL, since DATETIME NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.namegroup (id INT NOT NULL, language CHAR(2) NULL, name VARCHAR(99) NULL, owner INT NOT NULL, since DATETIME NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.scaling (description VARCHAR(127) NULL, id INT NOT NULL, unit VARCHAR(20) NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.graphtype (datatype INT NOT NULL, graphconfig INT NOT NULL, id INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.rawdata1700 (aa_xbeeaddr INT NULL, ab_structid TINYINT NULL, ac_timevalidity TINYINT NULL, ad_cpumac INT NULL, ae_timestamp DATETIME NULL, af_slot TINYINT NULL, ag_family TINYINT NULL, ah_sentlan BOOL NULL, ai_sendingwan BOOL NULL, aj_relayedgo BOOL NULL, ak_relayedreturn BOOL NULL, ba_usbnotconnect BOOL NULL, bb_solarpanelok BOOL NULL, bc_powerfilterok BOOL NULL, bd_voltsbattok BOOL NULL, be_lipochargingok BOOL NULL, bf_lipocharging BOOL NULL, bg_xbeeon BOOL NULL, bh_xbeeassoc BOOL NULL, bi_gsmon BOOL NULL, bj_gsmlast_ok BOOL NULL, bk_gsmlastretries BOOL NULL, bl_xbeerssi TINYINT NULL, bm_gsmrssi TINYINT NULL, bn_gsmber TINYINT NULL, bo_channel1ok BOOL NULL, bp_channel2ok BOOL NULL, bq_led1 BOOL NULL, br_dooralwaysclosed BOOL NULL, bs_ledalwaysoff BOOL NULL, ca_waterlevel INT NULL, cb_temperature SMALLINT NULL, cc_amp5v SMALLINT NULL, cd_voltsbatt SMALLINT NULL, ce_voltssol SMALLINT NULL, id INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.user (active BOOL NULL, admin BOOL NULL, dashboard INT NULL, email VARCHAR(255) NOT NULL, gsm VARCHAR(23) NULL, id INT NOT NULL, language CHAR(2) NULL, name VARCHAR(100) NULL, password VARCHAR(32) NULL, sleepbegin TINYINT NULL, sleepend TINYINT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.event (groupofusers INT NULL, hysteresis SMALLINT NULL, id INT NOT NULL, priority TINYINT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.alert (closinguser INT NULL, event INT NULL, id INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.namenetwork (id INT NOT NULL, language CHAR(2) NULL, name VARCHAR(99) NULL, owner INT NOT NULL, since DATETIME NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.testcondition (categorymask INT NULL, datatype INT NOT NULL, event INT NOT NULL, id INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.machine (active BOOL NULL, color TINYINT NULL, description VARCHAR(127) NULL, devicetype TINYINT NULL, gsm VARCHAR(15) NULL, id INT NOT NULL, latitude DOUBLE NULL, longitude DOUBLE NULL, machineid INT NULL, network INT NULL, nodeid VARCHAR(20) NULL, symbol TINYINT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.testedmachine (id INT NOT NULL, machine INT NOT NULL, since DATETIME NULL, testcondition INT NOT NULL, upto DATETIME NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.nameevent (id INT NOT NULL, language CHAR(2) NULL, name VARCHAR(99) NULL, owner INT NOT NULL, since DATETIME NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.alertMessage (alert INT NOT NULL, groupofusers INT NULL, id INT NOT NULL, received DATETIME NULL, sent DATETIME NULL, user INT NULL, via TINYINT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.audit (id INT NOT NULL, via TINYINT NULL, whenchanged DATETIME NOT NULL, whochanged INT NOT NULL, whychanged VARCHAR(99) NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.audittype (audit INT NOT NULL, id INT NOT NULL, whatchanged INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.auditalert (audit INT NOT NULL, id INT NOT NULL, whatchanged INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.scalingline (id INT NOT NULL, max DOUBLE NULL, min DOUBLE NULL, owner INT NOT NULL, rawmax DOUBLE NULL, rawmin DOUBLE NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.usergroup (audit INT NULL, groupofusers INT NOT NULL, id INT NOT NULL, since DATETIME NULL, upto DATETIME NULL, user INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.graphmachine (graphconfig INT NOT NULL, id INT NOT NULL, machine INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.audituser (audit INT NOT NULL, id INT NOT NULL, whatchanged INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.rawscaling (datafield SMALLINT NOT NULL, id INT NOT NULL, machine INT NOT NULL, scaling INT NOT NULL, since DATETIME NULL, upto DATETIME NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.namemachine (id INT NOT NULL, language CHAR(2) NULL, name VARCHAR(99) NULL, owner INT NOT NULL, since DATETIME NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.auditscaling (audit INT NOT NULL, id INT NOT NULL, whatchanged INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.subtype (active BOOL NULL, datatype INT NOT NULL, id INT NOT NULL, machine INT NULL, max DOUBLE NULL, min DOUBLE NULL, min1 DOUBLE NULL, min2 DOUBLE NULL, min3 DOUBLE NULL, min4 DOUBLE NULL, min5 DOUBLE NULL, min6 DOUBLE NULL, min7 DOUBLE NULL, min8 DOUBLE NULL, min9 DOUBLE NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.measure (category TINYINT NULL, datatype INT NULL, id INT NOT NULL, machine INT NULL, rawrecord INT NULL, rawtable TINYINT NULL, value DOUBLE NULL, whenmeasured DATETIME NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.namemeasure (id INT NOT NULL, language CHAR(2) NULL, name VARCHAR(99) NULL, owner INT NOT NULL, since DATETIME NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.auditgroup (audit INT NOT NULL, id INT NOT NULL, whatchanged INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.auditevent (audit INT NOT NULL, id INT NOT NULL, whatchanged INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.auditgraph (audit INT NOT NULL, id INT NOT NULL, whatchanged INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.auditnetwork (audit INT NOT NULL, id INT NOT NULL, whatchanged INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

CREATE TABLE labodata.auditmachine (audit INT NOT NULL, id INT NOT NULL, whatchanged INT NOT NULL, PRIMARY KEY (id)) ENGINE=InnoDB
;

ALTER TABLE labodata.nametype ADD FOREIGN KEY (owner) REFERENCES labodata.datatype (id)
;

ALTER TABLE labodata.namegroup ADD FOREIGN KEY (owner) REFERENCES labodata.groupofusers (id)
;

ALTER TABLE labodata.graphtype ADD FOREIGN KEY (datatype) REFERENCES labodata.datatype (id)
;

ALTER TABLE labodata.graphtype ADD FOREIGN KEY (graphconfig) REFERENCES labodata.graphconfig (id)
;

ALTER TABLE labodata.user ADD FOREIGN KEY (dashboard) REFERENCES labodata.graphconfig (id)
;

ALTER TABLE labodata.event ADD FOREIGN KEY (groupofusers) REFERENCES labodata.groupofusers (id)
;

ALTER TABLE labodata.alert ADD FOREIGN KEY (event) REFERENCES labodata.event (id)
;

ALTER TABLE labodata.alert ADD FOREIGN KEY (closinguser) REFERENCES labodata.user (id)
;

ALTER TABLE labodata.namenetwork ADD FOREIGN KEY (owner) REFERENCES labodata.network (id)
;

ALTER TABLE labodata.testcondition ADD FOREIGN KEY (event) REFERENCES labodata.event (id)
;

ALTER TABLE labodata.testcondition ADD FOREIGN KEY (datatype) REFERENCES labodata.datatype (id)
;

ALTER TABLE labodata.machine ADD FOREIGN KEY (network) REFERENCES labodata.network (id)
;

ALTER TABLE labodata.testedmachine ADD FOREIGN KEY (testcondition) REFERENCES labodata.testcondition (id)
;

ALTER TABLE labodata.testedmachine ADD FOREIGN KEY (machine) REFERENCES labodata.machine (id)
;

ALTER TABLE labodata.nameevent ADD FOREIGN KEY (owner) REFERENCES labodata.event (id)
;

ALTER TABLE labodata.alertMessage ADD FOREIGN KEY (groupofusers) REFERENCES labodata.groupofusers (id)
;

ALTER TABLE labodata.alertMessage ADD FOREIGN KEY (user) REFERENCES labodata.user (id)
;

ALTER TABLE labodata.alertMessage ADD FOREIGN KEY (alert) REFERENCES labodata.alert (id)
;

ALTER TABLE labodata.audit ADD FOREIGN KEY (whochanged) REFERENCES labodata.user (id)
;

ALTER TABLE labodata.audittype ADD FOREIGN KEY (whatchanged) REFERENCES labodata.datatype (id)
;

ALTER TABLE labodata.audittype ADD FOREIGN KEY (audit) REFERENCES labodata.audit (id)
;

ALTER TABLE labodata.auditalert ADD FOREIGN KEY (audit) REFERENCES labodata.audit (id)
;

ALTER TABLE labodata.auditalert ADD FOREIGN KEY (whatchanged) REFERENCES labodata.alert (id)
;

ALTER TABLE labodata.scalingline ADD FOREIGN KEY (owner) REFERENCES labodata.scaling (id)
;

ALTER TABLE labodata.usergroup ADD FOREIGN KEY (user) REFERENCES labodata.user (id)
;

ALTER TABLE labodata.usergroup ADD FOREIGN KEY (groupofusers) REFERENCES labodata.groupofusers (id)
;

ALTER TABLE labodata.graphmachine ADD FOREIGN KEY (machine) REFERENCES labodata.machine (id)
;

ALTER TABLE labodata.graphmachine ADD FOREIGN KEY (graphconfig) REFERENCES labodata.graphconfig (id)
;

ALTER TABLE labodata.audituser ADD FOREIGN KEY (whatchanged) REFERENCES labodata.user (id)
;

ALTER TABLE labodata.audituser ADD FOREIGN KEY (audit) REFERENCES labodata.audit (id)
;

ALTER TABLE labodata.rawscaling ADD FOREIGN KEY (scaling) REFERENCES labodata.scaling (id)
;

ALTER TABLE labodata.rawscaling ADD FOREIGN KEY (machine) REFERENCES labodata.machine (id)
;

ALTER TABLE labodata.namemachine ADD FOREIGN KEY (owner) REFERENCES labodata.machine (id)
;

ALTER TABLE labodata.auditscaling ADD FOREIGN KEY (whatchanged) REFERENCES labodata.scaling (id)
;

ALTER TABLE labodata.auditscaling ADD FOREIGN KEY (audit) REFERENCES labodata.audit (id)
;

ALTER TABLE labodata.subtype ADD FOREIGN KEY (datatype) REFERENCES labodata.datatype (id)
;

ALTER TABLE labodata.subtype ADD FOREIGN KEY (machine) REFERENCES labodata.machine (id)
;

ALTER TABLE labodata.measure ADD FOREIGN KEY (machine) REFERENCES labodata.machine (id)
;

ALTER TABLE labodata.measure ADD FOREIGN KEY (datatype) REFERENCES labodata.datatype (id)
;

ALTER TABLE labodata.namemeasure ADD FOREIGN KEY (owner) REFERENCES labodata.measure (id)
;

ALTER TABLE labodata.auditgroup ADD FOREIGN KEY (whatchanged) REFERENCES labodata.groupofusers (id)
;

ALTER TABLE labodata.auditgroup ADD FOREIGN KEY (audit) REFERENCES labodata.audit (id)
;

ALTER TABLE labodata.auditevent ADD FOREIGN KEY (whatchanged) REFERENCES labodata.event (id)
;

ALTER TABLE labodata.auditevent ADD FOREIGN KEY (audit) REFERENCES labodata.audit (id)
;

ALTER TABLE labodata.auditgraph ADD FOREIGN KEY (whatchanged) REFERENCES labodata.graphconfig (id)
;

ALTER TABLE labodata.auditgraph ADD FOREIGN KEY (audit) REFERENCES labodata.audit (id)
;

ALTER TABLE labodata.auditnetwork ADD FOREIGN KEY (whatchanged) REFERENCES labodata.network (id)
;

ALTER TABLE labodata.auditnetwork ADD FOREIGN KEY (audit) REFERENCES labodata.audit (id)
;

ALTER TABLE labodata.auditmachine ADD FOREIGN KEY (whatchanged) REFERENCES labodata.machine (id)
;

ALTER TABLE labodata.auditmachine ADD FOREIGN KEY (audit) REFERENCES labodata.audit (id)
;

DROP TABLE IF EXISTS AUTO_PK_SUPPORT
;

CREATE TABLE AUTO_PK_SUPPORT (  TABLE_NAME CHAR(100) NOT NULL,  NEXT_ID BIGINT NOT NULL, UNIQUE (TABLE_NAME))
;

DELETE FROM AUTO_PK_SUPPORT WHERE TABLE_NAME IN ('alert', 'alertMessage', 'audit', 'auditalert', 'auditevent', 'auditgraph', 'auditgroup', 'auditmachine', 'auditnetwork', 'auditscaling', 'audittype', 'audituser', 'datatype', 'event', 'graphconfig', 'graphmachine', 'graphtype', 'groupofusers', 'machine', 'measure', 'nameevent', 'namegraph', 'namegroup', 'namemachine', 'namemeasure', 'namenetwork', 'nametype', 'network', 'rawdata1700', 'rawscaling', 'scaling', 'scalingline', 'subtype', 'testcondition', 'testedmachine', 'user', 'usergroup')
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('alert', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('alertMessage', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('audit', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('auditalert', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('auditevent', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('auditgraph', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('auditgroup', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('auditmachine', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('auditnetwork', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('auditscaling', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('audittype', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('audituser', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('datatype', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('event', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('graphconfig', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('graphmachine', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('graphtype', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('groupofusers', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('machine', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('measure', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('nameevent', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('namegraph', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('namegroup', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('namemachine', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('namemeasure', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('namenetwork', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('nametype', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('network', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('rawdata1700', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('rawscaling', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('scaling', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('scalingline', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('subtype', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('testcondition', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('testedmachine', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('user', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('usergroup', 200)
;

