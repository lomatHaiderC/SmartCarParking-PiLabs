
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerVipInfo {

	public String vipinfo() {
		ResultSet vipcount1;
		String vippayment = "SELECT * FROM `vip_rfid` ORDER By VIPrfid_issue_date";
		PreparedStatement vcount1;
		String str = " ";
		try {
			vcount1 = MyConnection.getConnection().prepareStatement(vippayment);
			vipcount1 = vcount1.executeQuery();
			// String str="{";

			int flag = 0;
			while (vipcount1.next()) {

				if (flag == 0) {
					str += vipcount1.getString("VIPrfid_vehicle_type") + "," + vipcount1.getString("VIPrfid_mobile_no")
							+ "," + vipcount1.getString("generateRFID_NO") + ","
							+ vipcount1.getString("VIPrfid_payment_type") + ","
							+ vipcount1.getString("VIPrfid_remaining_balance") + ","
							+ vipcount1.getString("VIPrfid_balance_used");

				}

				else {
					str += ";" + vipcount1.getString("VIPrfid_vehicle_type") + ","
							+ vipcount1.getString("VIPrfid_mobile_no") + "," + vipcount1.getString("generateRFID_NO")
							+ "," + vipcount1.getString("VIPrfid_payment_type") + ","
							+ vipcount1.getString("VIPrfid_remaining_balance") + ","
							+ vipcount1.getString("VIPrfid_balance_used");

				}
				flag = flag + 1;

			}
			str += "&";
			str += flag;

		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		return str;
	}

	public String vipinfoPostpaid() {
		ResultSet vipcount1;
		String vippayment = "SELECT * FROM `vip_rfid`  where `VIPrfid_payment_type`=?  ORDER By VIPrfid_issue_date";
		PreparedStatement vcount1;
		String str = " ";

		try {
			vcount1 = MyConnection.getConnection().prepareStatement(vippayment);
			vcount1.setString(1, "Postpaid");
			vipcount1 = vcount1.executeQuery();
			int flag = 0;
			while (vipcount1.next()) {

				if (flag == 0) {

					str += vipcount1.getString("VIPrfid_vehicle_type") + "," + vipcount1.getString("VIPrfid_mobile_no")
							+ "," + vipcount1.getString("generateRFID_NO") + ","
							+ vipcount1.getString("VIPrfid_payment_type") + ","
							+ vipcount1.getString("VIPrfid_remaining_balance") + ","
							+ vipcount1.getString("VIPrfid_balance_used");

				}

				else {
					str += ";" + vipcount1.getString("VIPrfid_vehicle_type") + ","
							+ vipcount1.getString("VIPrfid_mobile_no") + "," + vipcount1.getString("generateRFID_NO")
							+ "," + vipcount1.getString("VIPrfid_payment_type") + ","
							+ vipcount1.getString("VIPrfid_remaining_balance") + ","
							+ vipcount1.getString("VIPrfid_balance_used");

				}

				flag = flag + 1;

			}
			str += "&";
			str += flag;

		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		return str;
	}

	public String vipinfoPrepaid() {
		ResultSet vipcount1;
		String vippayment = "SELECT * FROM `vip_rfid` where `VIPrfid_payment_type`=? ORDER By VIPrfid_issue_date  ";
		PreparedStatement vcount1;
		String str = " ";
		try {
			vcount1 = MyConnection.getConnection().prepareStatement(vippayment);
			vcount1.setString(1, "Prepaid");
			vipcount1 = vcount1.executeQuery();

			int flag = 0;
			while (vipcount1.next()) {

				if (flag == 0) {
					str += vipcount1.getString("VIPrfid_vehicle_type") + "," + vipcount1.getString("VIPrfid_mobile_no")
							+ "," + vipcount1.getString("generateRFID_NO") + ","
							+ vipcount1.getString("VIPrfid_payment_type") + ","
							+ vipcount1.getString("VIPrfid_remaining_balance") + ","
							+ vipcount1.getString("VIPrfid_balance_used");

				}

				else {
					str += ";" + vipcount1.getString("VIPrfid_vehicle_type") + ","
							+ vipcount1.getString("VIPrfid_mobile_no") + "," + vipcount1.getString("generateRFID_NO")
							+ "," + vipcount1.getString("VIPrfid_payment_type") + ","
							+ vipcount1.getString("VIPrfid_remaining_balance") + ","
							+ vipcount1.getString("VIPrfid_balance_used");

				}
				flag = flag + 1;

			}
			str += "&";
			str += flag;

		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		return str;
	}

}
