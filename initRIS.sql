INSERT INTO persons VALUES(00001, 'John A.', 'Macdonald', '24 Sussex Drive', 'JMac@gc.ca', '6135550294');
INSERT INTO users VALUES('admin', 'password', 'a', 00001, TO_DATE('18150111', 'YYYYMMDD'));

CREATE INDEX descriptionIndex ON radiology_record(description) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX diagnosisIndex ON radiology_record(diagnosis) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX fnameIndex ON persons(first_name) INDEXTYPE IS CTXSYS.CONTEXT;
CREATE INDEX lnameIndex ON persons(last_name) INDEXTYPE IS CTXSYS.CONTEXT;



set serveroutput on
declare
  job number;
  job2 number;
  job3 number;
  job4 number;
begin
  dbms_job.submit(job, 'ctx_ddl.sync_index(''descriptionIndex'');',
                  interval=>'SYSDATE+2/1440');
  dbms_job.submit(job2, 'ctx_ddl.sync_index(''diagnosisIndex'');',
                  interval=>'SYSDATE+2/1440');
  dbms_job.submit(job3, 'ctx_ddl.sync_index(''fnameIndex'');',
                  interval=>'SYSDATE+2/1440');
  dbms_job.submit(job4, 'ctx_ddl.sync_index(''lnameIndex'');',
                  interval=>'SYSDATE+2/1440');
  commit;
  dbms_output.put_line('job '||job||' has been submitted.');
  dbms_output.put_line('job '||job2||' has been submitted.');
  dbms_output.put_line('job '||job3||' has been submitted.');
  dbms_output.put_line('job '||job4||' has been submitted.');
end;
/
