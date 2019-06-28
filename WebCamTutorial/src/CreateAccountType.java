import javax.swing.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//To Select which type of account is to be created...('Admin Type'/'Employee Type')

public class CreateAccountType extends LoginSession {

	private JFrame frame;
	JRadioButton admin, employee;
	JButton create;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	int value;

	public CreateAccountType(int n) {
		value = n;
		initialize();
	}

	/* Frame Content */
	public void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((screenSize.width*33)/100,(screenSize.height*32)/100);
		//frame.setBounds(100, 100, 450, 250);
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

		admin = new JRadioButton("Admin", Boolean.parseBoolean(Integer.toString(1)));
		buttonGroup.add(admin);
		admin.setSelected(true);
		admin.setBounds(120, 50, 100, 23);
		frame.getContentPane().add(admin);

		employee = new JRadioButton("Employee", Boolean.parseBoolean(Integer.toString(2)));
		buttonGroup.add(employee);
		employee.setBounds(240, 50, 100, 23);
		frame.getContentPane().add(employee);

		create = new JButton("Create");
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/*
				 * if Admin Type is Selected it will redirect to Admin Registration page if
				 * Employee Type is selected it will redirect to Employee Registration page
				 */

				if (admin.isSelected()) {
					frame.setVisible(false);
					new AdminRegistration(value);
				} else if (employee.isSelected()) {

					frame.setVisible(false);
					new EmployeeRegistration(value);
				}
			}
		});
		create.setBounds(170, 120, 89, 23);
		frame.getContentPane().add(create);
	}
}
