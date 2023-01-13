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
<title>Employee Request</title>
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
		/* @SuppressWarnings("unchecked")
		List<Employee> employees = (List<Employee>)session.getAttribute("employees"); */
		@SuppressWarnings("unchecked")
		List<Leaverequest> listApprove = (List<Leaverequest>)session.getAttribute("listApprove");
		@SuppressWarnings("unchecked")
		List<Leaverequest> listReject = (List<Leaverequest>)session.getAttribute("listReject");
	%>
	<div class="banner-admin">
		<%@ include file = "Header.jsp" %>
		
		<div class="overlay container">			
			<h1 class="text-center"> Employee Outstanding Leave Request</h1>
			<p><a href="HolidayBookingSystemServlet?actiontype=allLeaveRequest&id=<%=userheader.getUSpKey() %>" class="button">View all Holiday booking</a></p>
			
			<div class='row'>
			<div class="col-md-6">
				<h2>Requests don't break constraints</h2>
				<table class="table table-dark table-hover">
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Date</th>
					<th>Comment</th>
					<th>Status</th>
					<th>Approve/Reject</th>
				</tr>
				<c:forEach items="${listApprove}" var="e">
				<tr>
				  <td>${e.employee1.empName}</td>
				  <td>${e.LRDescription}</td>
				  <td>${e.LRDate.toString().substring(0, 10)}</td>				  
				  <td>${e.LRStatus}</td>
				  <td>${e.LRComment}</td>
				  <td>
				  <button type="button" class="btn btn-primary pop"  data-value="${e.LRpKey }" data-comment=""> Approve/Reject </button>
				  </td>			  
				</tr>
				</c:forEach>
				</table>
			</div>
			<div class="col-md-6">
				<h2>Requests break constraints</h2>
				<table class="table table-dark table-hover">
				<tr>
					<th>Name</th>
					<th>Description</th>
					<th>Date</th>
					<th>Status</th>
					<th>Comment</th>
					<th>Approve/Reject</th>
				</tr>
				<c:forEach items="${listReject}" var="e">
				<tr>
				  <td>${e.employee1.empName}</td>
				  <td>${e.LRDescription}</td>
				  <td>${e.LRDate.toString().substring(0, 10)}</td>
				  <td>${e.LRStatus}</td>
				  <td>${e.LRComment}</td>
				  <td>
				  <button type="button" class="btn btn-primary pop"  data-value="${e.LRpKey }" data-comment="${e.LRComment}"> Approve/Reject </button>
				  </td>			  
				</tr>
				</c:forEach>
				</table>

			</div>
			</div>
		
			<!--
			<table class="table table-dark table-hover">
			<tr>
				<th>Employee Name</th>
				<th>Description</th>
				<th>Date</th>
				<th>Status</th>
				<th>Approve/Reject</th>
			</tr>
			<c:forEach items="${listRequest}" var="e">
				<tr>
				  <td>${e.employee1.empName}</td>
				  <td>${e.LRDescription}</td>
				  <td>${e.LRDate.toString().substring(0, 10)}</td>
				  <td>${e.LRStatus}</td>
				  <td>
				  <button type="button" class="btn btn-primary pop"  data-value="${e.LRpKey }"> Approve/Reject </button>
				  </td>			  
				</tr>
			</c:forEach>
			</table>
			  -->
			<div class="modal" id="myModal">
				  <div class="modal-dialog">
				    <div class="modal-content">
				
				      <!-- Modal Header -->
				      <div class="modal-header">
				        <h4 class="modal-title text-dark">Approve/Reject</h4>
				        <button type="button" class="btn-close close" data-bs-dismiss="modal"></button>
				      </div>
					<form method="GET" action="">
				      <!-- Modal body -->
				      <div class="modal-body">
				     	 	<div class="mb-3 mt-3 text-dark">
				      			<label for="approve"> Approve</label>
				      			<input type="radio" id="approve" name="aprrej" value="Approve" required>
				      			<label for="reject"> Reject</label>
				      			<input type="radio" id="reject" name="aprrej" value="Reject">
				     		</div>
					      	
							<div class="mb-3 mt-3">
								<label for="comment">Comment</label><br>
						        <textarea class="form-control" id="comment" name="comment" rows="3" required></textarea>
					        </div>
				      
				      </div>
				
				      <!-- Modal footer -->
				      <div class="modal-footer">
				      	<input type="hidden" id="hdnValue" name="LRpKey">
				      	<input type="hidden" name="id" value="<%=userheader.getUSpKey() %>">
				      	<input type="hidden" name="actiontype" value="aprRejRequest">
				      	<input type="submit" id="btnSave" class="btn btn-primary" data-bs-dismiss="modal" value="Save">
				        <button type="button" class="btn btn-danger close" data-bs-dismiss="modal">Close</button>
				      </div>
				 </form>
				    </div>
				  </div>
			</div>
		</div>	
	</div>
	<%}  else {%>	
	<a href="LoginPage.jsp"> Login </a>
	<%}%>
</body>
<script>
	$('.pop').click(function(){		
		$('#hdnValue').val($(this).attr("data-value"));
		//alert($(this).attr("data-commet"))
		$('textarea#comment').val($(this).attr("data-comment"));
		$('#myModal').toggle();
		
		});
	$('.close').click(function(){
		$('#myModal').toggle();
		});	
</script>
</html>