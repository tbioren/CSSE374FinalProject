/**
 * A data class for use by the flight service
 */

public class Flight {
	private String id;
	private String number;
	private String fromCode;
	private String toCode;
	private String airlineID;
	private String departureDate;
	private String load;

	Flight(String id, String airlineID, String number, String from, String to, String date, String load) {
		this.id = id;
		this.airlineID = airlineID;
		this.number = number;
		this.fromCode = from;
		this.toCode = to;
		this.departureDate = date;
		this.load = load;
	}

	public String getID() {
		return id;
	}

	public String getAirlineID() {
		return airlineID;
	}

	public String getNumber() {
		return number;
	}

	public String getFromCode() {
		return fromCode;
	}

	public String getToCode() {
		return toCode;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public String getLoad() {
		return load;
	}

	public Object getValue(String name) {
		switch (name)
		{
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
		}
		throw new IllegalArgumentException("Name of " + name + " is not a valid argument.");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Airline: ");
		sb.append(airlineID);
		sb.append(", Flight Number: ");
		sb.append(number);
		sb.append(", From: ");
		sb.append(fromCode);
		sb.append(", To: ");
		sb.append(toCode);
		sb.append(", On: ");
		sb.append(departureDate);
		sb.append(", Load: ");
		sb.append(load);
		return sb.toString();
	}
}
