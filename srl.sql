drop INDEX myindex;
drop INDEX diagnosisIndex;
drop INDEX pfirstindex;
drop INDEX plastindex;
drop VIEW tempDesc;
drop VIEW tempDiag;
drop VIEW tempPfirst;
drop VIEW tempPlast;

CREATE INDEX myindex ON radiology_record(description) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX diagnosisIndex ON radiology_record(diagnosis) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX pfirstindex ON persons(first_name) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX plastindex ON persons(last_name) INDEXTYPE IS CTXSYS.CONTEXT;

CREATE OR REPLACE VIEW tempDesc AS SELECT score(1) * 3 as score, record_id FROM radiology_record WHERE contains(description, 'test', 1) >= 0;
CREATE OR REPLACE VIEW tempDiag AS SELECT score(1) * 3 as score, record_id FROM radiology_record WHERE contains(diagnosis, 'test', 1) >= 0;

CREATE OR REPLACE VIEW tempPfirst AS SELECT score(1) * 3 as score, record_id FROM persons P, radiology_record R WHERE R.patient_id = P.person_id 
AND contains(P.first_name, 'test', 1) >= 0;

CREATE OR REPLACE VIEW tempPlast AS SELECT score(1) * 3 as score, record_id FROM persons P, radiology_record R WHERE R.patient_id = P.person_id 
AND contains(P.last_name, 'test', 1) >= 0;

SELECT DE.record_id AS r_id, DE.score + DI.score + PF.score + PL.score AS aggregate_score 
FROM tempDesc DE, tempDiag DI, tempPlast PL, tempPfirst PF
WHERE DE.record_id = DI.record_id 
AND DE.record_id = PL.record_id 
AND DE.record_id = PF.record_id;
