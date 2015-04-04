INSERT INTO persons VALUES(00001, 'John A.', 'Macdonald', '24 Sussex Drive', 'JMac@gc.ca', '6135550294');
INSERT INTO users VALUES('admin', 'password', 'a', 00001, TO_DATE('18150111', 'YYYYMMDD'));

CREATE INDEX descriptionIndex ON radiology_record(description) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX diagnosisIndex ON radiology_record(diagnosis) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX fnameIndex ON persons(first_name) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX lnameIndex ON persons(last_name) INDEXTYPE IS CTXSYS.CONTEXT;
