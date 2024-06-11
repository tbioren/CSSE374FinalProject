import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UpdateFlightInput extends JPanel {
	DbConnectionService db;
	ScreenController sc;

	JLabel tokenTracker;
	JLabel title;
	JButton backButton;
	JButton submitButton;
	JButton addDestinationButton;

	JPanel newPanel;

	JTextField flightNumberField;
	JTextField dateField;
	JComboBox<String> toBox;
	JComboBox<String> fromBox;
	JComboBox<String> airlineBox;
	HashMap<String, String> flightMap;

	String flightID;

	UpdateFlightInput(DbConnectionService db, ScreenController sc) {
		this.sc = sc;
		this.db = db;

		this.title = new JLabel("Update a flight");
		this.submitButton = new JButton("Submit");
		this.backButton = new JButton("Go Back");
		this.addDestinationButton = new JButton("Add or Update a Destination");
		this.flightNumberField = new JTextField(3);
		this.dateField = new JTextField();
		flightMap = new HashMap<String, String>();

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
		List<Destination> destinationList = serv.listDestinations();
		String[] destinationNames = new String[destinationList.size()];
		for (int i = 0; i < destinationList.size(); i++) {
			Destination d = destinationList.get(i);
			destinationNames[i] = d.iataCode;
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
		newPanel.add(new JLabel("Departure date (Don't mess up the formatting)"));
		newPanel.add(dateField);
	}

	void startListening() {
		addDestinationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Create Destination Screen");
				sc.createDestinationPage.setOrigin("Update Flight Input");
			}
		});
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Update Flight Select");
			}
		});
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FlightService f = new FlightService(db);
				String flightNumber = flightNumberField.getText();
				String fromCode = (String) fromBox.getSelectedItem();
				String toCode = (String) toBox.getSelectedItem();
				String airlineID = (String) airlineBox.getSelectedItem();
				if (f.updateFlight(flightNumber, dateField.getText(), fromCode, toCode, airlineID,
						flightID)) {
					JOptionPane.showMessageDialog(null, "Flight update successfully");
					sc.switchScreen("Welcome Screen");
				} else {
					JOptionPane.showMessageDialog(null, "Failed to update flight");
				}
			}
		});
	}

	/**
	 * Sets the flightID. Also puts the proper values in all the text boxes
	 */
	public void setFlightID(String id) {
		this.flightID = id;
		FlightService serv = new FlightService(db);
		Flight f = serv.getFlight(Integer.parseInt(flightID)).get();
		flightNumberField.setText(f.getNumber());
		dateField.setText(f.getDepartureDate());
		airlineBox.setSelectedItem(f.getAirlineID());
		toBox.setSelectedItem(f.getToCode());
		fromBox.setSelectedItem(f.getFromCode());
	}
}
