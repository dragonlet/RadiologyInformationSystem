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
	Integer rec_id = _uid.generate("record");

	SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd");

	_ul.addRecord(rec_id,
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
}
else if(request.getParameter("record_id") != null)
{
	if(request.getParameter("img") != null){
		Integer img_id = _uid.generate("record")
		
		DiskFileUpload fu = new DiskFileUpload();
	    List FileItems = fu.parseRequest(request);
	    // Process the uploaded items, assuming only 1 image file uploaded
	    Iterator i = FileItems.iterator();
	    FileItem item = (FileItem) i.next();
	    while (i.hasNext() && item.isFormField()) {
		    item = (FileItem) i.next();
	    }
		
		_ul.addImg( img_id, Integer.parseInt(request.getParameter("record_id")), item);
	}
	
	//ToDo: Get the record to be displayed
	valid = true;
}
%>

<% if(valid){ %>
	Record :)
	
	<% if( _ul.hasImage(Integer.parseInt(request.getParameter("record_id"))) ){ %>
		Image :)
	<% } if(session.getAttribute("permissions") != null ) { %>
		Upload
	<% } %>
<% } else {%>
	No record was submitted or the requested record failed to load...
<% } %>