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

/**
 * To change the password of 'Employee'
 */
public class ChangePassEmployee extends LoginSession{
	public static String username;
	private JFrame frame;
	private JPasswordField password1,oldpass,cNewPass;
	String requestMessage = "Pass Change Employee";
	static String ipAddress;
	static int portNumer;
	String[] splitReturn;
	int value;
	String pass1,pass2,pass3,mail;
	
	public ChangePassEmployee(int n) {
		value=n;
		IpPortConfiguration ipConfig = new IpPortConfiguration();
		ipAddress = ipConfig.getIpAddress();
		portNumer = ipConfig.getPortNumber();
		initialize();
	}
		
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
				frame.setVisible(false);
				new EmployeeHome(value);
			}
		});
		back.setBounds(10, 10, 80, 20);
		frame.getContentPane().add(back);

		JLabel LoldPass = new JLabel("Old Password :");
		LoldPass.setBounds(100, 70, 120, 30);
		frame.getContentPane().add(LoldPass);

		oldpass = new JPasswordField();
		oldpass.setBounds(280,70, 195, 30);
		frame.getContentPane().add(oldpass);

		JLabel Lpassword = new JLabel("New Password :");
		Lpassword.setBounds(100, 120, 120, 30);
		frame.getContentPane().add(Lpassword);

		password1 = new JPasswordField();
		password1.setBounds(280, 120, 195, 30);
		frame.getContentPane().add(password1);

		
		JLabel LconfirmPass=new JLabel("Confirm Password :");

		LconfirmPass.setBounds(100, 170, 120, 30);
		frame.getContentPane().add(LconfirmPass);
		
		cNewPass = new JPasswordField();
		cNewPass.setBounds(280, 170, 195, 30);
		frame.getContentPane().add(cNewPass);
		

		JButton change= new JButton("Change");
		change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pass1= String.valueOf(oldpass.getPassword());
				pass2= String.valueOf(password1.getPassword());
				pass3= String.valueOf(cNewPass.getPassword());
				mail=getEName(value);
				System.out.println("Value: "+value+ " "+mail);
				
				if(!pass2.equals(pass3)) {
					JOptionPane.showMessageDialog(null, "Confirm Password didn't Match");
				}
				else {
					try {
						Socket server = new Socket(ipAddress, portNumer);
						BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
						PrintStream sout = new PrintStream(server.getOutputStream());
						BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
						String sendMessage = requestMessage + '-' + ',' + mail+','+pass1+ ',' + pass2;

						sout.println(sendMessage);

						String s1 = sin.readLine();
						JOptionPane.showMessageDialog(null, s1);
						if(s1.equals("Password Updated")) {
							frame.setVisible(false);
							new EmployeeHome(value);
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
			}
		});
		change.setBounds(280,250,80, 30);
		frame.getContentPane().add(change);


	}
}
