


CREATE INDEX myindex ON radiology_record(description) INDEXTYPE IS CTXSYS.CONTEXT;

CREATE INDEX diagnosisIndex ON radiology_record(diagnosis) INDEXTYPE IS CTXSYS.CONTEXT;


SELECT DESC.record_id, DESC.score + DIAG.score + PFIRST.score + PLAST.score AS aggregate_score 
FROM tempDesc DESC, tempDiag DIAG, ..., ... 
WHERE DESC.record_id = DIAG.record_id = ...record_id = ....record_id;
