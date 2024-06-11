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

public class DeleteFlight extends JPanel {
	private DbConnectionService db;
	private JLabel tokenTracker;
	private ScreenController sc;
	private JComboBox<String> flightBox;
	private JButton selectButton;
	private JButton backButton;
	private JLabel title;
	private JPanel newPanel;
	private HashMap<String, String> flightMap;

	DeleteFlight(DbConnectionService db, ScreenController sc) {
		this.db = db;
		this.sc = sc;

		title = new JLabel("Choose a flight to delete");
		flightMap = new HashMap<String, String>();
		selectButton = new JButton("Delete");
		backButton = new JButton("Go Back");

		newPanel = new JPanel(new GridLayout(5, 2));
		this.add(newPanel, BorderLayout.CENTER);
		newPanel.add(title);
		makeTokenTracker();
		populate();
		newPanel.add(selectButton);
		newPanel.add(backButton);

		startListening();
	}

	/**
	 * Displays how many tokens the user has
	 */
	private void makeTokenTracker() {
		Tokens tokenGen = new Tokens(this.db, sc);
		this.tokenTracker = tokenGen.makeLabel();
		newPanel.add(this.tokenTracker);
	}

	private void populate() {
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

	private void startListening() {
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = flightMap.get(flightBox.getSelectedItem());
				FlightService srv = new FlightService(db);
				if (srv.dropFlight(id)) {
					JOptionPane.showMessageDialog(null, "Flight deleted successfully");
					sc.switchScreen("Welcome Screen");
				} else {
					JOptionPane.showMessageDialog(null, "Failed to delete flight");
				}
			}
		});
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Welcome Screen");
			}
		});
	}
}
