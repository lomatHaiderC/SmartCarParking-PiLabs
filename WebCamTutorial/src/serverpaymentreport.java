import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class serverpaymentreport {  
	
	public String totalincome(String rfid) 
	{   
		String str=" ";
	
		 String[] card = rfid.split(",");
		 String from=card[1]; 
		 String to=card[2]; 
		
		 ResultSet vipincome; 
		 
		 String vc="SELECT*\r\n" + 
		 		"FROM\r\n" + 
		 		"(\r\n" + 
		 		"SELECT date(exit_time)as time,t1.rfid_no,t1.entry_time,t1.exit_time,t1.balance\r\n" + 
		 		"FROM \r\n" + 
		 		"(\r\n" + 
		 		"SELECT rfid_no,entry_time,exit_time,balance FROM `rfid_info` WHERE 1\r\n" + 
		 		"UNION \r\n" + 
		 		"SELECT rfid_no,entry_time,exit_time,balance FROM `vip_rfid_info` WHERE 1 \r\n" + 
		 		")as t1 \r\n" + 
		 		")as t2 WHERE time BETWEEN ? AND ? ";
	        PreparedStatement vcount;
	      
	        try {
	        	vcount= MyConnection.getConnection().prepareStatement(vc);
	        	vcount.setString(1, from);
	        	vcount.setString(2, to);
	            vipincome = vcount.executeQuery();
	            
	            
	           
	            int flag=0;
	            while (vipincome.next()) {
	            	if(flag==0) 
	            	{
	            		str+=vipincome.getString("rfid_no")+","+vipincome.getString("entry_time")+","+vipincome.getString("exit_time")+","+vipincome.getString("balance");
	            	
	            	}
	            	
	            	else 
	            	{  
	            	
	            		str+=";"+vipincome.getString("rfid_no")+","+vipincome.getString("entry_time")+","+vipincome.getString("exit_time")+","+vipincome.getString("balance");
	            	
	            	}	
	            		
	            	
	            	
	            	flag=flag+1;
	            	
	            }
	            str+="&";
	            str+=flag; 
	            
	            

	        } 
	        catch (Exception e1) {

	            e1.printStackTrace();
	        } 
	        return str;
	       
		 
		
	} 
	public String normalincome(String rfid)  
	{ 
		
		 String str=" ";
		 String[] card = rfid.split(",");
		 String from=card[1]; 
		 String to=card[2]; 
		 
		 ResultSet vipincome; 
		
	        String vc="SELECT*\r\n" + 
	        		"FROM\r\n" + 
	        		"(SELECT Date(exit_time)as time ,rfid_no,entry_time,exit_time,button_id,balance,rfid_info_id FROM `rfid_info`)as t1,rfid_info WHERE t1.rfid_info_id=rfid_info.rfid_info_id and time BETWEEN ? AND ? \r\n" + 
	        		"";
	        PreparedStatement vcount;
	      
	        try {
	        	vcount= MyConnection.getConnection().prepareStatement(vc);
	        	vcount.setString(1, from);
	        	vcount.setString(2, to);
	            vipincome = vcount.executeQuery();
	            
	            
	         
	            
	            int flag=0;
	            while (vipincome.next()) {
	            	if(flag==0) 
	            	{
	            		str+=vipincome.getString("button_id")+","+vipincome.getString("rfid_no")+","+vipincome.getString("entry_time")+","+vipincome.getString("exit_time")+","+vipincome.getString("balance");
	            
	            	}
	            	
	            	else 
	            	{  
	            		
	            		str+=";"+vipincome.getString("button_id")+","+vipincome.getString("rfid_no")+","+vipincome.getString("entry_time")+","+vipincome.getString("exit_time")+","+vipincome.getString("balance");
	            	
	            	}	
	            		
	            	
	            	
	            	flag=flag+1;
	            	
	            }
	            str+="&";
	            str+=flag;
	            

	        } catch (Exception e1) {

	            e1.printStackTrace();
	        } 
           return str;
		 
	} 
	
	public String vipincome(String rfid) 
	{  
		 String str=" ";
		
		 String[] card = rfid.split(",");
		 String from=card[1]; 
		 String to=card[2]; 
		
		 ResultSet vipincome; 
		 
	        String vc ="SELECT*\r\n" + 
	        		"FROM\r\n" + 
	        		"(SELECT Date(exit_time)as time ,rfid_no,entry_time,exit_time,carType,balance,vip_rfid_info_id FROM `vip_rfid_info`)as t1,vip_rfid_info WHERE t1.vip_rfid_info_id=vip_rfid_info.vip_rfid_info_id and time BETWEEN ? AND ? ";
	        PreparedStatement vcount;
	      
	        try {
	        	vcount= MyConnection.getConnection().prepareStatement(vc);
	        	vcount.setString(1, from);
	        	vcount.setString(2, to);
	            vipincome = vcount.executeQuery();
	            
	            
	           
	           
	            int flag=0;
	            while (vipincome.next()) {
	            	if(flag==0) 
	            	{
	            		str+=vipincome.getString("carType")+","+vipincome.getString("cardsystem")+","+vipincome.getString("rfid_no")+","+vipincome.getString("balance");
	          
	            	}
	            	
	            	else 
	            	{  
	            		
	            		str+=";"+vipincome.getString("carType")+","+vipincome.getString("cardsystem")+","+vipincome.getString("rfid_no")+","+vipincome.getString("balance");
	            	
	            	}	
	            		
	            	
	            	
	            	flag=flag+1;
	            	
	            }
	            str+="&";
	            str+=flag; 
	            
	            
 
System.out.println("flag"+flag); 
        } catch (Exception e1) {

	            e1.printStackTrace();
	        } 
           
		 
		 return str; 
		 
		 
		 
	}

}
