import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageDependentsScreen extends JPanel {
    private DbConnectionService db;
    private ScreenController sc;
    private JScrollPane scrollPane;
    private GridBagConstraints gbc;
    private JList<String> dependentList;

    // Panel components
    JPanel mainPanel;

    public ManageDependentsScreen(DbConnectionService db, ScreenController sc) {
        this.db = db;
        this.sc = sc;

        mainPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();

        // Header
        JLabel header = new JLabel("<html><h1>Manage Your Dependents</h1></html>");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        mainPanel.add(header, gbc);

        // Current dependents label
        JLabel currentDependentsLabel = new JLabel("<html><h2>Current Dependents</h2></html>");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(currentDependentsLabel, gbc);

        // Your add code label
        JLabel yourCodeLabel = new JLabel("<html><h2>Your Add Code</h2></html>");
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(yourCodeLabel, gbc);

        JLabel yourCode = new JLabel("<html><h3>" + getCode() + "</h2></html>");
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(yourCode, gbc);

        createScrollPane();

        // Delete Selected User
        JButton deleteButton = new JButton("Remove Selected Dependent");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        mainPanel.add(deleteButton, gbc);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = dependentList.getSelectedIndex();
                if(index == -1) return;
                mainPanel.remove(scrollPane);
                deleteDependent(dependentList.getSelectedValue());
                createScrollPane();
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        JLabel unDeleteDependent = new JLabel("<html><h3>Un-remove a dependent</h3></html>");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        mainPanel.add(unDeleteDependent, gbc);

        JTextField codeField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        mainPanel.add(codeField, gbc);

        JButton unDeleteButton = new JButton("Un-remove Selected Dependent");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        mainPanel.add(unDeleteButton, gbc);

        unDeleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    final String sproc = "EXEC UnRemoveDependent @UserID = ?, @Dep = ?";
                    CallableStatement smt = db.getConnection().prepareCall(sproc);
                    smt.setInt(1, sc.getUser().id);
                    smt.setString(2, codeField.getText());
                    smt.execute();
                }
                catch(SQLException sqle) {
//                    sqle.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Un-Deletion Unsuccessful");
                }
                mainPanel.remove(scrollPane);
                createScrollPane();
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });

        JButton homeButton = new JButton("Home");
        gbc.gridx = 2;
        gbc.gridy = 3;
        mainPanel.add(homeButton, gbc);

        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sc.switchScreen("Welcome Screen");
            }
        });

        add(mainPanel);
    }

    private String getCode() {
        try {
            return Integer.toString(sc.getUser().id);
        } catch(Exception e) {
            return "Error. Contact Developers.";
        }

    }

    private void createScrollPane() {
        // Dependents List
        dependentList = new JList<String>(getDependents().toArray(new String[0]));
        scrollPane = new JScrollPane(dependentList);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(scrollPane, gbc);
    }
    
    private ArrayList<String> getDependents() {
//        System.out.println(sc.getUser());
        try {
            final String sproc = "EXEC GetDependents @EmployeeID = ?";
            CallableStatement smt = db.getConnection().prepareCall(sproc);
            smt.setInt(1, sc.getUser().id);
//            System.out.println(sc.getUser().id);
            ResultSet rs = smt.executeQuery();
            ArrayList<String> output = new ArrayList<String>();
            while (rs.next()) {
                String name = rs.getString(1);
                output.add(name);
//                System.out.println(name);
            }
            return output;
        } catch(Exception e) {}
        return new ArrayList<String>();
    }

    private void deleteDependent(String dependent) {
        try {
//            System.out.println(dependent);
            final String sproc = "EXEC RemoveDependent @Username = ?";
            CallableStatement smt = db.getConnection().prepareCall(sproc);
            smt.setString(1, dependent);
            smt.executeQuery();
        } catch(Exception e) {}
    }
}
