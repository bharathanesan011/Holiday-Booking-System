package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the employee database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e"),
	@NamedQuery(name="Employee.findbyLID", query="SELECT e FROM Employee e WHERE e.userlogin.USpKey = :id"),
	@NamedQuery(name="Employee.findbyID", query="SELECT e FROM Employee e WHERE e.emppKey = :id"),
	@NamedQuery(name="Employee.findENW", query="SELECT DISTINCT e FROM Employee e INNER JOIN e.leaverequests1 l on  e.emppKey=l.employee1.emppKey WHERE l.LRDate = :date  AND l.LRStatus= :status"),
	@NamedQuery(name="Employee.findvalidAdminUser", query="SELECT e FROM Employee e WHERE e.empType= :type and e.employeeDepartment= :dept"),
	@NamedQuery(name="Employee.finddept", query="SELECT e FROM Employee e WHERE e.employeeDepartment= :dept")
	
	})
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int emppKey;

	private int availableHoliday;

	private String empAddress;

	@Temporal(TemporalType.TIMESTAMP)
	private Date empDoJ;

	private String empEmail;

	private String employeeDepartment;

	private String empName;

	private String empPhone;

	private String empType;

	private int totalHoliday;

	//bi-directional many-to-one association to Userlogin
	@ManyToOne
	@JoinColumn(name="USpKey")
	private Userlogin userlogin;

	//bi-directional many-to-one association to Leaverequest
	@OneToMany(mappedBy="employee1")
	private List<Leaverequest> leaverequests1;

	//bi-directional many-to-one association to Leaverequest
	@OneToMany(mappedBy="employee2")
	private List<Leaverequest> leaverequests2;

	//bi-directional many-to-one association to Messagedrivenbean
	@OneToMany(mappedBy="employee")
	private List<Messagedrivenbean> messagedrivenbeans;

	public Employee() {
	}

	public int getEmppKey() {
		return this.emppKey;
	}

	public void setEmppKey(int emppKey) {
		this.emppKey = emppKey;
	}

	public int getAvailableHoliday() {
		return this.availableHoliday;
	}

	public void setAvailableHoliday(int availableHoliday) {
		this.availableHoliday = availableHoliday;
	}

	public String getEmpAddress() {
		return this.empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}

	public Date getEmpDoJ() {
		return this.empDoJ;
	}

	public void setEmpDoJ(Date empDoJ) {
		this.empDoJ = empDoJ;
	}

	public String getEmpEmail() {
		return this.empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public String getEmployeeDepartment() {
		return this.employeeDepartment;
	}

	public void setEmployeeDepartment(String employeeDepartment) {
		this.employeeDepartment = employeeDepartment;
	}

	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpPhone() {
		return this.empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public String getEmpType() {
		return this.empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public int getTotalHoliday() {
		return this.totalHoliday;
	}

	public void setTotalHoliday(int totalHoliday) {
		this.totalHoliday = totalHoliday;
	}

	public Userlogin getUserlogin() {
		return this.userlogin;
	}

	public void setUserlogin(Userlogin userlogin) {
		this.userlogin = userlogin;
	}

	public List<Leaverequest> getLeaverequests1() {
		return this.leaverequests1;
	}

	public void setLeaverequests1(List<Leaverequest> leaverequests1) {
		this.leaverequests1 = leaverequests1;
	}

	public Leaverequest addLeaverequests1(Leaverequest leaverequests1) {
		getLeaverequests1().add(leaverequests1);
		leaverequests1.setEmployee1(this);

		return leaverequests1;
	}

	public Leaverequest removeLeaverequests1(Leaverequest leaverequests1) {
		getLeaverequests1().remove(leaverequests1);
		leaverequests1.setEmployee1(null);

		return leaverequests1;
	}

	public List<Leaverequest> getLeaverequests2() {
		return this.leaverequests2;
	}

	public void setLeaverequests2(List<Leaverequest> leaverequests2) {
		this.leaverequests2 = leaverequests2;
	}

	public Leaverequest addLeaverequests2(Leaverequest leaverequests2) {
		getLeaverequests2().add(leaverequests2);
		leaverequests2.setEmployee2(this);

		return leaverequests2;
	}

	public Leaverequest removeLeaverequests2(Leaverequest leaverequests2) {
		getLeaverequests2().remove(leaverequests2);
		leaverequests2.setEmployee2(null);

		return leaverequests2;
	}

	public List<Messagedrivenbean> getMessagedrivenbeans() {
		return this.messagedrivenbeans;
	}

	public void setMessagedrivenbeans(List<Messagedrivenbean> messagedrivenbeans) {
		this.messagedrivenbeans = messagedrivenbeans;
	}

	public Messagedrivenbean addMessagedrivenbean(Messagedrivenbean messagedrivenbean) {
		getMessagedrivenbeans().add(messagedrivenbean);
		messagedrivenbean.setEmployee(this);

		return messagedrivenbean;
	}

	public Messagedrivenbean removeMessagedrivenbean(Messagedrivenbean messagedrivenbean) {
		getMessagedrivenbeans().remove(messagedrivenbean);
		messagedrivenbean.setEmployee(null);

		return messagedrivenbean;
	}

}