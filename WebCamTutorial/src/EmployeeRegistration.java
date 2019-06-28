import javax.swing.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class EmployeeRegistration extends LoginSession {

	private JFrame frame;
	String s, requestMessage = "Create New Employee", s1;
	JTextField textField_1, textField_2, textField_3, textField_4, textField_5;
	JButton browse, ok;
	JLabel image, lblName, email, phoneno, lblNewLabel, lblQues;
	InputStream isr, fis;
	File f;
	public BufferedReader br;
	public FileReader reader;
	static String ipAddress;
	static int portNumer;
	int value1;
	int possible;

	/**
	 * Launch the application.
	 */

	/**
	 * New Employee Registration Page. Will take the required information, verify
	 * 'Gmail' and Create Account
	 */

	/**
	 * Create the application.
	 */
	public EmployeeRegistration(int n) {
		value1 = n;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {

		IpPortConfiguration ipConfig = new IpPortConfiguration();
		ipAddress = ipConfig.getIpAddress();
		portNumer = ipConfig.getPortNumber();

		frame = new JFrame("CarParkingManagementSystem");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((screenSize.width*52)/100,(screenSize.height*78)/100);
		//frame.setBounds(300, 100, 700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				new CreateAccountType(value1);
			}
		});
		back.setBounds(10, 10, 80, 20);
		frame.getContentPane().add(back);

		ok = new JButton("Create");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GmailCheck gcheck = new GmailCheck();
				boolean value = gcheck.isAddressValid(textField_2.getText());
				if (value == true) {
					possible = 1;
					databaseInsertServer();
					if (s1.equals("New Employee Enrolled")) {
						frame.setVisible(false);
						new AdminHome(value1);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Gmail is not Valid");
				}

			}
		});
		ok.setBounds(300, 350, 106, 30);
		frame.getContentPane().add(ok);

		lblName = new JLabel("Name :");
		lblName.setBounds(150, 100, 200, 30);
		frame.getContentPane().add(lblName);

		textField_1 = new JTextField();
		textField_1.setBounds(250, 100, 200, 30);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		email = new JLabel("Email :");
		email.setBounds(150, 150, 200, 30);
		frame.getContentPane().add(email);

		textField_2 = new JTextField();
		textField_2.setBounds(250, 150, 200, 30);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		phoneno = new JLabel("Phone No :");
		phoneno.setBounds(150, 200, 200, 30);
		frame.getContentPane().add(phoneno);

		textField_3 = new JTextField();
		textField_3.setBounds(250, 200, 200, 30);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		lblQues = new JLabel("Security Ques. :");
		lblQues.setBounds(150, 250, 200, 30);
		frame.getContentPane().add(lblQues);

		textField_5 = new JTextField();
		textField_5.setBounds(250, 250, 200, 30);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);

	}

	protected void databaseInsertServer() {
		// TODO Auto-generated method stub
		try {
			Socket server = new Socket(ipAddress, portNumer);

			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String sendMessage = requestMessage + '-' + ',' + textField_1.getText() + ',' + textField_2.getText() + ','
					+ textField_3.getText() + ',' + textField_5.getText() + ',' + getName(value1);

			sout.println(sendMessage);

			s1 = sin.readLine();

			JOptionPane.showMessageDialog(null, s1);
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
