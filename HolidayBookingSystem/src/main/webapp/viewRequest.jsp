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
<html class="banner">
<head>
<meta charset="ISO-8859-1">
<title>View Request</title>
<link rel="stylesheet" href="CSS/Style.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<link rel="stylesheet" href="CSS/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
</head>
<body>
	<%
		Userlogin user=(Userlogin)session.getAttribute("User");		
		if(user != null)
		{
			int userKey=user.getUSpKey();
			@SuppressWarnings("unchecked")
			List<Leaverequest> listRequest = (List<Leaverequest>)session.getAttribute("listRequest");
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
	%>
	<div class="banner">
		<%@ include file = "Header.jsp" %>
		<div class="overlay container col-md-12 center_div">			
			<h1 class="text-center"> View Leave Request</h1>
			<p><a href="submitRequest.jsp" class="button">Add Leave</a></p>
			<table class="table table-dark table-hover">
			<tr>
				<th>Description</th>
				<th>Date</th>
				<th>Status</th>
				<th>Comment</th>
				<th>Authorized By</th>
			</tr>
			<c:forEach items="${listRequest}" var="e">
				<tr>
				  <td>${e.LRDescription}</td>
				  <td>${e.LRDate.toString().substring(0, 10)}</td>
				  <td>${e.LRStatus}</td>
				  <td>${e.LRComment}</td>
				  <td>${e.employee2.empName}</td>			  
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