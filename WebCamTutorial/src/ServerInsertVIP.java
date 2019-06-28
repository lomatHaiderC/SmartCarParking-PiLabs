
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Server Connection with database for necessary insertion of new VIP User
 */
public class ServerInsertVIP {
	String[] splitVIPInsert;
	LocalDateTime now;
	String nowDate;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public String insertVIPMember(String rfid) {
		now = LocalDateTime.now();

		splitVIPInsert = rfid.split(",");

		String returnResult = "0";
		ResultSet rsIndex, rsPrimary;
		PreparedStatement stmtIndex, stmtInsert, stmtPrimary;
		String queryInsert, queryIndex, queryPrimary;
		int position = 0;
		now = LocalDateTime.now();
		int possible = 0;
		nowDate = dtf.format(now);
		String number=splitVIPInsert[5];
		queryPrimary = "SELECT * FROM `vip_card` where `card_no`=? and  `isUsed`=' 0 ' ";
		try {
			stmtPrimary = MyConnection.getConnection().prepareStatement(queryPrimary);
			stmtPrimary.setString(1, number);
			rsPrimary = stmtPrimary.executeQuery();
			if (rsPrimary.next()) {
				possible = 1;
			} else {

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (possible == 1) {
			queryIndex = "SELECT max(`VIPrfid_No`) FROM `vip_rfid` ";
			try {
				stmtIndex = MyConnection.getConnection().prepareStatement(queryIndex);
				rsIndex = stmtIndex.executeQuery();
				if (rsIndex.next()) {
					position = rsIndex.getInt(1);
					position += 1;

				}
				queryInsert = "INSERT INTO `vip_rfid`(`VIPrfid_No`, `VIPrfid_issue_date`, `VIPrfid_remaining_balance`,"
						+ " `VIPrfid_balance_used`, `VIPrfid_mobile_no`, `VIPrfid_lastPayment`, `VIPrfid_vehicle_type`,"
						+ " `VIPrfid_payment_type`,  `generateRFID_NO`,`isParked`) VALUES (?,?,?,?,?,?,?,?,?,?)";

				stmtInsert = MyConnection.getConnection().prepareStatement(queryInsert);
				stmtInsert.setInt(1, position);
				stmtInsert.setString(2, nowDate);
				double vipBalance = Double.parseDouble(splitVIPInsert[2]);
				stmtInsert.setDouble(3, vipBalance);
				stmtInsert.setDouble(4, 0);
				stmtInsert.setString(5, splitVIPInsert[4]);
				stmtInsert.setString(6, nowDate);
				stmtInsert.setString(7, splitVIPInsert[1]);
				stmtInsert.setString(8, splitVIPInsert[3]);
				stmtInsert.setString(9, number);
				stmtInsert.setInt(10, 0);

				if (stmtInsert.executeUpdate() > 0) {
					returnResult = "Insert Done," + number;
				} else {
					returnResult = "Insert Error," + 0;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			returnResult = "Insert Error," + 0;
		}

		return returnResult;
	}

}
