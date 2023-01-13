<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="model.Userlogin"
    %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header</title>
<link rel="stylesheet" href="CSS/Style.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="CSS/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
</head>
<body>
	<%
		Userlogin userheader=(Userlogin)session.getAttribute("User");
		if(userheader != null)
		{			
			//int userKeyh=userheader.getUSpKey();
			if(userheader.getIsAdmin()==1)
			{
	%>
	<ul>
	  <li><a href="AdminPage.jsp" class="active">Home</a></li>
	  <li><a href="HolidayBookingSystemServlet?actiontype=viewRequest&id=<%=userheader.getUSpKey() %>">View Request</a></li>
	  <li><a href="HolidayBookingSystemServlet?actiontype=employeeRequest&id=<%=userheader.getUSpKey() %>">Employee Request</a></li>	
	  <li><a href="HolidayBookingSystemServlet?actiontype=viewEmployee" >View Employee</a></li>
	  <li style="float:right"><a href="logoutPage.jsp">Logout</a></li>
	</ul>
	<%} else {%>
	<ul>
	  <li><a href="#home" class="active">Home</a></li>
	  <li><a href="HolidayBookingSystemServlet?actiontype=viewRequest&id=<%=userheader.getUSpKey() %>">View Request</a></li>
	  <li style="float:right"><a href="logoutPage.jsp">Logout</a></li>
	</ul>
	<%} } else {%>	
	<a href="LoginPage.jsp"> Login </a>
	<%}%>
</body>
</html>