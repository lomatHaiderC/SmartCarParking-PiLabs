
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

/**
 * Will show the corresponding information of a card in 'postpaidpayment()'
 * method User can pay bill after that If bill is paid it will be printed in
 * 'vippostprintPayment()' method
 */

public class PaymentPostpaid extends LoginSession {

	private JFrame frame;
	static String ipAddress;
	static int portNumer;
	String sncard;
	String requestMessage = "postpaid payment";
	public static String blanaceused;
	public static String mbl;
	public static String vipcartyp;
	public String exittime;
	int value;

	/**
	 * Launch the application.
	 */
	JLabel lblTotalBill, lblVehicleType, lblPhoneNumber, lblRfidNumber, lblSystem, lblexittime;

	public PaymentPostpaid(String card, int n) {
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

		JButton payprint = new JButton("Pay & Print");
		payprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vippostprintPayment();
			}
		});

		postpaidpayment();
		payprint.setBounds(250, 350, 150, 30);
		frame.getContentPane().add(payprint);

		JLabel lblTotalBill = new JLabel("Total Bill :");
		lblTotalBill.setBounds(200, 130, 130, 30);
		frame.getContentPane().add(lblTotalBill);

		JLabel bill = new JLabel(blanaceused);
		bill.setFont(new Font("Tahoma", Font.PLAIN, 18));
		bill.setForeground(Color.RED);
		bill.setBounds(350, 130, 130, 30);
		frame.getContentPane().add(bill);

		JLabel lblVehicleType = new JLabel("Vehicle Type :");
		lblVehicleType.setBounds(200, 170, 130, 30);
		frame.getContentPane().add(lblVehicleType);

		JLabel vehicletype = new JLabel(vipcartyp);
		vehicletype.setBounds(350, 170, 130, 30);
		frame.getContentPane().add(vehicletype);

		JLabel lblPhoneNumber = new JLabel("Employee ID :");
		lblPhoneNumber.setBounds(200, 90, 130, 30);
		frame.getContentPane().add(lblPhoneNumber);

		JLabel phone = new JLabel(getEName(value));
		phone.setBounds(350, 90, 130, 30);
		frame.getContentPane().add(phone);

		JLabel lblRfidNumber = new JLabel("RFID Number :");
		lblRfidNumber.setBounds(200, 210, 130, 30);
		frame.getContentPane().add(lblRfidNumber);

		JLabel rfid = new JLabel(sncard);
		rfid.setBounds(350, 210, 130, 30);
		frame.getContentPane().add(rfid);

		JLabel lblSystem = new JLabel("System :");
		lblSystem.setBounds(200, 250, 130, 20);
		frame.getContentPane().add(lblSystem);

		JLabel system = new JLabel("Postpaid");
		system.setBounds(350, 250, 130, 20);
		frame.getContentPane().add(system);
	}

	public void vippostprintPayment() {
		String msg = "vip post payment done";
		try {
			Socket server = new Socket(ipAddress, portNumer);
			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			String sendMessage = msg + '-' + ',' + sncard;
			sout.println(sendMessage);

			String s1 = sin.readLine();
			String[] splitResult = s1.split(",");

			exittime = splitResult[0];

			JOptionPane.showMessageDialog(null, splitResult[1]);
			if (splitResult[1].equalsIgnoreCase("Payment Done")) {

				frame.setVisible(false);
				new EmployeeHome(value);
			} else {
				frame.setVisible(false);
				new Payment1(value);
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Document document = new Document();
		try {

			String exitTime = exittime;
			String[] dateValue = exitTime.split(":");

			String pdfName = sncard + "_" + dateValue[0] + "-" + dateValue[1] + "-" + dateValue[2] + ".pdf";
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfName));
			document.open();
			document.add(new Paragraph("Vip Payment Info"));

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(70);
			table.setSpacingBefore(8f);
			table.setSpacingAfter(8f);
			float[] colWidth = { 5f, 5f };
			table.setWidths(colWidth);

			PdfPCell c1 = new PdfPCell(new Paragraph("Used Balance:"));
			PdfPCell c2 = new PdfPCell(new Paragraph(blanaceused));
			PdfPCell c3 = new PdfPCell(new Paragraph("Card Type:"));
			PdfPCell c4 = new PdfPCell(new Paragraph(vipcartyp));
			PdfPCell c5 = new PdfPCell(new Paragraph("Card Number:"));
			PdfPCell c6 = new PdfPCell(new Paragraph(sncard));
			PdfPCell c7 = new PdfPCell(new Paragraph("Payment Type:"));
			PdfPCell c8 = new PdfPCell(new Paragraph("postpaid"));
			PdfPCell c9 = new PdfPCell(new Paragraph("Payment Clear Time:"));
			PdfPCell c10 = new PdfPCell(new Paragraph(exittime));
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
			table.addCell(c6);
			table.addCell(c7);
			table.addCell(c8);
			table.addCell(c9);
			table.addCell(c10);
			document.add(table);

			document.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void postpaidpayment() {
		try {
			Socket server = new Socket(ipAddress, portNumer);
			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

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
