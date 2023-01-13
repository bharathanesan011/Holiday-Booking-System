<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="model.Userlogin"
    %>
<!DOCTYPE html>
<html class="banner">
<head>
	<meta charset="ISO-8859-1">
	<link rel="stylesheet" href="CSS/Style.css">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<link rel="stylesheet" href="CSS/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
	<title>Submit Request</title>	
	<script>
	  $( function() {
	    $( "#LRdate" ).datepicker({
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
			int userKey=user.getUSpKey();
			session.setAttribute("User", user);
	%>
	<div class="banner">
		<%@ include file = "Header.jsp" %>
		<div class="overlay container col-md-5 center_div">
			
			<h1 class="text-center"> Leave Request</h1>
			<span class="Error">${MSG}</span>
			<form method="GET" action='HolidayBookingSystemServlet'>				 
				<div class="mb-3 mt-3">
					<label for="LRdate">Date<sup>*</sup>:</label>
					<input type="text" class="form-control" id="LRdate" name="LRdate" required>
				</div>
			    <div class="mb-3 mt-3">
			    	<label for="desc">Description<sup>*</sup>:</label>
					<textarea class="form-control" id="desc" name="desc" rows="3" required></textarea>
			    </div>
			    <div class="mb-3 mt-3">
			    	<input type="hidden" name="actiontype" value="insertLeaverequest">			    	
			    	<input type="hidden" name="userKey" value="<%=userKey %>">
			    	<input type="submit" class="btn btn-primary" value="Submit">
			    </div>
			  </form>
			  
		</div>	
	</div>
	<%}  else {%>	
	<a href="LoginPage.jsp"> Login </a>
	<%}%>
	<script>
	$( function() {
	    var dateFormat = "mm/dd/yy",
	      from = $( "#from" )
	        .datepicker({
	          defaultDate: "+1w",
	          changeMonth: true,
	          changeYear: true,
	          numberOfMonths: 2
	        })
	        .on( "change", function() {
	          to.datepicker( "option", "minDate", getDate( this ) );
	        }),
	      to = $( "#to" ).datepicker({
	        defaultDate: "+1w",
	        changeMonth: true,
	        changeYear: true,
	        numberOfMonths: 2
	      })
	      .on( "change", function() {
	        from.datepicker( "option", "maxDate", getDate( this ) );
	      });
	 
	    function getDate( element ) {
	      var date;
	      try {
	        date = $.datepicker.parseDate( dateFormat, element.value );
	      } catch( error ) {
	        date = null;
	      }
	 
	      return date;
	    }
	  } );
	 </script>
</body>
</html>