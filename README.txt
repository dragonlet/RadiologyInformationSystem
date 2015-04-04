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
