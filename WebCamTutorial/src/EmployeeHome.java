
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class EmployeeHome extends LoginSession {

	JFrame frame;
	int value;
	JButton passChange;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public EmployeeHome(int n) {
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
		 * User can pay his bill with the option
		 */

		JButton payment = new JButton("Payment");
		payment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new Payment1(value);

			}
		});
		menuBar.add(payment);
		/**
		 * To register new VIP User
		 */

		JButton vipregistration = new JButton("Vip Registration");
		vipregistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new VipRegistration(value);
			}
		});
		menuBar.add(vipregistration);

		/**
		 * To generate various report
		 */

		JButton report = new JButton("Report");
		report.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new ReportPageEmployee(value);
			}
		});
		menuBar.add(report);

		JButton logout = new JButton("Logout");
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// setEName(null);
				setOutEName(value);
				frame.setVisible(false);
				EmployeeLogin el = new EmployeeLogin();
				el.run();
			}
		});
		menuBar.add(logout);
		
		JLabel lblOption=new JLabel("More Options:");
		lblOption.setBounds(10, 30, 100, 30);
		frame.getContentPane().add(lblOption);
		
		passChange = new JButton("Change Password");
		passChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new ChangePassEmployee(value);
			}
		});
		passChange.setBounds(20, 80, 150, 30);
		frame.getContentPane().add(passChange);

	}
}
