
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
 * To set the Fare for each type of vehicle either per hour or per day defined
 * by owner
 */

public class FareTypePopup extends LoginSession {

	private JFrame frame;
	static String biketyp;
	static String microtyp;
	static String minityp;
	int type = 0;
	static String ipAddress;
	static int portNumer;

	String requestMessage = "FareType";
	double bikeFare, carFare, miniFare;
	int value;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public FareTypePopup(int n, int m) {
		value = m;
		type = n;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		IpPortConfiguration ipConfig = new IpPortConfiguration();
		ipAddress = ipConfig.getIpAddress();
		portNumer = ipConfig.getPortNumber();

		frame = new JFrame("CarParkingManagementSystem");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((screenSize.width*34)/100,(screenSize.height*39)/100);
		//frame.setBounds(100, 100, 470, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				new hourday(value);
			}
		});
		back.setBounds(10, 10, 80, 20);
		frame.getContentPane().add(back);

		JLabel lblBike = new JLabel("Bike : ");
		lblBike.setBounds(140, 47, 81, 26);
		frame.getContentPane().add(lblBike);

		if (type == 1) {
			JLabel lblBikeMin = new JLabel("per Hour");
			lblBikeMin.setBounds(310, 47, 50, 26);
			frame.getContentPane().add(lblBikeMin);

			JLabel lblCarMin = new JLabel("per Hour");
			lblCarMin.setBounds(310, 99, 50, 26);
			frame.getContentPane().add(lblCarMin);

			JLabel lblMiniMin = new JLabel("per Hour");
			lblMiniMin.setBounds(310, 160, 50, 26);
			frame.getContentPane().add(lblMiniMin);
		}

		if (type == 2) {
			JLabel lblBikeMin = new JLabel("per Day");
			lblBikeMin.setBounds(310, 47, 50, 26);
			frame.getContentPane().add(lblBikeMin);

			JLabel lblCarMin = new JLabel("per Day");
			lblCarMin.setBounds(310, 99, 50, 26);
			frame.getContentPane().add(lblCarMin);

			JLabel lblMiniMin = new JLabel("per Day");
			lblMiniMin.setBounds(310, 160, 50, 26);
			frame.getContentPane().add(lblMiniMin);
		}

		JTextField bike = new JTextField();
		bike.setBounds(210, 50, 86, 20);
		frame.getContentPane().add(bike);
		bike.setColumns(10);

		JLabel lblCar = new JLabel("Car");
		lblCar.setBounds(140, 99, 81, 26);
		frame.getContentPane().add(lblCar);

		JTextField car = new JTextField();
		car.setBounds(210, 102, 86, 20);
		frame.getContentPane().add(car);
		car.setColumns(10);

		JLabel lblMini = new JLabel("Mini");
		lblMini.setBounds(140, 160, 81, 26);
		frame.getContentPane().add(lblMini);

		JTextField mini = new JTextField();
		mini.setBounds(210, 163, 86, 20);
		frame.getContentPane().add(mini);
		mini.setColumns(10);

		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bikeFare = Double.parseDouble(bike.getText());
				carFare = Double.parseDouble(car.getText());
				miniFare = Double.parseDouble(mini.getText());
				if (type == 2) {
					bikeFare = bikeFare / 24;
					carFare = carFare / 24;
					miniFare = miniFare / 24;
				}
				databaseInsertServer();

			}
		});
		ok.setBounds(167, 227, 89, 23);
		frame.getContentPane().add(ok);

	}

	protected void databaseInsertServer() {
		// TODO Auto-generated method stub
		try {
			Socket server = new Socket(ipAddress, portNumer);

			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String sendMessage = requestMessage + '-' + ',' + bikeFare + ',' + carFare + ',' + miniFare + ','
					+ getName(value);

			String s1;
			sout.println(sendMessage);
			s1 = sin.readLine();

			JOptionPane.showMessageDialog(null, s1);
			frame.setVisible(false);
			new AdminHome(value);
			server.close();
			sin.close();
			sout.close();
			stdin.close();
		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
