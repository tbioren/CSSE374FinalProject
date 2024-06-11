import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ViewFlightPage extends JPanel {

	private FlightService fService;

	private JTable flightsTable;
	private JLabel tokenLabel;
	private JButton getFlightsButton;
	private JButton resetButton;
	private JButton backButton;
	private JTextField flightNumberBox;
	private JComboBox<String> airlineBox;

	DbConnectionService db;
	ScreenController sc;

	ViewFlightPage(DbConnectionService dbService, ScreenController sController) {
		this.db = dbService;
		this.sc = sController;
		this.fService = new FlightService(dbService);

		JPanel topPanel = generateSubmitPanel();
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);

		JScrollPane tablePane = generateFlightsTable();
		this.add(tablePane, BorderLayout.CENTER);

		startListening();
	}

	/**
	 * Generates the top bar of the page
	 */
	private JPanel generateSubmitPanel() {
		JPanel submitPanel = new JPanel();
		FlowLayout fLayout = new FlowLayout();
		fLayout.setHgap(10);
		submitPanel.setLayout(fLayout);

		Tokens tokenGen = new Tokens(this.db, this.sc);
		tokenLabel = tokenGen.makeLabel();
		submitPanel.add(this.tokenLabel);

		submitPanel.add(new JLabel("Flight number: "));
		flightNumberBox = new JTextField(10);
		submitPanel.add(flightNumberBox);

		submitPanel.add(new JLabel("Airline: "));
		airlineBox = populateAirlines();
		submitPanel.add(airlineBox);

		getFlightsButton = new JButton("Get Flights");
		submitPanel.add(getFlightsButton);

		resetButton = new JButton("Reset");
		submitPanel.add(resetButton);

		backButton = new JButton("Back");
		submitPanel.add(backButton);

		return submitPanel;
	}

	/**
	 * Creates the visible version of the flight table
	 */
	private JScrollPane generateFlightsTable() {
		this.flightsTable = new JTable(this.searchAllFlights());

		JScrollPane scrollPane = new JScrollPane(this.flightsTable);
		this.flightsTable.setFillsViewportHeight(true);
		this.flightsTable.getTableHeader().setReorderingAllowed(false);

		return scrollPane;
	}

	/**
	 * @return the dropdown menus for airlines
	 */
	private JComboBox<String> populateAirlines() {
		FlightService f = new FlightService(db);
		List<String> airlines = f.listAirlines();
		String[] airlineIDs = new String[airlines.size() + 1];
		airlineIDs[0] = null;
		for (int i = 0; i < airlines.size(); i++) {
			airlineIDs[i + 1] = airlines.get(i);
		}
		return new JComboBox<String>(airlineIDs);
	}

	private WholeFlightTableModel searchAllFlights() {
		List<WholeFlight> data = fService.listWholeFlight(null, null);
		return new WholeFlightTableModel(data);
	}

	/**
	 * Returns a new table based on the user's input
	 */
	private WholeFlightTableModel search() {
		String fNumber;
		if (this.flightNumberBox.getText().isBlank()) {
			fNumber = null;
		} else {
			try {
				Integer.parseInt(this.flightNumberBox.getText());
				fNumber = this.flightNumberBox.getText();
			} catch (NumberFormatException n) {
				n.printStackTrace();
				fNumber = null;
			}
		}

		String airlineID = null;
		if (airlineBox.getSelectedIndex() != 0) {
			airlineID = (String) airlineBox.getSelectedItem();
		}

		List<WholeFlight> data = fService.listWholeFlight(fNumber, airlineID);
		return new WholeFlightTableModel(data);
	}

	/**
	 * Resets any input fields the user may have filled
	 */
	private void clearFields() {
		flightNumberBox.setText("");
		airlineBox.setSelectedIndex(0);
	}

	/**
	 * Adds action listeners to all buttons
	 */
	void startListening() {

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Welcome Screen");
			}
		});

		getFlightsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flightsTable.setModel(search());
			}
		});

		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				flightsTable.setModel(searchAllFlights());
				clearFields();
			}
		});

	}
}
