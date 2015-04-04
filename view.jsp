<!DOCTYPE html>
<%@ page import="java.util.*, java.sql.*, java.text.*, com.UploadLayer, com.Person, com.UniqueID" %>

<% //@ include file="navbar.jsp" %>

<%
	session = request.getSession();
	UploadLayer _ul = new UploadLayer();
	UniqueID _uid = new UniqueID();
	

boolean valid = false;

if(request.getParameter("new") != null)
{
	Integer id = null;
	
	try{
		id = _uid.generate("record");
	}catch(Exception ex){
		id = null;
	}
	
	try{
		SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd");

		_ul.addRecord(id,
			Integer.parseInt(request.getParameter("doctor_id")),
			Integer.parseInt(request.getParameter("patient_id")),
			Integer.parseInt(request.getParameter("radiologist_id")),
			request.getParameter("test_type").trim(),
			_sdf.parse(request.getParameter("prescription_date")),
			_sdf.parse(request.getParameter("test_date")),
			request.getParameter("diagnosis"),
			request.getParameter("description")
			);
		
		valid = true;
	}catch(Exception ex){
		out.println(ex.message());
		valid = false;
	}
}
else if(request.getParameter("record_id") != null)
{
	valid = true;
}
%>

<% if(valid){ %>
	Record :)
<% } else {%>
	No record was submitted or the requested record failed to load...
<% } %>