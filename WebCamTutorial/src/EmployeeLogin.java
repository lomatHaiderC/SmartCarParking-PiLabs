
import javax.swing.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class EmployeeLogin extends LoginSession {
	public static String username;
	private JFrame frame;
	private JTextField name;
	private JPasswordField password1;
	String requestMessage = "Login";
	static String ipAddress;
	static int portNumer;
	String[] splitReturn;
	/**
	 * Launch the application.
	 */

	/**
	 * Login Page for both Admin and Employee. If Admin's 'Mail' and 'Password' is
	 * given then it will go to 'Admin Home' page If Employee's 'Mail' and
	 * 'Password' is given then it will go to 'Employee Home' page
	 */
	static EmployeeLogin window;

	public static void main(String[] args) {

		run();

	}

	/**
	 * Create the application.
	 */
	public static void run() {
		try {
			window = new EmployeeLogin();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EmployeeLogin() {
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
		frame.setSize((screenSize.width*44)/100,(screenSize.height*58)/100);
		//frame.setBounds(100, 100, 600, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel Lname = new JLabel("User ID:");
		Lname.setBounds(196, 76, 80, 22);
		frame.getContentPane().add(Lname);

		name = new JTextField();
		name.setBounds(196, 109, 195, 30);
		frame.getContentPane().add(name);
		name.setColumns(10);

		JLabel Lpassword = new JLabel("PassWord:");
		Lpassword.setBounds(196, 160, 80, 22);
		frame.getContentPane().add(Lpassword);

		password1 = new JPasswordField();
		password1.setBounds(196, 193, 195, 30);
		frame.getContentPane().add(password1);

		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				username = name.getText();
				String password = String.valueOf(password1.getPassword());

				try {
					Socket server = new Socket(ipAddress, portNumer);
					BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
					PrintStream sout = new PrintStream(server.getOutputStream());
					BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
					String sendMessage = requestMessage + '-' + ',' + username + ',' + password;

					sout.println(sendMessage);

					String s1 = sin.readLine();

					if (s1.equals("Employee Logged In")) {
						int value = getPosition();
						setEName(username);
						frame.setVisible(false);
						new EmployeeHome(value);
					} else if (s1.equals("Admin Logged In")) {
						int value = getPosition();
						setName(username);
						frame.setVisible(false);
						new AdminHome(value);
					} else {
						JOptionPane.showMessageDialog(null, s1);
					}

					server.close();
					sin.close();
					sout.close();
					stdin.close();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}
		});

		login.setBounds(163, 293, 97, 30);
		frame.getContentPane().add(login);

		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name.setText(null);
				password1.setText(null);
			}
		});
		reset.setBounds(332, 293, 97, 30);
		frame.getContentPane().add(reset);

		JButton forget = new JButton("Forget");
		forget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new GetPassword();
			}
		});
		forget.setBounds(250, 350, 97, 30);
		frame.getContentPane().add(forget);

	}
}
