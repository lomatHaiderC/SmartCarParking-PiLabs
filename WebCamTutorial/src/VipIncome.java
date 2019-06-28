import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class VipIncome {

	public static  JFrame frame; 
	String requestMessage="vipincome";
	static String ipAddress;
    static int portNumer;
    public static String header[]; 
    public static String [][]data;
    public static String s1; 
    public static String[] splitResult2; 
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf_1=new SimpleDateFormat("yyyy-MM-dd");
	public static String from;
	public static String to; 
	public static String result=" ";

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
					VipIncome window = new VipIncome();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public VipIncome() throws ParseException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 */
	private void initialize() throws ParseException {
		frame = new JFrame("CarParkingManagementSystem");
		frame.setBounds(0, 0, 1500, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null); 
		
		//newupdate  
		
		
	        
	        Label label = new Label("Search :");
	        label.setBounds(133, 33, 82, 27);
	        frame.getContentPane().add(label);
	        
	        Label label_1 = new Label("From :");
	        label_1.setBounds(221, 33, 82, 27);
	        frame.getContentPane().add(label_1); 
	        
	        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		       
	        JDateChooser dateChooser = new JDateChooser(); 
	        
	        dateChooser.setDateFormatString("yyyy-MM-dd");
	       // System.out.println(dateChooser.getDateFormatString());
	        
	        dateChooser.setBounds(300, 32, 125, 26);
	        frame.getContentPane().add(dateChooser);   
	        //System.out.println(dateChooser.getDateEditor().getDate());
	        // from=dateChooser.getDateEditor().getDate(); 
	        
	        
	        Label label_2 = new Label("To :");
	        label_2.setBounds(461, 33, 82, 27);
	        frame.getContentPane().add(label_2);
	        
	        DateFormat fmt_1 = new SimpleDateFormat("dd/MM/yyyy");
		    
	        JDateChooser dateChooser_1 = new JDateChooser();
	        
	        dateChooser_1.setDateFormatString("yyyy-MM-dd");
	        
	        dateChooser_1.setBounds(549, 32, 125, 26);
	        frame.getContentPane().add(dateChooser_1); 
	       
	        
	        JButton reset = new JButton("Data");
	    	reset.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			
	    			 JButton btnPrint = new JButton("Print");
	 				btnPrint.setBounds(696, 780, 102, 31);
	 				frame.getContentPane().add(btnPrint); 
	 				 System.out.println("end"); 
	 				 
	    			 from=sdf.format(dateChooser.getDateEditor().getDate());
	    	        System.out.println(from); 
	    	        to=sdf.format(dateChooser_1.getDateEditor().getDate());
	    	        System.out.println(from); 
	    	        System.out.println(to); 
	    	        vipincome(from,to); 
	    	        System.out.println("here end the function"); 
	    	              
	    		}
	    	});
	    	
	    	
	    	reset.setBounds(700, 32, 97, 30);
			frame.getContentPane().add(reset);  
			
			 
			
			
			
		
		

       
        
	} 
	
	public void vipincome(String from,String to)
	{  
		JLabel lblTotalIncome = new JLabel("Total Income :");
        lblTotalIncome.setBounds(1098, 33, 142, 31);
        frame.getContentPane().add(lblTotalIncome);
        
        JLabel income = new JLabel("1500");
        income.setFont(new Font("Tahoma", Font.PLAIN, 20));
        income.setForeground(Color.RED);
        income.setBounds(1221, 33, 142, 31);
        frame.getContentPane().add(income);
		try {
			System.out.println("totalvipcount1");
			// while(i!=10)
			// {
			System.out.println("vipincome");
			Socket server = new Socket(ipAddress, portNumer);
			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
          //  String from="2019-04-01"; 
           // String to="2019-05-05";
			String sendMessage = requestMessage + '-'+','+from+','+to; 
			System.out.println(sendMessage);
			sout.println(sendMessage);  
			
			
			//backmsg from server 
			s1 = sin.readLine(); 
			String flag[]=s1.split("&"); 
			String flagcount=flag[1];  
			int n=Integer.parseInt(flagcount); 
			System.out.println("n:"+n);
			String s1st=flag[0];

			System.out.println(s1);
			
			header = new String[4];
			header[0] = "Vehicel Type";
			header[1] = "System";
			header[2] = "RFID Number";
			header[3] = "Income";
			data = new String[n][4]; 
			 double sum=0; 
			double temp;
		
			for (int i = 0; i < n; i++) { 
				
				String[] splitResult = s1st.split(";");
				System.out.println(splitResult[i]);
				String s22 = splitResult[i];
				splitResult2 = s22.split(",");
			//	System.out.println("back msg: " + splitResult2[1]);

				for (int j = 0; j < 4; j++) {
					data[i][j] = splitResult2[j]; 
					if(j==3)
					{  
						 temp=Double.parseDouble(data[i][j]); 
						 sum=sum+temp;
						
					}
					
				} 
				System.out.println(sum); 
				result=String.valueOf(sum);
			} 
			
			//update here
		   //end
		
        System.out.println(result); 

			//end

			//end 
			 DefaultTableModel model = new DefaultTableModel(data,header);

		        JTable table = new JTable(model);
		        table.setBounds(133, 116, 1205, 636);
		        frame.getContentPane().add(table);

		        JScrollPane js=new JScrollPane(table);
		        js.setBounds(133, 91, 1205, 636);
		        frame.getContentPane().add(js); 
		        
		       
			 
				//date choose korar por jeno print button ta ashe
				
				
		        
			
			
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
