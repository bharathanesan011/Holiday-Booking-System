<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html class="banner-login">
<head>
	<link rel="stylesheet" href="CSS/Style.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<link rel="stylesheet" href="CSS/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
	<title>Login</title>
</head>
<body>
	<div class="banner-login">
		<div class="overlay container col-md-5 center_div">
			<span class="Error">${InvalidLogin}</span>
			<h1 class="text-center">Login</h1>
			<form method="POST">
				 <div class="mb-3 mt-3">
				    <label for="email">Email:</label>
					<input type="text" class="form-control" id="email" name="email" required>
				</div>
				<div class="mb-3 mt-3">
					<label for="password">Password:</label>
					<input type="password" class="form-control" id="password" name="password" required>
				</div>			    
			    <div class="mb-3 mt-3">
			    	<input type="submit" class="btn btn-primary" value="Login">
			    </div>
			  </form>	
			 <%			 	 
			 	Boolean InvalidLoginErr=(Boolean)request.getAttribute("InvalidLoginErr");
			 	if(InvalidLoginErr == null)
			 		InvalidLoginErr=false;
		      	if (request.getParameter("email") != null &&
		      		request.getParameter("password") != null && !InvalidLoginErr
		      		)
		      	{
		      		//alert(1);
		      		RequestDispatcher rd = request.getRequestDispatcher("HolidayBookingSystemServlet?actiontype=login");
		      		request.setAttribute("actiontype", "login");
		      		request.setAttribute("email", request.getParameter("email"));
		      		request.setAttribute("password", request.getParameter("password"));
		      		rd.forward(request, response);
		      	}
		      %>	
		</div>	
	</div>
</body>
</html>