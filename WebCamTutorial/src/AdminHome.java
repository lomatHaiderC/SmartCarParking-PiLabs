
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/*
Admin Home Page Containinng all operations performed by Admin 
Can operate Fare Type, Card Validity,User Registartion, Report Generation
*/

public class AdminHome extends LoginSession {

	private JFrame frame;
	int value;
	JButton passChange,insertVip,insertNormal;
	

	/**
	 * Create the application.
	 */
	public AdminHome(int n) {
		value = n;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame("CarParkingManagementSystem");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((screenSize.width/2)+200,(screenSize.height));
		//frame.setBounds(0, 0, 1500, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		/**
		 * To select the Fare Type either Owner want to set the Fare 'Per Hour' Based or
		 * 'Per Day' Based
		 */

		JButton payment = new JButton("Fare Type");
		payment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new hourday(value);
			}
		});
		menuBar.add(payment);

		/**
		 * To select the time to stay for each vehicle in the parking after payment
		 */

		JButton vipregistration = new JButton("Card Validity");
		vipregistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new ValidityPopup(value);
			}
		});
		menuBar.add(vipregistration);

		JButton barrierGate = new JButton("Barrier Gate");
		barrierGate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new BarrierGateOpen(value);
			}
		});
		menuBar.add(barrierGate);

		/**
		 * To Register New 'Admin'/'Employee' Account
		 */

		JButton userRegistration = new JButton("User Registration");
		userRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new CreateAccountType(value);
				// cvali.run();
			}
		});
		menuBar.add(userRegistration);
		

		/**
		 * To generate various report
		 */

		JButton report = new JButton("Report");
		report.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new ReportPage(value);
			}
		});
		menuBar.add(report);
		

		JButton logout = new JButton("Logout");
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// setName(null);
				setOutName(value);
				frame.setVisible(false);
				EmployeeLogin el = new EmployeeLogin();
				el.run();
			}
		});
		menuBar.add(logout);
		
		
		JLabel lblOption=new JLabel("More Options:");
		lblOption.setBounds(10, 30, 100, 30);
		frame.getContentPane().add(lblOption);
		
		/**
		 * Admin Can Change his password with this
		 */
		
		
		passChange = new JButton("Change Password");
		passChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new ChangePassAdmin(value);
			}
		});
		passChange.setBounds(20, 80, 150, 30);
		frame.getContentPane().add(passChange);
		
		/**
		 * New VIP Card can be Inserted
		 */
		insertVip = new JButton("New Vip Card");
		insertVip .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new VipCard(value);
			}
		});
		insertVip .setBounds(20, 110, 150, 30);
		frame.getContentPane().add(insertVip );
		
		/**
		 * New Normal card can be inserted
		 */
		insertNormal = new JButton("New Normal Card");
		insertNormal .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new NormalCard(value);
			}
		});
		insertNormal .setBounds(20, 140, 150, 30);
		frame.getContentPane().add(insertNormal );
		
		
		
	}
}
