<!DOCTYPE html>

<%@ include file="includes.jsp" %>
<%@ include file="navbar.jsp" %>
<%@ page import="com.DataAnalysisModule" %>
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

	boolean valid_date_format = true;
	boolean result_loaded = false;
	int img_count = 0;

	String test_type = "", patient_id = "", time_period = "", prnt_time = "all";

	if(request.getParameter("Submit") != null)
	{

		patient_id = request.getParameter("patient_id");

		/* Check for empty field. */
		if(patient_id == "")
		{
			patient_id = "all";
		}

		test_type = request.getParameter("test_type_selector").trim();
		String time_check = request.getParameter("time_list");
		String week = "", month = "", year = "";
		time_period = "all";

		if(time_check.matches("week"))
		{
			week = request.getParameter("week_list");
			year = request.getParameter("year").trim();
			time_period = week.trim() + "/" + year.trim();
			prnt_time = "wk "+week.trim()+" of "+year.trim();
		}
		else if(time_check.matches("month"))
		{
			month = request.getParameter("month_list");
			year = request.getParameter("year").trim();
			time_period = month.trim() + "/" + year.trim();
			prnt_time = month.trim()+"/"+year.trim();
		}
		else if(time_check.matches("year"))
		{
			year = request.getParameter("year");
			time_period = year.trim();		
			prnt_time = year;	
		}

		//Check that the year format is acceptable.

		if(!year.matches("^[0-9]{4}$") && !time_check.matches("all"))
		{
			valid_date_format = false;
		}
		else
		{
			try
				{
					img_count = DAM.countCorrespondingImages(patient_id, test_type, time_period, time_check);
					result_loaded = true;
				}
			catch(Exception ex)
				{
					// Error page redirect.
					out.println(ex); // DEBUG
				}

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

<!-- An alert is presented if the date format is incorrect. -->
<% if(!valid_date_format) { %>
  <BODY onload = "badDateAlert()">
<% } else { %>
  <BODY>
<% } %>

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
		<select NAME="time_list" id="time_select" onchange="setSelectMenus()" >
			<option value="all" >all</option>
			<option value="week" >weekly</option>
  			<option value="month" >monthly</option>
  			<option value="year" >yearly</option>
		</select>

	</TD>
</TR>

	</TABLE>

	<TABLE>

<TR>
	<TD id="prompt_td" style="display:none" ><B><I>Enter specific date information:</I></B></TD>
</TR>

<TR>

	<TD style="display:none" id="week_td" >
		<B><I>Week </I></B>
		<select NAME="week_list" id="week_select">
			<% for(int i = 0;i < 54;i++){ %>
				<option value="<% out.println(i); %>"><% out.println(i); %></option>
			<% } %>
		</select>
		<B><I> of </I></B>
	</TD>

	<TD style="display:none" id="month_td" >
		<select NAME="month_list" id="month_select">
			<option value="1" >January</option>
  			<option value="2" >February</option>
  			<option value="3" >March</option>
			<option value="4" >April</option>
			<option value="5" >May</option>
  			<option value="6" >June</option>
  			<option value="7" >July</option>
			<option value="8" >August</option>
			<option value="9" >September</option>
  			<option value="10" >October</option>
  			<option value="11" >November</option>
			<option value="12" >December</option>
		</select>
		<B><I>in the year: </I></B>
	</TD>

	<TD style="display:none" id="year_td" >
		<INPUT TYPE="text" NAME="year" id="y_sel" placeholder="yyyy" size = "4" />
	</TD>

</TR>

<TR>
	<TD><Button name= "Submit" type="submit">Go</button></TD>
</TR>

	</TABLE>

	</FORM>

<%
	if(result_loaded)
	{
%>

<br>

<TABLE class="resTABLE" >

<TR>
	<TD class="resTD" >Patient id: <% out.println(patient_id); %></TD>
	<TD class="resTD" >Test type: <% out.println(test_type); %></TD>
	<TD class="resTD" >Time period: <% out.println(prnt_time); %></TD>
</TR>

</TABLE>

<br>

<TABLE class="resTABLE" >

<TR>
	<TD class="resTD" >Images found matching criteria: <% out.println(img_count); %></TD>
</TR>

</TABLE>

<%
	}
%>

</BODY>

<!-- This closing bracket matches the else statement in the privilige-checking conditional above. -->
<% } %>

</HTML>


<script>

function setSelectMenus()
	{
		/* Display the appropriate fields depending on what the user selects. */

		var t_sel = document.getElementById("time_select");
		var opt = t_sel.value;
		
		document.getElementById("prompt_td").style.display = "inline";

		if(opt == "week")
			{
				document.getElementById("week_td").style.display = "inline";
				document.getElementById("month_td").style.display = "none";
				document.getElementById("year_td").style.display = "inline";
			}
		else if(opt == "month")
			{
				document.getElementById("week_td").style.display = "none";
				document.getElementById("month_td").style.display = "inline";
				document.getElementById("year_td").style.display = "inline";
			}
		else if(opt == "year")
			{
				document.getElementById("week_td").style.display = "none";
				document.getElementById("month_td").style.display = "none";
				document.getElementById("year_td").style.display = "inline";
			}
		else
			{
				document.getElementById("prompt_td").style.display = "none";
				document.getElementById("week_td").style.display = "none";
				document.getElementById("month_td").style.display = "none";
				document.getElementById("year_td").style.display = "none";
			}
	}

	function badDateAlert()
		{
			alert("Please use the format yyyy when entering the year.");
		}

</script>





