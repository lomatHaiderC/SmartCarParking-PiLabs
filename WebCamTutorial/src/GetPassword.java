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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * In case user forget his password he has to enter his mail and the 'Security
 * Word' that he has inserted during account creation. Then he will get his new
 * password
 */

public class GetPassword {

	private JFrame frame;
	String requestMsg = "Forget Pass";
	static String ipAddress;
	static int portNumer;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public GetPassword() {

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
		frame.setVisible(true);

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EmployeeLogin el = new EmployeeLogin();
				frame.setVisible(false);
				el.run();
			}
		});
		back.setBounds(10, 10, 80, 20);
		frame.getContentPane().add(back);

		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setBounds(93, 108, 162, 29);
		frame.getContentPane().add(lblEmail);

		JTextField email = new JTextField();
		email.setBounds(265, 108, 237, 29);
		frame.getContentPane().add(email);
		email.setColumns(10);

		JLabel lblSecurityQuestion = new JLabel("Security Question :");
		lblSecurityQuestion.setBounds(93, 161, 162, 29);
		frame.getContentPane().add(lblSecurityQuestion);

		JTextField securityquestion = new JTextField();
		securityquestion.setBounds(265, 161, 237, 29);
		frame.getContentPane().add(securityquestion);
		securityquestion.setColumns(10);

		JButton getpassword = new JButton("Get Password");
		getpassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Socket server = new Socket(ipAddress, portNumer);
					// Socket server=new Socket("localhost",8800);
					BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
					PrintStream sout = new PrintStream(server.getOutputStream());
					BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

					String sendMessage = requestMsg + '-' + ',' + email.getText() + ',' + securityquestion.getText();
					sout.println(sendMessage);

					String s1 = sin.readLine();

					if (s1.equals("Not Matched")) {
						JOptionPane.showMessageDialog(null, "Not Matched the Word");
					} else if (s1.equals("Not Found")) {
						JOptionPane.showMessageDialog(null, "Invalid Email");
					} else {
						JOptionPane.showMessageDialog(null, "New Password: " + s1);
						EmployeeLogin el = new EmployeeLogin();
						frame.setVisible(false);
						el.run();
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
		getpassword.setBounds(205, 284, 162, 29);
		frame.getContentPane().add(getpassword);
	}
}
