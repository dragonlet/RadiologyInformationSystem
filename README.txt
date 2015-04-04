***************************************************************************************************
Installation Guide
***************************************************************************************************


1. Compile the sourcecode
	- To compile the complete list of .java files into the necessary classes, first navigate to:
			 RadiologyInformationSystem/WEB-INF/classes
	- type "make" in command prompt
	- this will compile all .java files necessary for the website to function

2. Execute initializing SQL statements
	- This will add a single admin account to the database, create the necessary indexes, and update those indexes
	- This file is located in the main directory at:
		RadiologyInformationSystem/initRIS.sql
	- sign into sqlplus using your own "username" and "password"
	- execute the sql file by typing @ initRIS.sql

3. Start Tomcat
	- Make sure all of the RadiologyInformationSystem directory is placed in:
		~/catalina/webapps/
	- in command prompt type starttomcat
	- now the website should be accessable

4. Open the webpage
	- Now that the website is up and running, we can open a web browser (Chrome works best) and navigate to:
		http://[computer name]:[port number]/RadiologyInformationSystem/login.jsp
	- make sure to substitute [computer name] and [port number] with your own name and port number respectively.

5. Success!!!
	- you should now be greeted by a login screen where you can login with the admin information we populated in step 2





***************************************************************************************************
User Manual

Summary of modules
***************************************************************************************************

Login Module
-------------
The login module provides a simple interface for the user to log in to the system with the correct privileges. 
Usage:
Simply enter the username and password and click the login button.

JSP/HTML
The jsp file instantiates the java class LoginLayer, which is used to validate the users credentials with the database. After the user has been validated, the username and privilege level are stored as session variables (status us not saved in the backend). The completion of this set of actions constitutes a full login operation.

Java Implementation
The LoginLayer extends the BaseLayer class, and has access to the openConnection() and closeConnection methods which it uses to connect to the database. When the user attempts to login, the method validateLogin() is called, which then queries the users table in search of an entry matching the username and password entered on the web page.
The query used is:
“select password, class from users where user_name =<username>".
This password is then compared with that entered by the user and the method returns true or false for a positive match.




The BaseLayer Class
-------------------
Every module in this application requires a connection to the database on the server side and the ability to execute various sql statements. Thus it was logical to construct a class that encapsulated this common functionality. The database connection is managed entirely by this class. The other classes extend it in order to use its openConnection() and closeConnection() methods as well as GetQueryResult(), which simply receives a query as a string and passes it to the DB. It has its own exception class, which subclasses Exception but does not add any functionality (purely semantic) although it affords the possibility of future add-ons.


The Unique ID Class
-------------------
This class is used to generate a unique ID for one of the tables persons, paces_images, or radiology_record. It randomly creates a 5 digit id between 10000 and 99999 (up to 89999 possibilities) and ensures that it does not already exist in the relevant table. The id may then be used as the unique key when inserting a row.


User Management Module
----------------------
    This module grants privileges strictly for an administrator to manage (add, edit, and remove) entries from the database tables Users, Persons, and family_doctor, effectively managing the list of people (patients, doctors, and radiologists), their user accounts (username, passwords etc.), and the doctor/patient relationship pairs. This module is accomplished through the implementation of multiple jsp files that handle the front end communication with the current user, and talk to a single java file that serves as the back end database communication. 

JSP/HTML implementation 
The collective jsp files that comprise the user management module act as a succession of choices for the user to determine what they wish to edit. The first page, UserManagement.jsp, offers a simple choice to the user, asking if they would like to “edit users, edit persons, or edit doctors.” This choice branches to 3 different files based on the users choice. The user is then given the choice to again “add, edit, or delete” but now for which ever database table was specified above. This selection branches to a final set of jsp files that take in the users information based on whether they are adding editing or deleting from the tables. Once that information has been defined, it is compiled into a query statement and passed onto the executequery() function which will be detailed below. 
    When adding a new entry into the database, the web page simply takes in a set of information using text boxes and a submit button, and proceeds to execute the query. When editing entries, the web page uses text boxes and checkboxes, asks the user to enter the information they wish to update, and uncheck the checkbox of any information they wish to leave unchanged. 
specifically when adding a family_doctors pair, the function getClass(ID) is used to check that the doctor_id and patient_id that are entered, are in fact doctors and patients respectively. When adding a new user to the users table, the function getDate() is used to get the current date formatted as year, month, day. Both getClass() and getDate() are detailed below. 
The queries "INSERT INTO users VALUES(...)" and "DELETE FROM users WHERE person_id = ..." were used to insert and delete from the tables, substituting “users” for the table at hand. Editing was handled differently in that seperate queries where executed for every entry that was to be changed, for example "UPDATE persons SET first_name = ..." .

JAVA implementation 
This portion of the code found in ManagementModule.java handles the connection and disconnection to the database, along with the execution of queries and a few other helpful functions. Connecting to, disconnecting from, and executing queries on the database are handled by extending BaseLayer.java and utilizing the functions detailed above. 
    The first important function is executequery(). This function simply takes in a string query, and returns true or false based on if it was successful or not. The function tries openConnection(), followed by getQueryResult(), and lastly closeConnection() from BaseLayer.java, catching any errors that may have gotten thrown during the process.
    getClass() is a simple function that takes in a users ID, opens a connection to the database (using BaseLayer.java implementation), executes the simple query “"select class from users where person_id = ID" to get and return that particular users classification, and then closes the connection to the database.
    getDate() simply returns the current date as a string in the format YYYYMMDD.
    Lastly, the three functions getPersonsTable(), getUsersTable(), and getDoctorsTable() build an html table for each of the database tables respectively and return that as a string. This is accomplished by the simple query "select * from [persons/users/family_doctors]" where only one table is selected based on the function at hand.


My Account Module
------------------
    This module can be accessed by any user and is used to change only that users personal information. It allows them to update username, password, full name, address, email, and phone number.
JSP/HTML implementation 
This module is handled by a single jsp file called MyAccount.jsp. This web page uses text boxes with submit buttons for every text box. when an item is entered and submitted, it first uses the browser's session to get the current users username which is set in the Login Module. The username is then passed to the function getID() and their ID is returned. A query is compiled and sent to executequery(), and lastly confirmation is displayed. An example of one of the queries is as follows: "UPDATE persons SET first_name = ... WHERE person_id = …”.

JAVA implementation 
The functions mentioned above are found in MyAccountLayer.java. The first being executequery(), which is the same implementation of that found in ManagementModule.java. It takes in a query string, connects, executes the query, and disconnects. getID() takes in a username and finds the unique ID that is assosiated with that username using the query "select person_id from users where user_name = ...". 


Report Generating Module
------------------------
The Report Generating module receives a timeframe from the user, composed of start and end dates, as well as a specific diagnosis. The diagnosis is required to be precise because the report generator is not a search function - it simply retrieves a specified dataset from the database.

Usage:
The user must enter a valid start and end date (if the format is incorrect there is an alert), as well as a valid diagnosis, although leaving the diagnosis field blank will not cause an error. To obtain the results, click generate report.

JSP/HTML
The corresponding jsp file is “reportGen.jsp”; it contains the html and server-side code to process the data entered by the user, as well as redirect them to the login page if they do not have valid session privileges. In the code section, the ReportModule class is instantiated and used to obtain the relevant data. Before the data is passed to the class methods, however, it undergoes some processing. The diagnosis may be any string, and as such it is up to the user to ensure that it is legitimate, but the dates entered are checked for valid formatting. Regular expressions are used to perform this check. This is necessary because the generateReport method must parse them to obtain the date variables for the query.

Java Implementation
ReportModule.java extends BaseLayer to inherit the DB connection functionality required. When generateReport() is called, it then builds the query from the data passed and sends it to the DB. Since only the first result containing the specified diagnosis is required, only one row of the selection will be stored. The values of the found row are stored as attributes which can then be accessed by the code in the jsp file.
The query applied to obtain this data is:
“SELECT P.first_name, P.last_name, P.address, P.phone, R.test_date
FROM radiology_record R, persons P
WHERE R.diagnosis = '<user input>' AND R.test_date BETWEEN '<user entered date 1>' AND '<user entered date 2>'”.
If a result is found, the attribute ‘found’ is set to true. Otherwise it remains false, and this is used to let the jsp know there were no matching rows found.


Search Module
--------------
This module is accessed by any user and is the default page after a successful login. Given the users privileges (i.e. patient, doctor, radiologist, admin) the module allows the user to search the radiology records. A patient has access strictly to their own records, a doctor to his patients’ records, a radiologist to their records, and an admin to all the records in the table. 

JSP/HTML implementation
The front end interface is found in Search.jsp and contains a drop down box for selecting how to order the results, a text box for accepting a keyword to search, and a submit button. Once the submit button is pressed, the users privileges are read from the current session and the corresponding function is called from SearchLayer.java as detailed below.

JAVA implementation
The functions mentioned above are located in SearchLayer.java. The first function found in this file is Display() which is strictly for debugging and returns a complete list of the records in the radiology_records table. 
    The searchAll() function takes in the keyword the user wants to query, along with the type of ordering that the user has chosen. It opens a connection and proceeds with searching the database. The first query that is performed is to find the keyword in the records description. The next finds the keyword in diagnosis, followed by a query for first name, and lastly a query for last name. These 4 queries are then combined, taking into account the weighting as defined by the project specifications. This new ranking data is then used to compile an array that holds all of the relevant records’ data and finally passed back to the jsp file to be printed. 
    The functions searchPatient(), searchDoctor(), and searchRadiologist() are all identical to searchAll() except that their search queries only allow the relevent information to that particular user to be returned. For example searchPatient() would only search through the records for the users patient records. This ensures that no confidential information is displayed to the wrong user. 
    The relevant queries for searching the database are quite long and very slightly change between the 4 functions. These are the queries used for the searchAll() function. 
Description: "CREATE OR REPLACE VIEW tempDesc AS SELECT score(1) as score, record_id, patient_id, doctor_id, radiologist_id, test_type, prescribing_date, test_date, diagnosis, description FROM radiology_record WHERE (contains(description, '"+query+"', 1) >= 0)"
Diagnosis: "CREATE OR REPLACE VIEW tempDiag AS SELECT score(1) * 3 as score, record_id, patient_id, diagnosis, description FROM radiology_record WHERE (contains(diagnosis, '"+query+"', 1) >= 0)"
First Name: "CREATE OR REPLACE VIEW tempFname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons P, radiology_record R WHERE R.patient_id = P.person_id AND contains(P.first_name, '"+query+"', 1) >= 0"
Last Name: "CREATE OR REPLACE VIEW tempLname AS SELECT score(1) * 6 as score, record_id, first_name, last_name FROM persons P, radiology_record R WHERE R.patient_id = P.person_id AND contains(P.last_name, '"+query+"', 1) >= 0"
Final Result: "CREATE OR REPLACE VIEW tempRank AS SELECT DE.record_id, DE.score + DI.score + PF.score + PL.score AS aggregate_score, DE.patient_id, DE.doctor_id, DE.radiologist_id, DE.test_type, DE.prescribing_date, DE.test_date, DE.description, DI.diagnosis, PF.first_name, PL.last_name FROM tempDesc DE, tempDiag DI, tempFname PF, tempLname PL WHERE DE.record_id = DI.record_id AND DE.record_id = PF.record_id AND DE.record_id = PL.record_id AND ((DE.score + DI.score + PF.score + PL.score) > 0) " + order


Data Analysis Module
-----------------------
The data analysis module takes three inputs from the user - patient id, test type, and time period. It then obtains all records from the database that match the three specified inputs. The results are grouped by the time period selected - i.e. if the user selects ‘monthly’ from the dropdown menu, the results will be grouped by month.  It should be noted, that even if the results are grouped by month or week, they are still differentiated by the year they occurred in, so if the grouping is monthly, the counts for January of one year and January of another year remain separate. Thus the year is specified in all cases.

Usage:
The user has access to three fields; patient id, test type, and time period. By default, patient id and test type have a value of ‘all’, and time period has a default value of yearly (there are three time levels, weekly, monthly, and yearly). The user may enter any patient id and select a test type and time scale from the dropdown menus. The results are obtained by clicking the ‘Go” button.



JSP/HTML
The jsp file for data analysis first performs a query on the database to pull all the possible values of test type. These values are then used to populate the dropdown menu for the type of test. The reason for this is that it saves having to carefully check the formatting of the test type string, and it also makes the page more user friendly. Since the patient id is simply an integer, it is easy for the user to enter without much format checking. DataAnalysisModule is instantiated and used to obtain a list of results from the database; These results are stored in a variable length list and used to populate a table for the user to view.

Java Implementation
DataAnalysisModule (DAM)extends the base layer class like the other modules, and uses it to connect to and query the database. In general, it analyzes the input data and builds a corresonding query. It uses a data structure class called OlapShelf to store the results of the query. OlapShelf does some formatting on these values and is kept in an arraylist, which is returned to the jsp file. DAM contains the queryTestTypes() method initially used to obtain the values for the dropdown menu in the jsp page, as well as getTimeSpec() (determines the portion of the sql for the group by statement), getQType() (parses the inputs to determine the type of query), genSQL() (helper function) and countCorrespondingImages(), which performs the main query. The results are returned through the list of OlapShelf objects.


