REM 
Rem Copyright (c) 1999, 2001, Oracle Corporation.  All rights reserved.  
Rem
Rem    NAME
Rem      drjobdml.sql - DR dbms_JOB DML script
Rem
Rem    NOTES
Rem      This is a script which demonstrates how to submit a DBMS_JOB 
Rem      which will keep a context index up-to-date.
Rem
Rem      Before running this script, please ensure that your database
Rem      is set up to run dbms_jobs.  job_queue_processes must be set
Rem      (and non-zero) in init.ora.  See Administrator's Guide for more
Rem      information.
Rem
Rem      Also, due to PL/SQL security, ctxsys must manually grant EXECUTE
Rem      on ctx_ddl directly to the user.  CTXAPP role is not sufficient.
Rem
Rem    USAGE
Rem      as index owner, in SQL*Plus:
Rem
Rem        @drjobdml <indexname> <interval>
Rem
Rem      A job will be submitted to check for and process dml for the
Rem      named index every <interval> minutes
Rem
Rem    MODIFIED   (MM/DD/YY)
Rem    gkaminag    11/19/01  - bug 2052473.
Rem    gkaminag    05/14/99 -  bugs
Rem    gkaminag    04/09/99 -  Creation

define idxname = "&1"
define interval = "&2"

set serveroutput on
declare
  job number;
begin
  dbms_job.submit(job, 'ctx_ddl.sync_index(''&idxname'');',
                  interval=>'SYSDATE+&interval/1440');
  commit;
  dbms_output.put_line('job '||job||' has been submitted.');
end;
/
