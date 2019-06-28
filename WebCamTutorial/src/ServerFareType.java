import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Connection with database to set the Fare for each type of vehicles per hour
 */

public class ServerFareType {

	String res = null;
	String[] data;
	String queryBike, queryCar, queryMini, queryUpdate;
	double bikeFare, carFare, miniFare;
	String admin;

	ResultSet rsCarFare, rsMiniFare, rsBikeFare, rsUpdate;
	PreparedStatement stmtBike, stmtCar, stmtMini, stmtUpdate;

	public String getInfo(String rfid) {
		// TODO Auto-generated method stub
		data = rfid.split(",");
		bikeFare = Double.parseDouble(data[1]);
		carFare = Double.parseDouble(data[2]);
		miniFare = Double.parseDouble(data[3]);
		admin = data[4];

		queryBike = "Select * from `vehicle` where `vehicle_type`=? ";
		try {
			stmtBike = MyConnection.getConnection().prepareStatement(queryBike);
			stmtBike.setString(1, "Bike");
			rsBikeFare = stmtBike.executeQuery();

			if (rsBikeFare.next()) {
				queryUpdate = "UPDATE `vehicle` SET `vehicle_farePerHr`=?,`admin_id`=? WHERE `vehicle_type` = ?";
				stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
				stmtUpdate.setDouble(1, bikeFare);
				stmtUpdate.setString(2, admin);
				stmtUpdate.setString(3, "Bike");
				stmtUpdate.executeUpdate();

			} else {
				queryUpdate = "INSERT INTO `vehicle`(`vehicle_id`, `vehicle_type`, `vehicle_farePerHr`, `admin_id`, `open_barrier`) VALUES (?,?,?,?,?)";
				stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
				stmtUpdate.setInt(1, 1);
				stmtUpdate.setString(2, "Bike");
				stmtUpdate.setDouble(3, bikeFare);
				stmtUpdate.setString(4, admin);
				stmtUpdate.setInt(5, 0);

				if (stmtUpdate.executeUpdate() > 0) {

				}
			}
			res = "Updated";
		} catch (Exception e) {
			res = null;
		}

		queryCar = "Select * from `vehicle` where `vehicle_type`=? ";
		try {
			stmtCar = MyConnection.getConnection().prepareStatement(queryCar);
			stmtCar.setString(1, "Car");
			rsCarFare = stmtCar.executeQuery();

			if (rsCarFare.next()) {
				queryUpdate = "UPDATE `vehicle` SET `vehicle_farePerHr`=?,`admin_id`=? WHERE `vehicle_type` = ?";
				stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
				stmtUpdate.setDouble(1, carFare);
				stmtUpdate.setString(2, admin);
				stmtUpdate.setString(3, "Car");
				stmtUpdate.executeUpdate();

			} else {
				queryUpdate = "INSERT INTO `vehicle`(`vehicle_id`, `vehicle_type`, `vehicle_farePerHr`, `admin_id`, `open_barrier`) VALUES (?,?,?,?,?)";
				stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
				stmtUpdate.setInt(1, 2);
				stmtUpdate.setString(2, "Car");
				stmtUpdate.setDouble(3, carFare);
				stmtUpdate.setString(4, admin);
				stmtUpdate.setInt(5, 0);
				if (stmtUpdate.executeUpdate() > 0) {

				}
			}
			res = "Updated";
		} catch (Exception e) {
			res = null;
		}

		queryMini = "Select * from `vehicle` where `vehicle_type`=? ";
		try {
			stmtMini = MyConnection.getConnection().prepareStatement(queryMini);
			stmtMini.setString(1, "Mini");
			rsMiniFare = stmtMini.executeQuery();

			if (rsMiniFare.next()) {
				queryUpdate = "UPDATE `vehicle` SET `vehicle_farePerHr`=?,`admin_id`=? WHERE `vehicle_type` = ?";
				stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
				stmtUpdate.setDouble(1, miniFare);
				stmtUpdate.setString(2, admin);
				stmtUpdate.setString(3, "Mini");
				stmtUpdate.executeUpdate();

			} else {

				queryUpdate = "INSERT INTO `vehicle`(`vehicle_id`, `vehicle_type`, `vehicle_farePerHr`, `admin_id`, `open_barrier`) VALUES (?,?,?,?,?)";
				stmtUpdate = MyConnection.getConnection().prepareStatement(queryUpdate);
				stmtUpdate.setInt(1, 3);
				stmtUpdate.setString(2, "Mini");
				stmtUpdate.setDouble(3, miniFare);
				stmtUpdate.setString(4, admin);
				stmtUpdate.setInt(5, 0);
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
