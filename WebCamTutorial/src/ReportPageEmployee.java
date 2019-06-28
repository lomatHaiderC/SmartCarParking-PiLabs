import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JFrame;

/**
 * Page for generating Report
 */

public class ReportPageEmployee {

	private JFrame frame;
	int value;

	public ReportPageEmployee(int n) {
		value = n;
		initialize();
	}

	public void initialize() {

		frame = new JFrame("CarParkingManagementSystem");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((screenSize.width*29)/100,(screenSize.height*58)/100);
		//frame.setBounds(300, 100, 400, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				new EmployeeHome(value);
			}
		});
		back.setBounds(10, 10, 80, 20);
		frame.getContentPane().add(back);

		/**
		 * Will generate the report for VIP User Information
		 */

		JButton vipUser = new JButton("Vip User");
		vipUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new VipInfoEmployee(value);

			}
		});
		vipUser.setBounds(150, 100, 100, 30);
		frame.getContentPane().add(vipUser);

	}
}
