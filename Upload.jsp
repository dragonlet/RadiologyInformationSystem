<!DOCTYPE html>
<%@ page import="java.util.*, java.sql.*, com.UploadLayer, com.Person" %>

<HTML>
<%@ include file="navbar.jsp" %>

<%
	session = request.getSession();
	UploadLayer _ul = new UploadLayer();
	
	//Get a result set containing a list of all possible docotors
	Iterator<Person> _doctors = _ul.getDoctors().iterator();
	Person _doc;
	
	//Get a result set of all people who could be a patient ie: all of the users
	Iterator<Person> _patients = _ul.getPatients().iterator();
	Person _pat;
	
%>

<HEAD>
	<TITLE>Record Upload</TITLE>
</HEAD>

<BODY>
<H1>Radiology Information System</H1>

<Form Name="RecordForm" Action="view.jsp" Method="post" >
 
	Prescribing Doctor:
	<select name="doctor_id" >
	<% while(_doctors.hasNext()){
		_doc = _doctors.next(); %>
		<option value="<%= _doc.getID() %>" >
			Dr. <%= _doc.getFirstName() %> <%= _doc.getLastName() %>
		</option>
	<% } %>
	</select><br>
	
	Patient:
	<select name="patient_id" >
	<% while(_patients.hasNext()){
		_pat = _patients.next(); %>
		<option value="<%= _pat.getID()%>" >
			<%= _pat.getFirstName() %> <%= _pat.getLastName() %>
		</option>
	<% } %>
	</select><br>
	
	Test Type: <input type="text" name="test_type" size="24" required><br>
	
	Date of Prescription: <input type="date" name="prescription_date" required><br>
	
	Date of Test: <input type="date" name="test_date" required><br>
	
	Diagnosis: <input type="text" name="diagnosis" size="128" required><br>
	
	Description: <input type="text" name="description" size="1024" required><br>
	
	<input type="hidden" name="radiologist_id" value="<%=(String)session.getAttribute("person_id")%>" >
	<input type="hidden" name="new" value="true" >
	
	<input type="submit" value="Submit" >
	
</Form>
</HTML>