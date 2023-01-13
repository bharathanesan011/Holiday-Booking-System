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
<html  class="banner-admin">
<head>
<meta charset="ISO-8859-1">
<title>View All Request</title>
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
					
			<h1 class="text-center"> All Leave Request</h1>
			<div class="col-md-8">
			<form method="GET" action="">
				<div class="mb-3 mt-3">
					<input type="text" class="form-control search" id="EmpName" name="name" placeholder="Search by Name" required>
					<input type="hidden" name="actiontype" value='allLeaveRequest'>
					<input type="hidden" name="id" value="<%=user.getUSpKey() %>">
				   	<input type="submit" class="btn btn-primary" value="Search">
				</div>
			</form>	
			</div>
			<table class="table table-dark table-hover">
			<tr>
				<th>Employee Name</th>
				<th>Department</th>
				<th>Type</th>
				<th>Description</th>
				<th>Date</th>
				<th>Status</th>
			</tr>
			<c:forEach items="${listRequest}" var="e">
				<tr>
				  <td>${e.employee1.empName}</td>
				  <td>${e.employee1.employeeDepartment}</td>
				  <td>${e.employee1.empType}</td>
				  <td>${e.LRDescription}</td>
				  <td>${e.LRDate.toString().substring(0, 10)}</td>
				  <td>${e.LRStatus}</td>			  			  
				</tr>
			</c:forEach>
			</table>
		</div>	
	</div>
	<%} else {%>	
	<a href="LoginPage.jsp"> Login </a>
	<%}%>
</body>
</html>