import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateFlightsPage extends JPanel {
	DbConnectionService db;
	ScreenController sc;

	JLabel tokenTracker;
	JLabel title;
	JButton backButton;
	JButton submitButton;
	JButton addDestinationButton;

	JPanel newPanel;

	JTextField flightNumberField;
	JTextField dayField;
	JTextField monthField;
	JTextField yearField;
	JTextField timeField;
	JComboBox<String> toBox;
	JComboBox<String> fromBox;
	JComboBox<String> airlineBox;
	HashMap<String, String> destinationMap;

	CreateFlightsPage(DbConnectionService db, ScreenController sc) {
		this.sc = sc;
		this.db = db;
		this.destinationMap = new HashMap<String, String>();

		this.title = new JLabel("Create a flight");
		this.submitButton = new JButton("Submit");
		this.backButton = new JButton("Go Back");
		this.addDestinationButton = new JButton("Add or Update a Destination");
		this.flightNumberField = new JTextField(3);
		this.dayField = new JTextField();
		this.monthField = new JTextField();
		this.yearField = new JTextField();
		this.timeField = new JTextField();

		newPanel = new JPanel(new GridLayout(12, 2));
		newPanel.add(this.title);
		makeTokenTracker();
		newPanel.add(new JLabel("Flight Number: "));
		newPanel.add(flightNumberField);
		this.add(newPanel, BorderLayout.CENTER);
		populateDestinations();
		populateAirlines();
		populateDate();
		newPanel.add(backButton);
		newPanel.add(submitButton);
		newPanel.add(addDestinationButton);
		startListening();
	}

	/**
	 * Displays how many tokens the user has
	 */
	public void makeTokenTracker() {
		Tokens tokenGen = new Tokens(this.db, sc);
		this.tokenTracker = tokenGen.makeLabel();
		newPanel.add(this.tokenTracker);
	}

	/**
	 * Creates the dropdown menus for destinations
	 */
	private void populateDestinations() {
		DestinationService serv = new DestinationService(db);

		// This is just a little ugly
		ArrayList<Destination> destinationList = serv.listDestinations();
		String[] destinationNames = new String[destinationList.size()];
		for (int i = 0; i < destinationList.size(); i++) {
			Destination d = destinationList.get(i);
			destinationMap.put(d.toString(), d.iataCode);
			destinationNames[i] = d.toString();
		}
		newPanel.add(new JLabel("To"));
		this.toBox = new JComboBox<String>(destinationNames);
		newPanel.add(toBox);
		newPanel.add(new JLabel("From"));
		this.fromBox = new JComboBox<String>(destinationNames);
		newPanel.add(fromBox);
	}

	/**
	 * Creates the dropdown menus for airlines
	 */
	private void populateAirlines() {
		FlightService f = new FlightService(db);
		List<String> airlines = f.listAirlines();
		newPanel.add(new JLabel("Airline"));
		String[] airlineIDs = new String[airlines.size()];
		for (int i = 0; i < airlines.size(); i++) {
			airlineIDs[i] = airlines.get(i);
		}
		this.airlineBox = new JComboBox<String>(airlineIDs);
		newPanel.add(airlineBox);
	}

	/**
	 * Creates the inputs for departure date & time
	 */
	private void populateDate() {
		newPanel.add(new JLabel("Departure day (dd)"));
		newPanel.add(dayField);
		newPanel.add(new JLabel("Departure month (mm)"));
		newPanel.add(monthField);
		newPanel.add(new JLabel("Departure year (yyyy)"));
		newPanel.add(yearField);
		newPanel.add(new JLabel("Departure time (hh:mm)"));
		newPanel.add(timeField);
	}

	private void startListening() {
		addDestinationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Create Destination Screen");
				sc.createDestinationPage.setOrigin("Create Flight Screen");
			}
		});
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Welcome Screen");
		}
		});
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FlightService f = new FlightService(db);
				String flightNumber = flightNumberField.getText();
				String fromCode = destinationMap.get((String) fromBox.getSelectedItem());
				String toCode = destinationMap.get((String) toBox.getSelectedItem());
				String airlineID = (String) airlineBox.getSelectedItem();
				if (f.createFlight(flightNumber, formatDate(), fromCode, toCode, airlineID)) {
					JOptionPane.showMessageDialog(null, "Flight added successfully");
					sc.switchScreen("Welcome Screen");
				} else {
					JOptionPane.showMessageDialog(null, "Failed to add flight");
				}
			}
		});
	}

	private String formatDate() {
		StringBuilder sb = new StringBuilder();
		sb.append(yearField.getText());
		sb.append("-");
		sb.append(monthField.getText());
		sb.append("-");
		sb.append(dayField.getText());
		sb.append(" ");
		sb.append(timeField.getText());
		sb.append(":00.000");
		return sb.toString();
	}

}
