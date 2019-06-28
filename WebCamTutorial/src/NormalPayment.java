
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * Will show the corresponding information of a car in 'query()' method User can
 * pay bill after that If bill is paid it will be printed in 'printPayment()'
 * method
 */
public class NormalPayment extends LoginSession {

	private JFrame frame;
	static String exit_time;
	static String entry_time;
	static String stay_time;

	static int tk;
	static String total_fair;
	static String sncard;
	static String ipAddress;
	static int portNumer;
	String requestMessage = "Payment";
	String[] splitResult;
	int value;

	JLabel lblEmployeeId, lblTotalFair, lblStayTime, lblExitTime, lblentryTime, lblCardNumber, lblCardType,
			lblCardInformation, employeeid, totalfair, staytime, exittime, cardnumber, cardtype, entryTime;

	static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public NormalPayment(String cardnbr, int n) {
		value = n;
		IpPortConfiguration ipConfig = new IpPortConfiguration();
		ipAddress = ipConfig.getIpAddress();
		portNumer = ipConfig.getPortNumber();
		sncard = cardnbr;
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame("CarParkingManagementSystem");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((screenSize.width*40)/100,(screenSize.height*78)/100);
		//frame.setBounds(50, 100, 1500, 900);
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
				printPayment();
			}
		});
		payprint.setBounds(220, 400, 100, 30);
		frame.getContentPane().add(payprint);

		lblEmployeeId = new JLabel("Employee ID :");
		lblEmployeeId.setBounds(100, 340, 100, 30);
		frame.getContentPane().add(lblEmployeeId);

		lblTotalFair = new JLabel("Total Fair :");
		lblTotalFair.setBounds(100, 310, 100, 30);
		frame.getContentPane().add(lblTotalFair);

		lblStayTime = new JLabel("Stay Time :");
		lblStayTime.setBounds(100,270, 100, 30);
		frame.getContentPane().add(lblStayTime);

		lblExitTime = new JLabel("Exit Time :");
		lblExitTime.setBounds(100, 230, 100, 30);
		frame.getContentPane().add(lblExitTime);

		lblentryTime = new JLabel("Entry Time :");
		lblentryTime.setBounds(100, 190, 100, 30);
		frame.getContentPane().add(lblentryTime);

		lblCardNumber = new JLabel("Card Number :");
		lblCardNumber.setBounds(100, 150, 100, 30);
		frame.getContentPane().add(lblCardNumber);

		lblCardType = new JLabel("Card Type :");
		lblCardType.setBounds(100,110, 100, 30);
		frame.getContentPane().add(lblCardType);

		query();

		lblCardInformation = new JLabel("Card Information :");
		lblCardInformation.setBounds(50, 70, 120, 30);
		frame.getContentPane().add(lblCardInformation);

		employeeid = new JLabel(getEName(value));
		employeeid.setBounds(220, 340, 114, 30);
		frame.getContentPane().add(employeeid);

		totalfair = new JLabel(total_fair);
		totalfair.setBounds(220, 310, 114, 30);
		frame.getContentPane().add(totalfair);

		staytime = new JLabel(stay_time);
		staytime.setBounds(220, 270, 400, 30);
		frame.getContentPane().add(staytime);

		exittime = new JLabel(exit_time);
		exittime.setBounds(220, 230, 300, 30);
		frame.getContentPane().add(exittime);

		entryTime = new JLabel(entry_time);
		entryTime.setBounds(220, 190, 300, 30);
		frame.getContentPane().add(entryTime);

		cardnumber = new JLabel(sncard);
		cardnumber.setBounds(220, 150, 300, 30);
		frame.getContentPane().add(cardnumber);

		cardtype = new JLabel("Normal");
		cardtype.setBounds(220, 110, 300, 30);
		frame.getContentPane().add(cardtype);

	}

	private void query() {

		try {
			Socket server = new Socket(ipAddress, portNumer);
			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String sendMessage = requestMessage + '-' + ',' + sncard;
			sout.println(sendMessage);
			String s1 = sin.readLine();
			splitResult = s1.split(",");
			total_fair = splitResult[0] + " Tk";
			entry_time = splitResult[1];
			exit_time = splitResult[2];
			stay_time = splitResult[3];
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

	public void printPayment() {
		String msg = "payment done";
		try {
			Socket server = new Socket(ipAddress, portNumer);
			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String sendMessage = msg + '-' + ',' + sncard + ',' + splitResult[0] + ',' + splitResult[4];
			sout.println(sendMessage);
			String s1 = sin.readLine();
			if (s1.equalsIgnoreCase("Payment Done")) {
				JOptionPane.showMessageDialog(null, s1);
				frame.setVisible(false);
				new EmployeeHome(value);
			} else {
				JOptionPane.showMessageDialog(null, s1);

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

		Document document = new Document();
		try {

			String exitTime = exittime.getText();
			String[] dateValue = exitTime.split(":");

			String pdfName = cardnumber.getText() + "_" + dateValue[0] + "-" + dateValue[1] + "-" + dateValue[2]
					+ ".pdf";
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfName));
			document.open();
			document.add(new Paragraph("Payment Info"));

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(70);
			table.setSpacingBefore(8f);
			table.setSpacingAfter(8f);
			float[] colWidth = { 4f, 4f };
			table.setWidths(colWidth);
			PdfPCell c1 = new PdfPCell(new Paragraph(lblCardType.getText()));
			PdfPCell c2 = new PdfPCell(new Paragraph(cardtype.getText()));
			PdfPCell c3 = new PdfPCell(new Paragraph(lblCardNumber.getText()));
			PdfPCell c4 = new PdfPCell(new Paragraph(cardnumber.getText()));
			PdfPCell c5 = new PdfPCell(new Paragraph(lblentryTime.getText()));
			PdfPCell c6 = new PdfPCell(new Paragraph(entryTime.getText()));

			PdfPCell c7 = new PdfPCell(new Paragraph(lblExitTime.getText()));
			PdfPCell c8 = new PdfPCell(new Paragraph(exittime.getText()));
			PdfPCell c9 = new PdfPCell(new Paragraph(lblStayTime.getText()));
			PdfPCell c10 = new PdfPCell(new Paragraph(staytime.getText()));
			PdfPCell c11 = new PdfPCell(new Paragraph(lblTotalFair.getText()));
			PdfPCell c12 = new PdfPCell(new Paragraph(totalfair.getText()));
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
			table.addCell(c11);
			table.addCell(c12);
			document.add(table);

			document.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
