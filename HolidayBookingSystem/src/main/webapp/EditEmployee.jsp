<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="model.Userlogin"
    import="model.Employee" 
    import="java.util.Date"
    import="java.text.SimpleDateFormat"
     %>
<!DOCTYPE html>
<html class="banner-admin">
<head>
<meta charset="ISO-8859-1">
<title>Edit Employee</title>
<link rel="stylesheet" href="CSS/Style.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="CSS/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
<script>
  $( function() {
    $( "#EmpDoJ" ).datepicker({
      changeMonth: true,
      changeYear: true,
      yearRange: "1970:2100"
    });
  } );
 </script>
</head>
<body>
<%	Userlogin user=(Userlogin)session.getAttribute("User");
	if(user != null)
	{
		Userlogin nUser=new Userlogin();
		Employee nEmployee=new Employee();
		Employee employee = (Employee)session.getAttribute("employee");
		Userlogin employeeuser=employee.getUserlogin();
	%>
	<div class="banner-admin">
		<%@ include file = "Header.jsp" %>
		<div class="overlay container col-md-5 center_div">
		<span class="Error">${MSG}</span>
		<h1 class="text-center"> Edit Employee</h1>
			<form method="GET">
				 <div class="mb-3 mt-3">
				    <label for="EmpName">Employee Name<sup>*</sup>:</label>
					<input type="text" class="form-control" id="EmpName" name="EmpName" value="${employee.empName}" required>
				</div>
				<div class="mb-3 mt-3">
				    <label for="EmpAddress">Employee Address<sup>*</sup>:</label>
					<input type="text" class="form-control" id="EmpAddress" name="EmpAddress" value="${employee.empAddress}" required>
				</div>
				<div class="mb-3 mt-3">
				    <label for="EmpEmail">Employee Email<sup>*</sup>:</label>
					<input type="email" class="form-control" id="EmpEmail" name="EmpEmail" value="${employee.empEmail}" required>
				</div>				
				<div class="mb-3 mt-3">
				    <label for="EmpPhone">Employee Phone<sup>*</sup>:</label>
					<input type="tel" class="form-control" id="EmpPhone" name="EmpPhone" pattern="[0-9]{10}" value="${employee.empPhone}" required>
				</div>
				<div class="mb-3 mt-3">
					<label for="EmpDoJ">Date of Joining<sup>*</sup>:</label>
					<input type="text" id="EmpDoJ" class="form-control" name="EmpDoJ" required>
				</div>
				
			    <div class="mb-3 mt-3">
			    	<label for="EmpType">Employee Type<sup>*</sup>:</label>
					<select class="form-select form-select-sm form-control" aria-label=".form-select-sm example" id="EmpType" name="EmpType">
					  <option >--Select--</option>
					  <option value="Head">Head</option>
					  <option value="Deputy Head">Deputy Head</option>
					  <option value="Manager">Manager</option>
					  <option value="Apprentice">Apprentice</option>
					  <option value="Junior member">Junior member</option>					  
					  <option value="Senior member">Senior member</option>
					</select>
			    </div>
			    <div class="mb-3 mt-3">
			    	<label for="EmpDept">Employee Department<sup>*</sup>:</label>
					<select class="form-select form-select-sm form-control" aria-label=".form-select-sm example" id="EmpDept" name="EmpDept">
					  <option >--Select--</option>
					  <option value="Engineering">Engineering</option>
					  <option value="Plumbing">Plumbing</option>
					  <option value="Roofing">Roofing</option>
					  <option value="Carpentry">Carpentry</option>
					  <option value="Bricklaying">Bricklaying</option>					  
					  <option value="Office">Office</option>
					</select>
			    </div>
			    <div class="mb-3 mt-3">
			    	<input type="hidden" name="EmppKey" value='${employee.getEmppKey()}'>
			    	<input type="hidden" name="USpKey" value='${user.getUSpKey()}'>
			    	<input type="hidden" name="actiontype" value='updateEmployee'>
			    	<input type="submit" class="btn btn-primary" value="Save">
			    	<input type="reset" class="btn btn-primary" value="Reset">
			    </div>
			  </form>
			  
		</div>	
	</div>
	<script type="text/javascript">
	setTimeout(function() {
		$('#EmpType option[value="${employee.empType}"]').attr('selected','selected');
		$('#EmpDept option[value="${employee.employeeDepartment}"]').attr('selected','selected');
		$('#EmpDoJ').datepicker();
		$('#EmpDoJ').datepicker("setDate", new Date('${employee.empDoJ}'));
	});
	</script>	
<%}  else {%>	
	<a href="LoginPage.jsp"> Login </a>
	<%}%>
</body>
</html>