import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UpdateFlightSelect extends JPanel {
	DbConnectionService db;
	ScreenController sc;

	JLabel tokenTracker;
	JLabel title;
	JButton backButton;
	JButton submitButton;

	JPanel newPanel;

	JComboBox<String> flightBox;
	HashMap<String, String> flightMap;

	UpdateFlightSelect(DbConnectionService db, ScreenController sc) {
		this.sc = sc;
		this.db = db;

		this.title = new JLabel("Update a flight");
		this.submitButton = new JButton("Select");
		this.backButton = new JButton("Go Back");
		flightMap = new HashMap<String, String>();

		newPanel = new JPanel(new GridLayout(12, 2));
		newPanel.add(this.title);
		makeTokenTracker();
		populateFlight();
		this.add(newPanel, BorderLayout.CENTER);
		newPanel.add(backButton);
		newPanel.add(submitButton);
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
	 * Creates the inputs for flight
	 */
	public void populateFlight() {
		newPanel.add(new JLabel("Choose a flight to update"));
		FlightService serv = new FlightService(db);
		List<WholeFlight> flights = serv.listWholeFlight(null, null);
		String[] flightArr = new String[flights.size()];
		for (int i = 0; i < flights.size(); i++) {
			Flight f = flights.get(i).getFlight();
			flightArr[i] = f.toString();
			flightMap.put(f.toString(), f.getID());
		}
		flightBox = new JComboBox<String>(flightArr);
		newPanel.add(flightBox);
	}

	void startListening() {
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Welcome Screen");
			}
		});
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Update Flight Input");
				sc.updateFlightInputPage
						.setFlightID(flightMap.get((String) flightBox.getSelectedItem()));
			}
		});
	}
}
