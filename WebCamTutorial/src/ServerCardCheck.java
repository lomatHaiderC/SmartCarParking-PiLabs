
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * To Check the validity of the card for Payment purpose
 */

public class ServerCardCheck {

	public String cardCheck(String rfid) {

		String[] card = rfid.split(",");
		String cardnbr = card[1];
		String user = card[2];

		String returnResult = "0";

		ResultSet normal;
		String querynormal = null;
		if (user.equalsIgnoreCase("normal")) {
			querynormal = " SELECT * FROM `rfid` WHERE `generateRFID_NO`='" + cardnbr + "' and `isParked`=' 1 ' ";
		} else if (user.equalsIgnoreCase("vip")) {
			querynormal = " SELECT * FROM `vip_rfid` WHERE `generateRFID_NO`='" + cardnbr + "' ";
		}
		PreparedStatement stmtEmployee;
		try {
			stmtEmployee = MyConnection.getConnection().prepareStatement(querynormal);
			normal = stmtEmployee.executeQuery();
			if (normal.next()) {

				returnResult = cardnbr;
				if (user.equalsIgnoreCase("vip")) {
					String VIPrfid_payment_type = normal.getString("VIPrfid_payment_type");
					returnResult = cardnbr + "," + VIPrfid_payment_type;
				}

			} else {

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return returnResult;
	}

}
