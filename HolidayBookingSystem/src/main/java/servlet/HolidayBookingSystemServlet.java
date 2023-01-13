package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import dao.HolidayBookingSystemDTO;
import model.Employee;
import model.Userlogin;
import model.Leaverequest;

/**
 * Servlet implementation class HolidayBookingSystemServlet
 */
@WebServlet("/HolidayBookingSystemServlet")
public class HolidayBookingSystemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private HolidayBookingSystemDTO hbsDTO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HolidayBookingSystemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @return 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Context jndiContext;
		HttpSession session=request.getSession();
		String param_action = request.getParameter("actiontype");
		String tableStr = new String();
		try {
			jndiContext = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory) jndiContext.lookup("java:/ConnectionFactory");
			Queue calculationQueue = (Queue) jndiContext.lookup("java:/jms/HolidayQueue");
			Connection connect = factory.createConnection();
			Session session1 = connect.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer sender = session1.createProducer(calculationQueue);
			switch(param_action) {
				case "login":
				{
					String email = request.getParameter("email");
					String password = request.getParameter("password");
					Userlogin user=hbsDTO.isValidUser(email, password);
					if(user.getUSpKey()>0 && user.getIsAdmin()==1) {
						tableStr += "<br/><strong>Login Admin </strong>";
						session.setAttribute("User", user);
						RequestDispatcher dispatcher = request.getRequestDispatcher("AdminPage.jsp");
						dispatcher.forward(request, response); 
					}
					else if(user.getUSpKey()>0 && user.getIsAdmin()==0) {
						session.setAttribute("User", user);
						tableStr += "<br/><strong>Login non admin </strong>";
						RequestDispatcher dispatcher = request.getRequestDispatcher("submitRequest.jsp");
						dispatcher.forward(request, response); 
					}
					else {
						tableStr += "<br/><strong>Login failed </strong>";
						/*FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "error logging in", "Error logging in"));
				        return null;*/
						request.setAttribute("InvalidLogin", "Invalid Login Credentials!!!");
						request.setAttribute("InvalidLoginErr", true);
						RequestDispatcher dispatcher = request.getRequestDispatcher("LoginPage.jsp");
						dispatcher.forward(request, response); 
						
					}
				}
				break;
				case "insertLeaverequest":
				{
					String desc = request.getParameter("desc");
					int  id = Integer.parseInt(request.getParameter("userKey"));
					SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
					Date date = (Date) formatter.parse(request.getParameter("LRdate"));
					
					boolean requestReponse=hbsDTO.insertLeaverequest(date,desc,id);
					if(requestReponse) {
						MapMessage message = session1.createMapMessage();
						String userId = request.getParameter("userKey");
						message.setString("userId", userId);
						sender.send(message);
						connect.close();
						tableStr += " <br/><strong>New Requests were sent to the MDB</strong>";
						request.getRequestDispatcher("HolidayBookingSystemServlet?actiontype=viewRequest&id="+id).forward(request, response);
					}
					else {
						request.setAttribute("MSG", "Applied Leave more then Available Holiday");
						request.getRequestDispatcher("submitRequest.jsp").forward(request, response);
					}
					tableStr += "<br/><strong>Request Submitted</strong>";
				}
				break;
				case "insertEmployee":
				{
					
					Employee nEmployee = new Employee();
					Userlogin nUser=new Userlogin();					
					
					nEmployee.setEmpName(request.getParameter("EmpName"));
		      		nEmployee.setEmpAddress(request.getParameter("EmpAddress"));
		      		nEmployee.setEmpEmail(request.getParameter("EmpEmail"));
		      		nEmployee.setEmpPhone(request.getParameter("EmpPhone"));
					SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");					
		      		nEmployee.setEmpDoJ( (Date) formatter.parse(request.getParameter("EmpDoJ")));
		      		nEmployee.setEmpType(request.getParameter("EmpType"));
		      		nEmployee.setEmployeeDepartment(request.getParameter("EmpDept"));
		      		
		      		Calendar today = Calendar.getInstance();
		      		 Calendar cal = Calendar.getInstance(Locale.US);
		      	    cal.setTime(nEmployee.getEmpDoJ());
		      		int yearsInBetween = today.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
		      		nEmployee.setTotalHoliday(30+(yearsInBetween/5));
		      		nEmployee.setAvailableHoliday(30+(yearsInBetween/5));
		      		
		      		nUser.setUserPassword(request.getParameter("EmpPassword"));
		      		nUser.setUserEmail(request.getParameter("EmpEmail"));
		      		if(request.getParameter("EmpType").equals("Head"))
		      			nUser.setIsAdmin((byte)1);
		      		else
		      			nUser.setIsAdmin((byte)0);
					
					
					boolean insert=hbsDTO.insertUser(nUser,nEmployee);
					if(insert) {
					List<Employee> employees= hbsDTO.getAllEmployee();
					session.setAttribute("employees", employees);
					RequestDispatcher dispatcher = request.getRequestDispatcher("ViewEmployee.jsp");
					dispatcher.forward(request, response); 
					}
					else {
						request.setAttribute("MSG", nEmployee.getEmpType()+" type already in "+nEmployee.getEmployeeDepartment()+" Department");
						request.getRequestDispatcher("AddEmployee.jsp").forward(request, response);
					}
				}
				break;
				case "updateEmployee":
				{
					Employee nEmployee = new Employee();
					Userlogin nUser=new Userlogin();					
					
					nEmployee.setEmppKey(Integer.parseInt(request.getParameter("EmppKey")));
		      		nEmployee.setEmpName(request.getParameter("EmpName"));
		      		nEmployee.setEmpAddress(request.getParameter("EmpAddress"));
		      		nEmployee.setEmpEmail(request.getParameter("EmpEmail"));
		      		nEmployee.setEmpPhone(request.getParameter("EmpPhone"));
					SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");					
		      		nEmployee.setEmpDoJ( (Date) formatter.parse(request.getParameter("EmpDoJ")));
		      		nEmployee.setEmpType(request.getParameter("EmpType"));
		      		nEmployee.setEmployeeDepartment(request.getParameter("EmpDept"));
		      		
		      		nUser.setUSpKey(Integer.parseInt(request.getParameter("USpKey")));
		      		nUser.setUserEmail(request.getParameter("EmpEmail"));
		      		if(request.getParameter("EmpType").equals("Head"))
		      			nUser.setIsAdmin((byte)1);
		      		else
		      			nUser.setIsAdmin((byte)0);
		      		boolean insert= hbsDTO.updateEmployeeUser(nUser,nEmployee);	
		      		if(insert) {
						List<Employee> employees= hbsDTO.getAllEmployee();
						session.setAttribute("employees", employees);
						RequestDispatcher dispatcher = request.getRequestDispatcher("ViewEmployee.jsp");
						dispatcher.forward(request, response); 
		      		}
		      		else {
						request.setAttribute("MSG", nEmployee.getEmpType()+" type already in "+nEmployee.getEmployeeDepartment()+" Department");
						request.getRequestDispatcher("HolidayBookingSystemServlet?actiontype=editEmployee&id="+nEmployee.getEmppKey()).forward(request, response);
					}
				}
				break;
				case "viewEmployee":
				{
					List<Employee> employees= hbsDTO.getAllEmployee();
					session.setAttribute("employees", employees);
					RequestDispatcher dispatcher = request.getRequestDispatcher("ViewEmployee.jsp");
					dispatcher.forward(request, response); 
				}
				break;
				case "editEmployee":
				{
					int  id = Integer.parseInt(request.getParameter("id"));
					Employee employee= hbsDTO.getEmployeeByEID(id);
					session.setAttribute("employee", employee);
					session.setAttribute("user", employee.getUserlogin());
					RequestDispatcher dispatcher = request.getRequestDispatcher("EditEmployee.jsp");
					dispatcher.forward(request, response); 
				}
				break;
				case "deleteEmployee":
				{
					int  id = Integer.parseInt(request.getParameter("id"));
					hbsDTO.deleteEmployeeByID(id);
					List<Employee> employees= hbsDTO.getAllEmployee();
					session.setAttribute("employees", employees);
					RequestDispatcher dispatcher = request.getRequestDispatcher("ViewEmployee.jsp");
					dispatcher.forward(request, response); 
				}
				break;
				case "viewRequest":
				{
					int  id = Integer.parseInt(request.getParameter("id"));
					List<Leaverequest> listRequest= hbsDTO.getLeaveRequestByID(id);
					session.setAttribute("listRequest", listRequest);
					RequestDispatcher dispatcher = request.getRequestDispatcher("viewRequest.jsp");
					dispatcher.forward(request, response); 
				}
				break;
				
				case "employeeRequest":
				{
					int  id = Integer.parseInt(request.getParameter("id"));
					List<Leaverequest> listRequest= hbsDTO.getallLeaveRequestByAdminID(id);
					listRequest = SortListByPeak(id,listRequest);
					List<Leaverequest> listApproved= hbsDTO.getallLeaveApprovedByAdminID(id);
					List<List<Leaverequest>> list = getListByCondition(listRequest,listApproved);
					session.setAttribute("listApprove", list.get(0));
					session.setAttribute("listReject", list.get(1));
					RequestDispatcher dispatcher = request.getRequestDispatcher("employeeRequest.jsp");
					dispatcher.forward(request, response); 
				}
				break;
				case "aprRejRequest":
				{
					int  id = Integer.parseInt(request.getParameter("id"));
					int  LRpKey = Integer.parseInt(request.getParameter("LRpKey"));
					String aprRej = request.getParameter("aprrej");
					String comment= request.getParameter("comment");
					hbsDTO.updateLeaveRequest(id,LRpKey,aprRej,comment);
					RequestDispatcher dispatcher = request.getRequestDispatcher("HolidayBookingSystemServlet?actiontype=employeeRequest&id="+id);
					dispatcher.forward(request, response); 
				}
				break;
				case "allLeaveRequest":
				{
					int  id = Integer.parseInt(request.getParameter("id"));
					String  name = request.getParameter("name");		
					List<Leaverequest> listRequest= hbsDTO.getallLeaveRequestByIDandName(id,name);
					session.setAttribute("listRequest", listRequest);
					RequestDispatcher dispatcher = request.getRequestDispatcher("allLeaveRequest.jsp");
					dispatcher.forward(request, response);
				}
				break;
				case "getEmployeeByDate":
				{
					SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");					
		      		Date date=( (Date) formatter.parse(request.getParameter("date")));
					List<List<Employee>> list = getEmployeeByDate(date);
					session.setAttribute("listWorking", list.get(1));
					session.setAttribute("listNotWorking", list.get(0));
					RequestDispatcher dispatcher = request.getRequestDispatcher("AdminPage.jsp");
					dispatcher.forward(request, response);
				}
				break;
				
			/*
			 * case "actionRequest": { int lid =
			 * Integer.parseInt(request.getParameter("lid")); int uid =
			 * Integer.parseInt(request.getParameter("uid"));
			 * 
			 * 
			 * } break;
			 */
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title> Holiday Booking System</title>");
		out.println("</head>");
		
		out.println("<body>");
		out.println(tableStr);
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	

	private List<Leaverequest> SortListByPeak(int id, List<Leaverequest> listRequest) {
		List<Leaverequest> finalList= new ArrayList<Leaverequest>();
		List<Leaverequest> rList= new ArrayList<Leaverequest>();
		List<Leaverequest> pList = hbsDTO.getallLeavePApprovedByAdminID(id);		
		for( Leaverequest l : listRequest) {
			boolean flag=true;
			for (Leaverequest pl : pList) {
				if(l.getEmployee1().equals(pl.getEmployee1())) {
					flag=false;
					break;
				}
			}
			if(flag==true)
				finalList.add(l);
			else
				rList.add(l);
		}
		finalList.addAll(rList);
		return finalList;
	}

	private List<List<Leaverequest>> getListByCondition(List<Leaverequest> listRequest, List<Leaverequest> listApproved) {
		// TODO Auto-generated method stub
		List<Leaverequest> approve= new ArrayList<Leaverequest>();
		List<Leaverequest> reject=new ArrayList<Leaverequest>();
		
		try {
		
		for(Leaverequest l : listRequest) {
			boolean flag=true;
			String reason="";
			SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");
			Date sdate,edate;			
			sdate = (Date) formatter.parse("12/23/2022");
			edate = (Date) formatter.parse("01/03/2023");
			
			if(!(l.getLRDate().after(sdate) && (l.getLRDate().before(edate)))) {
				if(l.getEmployee1().getEmpType().equals("Head")) {
					List<Leaverequest> t = listApproved.stream().filter(r -> r.getEmployee1().getEmpType().equals("Deputy Head") && r.getLRDate().equals(l.getLRDate()))
							.collect(Collectors.toList());
					if(t.size()>0) {
						List<Leaverequest> t1 = t.stream().filter(r -> r.getLRDate().equals(l.getLRDate()))
								.collect(Collectors.toList());
						if(t1.size()>0) {
							reason = reason+"Deputy Head on Leave \n";
							flag=false;
						}
					}
				}
				else if(l.getEmployee1().getEmpType().equals("Deputy Head")) {
					List<Leaverequest> t = listApproved.stream().filter(r -> r.getEmployee1().getEmpType().equals("Head") && r.getLRDate().equals(l.getLRDate()))
							.collect(Collectors.toList());
					if(t.size()>0) {
						List<Leaverequest> t1 = t.stream().filter(r -> r.getLRDate().equals(l.getLRDate()))
								.collect(Collectors.toList());
						if(t1.size()>0) {
							reason = reason+"Head on Leave \n";
							flag=false;
						}
					}
				}//
				else if(l.getEmployee1().getEmpType().equals("Manager") || l.getEmployee1().getEmpType().equals("Senior member")) {
					List<Leaverequest> t = listApproved.stream().filter(r -> r.getEmployee1().getEmpType().equals(l.getEmployee1().getEmpType())&& r.getLRDate().equals(l.getLRDate()))
							.collect(Collectors.toList());
					int total= hbsDTO.getAllEmployeeByDept(l.getEmployee1().getEmpType(),l.getEmployee1().getEmployeeDepartment());
					if(total-t.size()-1==0) {						
							reason = reason+"All "+l.getEmployee1().getEmpType()+" on Department on Leave \n";
							flag=false;
						
					}
				}				
				Date asdate,aedate;
				asdate = (Date) formatter.parse("08/01/2022");
				aedate = (Date) formatter.parse("08/31/2022");
				int total=hbsDTO.getAllEmployeeByDept(l.getEmployee1().getEmployeeDepartment());
				List<Leaverequest> t = listApproved.stream().filter(r -> r.getEmployee1().getEmployeeDepartment().equals(l.getEmployee1().getEmployeeDepartment())&& r.getLRDate().equals(l.getLRDate()))
						.collect(Collectors.toList());
				//double size=((t.size()+1)/total);
				double percentage=((double)(t.size()+1)/(double)total)*100;
				if(!(l.getLRDate().after(asdate) && (l.getLRDate().before(aedate)))) {					
					if(100-percentage<60) {
						reason = reason+"Total employee less than 60% \n";
						flag=false;
					}
				}
				else {
					if(100-percentage<40) {
						reason = reason+"Total employee less than 40%(August) \n";
						flag=false;
					}
				}
				if(flag==true) {
					approve.add(l);
				}
				else {
					l.setLRComment(reason);
					reject.add(l);
				}
			}
		}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<List<Leaverequest>> result= new ArrayList<List<Leaverequest>>();
		result.add(approve);
		result.add(reject);
		return result;
	}

	private List<List<Employee>> getEmployeeByDate(Date date) {
		// TODO Auto-generated method stub
		List<Employee> ew= hbsDTO.getAllEmployee();
		List<Employee> enw=hbsDTO.getAllEmployeeNW(date);
		List<Employee> newl= new ArrayList<Employee>();
		if(enw.size()>0) {
			for (Employee e : ew) {
				boolean flag=false;
				for(Employee en : enw) {
					if(e.getEmppKey()==en.getEmppKey())
						flag=true;
						
				}
				if(!flag)
					newl.add(e);
			}
		}
		else
			newl=ew;		
		 //ew.removeAll(enw);
		List<List<Employee>> result= new ArrayList<List<Employee>>();
		result.add(enw);
		result.add(newl);
		return result;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	

}
