
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database Execution From Server to Register New 'Admin'
 */
public class ServerCreateAdmin {

	String[] data;
	ResultSet rsIndex, rsPrimary, rsSecondary;
	PreparedStatement stmtIndex, stmtInsert, stmtPrimary, stmtSecondary;
	String queryInsert, queryIndex, queryPrimary, res, querySecondary;
	File f;
	int size, possible = 1;
	FileInputStream fis;

	int position = 0;

	public String createAccount(String rfid) {

		data = rfid.split(",");

		/**
		 * Checking if there are already any User with same 'Email'
		 */

		queryPrimary = "SELECT * FROM `admin` WHERE `admin_mail`=? ";
		querySecondary = "SELECT * FROM `employ` WHERE `employ_mail`=? ";
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

			queryIndex = "SELECT max(`admin_id`) FROM `admin` ";
			try {

				stmtIndex = MyConnection.getConnection().prepareStatement(queryIndex);
				rsIndex = stmtIndex.executeQuery();
				if (rsIndex.next()) {

					position = rsIndex.getInt(1);
					position += 1;

				}
				queryInsert = "INSERT INTO `admin`(`admin_id`, `admin_name`, `admin_mail`, `admin_phoneNo`, `admin_type`, `admin_pass`, `ques`) VALUES (?,?,?,?,?,?,?)";
				stmtInsert = MyConnection.getConnection().prepareStatement(queryInsert);

				stmtInsert.setInt(1, position);
				stmtInsert.setString(2, data[1]);
				stmtInsert.setString(3, data[2]);
				stmtInsert.setString(4, data[3]);
				stmtInsert.setString(5, data[4]);
				stmtInsert.setString(6, "123456789");
				stmtInsert.setString(7, data[5]);
				if (stmtInsert.executeUpdate() > 0) {

					res = "New Admin Enrolled";
				} else {

					res = "Error to Create";
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return res;
	}

}
