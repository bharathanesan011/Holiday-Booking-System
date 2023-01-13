import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class LeaveRequest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LeaveRequest window = new LeaveRequest();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LeaveRequest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Date");
		lblNewLabel.setBounds(67, 45, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(143, 45, 149, 20);
		frame.getContentPane().add(dateChooser);
		
		JLabel lblNewLabel_1 = new JLabel("Description");
		lblNewLabel_1.setBounds(67, 93, 66, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(143, 93, 143, 45);
		frame.getContentPane().add(textPane);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(143, 192, 149, 23);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Date date = dateChooser.getDate();
				String desc=textPane.getText();
				lblNewLabel_3.setText("Leave Submited");
			}
		});
		btnNewButton.setBounds(143, 158, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Leave Request");
		lblNewLabel_2.setBounds(175, 11, 117, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
//		JLabel lblNewLabel_3 = new JLabel("");
//		lblNewLabel_3.setBounds(143, 192, 149, 23);
//		frame.getContentPane().add(lblNewLabel_3);
	}
}
