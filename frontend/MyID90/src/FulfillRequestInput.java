import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FulfillRequestInput extends JPanel {
	JPanel newPanel;

	ScreenController sc;
	DbConnectionService db;
	String flightID;

	JLabel title;
	JTextField loadField;
	JButton submitButton;
	JButton backButton;

	FulfillRequestInput(DbConnectionService dbService, ScreenController sc) {
		this.db = dbService;
		this.sc = sc;
		loadField = new JTextField(3);
		this.title = new JLabel();
		submitButton = new JButton("Submit");
		backButton = new JButton("Go back");

		this.newPanel = new JPanel(new GridLayout(7, 1));
		this.add(newPanel, BorderLayout.CENTER);
		newPanel.add(title);
	}

	/**
	 * Adds an appropriate title to the page with the given flight number.
	 * Also places the textbox & submit button on the screen
	 */
	public void populate(String flightNumber) {
		title.setText("Input load for flight " + flightNumber);
		newPanel.add(loadField);
		newPanel.add(submitButton);
		newPanel.add(backButton);
	}

	/**
	 * Adds action listeners to the screen's buttons
	 */
	public void startListening() {
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadRequestService lr = new LoadRequestService(db);
				System.out.println(flightID);
				if (lr.updateLoadRequest(Integer.parseInt(flightID), sc.getUser().username,
						Integer.parseInt(loadField.getText()))) {
					JOptionPane.showMessageDialog(null, "Update Successful");
					sc.switchScreen("Welcome Screen");
				} else {
					loadField.setText("");
					JOptionPane.showMessageDialog(null, "Update Unsuccessful");
				}
			}
		});

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Fulfill Request");
			}
		});
	}

	/**
	 * Sets the flightID field. Intended for use with the button that takes you to
	 * this screen
	 */
	void setFlightID(String flightID) {
		this.flightID = flightID;
	}
}
