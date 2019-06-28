import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database connection to change password for both 'Admin' and 'Employee'
 * Admin Password changes in 'changeAdmin()' method
 * Employee Password changes in 'changeEmployee()' method
 */

public class ServerPassChange {
	String[] splitDate;
	String pass,update;

	public String changeAdmin(String rfid) {
		// TODO Auto-generated method stub
		splitDate = rfid.split(",");
		String returnResult = "0";

		ResultSet  rsAdmin;
		
		String queryAdmin = " SELECT * FROM `admin` WHERE `admin_mail`='" + splitDate[1] + "' ";
		PreparedStatement stmtUpdate, stmtAdmin;
		try {
			stmtAdmin = MyConnection.getConnection().prepareStatement(queryAdmin);
			rsAdmin = stmtAdmin.executeQuery();
			if (rsAdmin.next()) {
				pass=rsAdmin.getString("admin_pass");
				if(pass.equals(splitDate[2])) {
					update="UPDATE `admin` SET `admin_pass`=? WHERE `admin_mail`=? ";
					stmtUpdate = MyConnection.getConnection().prepareStatement(update);
					stmtUpdate.setString(1, splitDate[3]);
					stmtUpdate.setString(2, splitDate[1]);
					stmtUpdate.executeUpdate();
					returnResult="Password Updated";
				}
				
				else {
					returnResult = "Password Invalid";
				}
				
			} else {
				returnResult = "Invalid username";
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return returnResult;
	}

	public String changeEmployee(String rfid) {
		// TODO Auto-generated method stub
				splitDate = rfid.split(",");
				String returnResult = "0";

				ResultSet  rsAdmin;
				
				String queryAdmin = " SELECT * FROM `employ` WHERE `employ_mail`='" + splitDate[1] + "' ";
				PreparedStatement stmtUpdate, stmtAdmin;
				try {
					stmtAdmin = MyConnection.getConnection().prepareStatement(queryAdmin);
					rsAdmin = stmtAdmin.executeQuery();
					if (rsAdmin.next()) {
						pass=rsAdmin.getString("employ_pass");
						if(pass.equals(splitDate[2])) {
							update="UPDATE `employ` SET `employ_pass`=? WHERE `employ_mail`=? ";
							stmtUpdate = MyConnection.getConnection().prepareStatement(update);
							stmtUpdate.setString(1, splitDate[3]);
							stmtUpdate.setString(2, splitDate[1]);
							stmtUpdate.executeUpdate();
							returnResult="Password Updated";
						}
						
						else {
							returnResult = "Password Invalid";
						}
						
					} else {
						returnResult = "Invalid username";
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				return returnResult;
	}

}
