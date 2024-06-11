import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JPanel {
	// initialize button, panel, label, and text field
	JButton loginButton;
	JButton registerButton;
	JButton importButton;
	JPanel newPanel;
	JLabel userLabel, passLabel;
	final JTextField textField1, textField2;
	private UserService us;
	DbConnectionService db;
	ScreenController sc;

	// Initializes a bunch of instance variables
	LoginScreen(DbConnectionService db, ScreenController sc) {
		this.sc = sc;
		this.db = db;
		this.us = new UserService(db);

		// create text field to get username from the user
		textField1 = new JTextField(15); // set length of the text

		// create text field to get password from the user
		textField2 = new JPasswordField(15); // set length for the password
		// create label for username
		userLabel = new JLabel();
		userLabel.setText("Username"); // set label value for textField1

		// create label for password
		passLabel = new JLabel();
		passLabel.setText("Password"); // set label value for textField2

		// create submit button
		loginButton = new JButton("Log In"); // set label to button
		registerButton = new JButton("Register"); // register button

		importButton = new JButton("Import Data");

		// create panel to put form elements
		newPanel = new JPanel(new GridLayout(4, 1));
		newPanel.add(userLabel); // set username label to panel
		newPanel.add(textField1); // set text field to panel
		newPanel.add(passLabel); // set password label to panel
		newPanel.add(textField2); // set text field to panel
		newPanel.add(registerButton); // set button to panel
		newPanel.add(loginButton);
		newPanel.add(importButton);

		// set border to panel
		add(newPanel, BorderLayout.CENTER);
		startListening();
	}

	/**
	 * Starts the buttons' action listeners. This method is responsible for creating
	 * the welcome screen on a successful login
	 */
	void startListening() {
		// perform action on button click
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = textField1.getText();
				if (us.login(username, textField2.getText())) {
					sc.updateUser(new User(username, us.getID(username),
							us.getIsDependent(username)));
//					System.out.println(us.getIsDependent(username));
					textField1.setText("");
					textField2.setText("");
					sc.switchScreen("Welcome Screen");
				} else {
					textField1.setText("");
					textField2.setText("");
					JOptionPane.showMessageDialog(null, "Login Unsuccessful");
				}
			}
		}); // add action listener to button
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textField1.setText("");
				textField2.setText("");
				sc.switchScreen("Register Screen");
			}
		}); // add action listener to button

		importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Parsing Screen");
			}
		});

	}
}
