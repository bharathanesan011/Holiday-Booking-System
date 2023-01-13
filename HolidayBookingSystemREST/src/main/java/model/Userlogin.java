package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the userlogin database table.
 * 
 */
@Entity
@NamedQuery(name="Userlogin.findAll", query="SELECT u FROM Userlogin u")
public class Userlogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int USpKey;

	private byte isAdmin;

	private String userEmail;

	private String userPassword;

	//bi-directional many-to-one association to Employee
	@OneToMany(mappedBy="userlogin")
	private List<Employee> employees;

	public Userlogin() {
	}

	public int getUSpKey() {
		return this.USpKey;
	}

	public void setUSpKey(int USpKey) {
		this.USpKey = USpKey;
	}

	public byte getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(byte isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public List<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee addEmployee(Employee employee) {
		getEmployees().add(employee);
		employee.setUserlogin(this);

		return employee;
	}

	public Employee removeEmployee(Employee employee) {
		getEmployees().remove(employee);
		employee.setUserlogin(null);

		return employee;
	}

}