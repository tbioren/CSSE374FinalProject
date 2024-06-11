public class WholeFlight {
	private Flight f;
	private String lastLoadUpdate;
	private int load;

	WholeFlight(String id, String lastLoadUpdate, String number, String load, String departureDateTime,
			String fromCode, String toCode, String airlineID) {
		f = new Flight(id, airlineID, number, fromCode, toCode, departureDateTime, load);
		this.lastLoadUpdate = lastLoadUpdate;
	}

	/**
	 * @return the flight's id
	 */
	public String getID() {
		return f.getID();
	}

	/**
	 * @returns when the flight's load was last updated
	 */
	public String getLastLoadUpdate() {
		return lastLoadUpdate;
	}

	/**
	 * @returns the flight's number
	 */
	public String getNumber() {
		return f.getNumber();
	}

	/**
	 * @return the number of seats taken for the flight
	 */
	public String getLoad() {
		return f.getLoad();
	}

	/**
	 * @return the date & time when the flight departs
	 */
	public String getDepartureDate() {
		return f.getDepartureDate();
	}

	/**
	 * @return the IATACode for the airport the flight is leaving from
	 */
	public String getFromCode() {
		return f.getFromCode();
	}

	/**
	 * @return the IATACode for the airport the flight is going to
	 */
	public String getToCode() {
		return f.getToCode();
	}

	/**
	 * @returns the airline's ID
	 */
	public String getAirlineID() {
		return f.getAirlineID();
	}

	/**
	 * Returns one value from this object, based on column names for
	 * WholeFlightTableModel
	 */
	public Object getValue(String name) {
		switch (name) {
			case "Flight Number":
				return getAirlineID() + " " + getNumber();
			case "From":
				return getFromCode();
			case "To":
				return getToCode();
			case "Departure Date Time":
				return getDepartureDate();
			case "Load":
				return getLoad();
			case "Last Updated":
				return getLastLoadUpdate();
		}
		throw new IllegalArgumentException("Name of " + name + " is not a valid argument.");
	}

	public Flight getFlight() {
		return f;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(f.toString());
		sb.append(", Last Updated: ");
		sb.append(lastLoadUpdate);
		return sb.toString();
	}
}
