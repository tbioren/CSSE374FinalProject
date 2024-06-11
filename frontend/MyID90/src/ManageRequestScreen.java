import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class ManageRequestScreen extends JPanel {

    private LoadRequestService lrService;

    private ArrayList<LoadRequest> requests;

    private JTable requestsTable;
    private JTextField tokenCostBox = null;
    private JButton updateButton = null;
    private JButton deleteButton = null;
    private JButton backButton = null;
    private JButton submitButton = null;
    private JLabel tokenLabel = null;

    DbConnectionService db;
    ScreenController sc;


    ManageRequestScreen(DbConnectionService dbService, ScreenController sController) {
        this.db = dbService;
        this.sc = sController;
        this.lrService = new LoadRequestService(dbService);

        JPanel managePanel = generateManagePanel();
        this.setLayout(new BorderLayout());
        this.add(managePanel, BorderLayout.NORTH);

        JScrollPane tablePane = generateActiveLoadRequestsTable();
        this.add(tablePane, BorderLayout.CENTER);

        JPanel bottomPanel = generateBottomPanel();
        this.add(bottomPanel, BorderLayout.SOUTH);

        startListening();
    }


    private JPanel generateManagePanel() {
        JPanel loadRequestPanel = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setHgap(15);
        this.tokenCostBox = new JTextField(10);

        loadRequestPanel.setLayout(layout);
        loadRequestPanel.add(new JLabel("Token Cost: "));
        loadRequestPanel.add(this.tokenCostBox);
        this.updateButton = new JButton("Update Load Request");
        loadRequestPanel.add(updateButton);
        this.deleteButton = new JButton("Delete Load Request");
        loadRequestPanel.add(deleteButton);


        return loadRequestPanel;
    }



    private JScrollPane generateActiveLoadRequestsTable() {
        this.requestsTable = new JTable(this.search());

        JScrollPane scrollPane = new JScrollPane(this.requestsTable);
        this.requestsTable.setFillsViewportHeight(true);
        this.requestsTable.getTableHeader().setReorderingAllowed(false);
        return scrollPane;
    }


    private LoadRequestTableModel search() {

        ArrayList<LoadRequest> data = this.lrService.listYourLoadRequests(sc.user.id, sc.user.username);
        requests = this.lrService.listYourLoadRequests(sc.user.id, sc.user.username);
        return new LoadRequestTableModel(data);
    }

    private JPanel generateBottomPanel() {
        JPanel toReturn = new JPanel();
        FlowLayout fLayout = new FlowLayout();
        fLayout.setHgap(15);

        toReturn.setLayout(fLayout);
        this.backButton = new JButton("Back");
        toReturn.add(backButton);

        Tokens tokenGen = new Tokens(this.db, this.sc);
        this.tokenLabel = tokenGen.makeLabel();
        toReturn.add(tokenLabel);

        this.submitButton = new JButton("Submit Load Request");
        toReturn.add(submitButton);

        return toReturn;
    }


    void startListening() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sc.switchScreen("Welcome Screen");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int employeeID = sc.user.id;
                if (requestsTable.getSelectedRowCount() != 1 || requestsTable.getSelectedRow() == -1 || tokenCostBox.getText().isBlank()) {
                    tokenCostBox.setText("");
                    JOptionPane.showMessageDialog(null, "Can not update.");
                } else {
                    try {
                        int flightID = Integer.parseInt(requests.get(requestsTable.getSelectedRow()).getID());
                        int tokenCost = Integer.parseInt(tokenCostBox.getText());
                        updateLoadRequestCost(employeeID, flightID, tokenCost);
                        refresh();
                        Tokens tokenGen = new Tokens(db, sc);
                        tokenLabel.setText(tokenGen.makeLabel().getText());
                    } catch (NumberFormatException n) {
                        n.printStackTrace();
                        tokenCostBox.setText("");
                        JOptionPane.showMessageDialog(null, "Can not update. :(");
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int employeeID = sc.user.id;
                tokenCostBox.setText("");
                if (requestsTable.getSelectedRowCount() != 1 || requestsTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Can not delete.");
                } else {
                    try {
                        int flightID = Integer.parseInt(requests.get(requestsTable.getSelectedRow()).getID());
                        lrService.DeleteLoadRequest(employeeID, flightID);
                        JOptionPane.showMessageDialog(null, "Load request deleted.");
                        refresh();
                        Tokens tokenGen = new Tokens(db, sc);
                        tokenLabel.setText(tokenGen.makeLabel().getText());
                    } catch (NumberFormatException n) {
                        n.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Can not delete. :(");
                    }
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sc.switchScreen("Submit Request Screen");
            }
        });
    }



    public void refresh() {
        requestsTable.setModel(search());
    }

    public void updateLoadRequestCost(int EID, int FID, int tokenCost) {
        if (lrService.UpdateLoadRequestCost(EID, FID, tokenCost)) {
            tokenCostBox.setText("");
            JOptionPane.showMessageDialog(null, "Load Request Updated.");
        } else {
            tokenCostBox.setText("");
            JOptionPane.showMessageDialog(null, "Cannot update load request.");
        }
    }
}
