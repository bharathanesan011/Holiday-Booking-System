<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="model.Userlogin"
    import="model.Employee"
    import="model.Leaverequest" 
    import="java.util.*"
    import="java.text.SimpleDateFormat"
    import="java.text.DateFormat"
     %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="banner-admin">
<head>
	<meta charset="ISO-8859-1">
	<title>Home</title>
	<link rel="stylesheet" href="CSS/Style.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<link rel="stylesheet" href="CSS/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
	<script>
	  $( function() {
	    $( "#date" ).datepicker({
	      changeMonth: true,
	      changeYear: true,
	      yearRange: "1970:2100"
	    });
	  } );
	 </script>
</head>
<body>
	<%
		Userlogin user=(Userlogin)session.getAttribute("User");
		if(user != null)
		{
			@SuppressWarnings("unchecked")
			List<Leaverequest> listWorking = (List<Leaverequest>)session.getAttribute("listWorking");
			@SuppressWarnings("unchecked")
			List<Leaverequest> listNotWorking = (List<Leaverequest>)session.getAttribute("listNotWorking");
	%>
	<div class="banner-admin">
	<%@ include file = "Header.jsp" %>
		<div class="overlay container col-md-12 center_div">								
			<form method="get" action="HolidayBookingSystemServlet">
				<div class="mb-3 mt-3">
					<input type="text" id="date" class="form-control search" name="date" placeholder="Select Date" required>
					<input type="hidden" name="actiontype" value='getEmployeeByDate'>
			    	<input type="submit" class="btn btn-primary" value="Search">
				</div>
			</form>
			<div class='row'>
			<div class="col-md-6">
				<h2>List of Working Employee</h2>
				<table class="table table-dark table-hover">
				<tr>
					<th>Employee ID</th>
					<th>Employee Name</th>
					<th>Department</th>
				</tr>
				<c:forEach items="${listWorking}" var="e">
					<tr>
					  <td>${e.emppKey}</td>
					  <td>${e.empName}</td>
					  <td>${e.employeeDepartment}</td>					 			  			  
					</tr>
				</c:forEach>
				</table>
			</div>
			<div class="col-md-6">
				<h2>List of Not Working Employee</h2>
				<table class="table table-dark table-hover">
				<tr>
					<th>Employee ID</th>
					<th>Employee Name</th>
					<th>Department</th>
				</tr>
				<c:forEach items="${listNotWorking}" var="e">
					<tr>
					  <td>${e.emppKey}</td>
					  <td>${e.empName}</td>
					  <td>${e.employeeDepartment}</td>					 			  			  
					</tr>
				</c:forEach>
				</table>
			</div>
			</div>
		</div>
	</div>
	<%}  else {%>	
	<a href="LoginPage.jsp"> Login </a>
	<%}%>
</body>
</html>