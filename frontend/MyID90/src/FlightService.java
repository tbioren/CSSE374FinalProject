import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightService {
	private DbConnectionService db;

	FlightService(DbConnectionService db) {
		this.db = db;
	}

	/**
	 * @returns a list of all airline ids in the database, or an empty list if an
	 *          error occurs
	 */
	public List<String> listAirlines() {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("EXEC ListAirlines");
			ResultSet rs = cs.executeQuery();
			ArrayList<String> airlines = new ArrayList<String>();
			while (rs.next()) {
				String id = rs.getString("ID");
				airlines.add(id);
			}
			return airlines;

		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}

	/**
	 * Adds a new airline to the database
	 */
	public boolean CreateAirline(String airlineID, String alliance) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("EXEC CreateAirline @AirlineID = ?, @Alliance = ?");
			cs.setString(1, airlineID);
			cs.setString(2, alliance);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Adds a new flight to the database
	 */
	public boolean createFlight(String flightNumber, String date, String fromCode, String toCode,
			String airlineID) {
		// This may very well be the worst way of inputting a date
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall(
					"DECLARE @Identity as int EXEC CreateFlight @FlightNumber = ?, @DepartureDateTime = ?, @FromCode = ?, @ToCode = ?, @AirlineID = ?, @ID = @IDENTITY");
			cs.setString(1, flightNumber);
			cs.setString(2, date);
			cs.setString(3, fromCode);
			cs.setString(4, toCode);
			cs.setString(5, airlineID);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Flight> listFlights(int uID) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("EXEC ListFlights ?");
			cs.setInt(1, uID);
			ResultSet rs = cs.executeQuery();
			List<Flight> l = new ArrayList<Flight>();
			while (rs.next()) {
				Flight f = new Flight(rs.getString("ID"),
						rs.getString("AirlineID"),
						rs.getString("Number"),
						rs.getString("FromCode"),
						rs.getString("ToCode"),
						rs.getString("DepartureDateTime"),
						rs.getString("Load"));
				l.add(f);
			}
			return l;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Flight>();
		}
	}

	public List<WholeFlight> listWholeFlight(String number, String airlineID) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("EXEC ListWholeFlight @Number = ?, @AirlineID = ?");
			cs.setString(1, number);
			cs.setString(2, airlineID);
			List<WholeFlight> l = new ArrayList<WholeFlight>();
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				WholeFlight f = new WholeFlight(rs.getString("ID"), rs.getString("LastLoadUpdate"),
						rs.getString("Number"), rs.getString("Load"),
						rs.getString("DepartureDateTime"), rs.getString("FromCode"),
						rs.getString("ToCode"), rs.getString("AirlineID"));
				l.add(f);
			}
			return l;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<WholeFlight>();
		}
	}

	public ArrayList<Flight> listFlightsWithConditions(int eID, String airlineID, String fNumber, String fromCode, String toCode,
		Integer dYear, Integer dMonth, Integer dDay) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("EXEC ListFlightsConditions " +
					"@eID = ?, " +
					"@aID = ?, " +
					"@fNumber = ?, " +
					"@fromCode = ?, " +
					"@toCode = ?, " +
					"@departureYear = ?, " +
					"@departureMonth = ?, " +
					"@departureDay = ? ");
			cs.setInt(1, eID);
			if (airlineID != null) {
				cs.setString(2, airlineID);
			} else {
				cs.setNull(2, Types.VARCHAR);
			}
			if (fNumber != null) {
				cs.setString(3, fNumber);
			} else {
				cs.setNull(3, Types.VARCHAR);
			}
			if (fromCode != null) {
				cs.setString(4, fromCode);
			} else {
				cs.setNull(4, Types.VARCHAR);
			}
			if (toCode != null) {
				cs.setString(5, toCode);
			} else {
				cs.setNull(5, Types.VARCHAR);
			}
			if (dYear != null) {
				cs.setInt(6, dYear);
			} else {
				cs.setNull(6, Types.INTEGER);
			}
			if (dMonth != null) {
				cs.setInt(7, dMonth);
			} else {
				cs.setNull(7, Types.INTEGER);
			}
			if (dDay != null) {
				cs.setInt(8, dDay);
			} else {
				cs.setNull(8, Types.INTEGER);
			}
			ResultSet rs = cs.executeQuery();
			ArrayList<Flight> flightList = new ArrayList<Flight>();
			while (rs.next()) {
				Flight f = new Flight(rs.getString("ID"),
						rs.getString("AirlineID"),
						rs.getString("Number"),
						rs.getString("FromCode"),
						rs.getString("ToCode"),
						rs.getString("DepartureDateTime"),
						rs.getString("Load"));
				flightList.add(f);
			}
			return flightList;
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Flight>();
		}
	}

	/**
	 * Sets a flight & any of its dependent objects to be invisible
	 */
	public boolean dropFlight(String flightID) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("EXEC DropFlight @FlightID = ?");
			cs.setString(1, flightID);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Updates a flight's information, if it exists in the database & is visible
	 */
	public boolean updateFlight(String flightNumber, String date, String fromCode, String toCode,
			String airlineID, String flightID) {
		// This may very well be the worst way of inputting a date
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall(
					"EXEC UpdateFlight @FlightNumber = ?, @DepartureDateTime = ?, @FromCode = ?, @ToCode = ?, @AirlineID = ?, @ID = ?");
			cs.setString(1, flightNumber);
			cs.setString(2, date);
			cs.setString(3, fromCode);
			cs.setString(4, toCode);
			cs.setString(5, airlineID);
			cs.setString(6, flightID);
			cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Optional<Flight> getFlight(int flightID) {
		Connection conn = db.getConnection();
		try {
			CallableStatement cs = conn.prepareCall("EXEC GetFlight @FlightID = ?");
			cs.setInt(1, flightID);
			ResultSet rs = cs.executeQuery();
			rs.next();
			return Optional.of(new Flight(rs.getString("ID"),
					rs.getString("AirlineID"), rs.getString("Number"),
					rs.getString("FromCode"),
					rs.getString("ToCode"), rs.getString("DepartureDateTime"),
					rs.getString("Load")));
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
}
