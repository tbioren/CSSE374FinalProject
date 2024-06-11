import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.*;

public class ParsingScreen extends JPanel {
	private DbConnectionService db;
	private ScreenController sc;
	private JPanel newPanel;
	private JButton backButton, destinationButton, airlineButton, flightButton;

	ParsingScreen(DbConnectionService db, ScreenController sc) {
		this.db = db;
		this.sc = sc;
		backButton = new JButton("Go Back");
		destinationButton = new JButton("Import Destination");
		airlineButton = new JButton("Import Airlines");
		flightButton = new JButton("Import Flights");

		newPanel = new JPanel(new GridLayout(6, 1));
		this.add(newPanel, BorderLayout.CENTER);
		newPanel.add(destinationButton);
		newPanel.add(airlineButton);
		newPanel.add(flightButton);
		newPanel.add(backButton);
		startListening();
	}

	/**
	 * Adds action listeners to all buttons on the page
	 */
	private void startListening() {

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Login Screen");
			}
		});

		destinationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				importDestination();
			}
		});

		airlineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				importAirline();
			}
		});

		flightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				importFlights();
			}
		});
	}

	/**
	 * Prompts the user for a file & adds all destinations found there to the
	 * database.
	 * Due to the formatting of the publically available CSV file, it expects the IATACode to be found in the 1st column & the name to be found
	 * in the 3rd column
	 */
	private void importDestination() {
		Optional<File> o = getFile();
		if (o.isPresent()) {
			CSVParser cs = new CSVParser(o.get(), 1);
			DestinationService serv = new DestinationService(db);
			while (cs.hasNext()) {
				List<String> l = cs.getRow();
				serv.createDestination(l.get(0), l.get(2));
			}
			JOptionPane.showMessageDialog(null, "Destinations Imported");
		}
	}

	/**
	 * Prompts the user for a file & adds all airlines found there to the database
	 * It expects the ID to be found in the 1st column & alliance to be found in the 2nd column
	 */
	private void importAirline() {
		Optional<File> o = getFile();
		if (o.isPresent()) {
			CSVParser cs = new CSVParser(o.get(), 1);
			FlightService serv = new FlightService(db);
			while (cs.hasNext()) {
				List<String> l = cs.getRow();
				String airlineID = l.get(0);
				if (airlineID.compareTo("n/a") != 0) {
					serv.CreateAirline(airlineID, l.get(1));
				}
			}
			JOptionPane.showMessageDialog(null, "Airlines Imported");
		}
	}


	private void importFlights() {
		Optional<File> o = getFile();
		if (o.isPresent()) {
			CSVParser cs = new CSVParser(o.get(), 1);
			FlightService serv = new FlightService(db);
			while (cs.hasNext()) {
				List<String> l = cs.getRow();
				String fNum = l.get(0);
				String dDateTime = l.get(12);
				String fCode = l.get(13);
				String tCode = l.get(14);
				String aID = l.get(15);
				if (fCode.compareTo(tCode) != 0) {
					serv.createFlight(fNum, dDateTime, fCode, tCode, aID);
				}
			}
			JOptionPane.showMessageDialog(null, "Flights Imported");
		}
	}

	/**
	 * Prompts the user to select a file
	 * 
	 * @returns an optional with the selected file, or an empty optional if no file
	 *          was selected
	 */
	private Optional<File> getFile() {
		JFileChooser j = new JFileChooser();
		int r = j.showOpenDialog(null);
		if (r == JFileChooser.APPROVE_OPTION) {
			return Optional.of(j.getSelectedFile());
		} else {
			return Optional.empty();
		}

	}
}
