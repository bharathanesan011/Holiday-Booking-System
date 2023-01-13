<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="model.Userlogin"
    import="model.Employee" 
    import="java.util.*"
    import="java.text.SimpleDateFormat"
     %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html class="banner-admin">
<head>
<meta charset="ISO-8859-1">
<title>View Employee</title>
<link rel="stylesheet" href="CSS/Style.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="CSS/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
</head>
<body>
<%	Userlogin user=(Userlogin)session.getAttribute("User");	
	
	if(user != null)
	{
		@SuppressWarnings("unchecked")
		List<Employee> employees = (List<Employee>)session.getAttribute("employees");
	%>
	<div class="banner-admin">
		<%@ include file = "Header.jsp" %>
		
		<div class="overlay container col-md-12 center_div">			
			<h1 class="text-center"> View Employee Detail</h1>
			<p><a href="AddEmployee.jsp" class="button">Add Employee</a></p>			
		<table class="table table-dark table-hover">
		<tr>
			<th>Employee ID</th>
			<th>Name</th>
			<th>Address</th>
			<th>Type</th>
			<th>Department</th>
			<th>Email</th>
			<th>Phone</th>
			<th>Date of Joining</th>
			<th>Total Holiday</th>
			<th>Remaining Holiday</th>	
			<th></th>
			<th></th>		
		</tr>
		<c:forEach items="${employees}" var="e">
			<tr>
			  <td>${e.emppKey}</td>
			  <td>${e.empName}</td>
			  <td>${e.empAddress}</td>
			  <td>${e.empType}</td>
			  <td>${e.employeeDepartment}</td>
			  <td>${e.empEmail}</td>
			  <td>${e.empPhone}</td>
			  <td>${e.empDoJ.toString().substring(0, 10)}</td>
			  <td>${e.totalHoliday}</td>
			  <td>${e.availableHoliday}</td>
			  <td><a href="HolidayBookingSystemServlet?actiontype=editEmployee&id=${e.emppKey}">Edit</a></td>
			  <td><a href="HolidayBookingSystemServlet?actiontype=deleteEmployee&id=${e.emppKey}">Delete</a></td>
			</tr>
		</c:forEach>
		</table>
		</div>
	</div> 
	<%}  else {%>	
	<a href="LoginPage.jsp"> Login </a>
	<%}%>
</body>
</html>