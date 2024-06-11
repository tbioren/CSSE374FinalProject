import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

public class FulfillRequest extends JPanel {
	DbConnectionService db;
	JLabel tokenTracker;
	ScreenController sc;
	JLabel title;
	JPanel newPanel;
	JButton backButton, submitButton;
	HashMap<String, LoadRequest> requestMap;
	JComboBox<String> requestBox;

	FulfillRequest(DbConnectionService db, ScreenController sc) {
		this.sc = sc;
		this.db = db;

		this.title = new JLabel("Fulfill a Load Request");
		this.backButton = new JButton("Go back");
		this.submitButton = new JButton("Select");
		requestMap = new HashMap<String, LoadRequest>();

		newPanel = new JPanel(new GridLayout(5, 1));
		makeTokenTracker();
		newPanel.add(this.title);
		this.add(newPanel, BorderLayout.CENTER);
		populate();
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

	public void populate() {
		LoadRequestService l = new LoadRequestService(db);
		ArrayList<LoadRequest> requests = l.listFulfillableRequests(sc.getUser().username);

		String[] s = new String[requests.size()];

		for (int i = 0; i < requests.size(); i++) {
			LoadRequest r = requests.get(i);
			s[i] = r.toString();
			requestMap.put(r.toString(), r);
		}

		requestBox = new JComboBox<String>(s);
		newPanel.add(requestBox);

		newPanel.add(backButton);
		newPanel.add(submitButton);
	}

	private void startListening() {
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Welcome Screen");
			}
		});
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Fulfill Request Input");
				LoadRequest r = requestMap.get((String) requestBox.getSelectedItem());
				sc.fulfillInputScreen.setFlightID(r.getID());
				sc.fulfillInputScreen.populate(r.getFlightNumber());
				sc.fulfillInputScreen.startListening();
			}
		});

	}
}
