
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serves the necessary information for VIP Payment 'postvippayment(String
 * rfid)' method contains the 'Postpaid' user information
 * 'vippostpaymentdone(String rfid)' method update the information for
 * 'Postpaid' user if payment is done 'prepaidpayment(String rfid)' method
 * contains the 'Prepaid' user information 'vipprepaymentdone(String rfid)'
 * method update the information for 'Prepaid' user if payment is done
 */

public class ServerVipPayment {
	String res;
	String mbl, vipcartyp;
	double balanceused;
	String VIPrfid_lastPayment, VIPrfid_issue_date = null;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public String postvippayment(String rfid) {
		String[] card = rfid.split(",");
		String cardnbr = card[1];

		ResultSet pay;
		String vippayment = " SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`='" + cardnbr + "' ";

		PreparedStatement vpay;
		try {
			vpay = MyConnection.getConnection().prepareStatement(vippayment);
			pay = vpay.executeQuery();
			if (pay.next()) {

				balanceused = pay.getDouble("VIPrfid_balance_used");
				mbl = pay.getString("VIPrfid_mobile_no");
				vipcartyp = pay.getString("VIPrfid_vehicle_type");
				String balance = balanceused + "";
				res = balance + ',' + mbl + ',' + vipcartyp;

			}

		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		return res;

	}

	public String vippostpaymentdone(String rfid) {
		res = "Payment not done";
		String[] card = rfid.split(",");
		String sncard = card[1];
		LocalDateTime now = LocalDateTime.now();
		VIPrfid_lastPayment = dtf.format(now);
		String q = "update vip_rfid set VIPrfid_balance_used= ?, VIPrfid_lastPayment= ? where generateRFID_NO = ?";

		PreparedStatement s;
		try {
			s = MyConnection.getConnection().prepareStatement(q);
			s.setDouble(1, 0.0);
			s.setString(2, VIPrfid_lastPayment);
			s.setString(3, sncard);
			s.executeUpdate();
			res = VIPrfid_lastPayment + ',' + "Payment Done";
		} catch (SQLException e) {

		}

		return res;
	}

	public String prepaidpayment(String rfid) {
		res = null;
		double remain, blanaceused;

		String[] card = rfid.split(",");
		String cardnbr = card[1];

		ResultSet pay;
		String vippayment = " SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`='" + cardnbr + "' ";

		PreparedStatement vpay;
		try {
			vpay = MyConnection.getConnection().prepareStatement(vippayment);
			pay = vpay.executeQuery();
			if (pay.next()) {

				remain = pay.getDouble("VIPrfid_remaining_balance");
				blanaceused = pay.getDouble("VIPrfid_balance_used");
				vipcartyp = pay.getString("VIPrfid_vehicle_type");
				String remaining = remain + "";
				String balance = blanaceused + "";
				res = remaining + ',' + balance + ',' + vipcartyp;

			}

		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		return res;
	}

	public String vipprepaymentdone(String rfid) {
		String[] card = rfid.split(",");
		String sncard = card[1];
		String deposite = card[2];
		double remain, ans = 0.0;

		LocalDateTime now = LocalDateTime.now();
		VIPrfid_lastPayment = dtf.format(now);

		ResultSet pay2;
		String vippayment2 = " SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`='" + sncard + "' ";
		PreparedStatement vpay2;

		try {
			vpay2 = MyConnection.getConnection().prepareStatement(vippayment2);
			pay2 = vpay2.executeQuery();

			if (pay2.next()) {

				remain = pay2.getDouble("VIPrfid_remaining_balance");
				double deposite2 = Double.parseDouble(deposite);
				ans = remain + deposite2;

			}

		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		String q = "update vip_rfid set VIPrfid_balance_used= ?, VIPrfid_lastPayment= ?, VIPrfid_remaining_balance=? where generateRFID_NO = ?";
		PreparedStatement s;
		try {
			s = MyConnection.getConnection().prepareStatement(q);
			s.setDouble(1, 0.0);
			s.setString(2, VIPrfid_lastPayment);
			s.setDouble(3, ans);
			s.setString(4, sncard);
			s.executeUpdate();
			res = VIPrfid_lastPayment + ',' + ans + ',' + "Payment Done";

		} catch (SQLException e) {

		}

		return res;
	}

}
