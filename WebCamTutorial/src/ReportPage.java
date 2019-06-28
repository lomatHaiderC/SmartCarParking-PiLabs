import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JFrame;

/**
 * Page for generating Report
 */

public class ReportPage {

	private JFrame frame;
	int value;

	public ReportPage(int n) {
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
				new AdminHome(value);
			}
		});
		back.setBounds(10, 10, 80, 20);
		frame.getContentPane().add(back);
		/**
		 * Will generate the report for Normal,VIP and Total income
		 */

		JButton income = new JButton("Income");
		income.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				frame.setVisible(false);
				new TotalIncome(value);


			}
		});
		income.setBounds(150, 100, 100, 30);
		frame.getContentPane().add(income);

		/**
		 * Will generate the report for VIP User Information
		 */

		JButton vipUser = new JButton("Vip User");
		vipUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new VipInfo(value);

			}
		});
		vipUser.setBounds(150, 150, 100, 30);
		frame.getContentPane().add(vipUser);

	}
}
