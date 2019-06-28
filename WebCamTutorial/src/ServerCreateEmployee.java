import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database Execution From Server to Register New 'Admin'
 */

public class ServerCreateEmployee {

	String[] data;
	ResultSet rsIndex, rsPrimary, rsSecondary;
	PreparedStatement stmtIndex, stmtInsert, stmtPrimary, stmtSecondary;
	String queryInsert, queryIndex, queryPrimary, querySecondary, res;
	File f;
	int size, possible = 1;
	FileInputStream fis;

	int position = 0;

	public String createAccount(String rfid) {

		data = rfid.split(",");

		/**
		 * Checking if there are already any User with same 'Email'
		 */

		queryPrimary = "SELECT * FROM `employ` WHERE `employ_mail`=? ";
		querySecondary = "SELECT * FROM `admin` WHERE `admin_mail`=? ";

		try {
			stmtPrimary = MyConnection.getConnection().prepareStatement(queryPrimary);
			stmtPrimary.setString(1, data[2]);
			rsPrimary = stmtPrimary.executeQuery();
			stmtSecondary = MyConnection.getConnection().prepareStatement(querySecondary);
			stmtSecondary.setString(1, data[2]);
			rsSecondary = stmtSecondary.executeQuery();
			if (rsPrimary.next()) {
				return "Already Exists";
			} else if (rsSecondary.next()) {
				return "Already Exists";
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/**
		 * if the user doesn't exist in the system, it will enroll the user
		 */

		if (possible == 1) {
			queryIndex = "SELECT max(`employ_id`) FROM `employ` ";
			try {
				stmtIndex = MyConnection.getConnection().prepareStatement(queryIndex);
				rsIndex = stmtIndex.executeQuery();
				if (rsIndex.next()) {
					position = rsIndex.getInt(1);
					position += 1;

				}
				queryInsert = "INSERT INTO `employ`(`employ_id`, `employ_name`, `employ_mail`, `employ_phoneNo`, `employ_pass`, `admin_id`,`ques`) VALUES (?,?,?,?,?,?,?)";
				stmtInsert = MyConnection.getConnection().prepareStatement(queryInsert);

				stmtInsert.setInt(1, position);
				stmtInsert.setString(2, data[1]);
				stmtInsert.setString(3, data[2]);
				stmtInsert.setString(4, data[3]);
				stmtInsert.setString(5, "123456");
				stmtInsert.setString(6, data[5]);
				stmtInsert.setString(7, data[4]);
				if (stmtInsert.executeUpdate() > 0) {

					res = "New Employee Enrolled";
				} else {
					res = "Error to Create";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// TODO Auto-generated method stub
		return res;
	}

}
