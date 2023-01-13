package mdb;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Employee;
import model.Messagedrivenbean;



/**
 * Message-Driven Bean implementation class for: HolidayMessageBean
 */
@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "java:/jms/HolidayQueue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "java:/jms/HolidayQueue")
public class HolidayMessageBean implements MessageListener {
	@PersistenceContext(unitName = "holidayRequestMDB")
	EntityManager em;

    /**
     * Default constructor. 
     */
    public HolidayMessageBean() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
System.out.println("Message received by MDB");
    	
    	try {
    		MapMessage holidayMSG = (MapMessage) message;
			String userId = holidayMSG.getString("userId");
			
			int id = insertAlertToDB(userId);
			
			System.out.println(String.format("New message request sent from employee ID : %s is saved in DB ", userId));
			
			try {
				deliverResult(holidayMSG, id);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Employee getEmployeeByID(int id) {
    	//change
    	List<Employee> listEmployee = em.createNamedQuery("Employee.findbyID", Employee.class).setParameter("id", id).getResultList();
    	return listEmployee.get(0);
    }
    
    public int insertAlertToDB(String employID)
    {
    	Employee e = getEmployeeByID(Integer.parseInt(employID));
    	Date date = new Date(System.currentTimeMillis());    
    	Messagedrivenbean mb = new  Messagedrivenbean();
    	mb.setEmployee(e);
    	mb.setEmpRequestDate(date);
    	
    	em.persist(mb);
    	em.flush();
    	
    	return mb.getMessageId();
    }
    
    private void deliverResult(MapMessage HolidayMsg, int id) throws JMSException, NamingException  {
		Context jndiContext = new InitialContext();
		ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup("java:/ConnectionFactory");
		
		Topic resultTopic = (Topic) jndiContext.lookup("java:/jms/HolidayTopic");
		Connection connect = connectionFactory.createConnection();
		Session session = connect.createSession(false, Session.AUTO_ACKNOWLEDGE);

		String userID = HolidayMsg.getString("userId");

		MessageProducer sender = session.createProducer(resultTopic);
		TextMessage message = session.createTextMessage();

		message.setText(String.format("New Request : %s with ID %d ", userID, id));
		sender.send(message);
		connect.close();
		
	}
    

}
