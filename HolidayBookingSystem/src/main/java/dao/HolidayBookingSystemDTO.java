package dao;

import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Employee;
import model.Leaverequest;
import model.Userlogin;

/**
 * Session Bean implementation class HolidayBookingSystemDTO
 */
@Stateless
@LocalBean
public class HolidayBookingSystemDTO {
	
	@PersistenceContext(unitName="HolidayBookingSystem")
	EntityManager em;
 
    /**
     * Default constructor. 
     */
    public HolidayBookingSystemDTO() {
        // TODO Auto-generated constructor stub
    }
    
    public List<Leaverequest> allRequests(){
    	List<?> queryResults = em.createQuery("SELECT l FROM leaverequest l").getResultList();
    	List<Leaverequest> requestList = new ArrayList<Leaverequest>();
    	
    	for(int i = 0; i < queryResults.size(); i++)
    	{
    		Leaverequest l = new Leaverequest();
    		l = (Leaverequest)queryResults.get(i);
    		requestList.add(l);
    	}
    	return requestList;
    }
    
    public boolean insertLeaverequest(Date date, String desc, int USpKey)
    {
    	Employee e= getEmployeeByID(USpKey);
    	Leaverequest l = new Leaverequest();
    	l.setLRDate(date);
    	//l.setLRDate(to);
    	l.setLRDescription(desc);
    	l.setLRStatus("Awaiting Approval");    	
    	l.setTotalDay(1);
    	l.setEmployee1(e);
    	if(e.getAvailableHoliday()>=1)
    	{    		
    		em.persist(l);
    		e.setAvailableHoliday(e.getAvailableHoliday()-1);
    		em.persist(e);
    	}
    	else
    		return false;
    	return true;
    }
    
    public Employee getEmployeeByID(int id) {
    	//change
    	List<Employee> listEmployee = em.createNamedQuery("Employee.findbyLID", Employee.class).setParameter("id", id).getResultList();
    	return listEmployee.get(0);
    }
    
    public Employee getEmployeeByEID(int id) {
    	//change
    	List<Employee> listEmployee = em.createNamedQuery("Employee.findbyID", Employee.class).setParameter("id", id).getResultList();
    	return listEmployee.get(0);
    }
    
	public Userlogin isValidUser(String email, String password) {
		// TODO Auto-generated method stub
		Userlogin user=new Userlogin();
		try {
			user =  (Userlogin) em.createNamedQuery("Userlogin.findLogin", Userlogin.class)
					.setParameter("email", email)
					.setParameter("password", generateSecurePassword(password))
					.getSingleResult();
		}catch(NoResultException nre) {
			
		}
		return user;
	}

	public boolean insertUser(Userlogin user, Employee employee) {
		// TODO Auto-generated method stub
		boolean update=false; 
		if(employee.getEmpType().equalsIgnoreCase("Head")||employee.getEmpType().equalsIgnoreCase("Deputy Head")) {
			update = validAdminUser(employee);
		}
		else {
			update=true;
		}
		if(update) {
			user.setUserPassword(generateSecurePassword(user.getUserPassword()));		
			em.persist(user);		
			employee.setUserlogin(user);
			em.persist(employee);
			return true;
		}
		return false;
	}
	
	private boolean validAdminUser(Employee employee) {
		// TODO Auto-generated method stub
		List<Employee> e= em.createNamedQuery("Employee.findvalidAdminUser", Employee.class)
				.setParameter("type", employee.getEmpType())
				.setParameter("dept", employee.getEmployeeDepartment())
				.getResultList();
		if(e.isEmpty())
			return true;
		return false;
	}

	private String generateSecurePassword(String Password) {
		String generatedPassword = null;
		try 
	    {
	      // Create MessageDigest instance for MD5
	      MessageDigest md = MessageDigest.getInstance("MD5");

	      // Add password bytes to digest
	      md.update(Password.getBytes());

	      // Get the hash's bytes
	      byte[] bytes = md.digest();

	      // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
	      StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < bytes.length; i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	      }

	      // Get complete hashed password in hex format
	      generatedPassword = sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }
	    return generatedPassword;
	}

	public List<Employee> getAllEmployee() {
		// TODO Auto-generated method stub
		List<Employee> listEmployee= em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
		return listEmployee;
	}

	public boolean updateEmployeeUser(Userlogin user, Employee employee) {
		// TODO Auto-generated method stub
		Employee nEmployee = em.find(Employee.class, employee.getEmppKey());
		Userlogin nUser= em.find(Userlogin.class, user.getUSpKey());
		
		boolean update=false; 
		if(employee.getEmpType().equalsIgnoreCase("Head")||employee.getEmpType().equalsIgnoreCase("Deputy Head")) {
			update = validAdminUser(employee);
		}
		else {
			update=true;
		}
		if(update) {
		//nEmployee.setEmppKey(employee.getEmppKey());
	  		nEmployee.setEmpName(employee.getEmpName());
	  		nEmployee.setEmpAddress(employee.getEmpAddress());
	  		nEmployee.setEmpEmail(employee.getEmpEmail());
	  		nEmployee.setEmpPhone(employee.getEmpPhone());
			//SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yyyy");					
	  		nEmployee.setEmpDoJ(employee.getEmpDoJ());
	  		nEmployee.setEmpType(employee.getEmpType());
	  		nEmployee.setEmployeeDepartment(employee.getEmployeeDepartment());
	  		
	  		//nUser.setUSpKey(Integer.parseInt(request.getParameter("USpKey")));
	  		nUser.setUserEmail(user.getUserEmail());  		
	  		nUser.setIsAdmin(user.getIsAdmin());
			
			em.persist(nEmployee);
			em.persist(nUser);
			return true;
		}
		return false;
	}

	public void deleteEmployeeByID(int id) {
		// TODO Auto-generated method stub
		Employee e = getEmployeeByEID(id);
		Userlogin u=e.getUserlogin();
		em.remove(e); 
		em.remove(u);
	}

	public List<Leaverequest> getLeaveRequestByID(int id) {
		// TODO Auto-generated method stub
		Employee e=getEmployeeByID(id);
		List<Leaverequest> listRequest= em.createNamedQuery("Leaverequest.findAllbyId", Leaverequest.class)
				.setParameter("id",e.getEmppKey())
				.getResultList();
		return listRequest;
	}

	public List<Leaverequest> getallLeaveRequestByAdminID(int id) {
		// TODO Auto-generated method stub
		Employee e=getEmployeeByID(id);
		List<Leaverequest> listRequest= em.createNamedQuery("Leaverequest.findAllbyAdminDept", Leaverequest.class)
				.setParameter("dept",e.getEmployeeDepartment())
				.setParameter("status", "Awaiting Approval")
				.getResultList();
		return listRequest;
	}
	public List<Leaverequest> getallLeaveApprovedByAdminID(int id) {
		// TODO Auto-generated method stub
		Employee e=getEmployeeByID(id);
		List<Leaverequest> listRequest= em.createNamedQuery("Leaverequest.findAllbyAdminDept", Leaverequest.class)
				.setParameter("dept",e.getEmployeeDepartment())
				.setParameter("status", "Approve")
				.getResultList();
		return listRequest;
	}
	
	public List<Leaverequest> getallLeavePApprovedByAdminID(int id) {
		// TODO Auto-generated method stub
		Employee e=getEmployeeByID(id);
		int easterDay = getEasterDay(new Date().getYear()+1900);
    	int easterMonth = getEasterMonth(new Date().getYear()+1900);
		List<Leaverequest> listRequest= em.createNamedQuery("Leaverequest.getSortedPendingRequestBasedonPeakTime", Leaverequest.class)
				.setParameter("dept",e.getEmployeeDepartment())
				.setParameter("status", "Approve")
				.setParameter("easter", easterMonth + "-" + easterDay)
				.setParameter("weekBeforeEaster", easterMonth + "-" + (easterDay-7))
				.setParameter("weekAfterEaster", easterMonth + "-" + (easterDay+7))
				.getResultList();
		
		return listRequest;
	}

	public void updateLeaveRequest(int id, int lRpKey, String aprRej, String comment) {
		// TODO Auto-generated method stub
		Employee e=getEmployeeByID(id);
		Leaverequest l = (Leaverequest) em.createNamedQuery("Leaverequest.findAllbyLRId",  Leaverequest.class)
							.setParameter("LRpKey", lRpKey)
							.getSingleResult();
		l.setLRStatus(aprRej);
		l.setLRComment(comment);
		l.setEmployee2(e);
		em.persist(l);
	}

	public List<Leaverequest> getallLeaveRequestByIDandName(int id, String name) {
		// TODO Auto-generated method stub
		//Employee e=getEmployeeByID(id);
		List<Leaverequest> listRequest = new ArrayList<Leaverequest>();
		if(name!=null) {
			listRequest = em.createNamedQuery("Leaverequest.findAllName", Leaverequest.class)
					.setParameter("name", name)
					.getResultList();
		}
		else {
			listRequest = em.createNamedQuery("Leaverequest.findAll", Leaverequest.class)				
					.getResultList();
		}
		
		return listRequest;
	}

	public List<Employee> getAllEmployeeNW(Date date) {
		// TODO Auto-generated method stub
		List<Employee> e= em.createNamedQuery("Employee.findENW", Employee.class)
				.setParameter("date", date)
				.setParameter("status", "Approve")
				.getResultList();
		return e;
	}

	public int getAllEmployeeByDept(String employeeDepartment) {
		// TODO Auto-generated method stub
		List<Employee> e= em.createNamedQuery("Employee.finddept", Employee.class)
				.setParameter("dept", employeeDepartment)
				.getResultList();
		return e.size();
	}
	
	public int getAllEmployeeByDept(String empType, String employeeDepartment) {
		// TODO Auto-generated method stub
		List<Employee> e= em.createNamedQuery("Employee.findvalidAdminUser", Employee.class)
				.setParameter("type", empType)
				.setParameter("dept", employeeDepartment)
				.getResultList();
		return e.size();
	}

/* method to prioritize the pending requests */
	
	public List<Leaverequest> prioritizeRequest(){
		
		List<Leaverequest> prioretizedRequestsList = new ArrayList<>();
		
		TypedQuery query = em.createNamedQuery("Leaverequest.getPendingRequests",Leaverequest.class);
		query.setParameter("status","Awaiting Approval");
		List<Leaverequest> pendingRequests = query.getResultList();
		List<Employee> employees = new ArrayList<>();
		
		pendingRequests.stream().forEach(request -> {
			employees.add(request.getEmployee1());
		});
		
		// to get all the approved requests in the current year(sorted order from less holidays to more holidays) for the leaveRequests applied employees
		
		TypedQuery query1 = em.createNamedQuery("Leaverequest.prioretizedRequestList",Leaverequest.class);
    	query1.setParameter("status", "Approve");
    	query1.setParameter("employee", employees);
    	List<Leaverequest> queryResults = query1.getResultList();

    
    	// sort the pending requests in the order we got in above implementation
    	
    	queryResults.stream().forEach(result -> {
    		TypedQuery<Leaverequest> query3 = em.createNamedQuery("Leaverequest.getSortedPendingRequest", Leaverequest.class);
    	    query3.setParameter("Employee", result.getEmployee1());
    	    query3.setParameter("status", "Awaiting Approval");
    	    Leaverequest  leaveRequest = (Leaverequest) query3.getSingleResult();
    	    prioretizedRequestsList.add(leaveRequest);
    	    
    	});
    	
    	List<Leaverequest> finalList = new ArrayList<>();
    	
    	//if any employee has not taken any leave in the current year
    	pendingRequests.stream().forEach(pendingRequest -> {
    		if(!prioretizedRequestsList.contains(pendingRequest)) {
    			finalList.add(pendingRequest);
    		}
    	});
    	
    	finalList.addAll(prioretizedRequestsList);
    	
    	// getting easter day and easter month for the current year
    	
    	int easterDay = getEasterDay(new Date().getYear()+1900);
    	int easterMonth = getEasterMonth(new Date().getYear()+1900);
    
    		
    	// sorting request based on peak times
    	
    	TypedQuery query2 = em.createNamedQuery("Leaverequest.getSortedPendingRequestBasedonPeakTime",Leaverequest.class);
    	query2.setParameter("status",  "Approve");
    	query2.setParameter("employee", employees);
    	query2.setParameter("easter", easterMonth + "-" + easterDay);
    	query2.setParameter("weekBeforeEaster", easterMonth + "-" + (easterDay-7));
    	query2.setParameter("weekAfterEaster", easterMonth + "-" + (easterDay+7));
    	List<Leaverequest> queryResults1 = query2.getResultList();
    	
    	// sort the pending requests in the order we got in above implementation
    	
    	queryResults1.stream().forEach(result -> {
    		System.out.println(result.getLRpKey());
    		TypedQuery<Leaverequest> query3 = em.createNamedQuery("Leaverequest.getSortedPendingRequest", Leaverequest.class);
    	    query3.setParameter("Employee", result.getEmployee1());
    	    query3.setParameter("status", "Awaiting Approval");
    	    Leaverequest  leaveRequest = (Leaverequest) query3.getSingleResult();
    	    if(!prioretizedRequestsList.contains(leaveRequest)) {
    	        finalList.add(leaveRequest);
    	    }
    		
    	});
    	
    	
		return finalList;
	}
	
	/* method to suggest alternative day */
	
	public Date suggestAlternativeDate(Leaverequest leaveRequest) {
		
		Date requestedDate = leaveRequest.getLRDate();
		Date alternativeDate = null;
		boolean canSuggest = false;
		
		for(int i=0 ; i<=30 ; i++) {
			
			 Instant instant = requestedDate.toInstant();
		     Instant alternativeInstant = instant.plus(i, ChronoUnit.DAYS);
		     alternativeDate = Date.from(alternativeInstant);
		     
		     canSuggest = checkForAlternativeDate(leaveRequest, alternativeDate);
		     if(canSuggest == true) {
		    	 break;
		     }
		}
		
		if(canSuggest == true) {
		 return alternativeDate;
		}else {
			return null;
		}
		
		
	}
	
	public boolean checkForAlternativeDate(Leaverequest leaveRequest,Date alternativeDate) {
		   if(alternativeDate.getYear()!= new Date().getYear()) {
		    	 return false;
		   }
			//check for constraints
			return true;
		}
		
		/* method to get easter day */
		
		public int getEasterDay(int year) {
			Easter easterDay = new Easter(year);
			int easterDate = easterDay.getEasterSundayDay();
			return easterDate;
		}
		
		/* method to get easter month */
		
		public int getEasterMonth(int year) {
			Easter easterDay = new Easter(year);
			int easterMonth = easterDay.getEasterSundayMonth();
			return easterMonth;
		}
	

}
