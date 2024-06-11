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
import javax.swing.JTextField;

class AddFlightToPlanPage extends JPanel {
	private DbConnectionService db;
	private ScreenController sc;

	private HashMap<String, Integer> flightMap;
	private JLabel tokenTracker;
	private JPanel newPanel;
	private JButton backButton, submitButton;
	private JComboBox<String> flightBox;
	private JTextField orderField;

	private String planName;

	AddFlightToPlanPage(DbConnectionService db, ScreenController sc) {
		this.db = db;
		this.sc = sc;
		submitButton = new JButton("Add");
		backButton = new JButton("Go Back");
		flightMap = new HashMap<String, Integer>();
		orderField = new JTextField();

		newPanel = new JPanel(new GridLayout(5, 2));
		this.add(newPanel, BorderLayout.CENTER);
		newPanel.add(new JLabel("Create a plan"));
		makeTokenTracker();
		newPanel.add(new JLabel("Add flight to plan"));
		populate();
		newPanel.add(new JLabel("Order of Flight in plan"));
		newPanel.add(orderField);
		newPanel.add(submitButton);
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
	 * Fills the flight box with data
	 */
	private void populate() {
		FlightService serv = new FlightService(db);
		List<WholeFlight> flights = serv.listWholeFlight(null, null);
		String[] flightArr = new String[flights.size()];
		for (int i = 0; i < flights.size(); i++) {
			Flight f = flights.get(i).getFlight();
			flightArr[i] = f.toString();
			flightMap.put(f.toString(), Integer.parseInt(f.getID()));
		}
		flightBox = new JComboBox<String>(flightArr);
		newPanel.add(flightBox);
	}

	/**
	 * Adds action listeners to all buttons
	 */
	private void startListening() {
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Plan Screen");
			}
		});
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlanService serv = new PlanService(db);
				serv.createPlan(planName,
						flightMap.get((String) flightBox.getSelectedItem()), sc.getUser().id,
						Integer.parseInt(orderField.getText()));
				sc.switchScreen("Plan Screen");
			}
		});
	}

	/**
	 * Sets the plan name
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}
}
