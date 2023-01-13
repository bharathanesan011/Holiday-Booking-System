package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the leaverequest database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Leaverequest.findAll", query="SELECT l FROM Leaverequest l"),  
	
	//@NamedQuery(name="Leaverequest.prioretizedRequestList",query = "select l from Leaverequest l where l.LRStatus = :status and YEAR(l.LRDate) = YEAR(curdate()) and l.employee1 in (:employee) group by l.employee1 order by sum(l.totalDay) asc"),
    
    //@NamedQuery(name="Leaverequest.getPendingRequests", query="SELECT l FROM Leaverequest l where l.LRStatus = :status and YEAR(l.LRDate) = YEAR(curdate())"),
    
//    @NamedQuery(name="Leaverequest.getSortedPendingRequest", query="SELECT l FROM Leaverequest l where l.employee1 = :Employee and l.LRStatus = :status"),
//    
    @NamedQuery(name="Leaverequest.getSortedPendingRequestBasedonPeakTime",
    query="select l from Leaverequest l inner join l.employee1 e on e.emppKey=l.employee1.emppKey where l.LRStatus = :status  and YEAR(l.LRDate) = YEAR(curdate()) "
    		+ "and e.employeeDepartment=:dept and ((DATE_FORMAT(l.LRDate, '%m-%d') between '07-15' and '08-31') or"
    		+ " (DATE_FORMAT(l.LRDate, '%m-%d') between '12-15' and '12-22') or"
    		+ "(DATE_FORMAT(l.LRDate, '%m-%d') between :weekBeforeEaster and :easter) or"
    		+ "(DATE_FORMAT(l.LRDate, '%m-%d') between :weekAfterEaster and :easter)  )"
    		+ " group by l.employee1 ORDER BY e.availableHoliday ASC"),
	@NamedQuery(name="Leaverequest.findAllbyLRId", query="SELECT l FROM Leaverequest l WHERE l.LRpKey = :LRpKey"),
	@NamedQuery(name="Leaverequest.findAllbyId", query="SELECT l FROM Leaverequest l WHERE l.employee1.emppKey = :id"),
	@NamedQuery(name="Leaverequest.findAllName", query="SELECT l FROM Leaverequest l WHERE l.employee1.empName = :name"),
	@NamedQuery(name="Leaverequest.findAllbyAdminDept", query="select l from Leaverequest l inner join l.employee1 e on e.emppKey=l.employee1.emppKey where e.employeeDepartment=:dept and l.LRStatus=:status ORDER BY e.availableHoliday DESC")
})
public class Leaverequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int LRpKey;

	private String LRComment;

	@Temporal(TemporalType.TIMESTAMP)
	private Date LRDate;

	private String LRDescription;

	private String LRStatus;

	private int totalDay;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="EmppKey")
	private Employee employee1;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="LRApprovedBy")
	private Employee employee2;

	public Leaverequest() {
	}

	public int getLRpKey() {
		return this.LRpKey;
	}

	public void setLRpKey(int LRpKey) {
		this.LRpKey = LRpKey;
	}

	public String getLRComment() {
		return this.LRComment;
	}

	public void setLRComment(String LRComment) {
		this.LRComment = LRComment;
	}

	public Date getLRDate() {
		return this.LRDate;
	}

	public void setLRDate(Date LRDate) {
		this.LRDate = LRDate;
	}

	public String getLRDescription() {
		return this.LRDescription;
	}

	public void setLRDescription(String LRDescription) {
		this.LRDescription = LRDescription;
	}

	public String getLRStatus() {
		return this.LRStatus;
	}

	public void setLRStatus(String LRStatus) {
		this.LRStatus = LRStatus;
	}

	public int getTotalDay() {
		return this.totalDay;
	}

	public void setTotalDay(int totalDay) {
		this.totalDay = totalDay;
	}

	public Employee getEmployee1() {
		return this.employee1;
	}

	public void setEmployee1(Employee employee1) {
		this.employee1 = employee1;
	}

	public Employee getEmployee2() {
		return this.employee2;
	}

	public void setEmployee2(Employee employee2) {
		this.employee2 = employee2;
	}

}