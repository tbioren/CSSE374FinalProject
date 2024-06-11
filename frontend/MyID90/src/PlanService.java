import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanService {
	private DbConnectionService db;

	PlanService(DbConnectionService db) {
		this.db = db;
	}

	/**
	 * @return a list of all flights in a plan
	 */
	public List<WholeFlight> listPlanContents(String planName, int userID) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("EXEC ListPlan @PlanName = ?, @UserID = ?");
			cs.setString(1, planName);
			cs.setInt(2, userID);
			ResultSet rs = cs.executeQuery();
			List<WholeFlight> flights = new ArrayList<WholeFlight>();
			while (rs.next()) {
				String id = rs.getString("ID");
				String lastLoadUpdate = rs.getString("LastLoadUpdate");
				String flightNumber = rs.getString("Number");
				String load = rs.getString("Load");
				String departureDate = rs.getString("DepartureDateTime");
				String fromCode = rs.getString("FromCode");
				String toCode = rs.getString("ToCode");
				String airlineID = rs.getString("AirlineID");
				flights.add(new WholeFlight(id, lastLoadUpdate, flightNumber, load, departureDate,
						fromCode, toCode, airlineID));
			}
			return flights;

		} catch (SQLException e) {
			return new ArrayList<WholeFlight>();
		}

	}

	/**
	 * @return a list of all of a user's plans
	 */
	public List<String> listPlans(int userID) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("EXEC ListAllPlans @UserID = ?");
			cs.setInt(1, userID);
			ResultSet rs = cs.executeQuery();
			List<String> plans = new ArrayList<String>();
			while (rs.next()) {
				plans.add(rs.getString("PlanName"));
			}
			return plans;

		} catch (SQLException e) {
			return new ArrayList<String>();
		}

	}

	/**
	 * Add a flight to a user's plan
	 * 
	 * @return true, if the flight is added, false if the flight is not added
	 */
	public boolean createPlan(String planName, int flightID, int userID, int order) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall(
					"EXEC CreatePlan @PlanName = ?, @FlightID = ?, @UserID = ?, @Order = ?");
			cs.setString(1, planName);
			cs.setInt(2, flightID);
			cs.setInt(3, userID);
			cs.setInt(4, order);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * Remove a flight from a user's plan
	 * 
	 * @return true if the flight is removed, false if it is not
	 */
	public boolean DropPlanItem(String planName, int flightID, int userID) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn
					.prepareCall("EXEC DropPlanItem @PlanName = ?, @FlightID = ?, @UserID = ?");
			cs.setString(1, planName);
			cs.setInt(2, flightID);
			cs.setInt(3, userID);
			cs.execute();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	/**
	 * Remove a whole plan from the database
	 * 
	 * @return true if the plan is removed, false if it is not
	 */
	public boolean DropPlan(String planName, int userID) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn
					.prepareCall("EXEC DropPlanItem @PlanName = ?, @UserID = ?");
			cs.setString(1, planName);
			cs.setInt(2, userID);
			cs.execute();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}
