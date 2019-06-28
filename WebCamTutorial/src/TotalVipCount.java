import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.xdevapi.Table;

public class TotalVipCount {
    static int i=0;
	private JFrame frame;  
	String requestMessage="totalvipcount";
	static String ipAddress;
    static int portNumer;  
    static String vehile_type,phnnbr,rfidnbr,system; 
    public static String s1; 
    public static String[] splitResult2; 
    public static String header[]; 
    public static String [][]data;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IpPortConfiguration ipConfig=new IpPortConfiguration();
					ipAddress=ipConfig.getIpAddress();
 			 		portNumer=ipConfig.getPortNumber();
					TotalVipCount window = new TotalVipCount();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TotalVipCount() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("CarParkingManagementSystem");
		frame.setBounds(0, 0, 1500, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.setBounds(696, 780, 102, 31);
		frame.getContentPane().add(btnPrint); 
		
		total_vipcount();
		/*
		
		String [] header={"Vehicle Type","Phone Number","RFID Number","System"};
		
       // String [][] data={{"Bike","01686544775","705","Prepaid"}};
		//String [][] data={{" "},{" "},{" "}};
		String [][] data=new String [1000][1000];
		 for(int i=0;i<3;i++)
		 { 
			 for(int j=0;j<4;j++) 
			 { 
				data[i][j]="tn ";
			 }
		 }
	  */
        DefaultTableModel model = new DefaultTableModel(data,header);

        JTable table = new JTable(model);
        table.setBounds(133, 116, 1205, 636);
        frame.getContentPane().add(table);

        JScrollPane js=new JScrollPane(table);
        js.setBounds(133, 91, 1205, 636);
        frame.getContentPane().add(js); 
        
        
       
	}

	void total_vipcount() {

		try {
			System.out.println("totalvipcount1");
			// while(i!=10)
			// {
			System.out.println("totalvipcount2");
			Socket server = new Socket(ipAddress, portNumer);
			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

			String sendMessage = requestMessage + '-';
			sout.println(sendMessage);
			s1 = sin.readLine(); 
			String flag[]=s1.split("&"); 
			String flagcount=flag[1];  
			int n=Integer.parseInt(flagcount); 
			System.out.println("n:"+n);
			String s1st=flag[0];

			System.out.println(s1);
			
			header = new String[4];
			header[0] = "Vehicel Type";
			header[1] = "Phone Number";
			header[2] = "RFID Number";
			header[3] = "System";
			data = new String[n][4];
		
			for (int i = 0; i < n; i++) { 
				
				String[] splitResult = s1st.split(";");
				System.out.println(splitResult[i]);
				String s22 = splitResult[i];
				splitResult2 = s22.split(",");
				System.out.println("back msg: " + splitResult2[1]);

				for (int j = 0; j < 4; j++) {
					data[i][j] = splitResult2[j];
				}
			}

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

	}  
	

}	      
	      
	


