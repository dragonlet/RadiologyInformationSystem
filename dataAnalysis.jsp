<!DOCTYPE html>

<%@ include file="includes.jsp" %>
<%@ include file="navbar.jsp" %>
<%@ page import="com.DataAnalysisModule" %>
<%@ page import="com.OlapShelf" %>
<%@ page import="java.util.ArrayList" %>

<HTML>
<!-- Data Analysis module jsp page. -->

<%
	session = request.getSession();

	if((Character) session.getAttribute("privileges") != 'a') 
	{
%>
	<H1>Radiology Information System</H1>
	<H2>This page requires system administrator privileges.</H2>
<%
	}
	else {

	DataAnalysisModule DAM = new DataAnalysisModule();

	ArrayList<String> test_types_list = DAM.queryTestTypes();
	ArrayList<OlapShelf> shelvedRows = new ArrayList<OlapShelf>();

	boolean result_loaded = false;

	String test_type = "", patient_id = "", time_check = "";

	if(request.getParameter("Submit") != null)
	{

		patient_id = request.getParameter("patient_id");

		/* Check for empty field. */
		if(patient_id == "")
		{
			patient_id = "all";
		}

		test_type = request.getParameter("test_type_selector").trim();
		time_check = request.getParameter("time_list").trim();

			try
				{
					shelvedRows = DAM.countCorrespondingImages(patient_id, test_type, time_check);
					result_loaded = true;
				}
			catch(Exception ex)
				{
					// Error page redirect.
					out.println(ex); // DEBUG
				}
	}

%>

<HEAD>
    <TITLE>OLAP Reports</TITLE>
		<style>
		.resTABLE, .resTD {
    	border: 2px solid black;
    	border-collapse: collapse;
		}
		.resTD {
    	padding: 15px;
		}
		</style>
</HEAD>

<BODY>

<H1>Radiology Information System</H1>

<H2>OLAP - Data Analysis</H2>

<P>Display number of images for:</P>

	<FORM NAME="DataForm" id = "olapform" ACTION="dataAnalysis.jsp" METHOD="post" >

	<TABLE>

<TR>
	<TD><B><I>Patient ID: </I></B>
		<INPUT TYPE="text" NAME="patient_id" id="p_id" placeholder="Default: All patients" size="20"/>
	</TD>
	
	<TD><B><I>Test Type: </I></B>
		<select NAME="test_type_selector" id="t_type_sel">
			<option value="all" >all</option>
			<% for(int i = 0;i < test_types_list.size();i++){ %>
			<option value="<% out.println(test_types_list.get(i)); %>" ><% out.println(test_types_list.get(i)); %></option>
			<% } %>
	</TD>


	<TD><B><I>Timescale: </I></B>
		<select NAME="time_list" id="time_select" >
  			<option value="year" >yearly</option>
  			<option value="month" >monthly</option>
			<option value="week" >weekly</option>
		</select>

	</TD>
</TR>

<TR>
	<TD><Button name= "Submit" type="submit">Go</button></TD>
</TR>

	</TABLE>

	</FORM>

<%
	if(result_loaded && shelvedRows.size() > 0)
	{
%>

<br>

<TABLE class="resTABLE" >
	<TR><TD class = resTD >Patient ID: <% out.println(patient_id); %></TD>
	<TD class = resTD >Test Type: <% out.println(test_type); %></TD></TR>
</TABLE>

<br>

<TABLE class="resTABLE" >

	<TR><TD class = resTD >Year</TD>

<%
	if(time_check.matches("month"))
	{
%>
	<TD class = resTD >Month</TD>
<%
	}
%>

<%
	if(time_check.matches("week"))
	{
%>
	<TD class = resTD >Week</TD>
<%
	}
%>
	<TD class = resTD >Images Found</TD></TR>

<%  for(int i = 0;i < shelvedRows.size();i++)
	{
%>
	<TR><TD class = resTD ><% out.println(shelvedRows.get(i).getYear()); %></TD>
<%

	if(time_check.matches("month"))
	{
%>
	<TD class = resTD ><% out.println(shelvedRows.get(i).getMonth()); %></TD>
<%
	}
	if(time_check.matches("week"))
	{
%>
	<TD class = resTD ><% out.println(shelvedRows.get(i).getWeek()); %></TD>
<%
	}
%>
	<TD class = resTD ><% out.println(shelvedRows.get(i).getCount()); %></TD></TR>
<%
	}
%>

</TABLE>

<%
	} else if(result_loaded && shelvedRows.size() == 0){
%>
<TABLE class="resTABLE" >
	<TR><TD class = resTD >There were no images found matching the criteria specified.</TD></TR>
</TABLE>
<%	}	%>

</BODY>

<!-- This closing bracket matches the else statement in the privilige-checking conditional above. -->
<% } %>

</HTML>




