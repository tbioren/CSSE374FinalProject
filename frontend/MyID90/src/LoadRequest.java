/**
 * A data class for use by the load request service
 */

public class LoadRequest {
	private Flight f;
	private int tokenCost;
	private String submitter;

	LoadRequest(String flightID, String airlineID, String flightNumber, String from, String to, String date, String load, int tokens, String submitter) {
		this.f = new Flight(flightID, airlineID, flightNumber, from, to, date, load);
		this.tokenCost = tokens;
		this.submitter = submitter;
	}

	public Object getValue(String name) {
		switch (name)
		{
			case "Flight Number":
				return f.getAirlineID() + " " + f.getNumber();
			case "From":
				return f.getFromCode();
			case "To":
				return f.getToCode();
			case "Departure Date Time":
				return f.getDepartureDate();
			case "Load":
				return f.getLoad();
			case "Token":
				return tokenCost;
		}
		throw new IllegalArgumentException("Name of " + name + " is not a valid argument.");
	}

	public String getID() {
		return f.getID();
	}

	public String getFlightNumber() {
		return f.getNumber();
	}

	public String getSubmitter() {
		return submitter;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(f.toString());
		sb.append(", Token cost: ");
		sb.append(tokenCost);
		sb.append(", Submitter: ");
		sb.append(submitter);
		return sb.toString();
	}
}
