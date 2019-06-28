
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

/**
 * To select the Fare either 'Per Hour' or 'Per Day'
 */

public class hourday {
	private JFrame frame;
	JRadioButton perHour, perDay;
	JButton select;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	int value;

	public hourday(int n) {
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
				new AdminHome(value);
			}
		});
		back.setBounds(10, 10, 80, 20);
		frame.getContentPane().add(back);

		perHour = new JRadioButton("Per Hour", Boolean.parseBoolean(Integer.toString(1)));
		buttonGroup.add(perHour);
		perHour.setSelected(true);
		perHour.setBounds(120, 50, 100, 23);
		frame.getContentPane().add(perHour);

		perDay = new JRadioButton("Per Day", Boolean.parseBoolean(Integer.toString(2)));
		buttonGroup.add(perDay);
		perDay.setBounds(240, 50, 100, 23);
		frame.getContentPane().add(perDay);

		select = new JButton("Create");
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/*
				 * if Admin Type is Selected it will redirect to Admin Registration page if
				 * Employee Type is selected it will redirect to Employee Registration page
				 */

				if (perHour.isSelected()) {
					frame.setVisible(false);
					new FareTypePopup(1, value);

				} else if (perDay.isSelected()) {
					frame.setVisible(false);
					new FareTypePopup(2, value);
				}
			}
		});
		select.setBounds(170, 120, 89, 23);
		frame.getContentPane().add(select);

	}

}