package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the leaverequest database table.
 * 
 */
@Entity
@NamedQuery(name="Leaverequest.findAll", query="SELECT l FROM Leaverequest l")
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