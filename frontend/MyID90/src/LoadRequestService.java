import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LoadRequestService {

	private DbConnectionService dbService;

	public LoadRequestService(DbConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean CreateLoadRequest(int EID, int FID, int tokenCost) {
		if (EID < 0 || FID < 0 || tokenCost <= 0) {
			return false;
		}
		final String sproc = "{ call CreateLoadRequest(?, ?, ?) }";
		try {
			CallableStatement cs = dbService.getConnection().prepareCall(sproc);
			cs.setInt(1, EID);
			cs.setInt(2, FID);
			cs.setInt(3, tokenCost);
			cs.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean UpdateLoadRequestCost(int EID, int FID, int tokenCost) {
		if (EID < 0 || FID < 0) {
			return false;
		}
		final String sproc = "{ call UpdateLoadRequestCost(?, ?, ?) }";
		try {
			CallableStatement cs = dbService.getConnection().prepareCall(sproc);
			cs.setInt(1, EID);
			cs.setInt(2, FID);
			cs.setInt(3, tokenCost);
			cs.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean DeleteLoadRequest(int EID, int FID) {
		if (EID < 0 || FID < 0) {
			return false;
		}
		final String sproc = "{ call DeleteLoadRequest(?, ?) }";
		try {
			CallableStatement cs = dbService.getConnection().prepareCall(sproc);
			cs.setInt(1, EID);
			cs.setInt(2, FID);
			ResultSet rs = cs.executeQuery();
			rs.next();
			return rs.getInt(1) == 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateLoadRequest(int flightID, String username, int newLoad) {
		if (flightID < 0 || newLoad < 0) {
			return false;
		}
		final String sproc = "EXEC UpdateLoadRequest @FlightID = ?, @Username = ?, @NewLoad = ?";
		try {
			CallableStatement cs = dbService.getConnection().prepareCall(sproc);
			cs.setInt(1, flightID);
			cs.setString(2, username);
			cs.setInt(3, newLoad);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * @returns a list of all load requests the user with the given username can
	 *          fulfill
	 *          If the system encounters an error, an empty list will be returned
	 */
	public ArrayList<LoadRequest> listFulfillableRequests(String username) {
		Connection conn = dbService.getConnection();
		ArrayList<LoadRequest> arrayList = new ArrayList<LoadRequest>();
		try {
			CallableStatement cs = conn.prepareCall("{ call ListLoadRequests (?) }");
			cs.setString(1, username);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				String flightID = rs.getString("ID");
				String airlineID = rs.getString("AirlineID");
				String flightNumber = rs.getString("Number");
				String fromCode = rs.getString("FromCode");
				String toCode = rs.getString("ToCode");
				String departureDate = rs.getString("DepartureDateTime");
				String load = rs.getString("Load");
				int tokenCost = rs.getInt("Token");
				String submitter = rs.getString("Username");
				LoadRequest l = new LoadRequest(flightID, airlineID, flightNumber, fromCode, toCode, departureDate, load, tokenCost, submitter);
				arrayList.add(l);
			}
			return arrayList;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<LoadRequest>();
		}
	}


	public ArrayList<LoadRequest> listYourLoadRequests(int eID, String eUsername) {
		Connection conn = dbService.getConnection();
		ArrayList<LoadRequest> yourLoadRequests = new ArrayList<LoadRequest>();
		try {
			CallableStatement cs = conn.prepareCall( "{ call listYourLoadRequests(?) }");
			cs.setInt(1, eID);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				String fID = rs.getString("ID");
				String aID = rs.getString("AirlineID");
				String fNum = rs.getString("Number");
				String fCode = rs.getString("FromCode");
				String tCode = rs.getString("ToCode");
				String departureDate = rs.getString("DepartureDateTime");
				String load = rs.getString("Load");
				int tokenCost = rs.getInt("Token");
				LoadRequest l = new LoadRequest(fID, aID, fNum, fCode, tCode, departureDate, load, tokenCost, eUsername);
				yourLoadRequests.add(l);
			}
			return yourLoadRequests;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<LoadRequest>();
		}
	}
}
