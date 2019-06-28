import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Necessary insertion for Entry. Both for VIP and Normal user
 */

public class ServerRFIDBooth {
	int clientCount, timeExit;
	int position, commaCount = 0;
	String[] splitInfoNormal, splitInfoVIP;
	String nowDate;
	String res = "0", type, paymentType;
	LocalDateTime now;
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public String rfidbooth(String rfid) {
		now = LocalDateTime.now();
		nowDate = dtf.format(now);
		clientCount++;
		commaCount = 0;
		res = "0";
		int i;
		for (i = 0; i < rfid.length(); i++) {
			char c = rfid.charAt(i);
			if (c == ',') {
				commaCount++;
			}
		}

		if (commaCount == 4) {
			splitInfoNormal = rfid.split(",");
		} else if (commaCount == 3) {
			splitInfoVIP = rfid.split(",");
		}
		// try end
		dbConnectionRFIDEntry();

		return res;
	}

	private void dbConnectionRFIDEntry() {
		// TODO Auto-generated method stub
		ResultSet rsPosition, rsFinal, rsValidRFID, rsReturn;
		PreparedStatement stmt, stmtStatus, stmtPosition, stmtFinal, stmtValidRFID, stmtUpdate, stmtReturn;
		String queryPosition, query, queryStatus, queryFinal, queryValidRFID, queryUpdate, queryReturn;

		if (commaCount == 3) {
			
			queryValidRFID = "SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`=? and isParked=' 0 ' ";
			//String value = splitInfoVIP[2];
			String value = splitInfoVIP[3];
			try {

				stmtValidRFID = MyConnection.getConnection().prepareStatement(queryValidRFID);

				stmtValidRFID.setString(1, value);

				rsValidRFID = stmtValidRFID.executeQuery();

				if (rsValidRFID.next()) {

					type = rsValidRFID.getString(7);
					paymentType = rsValidRFID.getString(8);

					queryPosition = " SELECT max(`vip_rfid_info_id`) FROM `vip_rfid_info`";

					query = " INSERT INTO `vip_rfid_info`(`vip_rfid_info_id`, `booth_id`, `carType`, `rfid_no`, `entry_time`, `image`, `rfid_info_status`,`exit_time`,`exit_booth_id`, `balance`, `cardsystem` ) VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
					stmtPosition = MyConnection.getConnection().prepareStatement(queryPosition);
					rsPosition = stmtPosition.executeQuery();
					if (rsPosition.next()) {

						position = rsPosition.getInt(1);
						position += 1;

					}
					stmt = MyConnection.getConnection().prepareStatement(query);
					stmt.setInt(1, position);
					stmt.setString(2, splitInfoVIP[1]);
					stmt.setString(3, type);
					//stmt.setString(4, splitInfoVIP[2]);
					stmt.setString(4, value);
					stmt.setString(5, nowDate);
					stmt.setString(6, null);
					stmt.setInt(7, 0);
					stmt.setString(8, nowDate);
					stmt.setString(9, "0");
					stmt.setDouble(10, 0);
					stmt.setString(11, paymentType);
					if (stmt.executeUpdate() > 0) {
						
					} else {

					}
					try {

						WebcamTutorial.setOption(1);
						WebcamTutorial.dbConnection();

					} catch (IOException e) {

						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					queryStatus = "UPDATE `vip_rfid_info` SET `rfid_info_status`=? WHERE `rfid_info_status`=' 0 ' and  `image`!='+null+' ORDEr BY `entry_time` desc  limit 1";

					stmtStatus = MyConnection.getConnection().prepareStatement(queryStatus);

					stmtStatus.setInt(1, 1);

					stmtStatus.executeUpdate();

					//value = splitInfoVIP[2];

					queryFinal = "SELECT * FROM `vip_rfid_info` WHERE `rfid_no`=? and `image`!='+null+' and `rfid_info_status`=' 1 ' ";

					stmtFinal = MyConnection.getConnection().prepareStatement(queryFinal);

					stmtFinal.setString(1, value);

					rsFinal = stmtFinal.executeQuery();

					if (rsFinal.next()) {

						queryUpdate = "UPDATE `vip_rfid` SET `isParked`=' 1 ' WHERE `generateRFID_NO`=? ";
						stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
						//stmtUpdate.setString(1, splitInfoVIP[2]);
						stmtUpdate.setString(1, value);
						stmtUpdate.executeUpdate();

						queryReturn = " SELECT * FROM `vehicle` WHERE `vehicle_type`=? ";
						stmtReturn = MyConnection.getConnection().prepareStatement(queryReturn);

						stmtReturn.setString(1, type);
						rsReturn = stmtReturn.executeQuery();
						if (rsReturn.next()) {
							timeExit = rsReturn.getInt(5);
							res = timeExit + "s";
						}
					}

				} else {

					res = "0s";
				}
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (commaCount == 4) {
			//String value="49006B60E5A7";
			String value = splitInfoNormal[3];
			String query1 = "";
			ResultSet rsQuery1;
			PreparedStatement stmtQuery1;
			//query1 = " SELECT * FROM `rfid` WHERE `isParked`=' 0 ' LIMIT 1 ";
			query1 = " SELECT * FROM `rfid` WHERE `isParked`=' 0 ' and `generateRFID_NO`=? ";
			try {
				stmtQuery1 = MyConnection.getConnection().prepareStatement(query1);
				stmtQuery1.setString(1, value);
				rsQuery1 = stmtQuery1.executeQuery();
				if (rsQuery1.next()) {
					//value = rsQuery1.getString("generateRFID_NO");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			queryValidRFID = "SELECT * FROM `rfid` WHERE `generateRFID_NO`=? and isParked=' 0 '  ";

			try {
				
				stmtValidRFID = MyConnection.getConnection().prepareStatement(queryValidRFID);

				stmtValidRFID.setString(1, value);

				rsValidRFID = stmtValidRFID.executeQuery();

				if (rsValidRFID.next()) {
					type = splitInfoNormal[2];

					queryPosition = " SELECT max(`rfid_info_id`) FROM `rfid_info`";

					query = " INSERT INTO `rfid_info`(`rfid_info_id`, `booth_id`, `button_id`, `rfid_no`, `entry_time`, `image`, `rfid_info_status`, `paymentdone`, `settime`, `exit_time`,`exit_booth_id`,`balance`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

					try {
						stmtPosition = MyConnection.getConnection().prepareStatement(queryPosition);
						rsPosition = stmtPosition.executeQuery();
						if (rsPosition.next()) {

							position = rsPosition.getInt(1);
							position += 1;

						}

						stmt = MyConnection.getConnection().prepareStatement(query);
						stmt.setInt(1, position);
						stmt.setString(2, splitInfoNormal[1]);
						stmt.setString(3, splitInfoNormal[2]);
						stmt.setString(4, value);
						stmt.setString(5, nowDate);
						stmt.setString(6, null);
						stmt.setInt(7, 0);
						stmt.setInt(8, 0);
						stmt.setInt(9, 0);
						stmt.setString(10, nowDate);
						stmt.setString(11, "0");
						stmt.setDouble(12, 0);
						if (stmt.executeUpdate() > 0) {

						} else {

						}

						try {

							WebcamTutorial.setOption(2);
							WebcamTutorial.dbConnection();
						} catch (IOException e) {

							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						queryStatus = "UPDATE `rfid_info` SET `rfid_info_status`=? WHERE `rfid_info_status`=' 0 ' and  `image`!='+null+' ORDEr BY `entry_time` desc LIMIT 1";

						stmtStatus = MyConnection.getConnection().prepareStatement(queryStatus);
						stmtStatus.setInt(1, 1);
						stmtStatus.executeUpdate();

						String rfidValue = value;

						queryFinal = "SELECT * FROM `rfid_info` WHERE `rfid_no`=? and `image`!='+null+' and `rfid_info_status`=' 1 ' ";

						stmtFinal = MyConnection.getConnection().prepareStatement(queryFinal);
						stmtFinal.setString(1, rfidValue);
						rsFinal = stmtFinal.executeQuery();
						if (rsFinal.next()) {
							queryUpdate = "UPDATE `rfid` SET `isParked`=' 1 ' WHERE `generateRFID_NO`=? ";
							stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
							stmtUpdate.setString(1, value);
							stmtUpdate.executeUpdate();

							// res="1";
							queryReturn = " SELECT * FROM `vehicle` WHERE `vehicle_type`=? ";
							stmtReturn = MyConnection.getConnection().prepareStatement(queryReturn);

							stmtReturn.setString(1, type);

							rsReturn = stmtReturn.executeQuery();
							if (rsReturn.next()) {
								timeExit = rsReturn.getInt(5);
								res = timeExit + "s";

							}
						}

					} catch (SQLException e) {

						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {

					res = "0s";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
