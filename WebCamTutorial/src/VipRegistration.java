
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

/**
 * Page to Register new VIP User Prepaid User can recharge initially Postpaid
 * User doesn't have any initial balance
 */

public class VipRegistration {

	private JFrame frame;
	JLabel lblRfid, lblCarType, lblDate, lblSystem, lblPhoneNo, lblBalance,lblCard;
	JTextField rfid, phoneno, balance,card;
	JComboBox<String> system, cartype;
	JDateChooser jdate;
	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
	static LocalDateTime now;
	static String nowDate, paymentType, carType, requestMessage = "New VIP User Entry", balancePost,cardno;
	static String ipAddress;
	static int portNumer;
	int value;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public VipRegistration(int n) {
		value = n;
		IpPortConfiguration ipConfig = new IpPortConfiguration();
		ipAddress = ipConfig.getIpAddress();
		portNumer = ipConfig.getPortNumber();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((screenSize.width*58)/100,(screenSize.height*78)/100);
		//frame.setBounds(0, 0, 800, 600);
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
		
		lblCard=new JLabel("Card No :");
		lblCard.setBounds(200, 150, 90, 30);
		frame.getContentPane().add(lblCard);
		
		card=new JTextField();
		card.setBounds(350, 150, 220, 30);
		frame.getContentPane().add(card);
		card.setColumns(10);
		
		lblCarType = new JLabel("Car Type :");
		lblCarType.setBounds(200, 200, 90, 30);
		frame.getContentPane().add(lblCarType);

		cartype = new JComboBox<String>();
		cartype.setBounds(350, 200, 220, 30);
		frame.getContentPane().add(cartype);
		cartype.addItem("Car");
		cartype.addItem("Bike");
		cartype.addItem("Micro");

		lblBalance = new JLabel("Balance:");
		lblBalance.setBounds(200, 250, 90, 30);
		frame.getContentPane().add(lblBalance);

		balance = new JTextField();
		balance.setBounds(350, 250, 220, 30);
		frame.getContentPane().add(balance);
		balance.setColumns(10);

		lblSystem = new JLabel("System :");
		lblSystem.setBounds(200, 300, 90, 30);
		frame.getContentPane().add(lblSystem);

		system = new JComboBox<String>();
		system.setBounds(350, 300, 90, 30);
		frame.getContentPane().add(system);
		system.addItem("Prepaid");
		system.addItem("Postpaid");

		lblPhoneNo = new JLabel("Phone No :");
		lblPhoneNo.setBounds(200, 350, 90, 30);
		frame.getContentPane().add(lblPhoneNo);

		phoneno = new JTextField();
		phoneno.setColumns(10);
		phoneno.setBounds(350, 350, 220, 30);
		frame.getContentPane().add(phoneno);

		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indexSelected = system.getSelectedIndex();
				if (indexSelected == 0) {
					paymentType = "prepaid";
					balancePost = balance.getText();
				} else if (indexSelected == 1) {
					paymentType = "postpaid";
					balance.setText("0");
					balancePost = balance.getText();

				}
				int typeSelected = cartype.getSelectedIndex();
				if (typeSelected == 0) {
					carType = "Car";
				} else if (typeSelected == 1) {
					carType = "Bike";
				} else if (typeSelected == 2) {
					carType = "Micro";
				}
				cardno=card.getText();
				databaseInsertServer();
				frame.setVisible(false);
				new EmployeeHome(value);

			}
		});
		create.setBounds(350, 450, 100, 30);
		frame.getContentPane().add(create);

	}

	protected void databaseInsertServer() {
		// TODO Auto-generated method stub
		try {
			Socket server = new Socket(ipAddress, portNumer);
			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

			String sendMessage = requestMessage + '-' + ',' + carType + ',' + balancePost + ',' + paymentType + ','
					+ phoneno.getText()+','+cardno;

			sout.println(sendMessage);

			String s1 = sin.readLine();
			String[] splitResult = s1.split(",");

			if (splitResult[0].equals("Insert Done")) {
				JOptionPane.showMessageDialog(null, "New VIP User Is Registered with RFID No: " + splitResult[1]);
			} else if (splitResult[0].equals("Insert Error")) {
				JOptionPane.showMessageDialog(null, "Registration Cannot be done;Try Again");
			}
			server.close();
			sin.close();
			sout.close();
			stdin.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
