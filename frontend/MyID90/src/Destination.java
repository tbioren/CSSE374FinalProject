public class Destination {
	String iataCode;
	String name;

	Destination(String iataCode, String name) {
		this.iataCode = iataCode;
		this.name = name;
	}

	@Override
	public String toString() {
		return iataCode + ": " + name;
	}
}
