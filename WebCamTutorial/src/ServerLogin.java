import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database Connection to Log in an user
 */
public class ServerLogin extends LoginSession {
	String[] splitLogin;

	public String loginUser(String rfid) {

		// TODO Auto-generated method stub

		splitLogin = rfid.split(",");
		String returnResult = "0";

		ResultSet rsEmployee, rsAdmin;
		String queryEmployee = " SELECT * FROM `employ` WHERE `employ_mail`='" + splitLogin[1] + "' and `employ_pass`='"
				+ splitLogin[2] + "' ";
		String queryAdmin = " SELECT * FROM `admin` WHERE `admin_mail`='" + splitLogin[1] + "' and `admin_pass`='"
				+ splitLogin[2] + "' ";
		PreparedStatement stmtEmployee, stmtAdmin;
		try {
			stmtEmployee = MyConnection.getConnection().prepareStatement(queryEmployee);
			stmtAdmin = MyConnection.getConnection().prepareStatement(queryAdmin);
			rsEmployee = stmtEmployee.executeQuery();
			rsAdmin = stmtAdmin.executeQuery();
			if (rsEmployee.next()) {
				returnResult = "Employee Logged In";

			} else if (rsAdmin.next()) {
				returnResult = "Admin Logged In";
			} else {
				returnResult = "Invalid username or password";
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// TODO Auto-generated method stub
		return returnResult;
	}

}
