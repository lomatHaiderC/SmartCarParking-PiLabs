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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Necessary works to insert new Normal card
 */

public class NormalCard {
	JFrame frame;
	public static String cardnbr;
	static String ipAddress;
	static int portNumer;
	String requestMessage = "Normal Card Insert";
	public static int typ = 0;
	JComboBox<String> cardnumber;
	JLabel number,lblCardNo;
	JTextField card;
	int value,option=0,cardValue;
	
	
	public NormalCard(int n) {
		value = n;
		IpPortConfiguration ipConfig = new IpPortConfiguration();
		ipAddress = ipConfig.getIpAddress();
		portNumer = ipConfig.getPortNumber();
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((screenSize.width*29)/100,(screenSize.height*39)/100);
		//frame.setBounds(100, 100, 400, 300);
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
		
		frameIteam();

		JButton btnok = new JButton("Ok");
		btnok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(option==0) {
					cardValue=cardnumber.getSelectedIndex()+1;
				}
				option=1;
				if(cardValue>0) {
					cardnbr = card.getText();
					vipquery();
					cardValue--;

					frame.setVisible(false);
					if(cardValue!=0) {
						initialize();
						
					}
					else {
						new AdminHome(value);
					}
				}
				

			}
		});

		btnok.setBounds(175, 150, 55, 30);
		frame.getContentPane().add(btnok);

	}
	
	public void frameIteam() {
		if(option==0) {
			number=new JLabel("How Many: ");
			number.setBounds(70, 50, 130, 23);
			frame.getContentPane().add(number);
			
			cardnumber = new JComboBox<String>();
			cardnumber.setBounds(150,50, 50, 30);
			frame.getContentPane().add(cardnumber);
			cardnumber.addItem("1");
			cardnumber.addItem("2");
			cardnumber.addItem("3");
			cardnumber.addItem("4");
			cardnumber.addItem("5");
			cardnumber.addItem("6");
			cardnumber.addItem("7");
			cardnumber.addItem("8");
			cardnumber.addItem("9");
			cardnumber.addItem("10");
		}
		
		card = new JTextField();
		card.setBounds(150, 100, 150, 30);
		frame.getContentPane().add(card);
		card.setColumns(10);

		lblCardNo = new JLabel("Card No :");
		lblCardNo.setBounds(70, 100, 130, 23);
		frame.getContentPane().add(lblCardNo);
	}
	
	public void vipquery() {
		try {
			Socket server = new Socket(ipAddress, portNumer);
			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String sendMessage = requestMessage + '-' + ',' + cardnbr;
			sout.println(sendMessage);
			String s1 = sin.readLine();
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
