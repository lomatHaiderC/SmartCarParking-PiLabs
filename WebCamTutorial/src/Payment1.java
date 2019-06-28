import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

/**
 * To select which type of user wants to pay 'Normal/VIP'
 */
public class Payment1 {
	private JFrame frame;
	JRadioButton normal, vip;
	JButton select;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	int value;

	public Payment1(int n) {
		value = n;
		initialize();
	}

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
				new EmployeeHome(value);
			}
		});
		back.setBounds(10, 10, 80, 20);
		frame.getContentPane().add(back);

		normal = new JRadioButton("Normal", Boolean.parseBoolean(Integer.toString(1)));
		buttonGroup.add(normal);
		normal.setSelected(true);
		normal.setBounds(120, 50, 100, 23);
		frame.getContentPane().add(normal);

		vip = new JRadioButton("VIP", Boolean.parseBoolean(Integer.toString(2)));
		buttonGroup.add(vip);
		vip.setBounds(240, 50, 100, 23);
		frame.getContentPane().add(vip);

		select = new JButton("Next");
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/*
				 * if Normal Type is Selected it will be redirected to 'Select Normal' page if
				 * VIP Type is selected it will be redirected to 'Select VIP' page
				 */

				if (normal.isSelected()) {

					frame.setVisible(false);
					new SelectNormal(value);

				} else if (vip.isSelected()) {

					frame.setVisible(false);
					new SelectVip(value);
				}
			}
		});
		select.setBounds(170, 120, 89, 23);
		frame.getContentPane().add(select);

	}
}