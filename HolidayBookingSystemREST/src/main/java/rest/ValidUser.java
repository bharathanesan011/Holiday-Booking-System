package rest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import model.Userlogin;

@RequestScoped
@Path("ValidUser")
@Produces({"application/xml","text/plain"})
@Consumes({"application/xml","text/plain"})
public class ValidUser {
	
	@PersistenceContext(unitName = "HolidayBookingSystemREST")
	EntityManager em;
	 @Resource
	 private UserTransaction userTransaction;
	
	 @GET
	 @Path("/{parm}/{parm2}")
	 public String insertLeaverequest(@PathParam("parm") String parm,@PathParam("parm2") String parm2) {
		boolean val=isValidUser(parm, parm2);    	   	
		 String result;
		result = "<result>";
		result += val;
		result += "</result>";
		return result;
	 }
	public boolean isValidUser(String email, String password) {
		// TODO Auto-generated method stub
		Userlogin user=new Userlogin();
		Userlogin userd=new Userlogin();
		try {
			user =  (Userlogin) em.createNamedQuery("Userlogin.findLogin", Userlogin.class)
					.setParameter("email", email)
					.setParameter("password", generateSecurePassword(password))
					.getSingleResult();
			if(user != null) {
				userd.setUSpKey(user.getUSpKey());
				userd.setUserEmail(user.getUserEmail());
				userd.setUserPassword(user.getUserPassword());
				userd.setIsAdmin(user.getIsAdmin());
				return true;
			}
			
		}catch(NoResultException nre) {
			
		}
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
}
