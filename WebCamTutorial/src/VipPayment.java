import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

/**
 * VIP user can either pay his bill or can view the bill details
 */
public class VipPayment {
	private JFrame frame;
	JRadioButton userInfo, bill;
	JButton select;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	String cardnbr;
	int value;

	public VipPayment(String card, int n) {
		value = n;
		cardnbr = card;
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
				new Payment1(value);
			}
		});
		back.setBounds(10, 10, 80, 20);
		frame.getContentPane().add(back);

		userInfo = new JRadioButton("User Information", Boolean.parseBoolean(Integer.toString(1)));
		buttonGroup.add(userInfo);
		userInfo.setSelected(true);
		userInfo.setBounds(100, 50, 150, 23);
		frame.getContentPane().add(userInfo);

		bill = new JRadioButton("Pay Bill", Boolean.parseBoolean(Integer.toString(2)));
		buttonGroup.add(bill);
		bill.setBounds(260, 50, 100, 23);
		frame.getContentPane().add(bill);

		select = new JButton("Next");
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/*
				 * if 'User Information' is Selected it will be redirected to 'UserInfo' page if
				 * 'Pay Bill' is selected it will be redirected to either 'PaymentPrepaid' or
				 * 'PaymentPostpaid' page
				 */

				if (userInfo.isSelected()) {
					frame.setVisible(false);
					new UserInfo(cardnbr, value);

				} else if (bill.isSelected()) {

					if (SelectVip.typ == 1) {
						frame.setVisible(false);
						new PaymentPrepaid(cardnbr, value);
						// pre.prepaid();
					} else if (SelectVip.typ == 2) {
						frame.setVisible(false);
						new PaymentPostpaid(cardnbr, value);
					}
				}
			}
		});
		select.setBounds(170, 120, 89, 23);
		frame.getContentPane().add(select);

	}
}