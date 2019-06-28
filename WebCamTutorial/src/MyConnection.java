import java.sql.*;

/**
 * Database Connection by 'jdbc'
 */
public class MyConnection {

	public static Connection getConnection() {
		Connection conn=null;
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost/carparking","root","");
		}
		catch(Exception e) {
			System.out.println("not found");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
}
