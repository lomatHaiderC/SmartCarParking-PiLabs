import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * VIP user can show his Bill information also without paying the bill Will show
 * the corresponding information of a car in 'prepaidpayment()' and
 * 'postpaidpayment()' method
 */

public class UserInfo {
	private JFrame frame;
	static String ipAddress;
	static int portNumer;
	String sncard;
	String requestMessage = "postpaid payment";
	public static String blanaceused;
	public static String mbl;
	public static String vipcartyp;
	public String exittime;
	public static String remaining;
	int value;

	public UserInfo(String card, int n) {
		value = n;
		sncard = card;
		IpPortConfiguration ipConfig = new IpPortConfiguration();
		ipAddress = ipConfig.getIpAddress();
		portNumer = ipConfig.getPortNumber();
		initialize();
	}

	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((screenSize.width*51)/100,(screenSize.height*78)/100);
		//frame.setBounds(200, 50, 700, 600);
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

		if (SelectVip.typ == 1) {
			JLabel system = new JLabel("Prepaid");
			system.setBounds(350, 250, 130, 30);
			frame.getContentPane().add(system);
			prepaidpayment();

			JLabel lblTotalBill = new JLabel("Remaining Balance :");
			lblTotalBill.setBounds(200, 90, 130, 30);
			frame.getContentPane().add(lblTotalBill);

			JLabel bill = new JLabel(remaining);
			bill.setFont(new Font("Tahoma", Font.PLAIN, 18));
			bill.setForeground(Color.RED);
			bill.setBounds(350, 90, 130, 30);
			frame.getContentPane().add(bill);

		} else {
			JLabel system = new JLabel("Postpaid");
			system.setBounds(350, 250, 130, 30);
			frame.getContentPane().add(system);
			postpaidpayment();
		}

		JButton payprint = new JButton("Ok");
		payprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				new EmployeeHome(value);
			}
		});

		payprint.setBounds(300, 350, 50, 30);
		frame.getContentPane().add(payprint);

		JLabel lblTotalBill = new JLabel("Balance Used :");
		lblTotalBill.setBounds(200, 130, 130, 30);
		frame.getContentPane().add(lblTotalBill);

		JLabel bill = new JLabel(blanaceused);
		bill.setFont(new Font("Tahoma", Font.PLAIN, 18));
		bill.setForeground(Color.RED);
		bill.setBounds(350, 130, 136, 30);
		frame.getContentPane().add(bill);

		JLabel lblVehicleType = new JLabel("Vehicle Type :");
		lblVehicleType.setBounds(200, 170, 130, 30);
		frame.getContentPane().add(lblVehicleType);

		JLabel vehicletype = new JLabel(vipcartyp);
		vehicletype.setBounds(350, 170, 130, 30);
		frame.getContentPane().add(vehicletype);

		JLabel lblRfidNumber = new JLabel("RFID Number :");
		lblRfidNumber.setBounds(200, 210, 130, 30);
		frame.getContentPane().add(lblRfidNumber);

		JLabel rfid = new JLabel(sncard);
		rfid.setBounds(350, 210, 130, 30);
		frame.getContentPane().add(rfid);

		JLabel lblSystem = new JLabel("System :");
		lblSystem.setBounds(200, 250, 130, 20);
		frame.getContentPane().add(lblSystem);

	}

	void prepaidpayment() {

		try {
			Socket server = new Socket(ipAddress, portNumer);
			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			requestMessage = "prepaid payment";
			String sendMessage = requestMessage + '-' + ',' + sncard;
			sout.println(sendMessage);
			String s1 = sin.readLine();
			String[] splitResult = s1.split(",");

			remaining = splitResult[0];
			blanaceused = splitResult[1];
			vipcartyp = splitResult[2];

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

	void postpaidpayment() {
		try {
			Socket server = new Socket(ipAddress, portNumer);
			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			requestMessage = "postpaid payment";
			String sendMessage = requestMessage + '-' + ',' + sncard;
			sout.println(sendMessage);
			String s1 = sin.readLine();
			String[] splitResult = s1.split(",");

			blanaceused = splitResult[0];
			mbl = splitResult[1];
			vipcartyp = splitResult[2];

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
