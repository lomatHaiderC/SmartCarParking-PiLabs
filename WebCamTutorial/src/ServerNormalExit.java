
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Necessary database connection for 'Normal User' exit If bill is paid, he can
 * exit If bill is paid but exit time is over then make some update in database
 * and user has to pay again If bill is not paid,user must pay the bill
 */
public class ServerNormalExit {
	String time, exittime, entrytime, exit_boothid, returnResult = "0", datediff;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	int hour, min, totalTime;

	public String exit(String rfid) {
		// TODO Auto-generated method stub

		String[] exitcard = rfid.split(",");
		String exitcardnbr = exitcard[2];
		exit_boothid = exitcard[1];
		ResultSet rs1;
		String query1 = "SELECT * FROM `rfid_info` WHERE `rfid_no`='" + exitcardnbr
				+ "' and `rfid_info_status` = ' 1 ' ";

		PreparedStatement stmt1;
		try {
			stmt1 = MyConnection.getConnection().prepareStatement(query1);
			rs1 = stmt1.executeQuery();
			if (rs1.next()) {

				int paymentdone = rs1.getInt("paymentdone");
				int setTime = rs1.getInt("settime");
				if (paymentdone == 1) {
					query(exitcardnbr);
				} else if (paymentdone == 0 && setTime == 0) {
					returnResult = "0s";
				} else if (paymentdone == 0 && setTime == 1) {
					returnResult = "-1s";

				}

			} else {
				returnResult = "-2s";
			}

		} catch (SQLException e1) {

		}
		return returnResult;
	}

	public void query(String exitcardnbr) {
		String carType = null;
		LocalDateTime now = LocalDateTime.now();
		time = dtf.format(now);
		ResultSet res;
		String ans = "SELECT *,timeDiff(now(),`exit_time`) FROM `rfid_info` WHERE `rfid_no`='" + exitcardnbr
				+ "' and `rfid_info_status` = ' 1 ' ";
		PreparedStatement ment;
		try {
			ment = MyConnection.getConnection().prepareStatement(ans);
			res = ment.executeQuery();

			if (res.next()) {

				datediff = res.getString("timeDiff(now(),`exit_time`)");
				String[] timeDiff = datediff.split(":");
				hour = (Integer.parseInt(timeDiff[0])) * 60;
				min = Integer.parseInt(timeDiff[1]);
				totalTime = hour + min;

				exittime = res.getString("exit_time");
				carType = res.getString("button_id");
			}
			ment.close();
		} catch (Exception e) {
		}

		int set = 1;
		ResultSet timeset;
		String settime = " SELECT * FROM `settime` where `Type`=? ";
		PreparedStatement st;
		try {
			st = MyConnection.getConnection().prepareStatement(settime);
			st.setString(1, carType);
			timeset = st.executeQuery();
			if (timeset.next()) {

				set = timeset.getInt("set_time");

			} else {

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		if (totalTime > set) {

			String query2 = "update rfid_info set settime= ? , paymentdone= ? , entry_time= ?, exit_time= ? where rfid_no = ? and `rfid_info_status` = ' 1 ' ";
			PreparedStatement stmt2;
			try

			{

				stmt2 = MyConnection.getConnection().prepareStatement(query2);
				stmt2.setInt(1, 1);
				stmt2.setInt(2, 0);
				stmt2.setString(3, exittime);
				stmt2.setString(4, time);

				stmt2.setString(5, exitcardnbr);
				stmt2.executeUpdate();
			} catch (SQLException e) {

			}
			returnResult = "-1s";

		} else if (totalTime <= set) {

			int open_barrier = 0;
			ResultSet rs2;
			String rent = " SELECT*\n" + "                from\n" + "                (SELECT button_id\n"
					+ "                FROM rfid_info \n" + "                where rfid_no='" + exitcardnbr
					+ "' and `rfid_info_status` = ' 1 '  )\n" + "                as t1,vehicle\n"
					+ "                where button_id=vehicle_type";

			PreparedStatement stmt;
			try {
				stmt = MyConnection.getConnection().prepareStatement(rent);
				rs2 = stmt.executeQuery();

				if (rs2.next()) {
					open_barrier = rs2.getInt("open_barrier");
					returnResult = open_barrier + "";

				}

			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			String qq = "update rfid_info set rfid_info_status	= ?,exit_booth_id=?,exit_time= ? where rfid_no = ? and `rfid_info_status` = ' 1 ' ";

			PreparedStatement ss;

			try {
				ss = MyConnection.getConnection().prepareStatement(qq);
				ss.setInt(1, 0);
				ss.setString(2, exit_boothid);
				ss.setString(3, time);
				ss.setString(4, exitcardnbr);
				ss.executeUpdate();
			} catch (SQLException e) {

			}
			String rfidUp = "update `rfid` set `isParked`	= ' 0 ' where `generateRFID_NO`= ? and `isParked` = ' 1 ' ";

			PreparedStatement ssUp;

			try {
				ssUp = MyConnection.getConnection().prepareStatement(rfidUp);

				ssUp.setString(1, exitcardnbr);
				ssUp.executeUpdate();
			} catch (SQLException e) {

			}

		}

	}

}
