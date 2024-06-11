import java.awt.ActiveEvent;
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

public class DeleteDestinationPage extends JPanel {
	private DbConnectionService db;
	private ScreenController sc;

	private JPanel newPanel;
	private JLabel tokenTracker;
	private HashMap<String, String> destinationMap;
	private JComboBox<String> destinationBox;
	private JButton deleteButton;
	private JButton backButton;

	DeleteDestinationPage(DbConnectionService db, ScreenController sc) {
		this.db = db;
		this.sc = sc;
		this.destinationMap = new HashMap<String, String>();
		deleteButton = new JButton("Delete");
		backButton = new JButton("Go Back");

		newPanel = new JPanel(new GridLayout(5, 2));
		this.add(newPanel, BorderLayout.CENTER);
		newPanel.add(new JLabel("Delete a destination"));
		makeTokenTracker();
		populate();
		newPanel.add(deleteButton);
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

	/**
	 * Creates the dropdown menu for the destinations
	 */
	private void populate() {
		DestinationService serv = new DestinationService(db);
		List<Destination> destinationList = serv.listDestinations();
		String[] destinationNames = new String[destinationList.size()];
		for (int i = 0; i < destinationList.size(); i++) {
			Destination d = destinationList.get(i);
			destinationMap.put(d.toString(), d.iataCode);
			destinationNames[i] = d.toString();
		}
		destinationBox = new JComboBox<String>(destinationNames);
		newPanel.add(destinationBox);
	}

	private void startListening() {
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Welcome Screen");
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DestinationService serv = new DestinationService(db);
				if (serv.deleteDestination(
						destinationMap.get((String) destinationBox.getSelectedItem()))) {
					JOptionPane.showMessageDialog(null, "Destination deleted successfully");
					sc.switchScreen("Welcome Screen");
				} else {
					JOptionPane.showMessageDialog(null, "Failed to delete destination");
				}
			}
		});
	}
}
