import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterScreen extends JPanel{
    JButton registerButton, cancelButton;
    JPanel newPanel;
    JLabel userLabel, passLabel, firstNameLabel, lastNameLabel, isDependentLabel, employeeUsernameLabel, addCode, employeeAirlineLabel;
    final JTextField usernameField, passwordField, firstNameField, lastNameField, employeeUsernameField, addField, employeeAirlineField;
    JCheckBox isDependent;
    ScreenController sc;

    //calling constructor
    RegisterScreen(DbConnectionService db, ScreenController sc) {
        this.sc = sc;
        UserService us = new UserService(db);
        //create label for username
        userLabel = new JLabel();
        userLabel.setText("Username");      //set label value for usernameField

        //create text field to get username from the user
        usernameField = new JTextField(15);    //set length of the text

        //create label for password
        passLabel = new JLabel();
        passLabel.setText("Password");      //set label value for passwordField

        //create text field to get password from the user
        passwordField = new JPasswordField(15);    //set length for the password

        // Create field and label for first name
        firstNameLabel = new JLabel();
        firstNameLabel.setText("First Name");

        firstNameField = new JTextField(15);

        // Create field and label for last name
        lastNameLabel = new JLabel();
        lastNameLabel.setText("Last Name");

        lastNameField = new JTextField(15);

        // Create field and label for isDependent
        isDependentLabel = new JLabel();
        isDependentLabel.setText("Is Dependent");

        isDependent = new JCheckBox();

        // Create field and label for employee username
        employeeUsernameLabel = new JLabel();
        employeeUsernameLabel.setText("Employee Username");

        employeeUsernameField = new JTextField(15);

        // Create field and label for add code
        addCode = new JLabel();
        addCode.setText("Add Code");

        addField = new JTextField(15);

        // Create field and label for employee airline
        employeeAirlineLabel = new JLabel();
        employeeAirlineLabel.setText("Employee Airline");

        employeeAirlineField = new JTextField(3);

        //create submit button
        registerButton = new JButton("Register"); //set label to button

        // Create cancel button
        cancelButton = new JButton("Cancel");

        //create panel to put form elements
        newPanel = new JPanel();
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
        newPanel.add(userLabel);    //set username label to panel
        newPanel.add(usernameField);   //set text field to panel
        newPanel.add(passLabel);    //set password label to panel
        newPanel.add(passwordField);   //set text field to panel
        newPanel.add(firstNameLabel);
        newPanel.add(firstNameField);
        newPanel.add(lastNameLabel);
        newPanel.add(lastNameField);
        newPanel.add(employeeAirlineLabel);
        newPanel.add(employeeAirlineField);
        newPanel.add(isDependentLabel);
        newPanel.add(isDependent);
        newPanel.add(registerButton);
        newPanel.add(cancelButton);

        //set border to panel
        add(newPanel, BorderLayout.CENTER);

        //perform action on button click
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isDependent.isSelected()) {
                    if(us.registerEmployee(usernameField.getText(), passwordField.getText(), firstNameField.getText(),
                            lastNameField.getText(), employeeAirlineField.getText())) {
                        sc.switchScreen("Login Screen");
                    } else {
                        JOptionPane.showMessageDialog(null, "Registration Failed.");
                    }
                }
                else {
                    if(us.registerDependent(usernameField.getText(), passwordField.getText(), firstNameField.getText(),
                            lastNameField.getText(), employeeUsernameField.getText(), Integer.parseInt(addField.getText()))) {
                        JOptionPane.showMessageDialog(null, "Registration Successful.\nPlease Login.");
                        sc.switchScreen("Login Screen");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Registration Failed.");
                    }
                }
            }
        });     //add action listener to button

        isDependent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.updateComponentTreeUI(newPanel);
                if(isDependent.isSelected()) {
                    newPanel.remove(employeeAirlineLabel);
                    newPanel.remove(employeeAirlineField);
                    newPanel.remove(registerButton);
                    newPanel.remove(cancelButton);
                    newPanel.add(employeeUsernameLabel);
                    newPanel.add(employeeUsernameField);
                    newPanel.add(addCode);
                    newPanel.add(addField);
                    newPanel.add(registerButton);
                    newPanel.add(cancelButton);
                }
                else {
                    newPanel.remove(registerButton);
                    newPanel.remove(cancelButton);
                    newPanel.remove(employeeUsernameLabel);
                    newPanel.remove(employeeUsernameField);
                    newPanel.remove(addCode);
                    newPanel.remove(addField);
                    newPanel.add(employeeAirlineLabel);
                    newPanel.add(employeeAirlineField);
                    newPanel.add(registerButton);
                    newPanel.add(cancelButton);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sc.switchScreen("Login Screen");
            }
        });
    }
}