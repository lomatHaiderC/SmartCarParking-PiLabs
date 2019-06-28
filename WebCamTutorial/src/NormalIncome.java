import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.Toolkit;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
/**
 * Will show the Normal income 
 */
public class NormalIncome{

	public static  JFrame frame; 
	String requestMessage="normalincome";
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
	int value;  
	public static double sum;
	JComboBox<String> userType; 
	JLabel lblTotalIncome, income;

	/**
	 * Launch the application.
	 */  
	
	public NormalIncome(int n) {
		value = n;
		initialize();
	}
	 
	

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	

	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 */
	private void initialize() {  
		
		IpPortConfiguration ipConfig = new IpPortConfiguration();
		ipAddress = ipConfig.getIpAddress();
		portNumer = ipConfig.getPortNumber();
		frame = new JFrame("CarParkingManagementSystem"); 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((screenSize.width),(screenSize.height));
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null); 
		frame.setVisible(true); 
		//add button
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				new ReportPage(value);
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
				new AdminHome(value);
			}
		});
		ok.setBounds(600, 650, 102, 31);
		frame.getContentPane().add(ok);
		
		//combo box add here 
		userType = new JComboBox<String>();
		userType.setBounds(10, 40, 100, 30);
		frame.getContentPane().add(userType);
		userType.addItem("All");
		userType.addItem("Normal Income");
		userType.addItem("Vip Income");
		userType.setSelectedIndex(1);

		  
		
		//total fair show   
				
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
		
		//combo box  
		
		int typeSelected = userType.getSelectedIndex();
		if (typeSelected == 0) {
			frame.setVisible(false);
			new TotalIncome(value);
		} else if (typeSelected == 1) {
			normalincome(from,to);
		} else if (typeSelected == 2) {
			frame.setVisible(false);
			new VipIncomeReport(value);
		}
		 
		
		
	        
	        Label label = new Label("Search :"); 
	        label.setBounds(133, 33, 82, 27);
	       
	        frame.getContentPane().add(label);
	        
	        Label label_1 = new Label("From :");
	        label_1.setBounds(221, 33, 82, 27);
	        frame.getContentPane().add(label_1); 
	        
	        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		       
	        JDateChooser dateChooser = new JDateChooser(); 
	        
	        dateChooser.setDateFormatString("yyyy-MM-dd");
	     
	        
	        dateChooser.setBounds(300, 32, 125, 26);
	        frame.getContentPane().add(dateChooser);   
	        
	        
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
	 				 
	 				 
	    			 from=sdf.format(dateChooser.getDateEditor().getDate());
	    	     
	    	        to=sdf.format(dateChooser_1.getDateEditor().getDate()); 
	    	        normalincome(from,to); 
	    	         
	    	              
	    		}
	    	});
	    	
	    	
	    	reset.setBounds(700, 32, 97, 30);
			frame.getContentPane().add(reset);  
			
			
			 
			
			
		
		

       
        
	} 
	
	public void normalincome(String from,String to)
	{  
		 
		
		try {
			
			Socket server = new Socket(ipAddress, portNumer);
			BufferedReader sin = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintStream sout = new PrintStream(server.getOutputStream());
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
         
			String sendMessage = requestMessage + '-'+','+from+','+to; 
		
			sout.println(sendMessage);  
			
			
			//backmsg from server 
			s1 = sin.readLine(); 
			String flag[]=s1.split("&"); 
			String flagcount=flag[1];  
			int n=Integer.parseInt(flagcount); 

			String s1st=flag[0];


			
			header = new String[5];
			header[0] = "Vehicel Type";
			header[1] = "RFID NUMBER";
			header[2] = "Entry Time";
			header[3] = "Exit Time"; 
			header[4] = "Total Fair";
			data = new String[n][5]; 
			 sum=0; 
			double temp;
		
			for (int i = 0; i < n; i++) { 
				
				String[] splitResult = s1st.split(";");
				
				String s22 = splitResult[i];
				splitResult2 = s22.split(",");
		

				for (int j = 0; j < 5; j++) {
					data[i][j] = splitResult2[j]; 
					if(j==4)
					{  
						 temp=Double.parseDouble(data[i][j]); 
						 sum=sum+temp;
						
					}
					
				} 
				
				result=String.valueOf(sum);
			} 
			
			
			 DefaultTableModel model = new DefaultTableModel(data,header);

		        JTable table = new JTable(model);
		        table.setBounds(133, 100, 1100, 500);
		        frame.getContentPane().add(table);

		        JScrollPane js=new JScrollPane(table);
		        js.setBounds(133, 100, 1100, 500);
		        frame.getContentPane().add(js); 
		        
		       
			 
				
		        lblTotalIncome.setVisible(true);
				income.setText(result+ "");
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
					new TotalIncome(value);
				} else if (typeSelected == 2) {
					frame.setVisible(false);
					new VipIncomeReport(value);
				}
			}
		});
	}
	}


