import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * To set the time how much time user can stay in the parking once payment is
 * done
 */

public class ServerValidity {

	String res = null;
	String[] data;
	String queryBike, queryCar, queryMini, queryUpdate;
	int bikeFare, carFare, miniFare;
	String admin;

	ResultSet rsCarFare, rsMiniFare, rsBikeFare, rsUpdate;
	PreparedStatement stmtBike, stmtCar, stmtMini, stmtUpdate;

	public String getInfo(String rfid) {
		// TODO Auto-generated method stub
		data = rfid.split(",");
		bikeFare = Integer.parseInt(data[1]);
		carFare = Integer.parseInt(data[2]);
		miniFare = Integer.parseInt(data[3]);
		admin = data[4];

		queryBike = "Select * from `settime` where `Type`=? ";
		try {
			stmtBike = MyConnection.getConnection().prepareStatement(queryBike);
			stmtBike.setString(1, "Bike");
			rsBikeFare = stmtBike.executeQuery();

			if (rsBikeFare.next()) {
				queryUpdate = "UPDATE `settime` SET `set_time`=?,`admin_id`=? WHERE `Type` = ?";
				stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
				stmtUpdate.setInt(1, bikeFare);
				stmtUpdate.setString(2, admin);
				stmtUpdate.setString(3, "Bike");
				stmtUpdate.executeUpdate();

			} else {
				queryUpdate = "INSERT INTO `settime`(`id`, `admin_id`, `set_time`, `Type`) VALUES (?,?,?,?)";
				stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
				stmtUpdate.setInt(1, 1);
				stmtUpdate.setString(2, admin);
				stmtUpdate.setInt(3, bikeFare);
				stmtUpdate.setString(4, "Bike");
				if (stmtUpdate.executeUpdate() > 0) {

				}
			}
			res = "Updated";
		} catch (Exception e) {
			res = null;
		}

		queryCar = "Select * from `settime` where `Type`=? ";
		try {
			stmtCar = MyConnection.getConnection().prepareStatement(queryCar);
			stmtCar.setString(1, "Car");
			rsCarFare = stmtCar.executeQuery();

			if (rsCarFare.next()) {
				queryUpdate = "UPDATE `settime` SET `set_time`=?,`admin_id`=? WHERE `Type` = ?";
				stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
				stmtUpdate.setInt(1, carFare);
				stmtUpdate.setString(2, admin);
				stmtUpdate.setString(3, "Car");
				stmtUpdate.executeUpdate();

			} else {
				queryUpdate = "INSERT INTO `settime`(`id`, `admin_id`, `set_time`, `Type`) VALUES (?,?,?,?)";
				stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
				stmtUpdate.setInt(1, 2);
				stmtUpdate.setString(2, admin);
				stmtUpdate.setInt(3, carFare);
				stmtUpdate.setString(4, "Car");
				if (stmtUpdate.executeUpdate() > 0) {

				}
			}
			res = "Updated";
		} catch (Exception e) {
			res = null;
		}

		queryMini = "Select * from `settime` where `Type`=? ";
		try {
			stmtMini = MyConnection.getConnection().prepareStatement(queryMini);
			stmtMini.setString(1, "Mini");
			rsMiniFare = stmtMini.executeQuery();

			if (rsMiniFare.next()) {
				queryUpdate = "UPDATE `settime` SET `set_time`=?,`admin_id`=? WHERE `Type` = ?";
				stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
				stmtUpdate.setInt(1, miniFare);
				stmtUpdate.setString(2, admin);
				stmtUpdate.setString(3, "Mini");
				stmtUpdate.executeUpdate();

			} else {

				queryUpdate = "INSERT INTO `settime`(`id`, `admin_id`, `set_time`, `Type`) VALUES (?,?,?,?)";
				stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
				stmtUpdate.setInt(1, 3);
				stmtUpdate.setString(2, admin);
				stmtUpdate.setInt(3, miniFare);
				stmtUpdate.setString(4, "Mini");
				if (stmtUpdate.executeUpdate() > 0) {

				}
			}
			res = "Updated";
		} catch (Exception e) {
			res = null;
		}

		return res;

	}

}
