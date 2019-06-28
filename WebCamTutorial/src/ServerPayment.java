import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Passes Normal User Payment Information and do necessary update in Payment is
 * done
 */
public class ServerPayment {
	String exit_time, sncard, query, entry_time, stay_time, total_fair, returnResult = "0";
	String nowDate;
	LocalDateTime now;
	Double totalFare;
	String[] card;
	ResultSet rs, rs2;;
	PreparedStatement stmt;
	double tk;
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	String timeDiff;
	Double[] fare = new Double[5];
	Double prevFare;

	public String payment(String rfid) {
		now = LocalDateTime.now();
		nowDate = dtf.format(now);
		String[] card = rfid.split(",");
		String sncard = card[1];
		LocalDateTime now = LocalDateTime.now();
		exit_time = dtf.format(now);
		ResultSet rs;
		String query = "SELECT timeDiff(now(),`entry_time`),`entry_time`,`balance`  FROM `rfid_info` WHERE `rfid_no`='"
				+ sncard + "' and `rfid_info_status`=' 1 ' limit 1 ";
		PreparedStatement stmt;
		try {
			stmt = MyConnection.getConnection().prepareStatement(query);
			rs = stmt.executeQuery();
			if (rs.next()) {
				timeDiff = rs.getString(1);
				String[] timeSplit = timeDiff.split(":");
				fare[0] = Double.parseDouble(timeSplit[0]);
				fare[1] = Double.parseDouble(timeSplit[1]);
				fare[1] = fare[1] + 1;
				fare[1] = fare[1] / 60;
				fare[2] = Double.parseDouble(timeSplit[2]);
				entry_time = rs.getString("entry_time");
				prevFare = rs.getDouble("balance");
				staytime_calculation(sncard);
			}

		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		return returnResult;
	}

	private void staytime_calculation(String sncard)

	{
		String rent = " SELECT*\n" + "                from\n" + "                (SELECT button_id\n"
				+ "                FROM rfid_info \n" + "                where rfid_no='" + sncard
				+ "' and `rfid_info_status`=' 1 ' limit 1)\n" + "                as t1,vehicle\n"
				+ "                where button_id=vehicle_type";

		PreparedStatement stmt1;
		try {
			stmt1 = MyConnection.getConnection().prepareStatement(rent);
			rs2 = stmt1.executeQuery();

			if (rs2.next()) {
				tk = rs2.getDouble("vehicle_farePerHr");
			}

		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		try {

			fare[0] = fare[0] * tk;
			fare[1] = fare[1] * tk;

			totalFare = fare[0] + fare[1];
			totalFare = Math.ceil(totalFare);

			total_fair = Double.toString(totalFare);
			returnResult = total_fair + ',' + entry_time + ',' + exit_time + ',' + timeDiff + ',' + prevFare;
			System.out.println("tk: "+tk+" "+returnResult);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String paymentdone(String rfid) {
		now = LocalDateTime.now();
		nowDate = dtf.format(now);

		LocalDateTime now = LocalDateTime.now();
		exit_time = dtf.format(now);

		String[] card = rfid.split(",");
		String sncard = card[1];
		double fare = Double.parseDouble(card[2]);
		prevFare = Double.parseDouble(card[3]);
		String query2 = "update rfid_info set exit_time= ?,paymentdone=' 1 ',`settime`=' 0 ',`balance`=? where rfid_no = ? and `rfid_info_status`=' 1 ' limit 1";
		PreparedStatement stmt2;

		try {
			stmt2 = MyConnection.getConnection().prepareStatement(query2);
			fare = prevFare + fare;
			stmt2.setString(1, exit_time);
			stmt2.setDouble(2, fare);
			stmt2.setString(3, sncard);
			returnResult = "Payment Done";
			stmt2.executeUpdate();
		} catch (SQLException e) {
			returnResult = "Exception found";
		}

		return returnResult;

	}

}
