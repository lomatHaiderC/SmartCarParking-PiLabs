import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database insertion to insert new Cards
 * VIP card is inserted inside 'insert()' method
 * Normal card is inserted inside 'insertNormal()' method
 * @author Restart
 *
 */
public class ServerCard {
	String returnResult = "0",queryPrimary;
	PreparedStatement stmtIndex, stmtInsert, stmtPrimary;
	ResultSet rsIndex, rsPrimary;
	int possible=1;
	String[] splitdata;
	

	public String insert(String rfid) {
		// TODO Auto-generated method stub
		splitdata=rfid.split(",");
		queryPrimary = "SELECT * FROM `vip_card` where `card_no`=? ";
		try {
			stmtPrimary = MyConnection.getConnection().prepareStatement(queryPrimary);
			stmtPrimary.setString(1, splitdata[1]);
			rsPrimary = stmtPrimary.executeQuery();
			if (rsPrimary.next()) {
				possible = 0;
				returnResult="Already Exists";
			} 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(possible==1) {
			String queryInsert=" INSERT INTO `vip_card`(`card_no`, `isUsed`) VALUES (?,?) ";
			try {
				stmtInsert = MyConnection.getConnection().prepareStatement(queryInsert);
				stmtInsert.setString(1, splitdata[1]);
				stmtInsert.setInt(2, 0);
				if (stmtInsert.executeUpdate() > 0) {
					returnResult = "Insert Done";
				} else {
					returnResult = "Insert Error";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return returnResult;
	}


	public String insertNormal(String rfid) {
		splitdata=rfid.split(",");
		queryPrimary = "SELECT * FROM `rfid` where `generateRFID_NO`=? ";
		try {
			stmtPrimary = MyConnection.getConnection().prepareStatement(queryPrimary);
			stmtPrimary.setString(1, splitdata[1]);
			rsPrimary = stmtPrimary.executeQuery();
			if (rsPrimary.next()) {
				possible = 0;
				returnResult="Already Exists";
			} 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(possible==1) {
			String queryInsert=" INSERT INTO `rfid`(`generateRFID_NO`, `isParked`) VALUES (?,?) ";
			try {
				stmtInsert = MyConnection.getConnection().prepareStatement(queryInsert);
				stmtInsert.setString(1, splitdata[1]);
				stmtInsert.setInt(2, 0);
				if (stmtInsert.executeUpdate() > 0) {
					returnResult = "Insert Done";
				} else {
					returnResult = "Insert Error";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return returnResult;
	}

}
