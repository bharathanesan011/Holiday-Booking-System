package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the messagedrivenbean database table.
 * 
 */
@Entity
@NamedQuery(name="Messagedrivenbean.findAll", query="SELECT m FROM Messagedrivenbean m")
public class Messagedrivenbean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int messageId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date empRequestDate;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="EmppKey")
	private Employee employee;

	public Messagedrivenbean() {
	}

	public int getMessageId() {
		return this.messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public Date getEmpRequestDate() {
		return this.empRequestDate;
	}

	public void setEmpRequestDate(Date empRequestDate) {
		this.empRequestDate = empRequestDate;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}