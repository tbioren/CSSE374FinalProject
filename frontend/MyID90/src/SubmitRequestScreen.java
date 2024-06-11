import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubmitRequestScreen extends JPanel {

    private FlightService fService;
    private LoadRequestService lrService;

    private ArrayList<Flight> flights;

    private JTable flightsTable;
    private JLabel tokenLabel = null;
    private JTextField tokenCostBox = null;
    private JButton submitButton = null;
    private JButton manageButton = null;
    private JButton getFlightsButton = null;
    private JButton resetButton = null;
    private JButton backButton = null;
    private JComboBox<String> airlineComboBox = null;

    private JTextField flightNumberBox = null;
    private HashMap<String, String> destinationMap;
    private JComboBox<String> fromBox = null;
    private JComboBox<String> toBox = null;
    private JTextField yearBox = null;
    private JComboBox<String> monthBox = null;
    private JComboBox<Integer> dayBox = null;

    DbConnectionService db;
    ScreenController sc;

    private JPanel toReturn;

    SubmitRequestScreen(DbConnectionService dbService, ScreenController sController) {
        this.db = dbService;
        this.sc = sController;
        this.fService = new FlightService(dbService);
        this.lrService = new LoadRequestService(dbService);
        this.destinationMap = new HashMap<String, String>();

        JPanel topPanel = generateSubmitPanel();
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);

        JScrollPane tablePane = generateFlightsTable();
        this.add(tablePane, BorderLayout.CENTER);

        JPanel bottomPanel = generateFlightInputPanel();
        this.add(bottomPanel, BorderLayout.SOUTH);

        startListening();
    }


    private JPanel generateSubmitPanel() {
        JPanel submitPanel = new JPanel();
        FlowLayout fLayout = new FlowLayout();
        fLayout.setHgap(15);
        submitPanel.setLayout(fLayout);

        this.backButton = new JButton("Back");
        submitPanel.add(backButton);

        Tokens tokenGen = new Tokens(this.db, this.sc);
        this.tokenLabel = tokenGen.makeLabel();
        submitPanel.add(this.tokenLabel);

        submitPanel.add(new JLabel("Token Cost: "));
        this.tokenCostBox = new JTextField(10);
        submitPanel.add(tokenCostBox);

        this.submitButton = new JButton("Submit Load Request");
        submitPanel.add(submitButton);

        this.manageButton = new JButton("Manage Load Request");
        submitPanel.add(manageButton);

        this.getFlightsButton = new JButton("Get Flights");
        submitPanel.add(getFlightsButton);

        this.resetButton = new JButton("Reset");
        submitPanel.add(resetButton);


        return submitPanel;
    }


    private JScrollPane generateFlightsTable() {
        this.flightsTable = new JTable(this.searchAllFlights());

        JScrollPane scrollPane = new JScrollPane(this.flightsTable);
        this.flightsTable.setFillsViewportHeight(true);
        this.flightsTable.getTableHeader().setReorderingAllowed(false);

        return scrollPane;
    }


    private FlightTableModel searchAllFlights() {
        ArrayList<Flight> data = this.fService.listFlightsWithConditions(sc.user.id, null, null, null, null, null, null, null);
        flights = this.fService.listFlightsWithConditions(sc.user.id, null, null, null, null, null, null, null);
        return new FlightTableModel(data);
    }


    private FlightTableModel search() {
        int eID = sc.user.id;

        String airlineID;
        if (this.airlineComboBox.getSelectedItem() == null) {
            airlineID = null;
        } else {
            airlineID = this.airlineComboBox.getSelectedItem().toString();
        }

        String fNumber;
        if (this.flightNumberBox.getText().isBlank()) {
            fNumber = null;
        } else {
            try {
                Integer.parseInt(this.flightNumberBox.getText());
                fNumber = this.flightNumberBox.getText();
            } catch (NumberFormatException n) {
                n.printStackTrace();
                fNumber = null;
            }
        }


        String fCode = null;
        if (this.fromBox.getSelectedIndex() != 0) {
            fCode = destinationMap.get(fromBox.getSelectedItem());
        }

        String tCode = null;
        if (this.toBox.getSelectedIndex() != 0) {
            tCode = destinationMap.get(toBox.getSelectedItem());
        }

        Integer year;
        if (this.yearBox.getText().isBlank()) {
            year = null;
        } else {
            try {
                year = Integer.parseInt(this.yearBox.getText());
            } catch (NumberFormatException n) {
                n.printStackTrace();
                year = null;
            }
        }

        Integer month = null;
        if (this.monthBox.getSelectedIndex() != 0) {
            month = this.monthBox.getSelectedIndex();
        }

        Integer day = null;
        if (this.dayBox.getSelectedIndex() != 0) {
            day = this.dayBox.getSelectedIndex();
        }

        ArrayList<Flight> data = this.fService.listFlightsWithConditions(eID, airlineID, fNumber, fCode, tCode, year, month, day);
        flights = this.fService.listFlightsWithConditions(eID, airlineID, fNumber, fCode, tCode, year, month, day);
        return new FlightTableModel(data);
    }


    private JPanel generateFlightInputPanel() {
        toReturn = new JPanel();
        FlowLayout fLayout = new FlowLayout();
        fLayout.setHgap(5);
        toReturn.setLayout(fLayout);

        toReturn.add(new JLabel("Airline: "));
        populateAirlineBoxes();

        toReturn.add(new JLabel("Flight number: "));
        this.flightNumberBox = new JTextField(10);
        toReturn.add(flightNumberBox);

        populateComboBoxes();

        return toReturn;
    }

    private void populateAirlineBoxes() {
        List<String> airlines = fService.listAirlines();
        String[] airlineIDs = new String[airlines.size() + 1];
        airlineIDs[0] = null;
        for (int i = 0; i < airlines.size(); i++) {
            airlineIDs[i + 1] = airlines.get(i);
        }
        this.airlineComboBox = new JComboBox<>(airlineIDs);
        toReturn.add(airlineComboBox);
    }

    private void populateComboBoxes() {
        DestinationService dService = new DestinationService(db);

        ArrayList<Destination> destinationList = dService.listDestinations();
        String[] destinationNames = new String[destinationList.size() + 1];
        destinationNames[0] = null;
        for (int i = 0; i < destinationList.size(); i++) {
            Destination d = destinationList.get(i);
            destinationMap.put(d.toString(), d.iataCode);
            destinationNames[i + 1] = d.toString();
        }

        toReturn.add(new JLabel("From: "));
        this.fromBox = new JComboBox<String>(destinationNames);
        toReturn.add(fromBox);

        toReturn.add(new JLabel("To: "));
        this.toBox = new JComboBox<String>(destinationNames);
        toReturn.add(toBox);

        toReturn.add(new JLabel("Year: "));
        this.yearBox = new JTextField(10);
        toReturn.add(yearBox);

        toReturn.add(new JLabel("Month: "));
        String[] monthList = {null, "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        this.monthBox = new JComboBox<String>(monthList);
        toReturn.add(monthBox);

        toReturn.add(new JLabel("Day: "));
        Integer[] dayList = new Integer[32];
        dayList[0] = null;
        for (int i = 1; i < 32; i++) {
            dayList[i] = i;
        }
        this.dayBox = new JComboBox<Integer>(dayList);
        toReturn.add(dayBox);
    }


    private void clearFields() {
        tokenCostBox.setText("");
        airlineComboBox.setSelectedIndex(0);
        flightNumberBox.setText("");
        fromBox.setSelectedIndex(0);
        toBox.setSelectedIndex(0);
        yearBox.setText("");
        monthBox.setSelectedIndex(0);
        dayBox.setSelectedIndex(0);
    }


    void startListening() {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sc.switchScreen("Welcome Screen");
            }
        });

        manageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sc.switchScreen("Manage Request Screen");
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int eID = sc.user.id;

                if (flightsTable.getSelectedRowCount() != 1 || flightsTable.getSelectedRow() == -1 || tokenCostBox.getText().isBlank()) {
                    clearFields();
                    JOptionPane.showMessageDialog(null, "Token value is blank.");
                } else {
                    try{
                        int fID = Integer.parseInt(flights.get(flightsTable.getSelectedRow()).getID());
                        int tokenCost = Integer.parseInt(tokenCostBox.getText());
                        createLoadRequest(eID, fID, tokenCost);
                        flightsTable.setModel(searchAllFlights());
                        Tokens tokenGen = new Tokens(db, sc);
                        tokenLabel.setText(tokenGen.makeLabel().getText());
                    } catch (NumberFormatException n) {
                        n.printStackTrace();
                        clearFields();
                        JOptionPane.showMessageDialog(null, "Enter valid token value.");
                    }
                }


            }
        });

        getFlightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flightsTable.setModel(search());
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flightsTable.setModel(searchAllFlights());
                clearFields();
            }
        });

    }

    public void createLoadRequest(int EID, int FID, int tokenCost) {
        if (lrService.CreateLoadRequest(EID, FID, tokenCost)) {
            clearFields();
            JOptionPane.showMessageDialog(null, "Load Request created.");
        } else {
            clearFields();
            JOptionPane.showMessageDialog(null, "Cannot create load request.");
        }
    }
}
