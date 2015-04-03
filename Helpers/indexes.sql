


CREATE INDEX myindex ON radiology_record(description) INDEXTYPE IS CTXSYS.CONTEXT;

CREATE INDEX diagnosisIndex ON radiology_record(diagnosis) INDEXTYPE IS CTXSYS.CONTEXT;


