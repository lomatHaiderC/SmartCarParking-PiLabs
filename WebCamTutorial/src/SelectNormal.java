
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * To Check whether the card is valid or parked. 
 * If the card is valid it will show the corresponding information of that card in next page
 * If card is not valid or not parked it will show a notification
 */

public class SelectNormal {

    JFrame frame;
    public static String cardnbr;
    
    static String ipAddress;
    static int portNumer; 
    String requestMessage="Card Check";
    int value;

    /**
     * Launch the application.
     */

    /**
     * Create the application.
     */
    public SelectNormal(int n) { 
    	value=n;
	    IpPortConfiguration ipConfig=new IpPortConfiguration();
 		ipAddress=ipConfig.getIpAddress();
 		portNumer=ipConfig.getPortNumber();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("CarParkingManagementSystem");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((screenSize.width*29)/100,(screenSize.height*26)/100);
       // frame.setBounds(100, 100, 400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        
        JButton back=new JButton("Back");
        back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.setVisible(false);
				new Payment1(value); 
			}
		});
        back.setBounds(10,10,80, 20);
        frame.getContentPane().add(back);
        

        JTextField card = new JTextField();
        card.setBounds(130, 50, 150, 30);
        frame.getContentPane().add(card);
        card.setColumns(10);

        JLabel lblCardNo = new JLabel("Card No :");
        lblCardNo.setBounds(70, 50, 130, 23);
        frame.getContentPane().add(lblCardNo);

        JButton btnok = new JButton("Ok");
        btnok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	
                cardnbr = card.getText();
                try {
					nbrcheck();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }
        });

        btnok.setBounds(175, 90, 55, 30);
        frame.getContentPane().add(btnok);

    }

    public void nbrcheck() throws UnknownHostException, IOException 
    {
    	try
    	{
        
    	Socket server=new Socket(ipAddress,portNumer);
        BufferedReader sin=new BufferedReader(new InputStreamReader(server.getInputStream()));
        PrintStream sout=new PrintStream(server.getOutputStream());
        BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));    
        String user="normal";
        String sendMessage =requestMessage+'-'+','+cardnbr+','+user ; 
        sout.println(sendMessage);
        String s1=sin.readLine(); 
        String[] splitResult=s1.split(","); 
       
        if(splitResult[0].equals(cardnbr)) {
        	frame.setVisible(false);
        	new NormalPayment(cardnbr,value); 
        }
        else 
        { 
        	JOptionPane.showMessageDialog(null, "Invalid Card Number/Not Parked");
        	frame.setVisible(false);
        	new EmployeeHome(value);
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
       
    }
}
