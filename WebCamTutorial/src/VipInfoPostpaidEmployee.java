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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Will show the VIP User('Postpaid') Informations
 */

public class VipInfoPostpaidEmployee {
	static int i = 0;
	private JFrame frame;
	String requestMessage = "vipinfoPostpaid";
	static String ipAddress;
	static int portNumer;
	public static String s1;
	public static String[] splitResult2;
	public static String header[];
	public static String[][] data;
	JLabel lblTotalIncome, income;
	JComboBox<String> userType;
	int value;

	public VipInfoPostpaidEmployee(int n) {

		value = n;
		initialize();
	}

	private void initialize() {
		IpPortConfiguration ipConfig = new IpPortConfiguration();
		ipAddress = ipConfig.getIpAddress();
		portNumer = ipConfig.getPortNumber();
		frame = new JFrame("CarParkingManagementSystem");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((screenSize.width),(screenSize.height));
		//frame.setBounds(0, 0, 1500, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				new ReportPageEmployee(value);
			}
		});
		back.setBounds(10, 10, 80, 20);
		frame.getContentPane().add(back);

		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				new EmployeeHome(value);
			}
		});
		ok.setBounds(600, 650, 102, 31);
		frame.getContentPane().add(ok);

		userType = new JComboBox<String>();
		userType.setBounds(150, 40, 100, 30);
		frame.getContentPane().add(userType);
		userType.addItem("All");
		userType.addItem("Prepaid");
		userType.addItem("Postpaid");
		userType.setSelectedIndex(2);

		lblTotalIncome = new JLabel("Total User :");
		lblTotalIncome.setBounds(1098, 33, 142, 31);
		income = new JLabel("");
		income.setFont(new Font("Tahoma", Font.PLAIN, 20));
		income.setForeground(Color.RED);
		income.setBounds(1221, 33, 142, 31);
		frame.getContentPane().add(lblTotalIncome);

		frame.getContentPane().add(income);
		lblTotalIncome.setVisible(false);
		income.setVisible(false);

		int typeSelected = userType.getSelectedIndex();
		if (typeSelected == 0) {
			frame.setVisible(false);
			new VipInfoEmployee(value);
		} else if (typeSelected == 1) {
			frame.setVisible(false);
			new VipInfoPrepaidEmployee(value);
		} else if (typeSelected == 2) {
			vipinfo();
		}

		DefaultTableModel model = new DefaultTableModel(data, header);

		JTable table = new JTable(model);
		table.setBounds(133, 100, 1100, 500);
		frame.getContentPane().add(table);

		JScrollPane js = new JScrollPane(table);
		js.setBounds(133, 100, 1100, 500);
		frame.getContentPane().add(js);

	}

	void vipinfo() {
		try {

			Socket server = new Socket(ipAddress, portNumer);
			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

			String sendMessage = requestMessage + '-';
			sout.println(sendMessage);
			s1 = sin.readLine();
			String flag[] = s1.split("&");
			String flagcount = flag[1];
			int n = Integer.parseInt(flagcount);
			String s1st = flag[0];

			header = new String[7];
			header[0] = "Vehicel Type";
			header[1] = "Phone Number";
			header[2] = "RFID Number";
			header[3] = "System";
			header[4] = "Current Balance";
			header[5] = "Total Used";
			header[6] = "Total Deposite";
			data = new String[n][7];
			String result = "";
			double sum;
			double temp1 = 0.0, temp2 = 0.0;
			for (int i = 0; i < n; i++) {
				String[] splitResult = s1st.split(";");
				String s22 = splitResult[i];
				splitResult2 = s22.split(",");

				for (int j = 0; j < 6; j++) {
					data[i][j] = splitResult2[j];
					if (j == 4) {
						temp1 = Double.parseDouble(data[i][j]);
					}
					if (j == 5) {
						temp2 = Double.parseDouble(data[i][j]);
					}

				}
				sum = temp1 + temp2;
				result = String.valueOf(sum);
				data[i][6] = result;
			}
			lblTotalIncome.setVisible(true);
			income.setText(n + "");
			income.setVisible(true);

			server.close();
			sin.close();
			sout.close();
			stdin.close();

		}

		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		userType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int typeSelected = userType.getSelectedIndex();
				if (typeSelected == 0) {
					frame.setVisible(false);
					new VipInfoEmployee(value);
				} else if (typeSelected == 1) {
					frame.setVisible(false);
					new VipInfoPrepaidEmployee(value);
				}
			}
		});
	}

}
