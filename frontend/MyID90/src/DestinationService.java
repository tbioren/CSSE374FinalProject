import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DestinationService {
	private DbConnectionService db;

	DestinationService(DbConnectionService db) {
		this.db = db;
	}

	/**
	 * @returns a list of all destinations in the database, or an empty list if an
	 *          error occurs
	 */
	public ArrayList<Destination> listDestinations() {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("EXEC ListDestinations");
			ResultSet rs = cs.executeQuery();
			ArrayList<Destination> destinationList = new ArrayList<Destination>();
			while (rs.next()) {
				String iataCode = rs.getString("IATACode");
				String name = rs.getString("Name");
				Destination d = new Destination(iataCode, name);
				destinationList.add(d);
			}
			return destinationList;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Destination>();
		}
	}

	/**
	 * Adds a new destination to the database with the given iataCode & cityName, or
	 * if
	 * a destination with that iataCode already exists, updates it
	 * 
	 * @return true on a success, false on a failure
	 */
	public boolean createDestination(String iataCode, String cityName) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("EXEC CreateDestination @IATACode = ?, @Name = ?");
			cs.setString(1, iataCode);
			cs.setString(2, cityName);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Marks the destination with the given iataCode as hidden, effectively deleting
	 * it
	 * 
	 * @return true on a success, false on a failure
	 */
	public boolean deleteDestination(String iataCode) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("EXEC DropDestination @IATACode = ?");
			cs.setString(1, iataCode);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
}
