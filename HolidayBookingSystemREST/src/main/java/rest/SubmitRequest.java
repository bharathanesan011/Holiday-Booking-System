package rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import model.Employee;
import model.Leaverequest;

@RequestScoped
@Path("SubmitRequest")
@Produces({"application/xml","text/plain"})
@Consumes({"application/xml","text/plain"})
public class SubmitRequest {
	

	@PersistenceContext(unitName = "HolidayBookingSystemREST")
	EntityManager em;
	 @Resource
	 private UserTransaction userTransaction;
	
	@GET
	@Path("/{parm}/{parm2}/{parm3}")
	public String insertLeaverequest(@PathParam("parm") int parm, @PathParam("parm2") String parm2, @PathParam("parm3") String parm3) {
		
		int USpKey= parm; 
    	SimpleDateFormat formatter=new SimpleDateFormat("MM-dd-yyyy");
		Date LRdate = new Date();
		try {
			LRdate = (Date) formatter.parse(parm2);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 insertLeaverequest(LRdate, parm3,  USpKey);    	   	
		 String result;
		result = "<result>";
		result += "Inserted";
		result += "</result>";
			return result;
	}
	
	 	public void insertLeaverequest(Date date, String desc, int USpKey)
	    {
	 		try {
			userTransaction.begin();
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
	    	userTransaction.commit();
			} catch (NotSupportedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (HeuristicMixedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (HeuristicRollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 		
	    	
	    }
		
	 	
	
	public Employee getEmployeeByID(int id) {
    	//change
    	List<Employee> listEmployee = em.createNamedQuery("Employee.findbyLID", Employee.class).setParameter("id", id).getResultList();
    	return listEmployee.get(0);
    }
	
}
