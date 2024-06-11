import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PlanPage extends JPanel {

	private JTable flightsTable;
	private JLabel tokenLabel;
	private JButton getFlightsButton, resetButton, backButton, addPlanButton, addFlightToPlanButton,
			changeOrderButton;
	private JComboBox<String> planBox;
	private JTextField orderField;

	private DbConnectionService db;
	private ScreenController sc;

	PlanPage(DbConnectionService dbService, ScreenController sController) {
		this.db = dbService;
		this.sc = sController;

		JPanel topPanel = generateSubmitPanel();
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);

		JScrollPane tablePane = generateFlightsTable();
		this.add(tablePane, BorderLayout.CENTER);
		flightsTable.setModel(search());

		startListening();
	}

	/**
	 * Generates the top bar of the page
	 */
	private JPanel generateSubmitPanel() {
		JPanel submitPanel = new JPanel();
		FlowLayout fLayout = new FlowLayout();
		fLayout.setHgap(10);
		submitPanel.setLayout(fLayout);

		Tokens tokenGen = new Tokens(this.db, this.sc);
		tokenLabel = tokenGen.makeLabel();
		submitPanel.add(this.tokenLabel);

		getFlightsButton = new JButton("Show Plan");
		submitPanel.add(getFlightsButton);

		planBox = makePlanBox();
		submitPanel.add(planBox);

		addPlanButton = new JButton("Add Plan");
		submitPanel.add(addPlanButton);

		addFlightToPlanButton = new JButton("Add Flight To Plan");
		submitPanel.add(addFlightToPlanButton);

		orderField = new JTextField();
		submitPanel.add(orderField);

		changeOrderButton = new JButton("Update Flight Order");
		submitPanel.add(changeOrderButton);

		resetButton = new JButton("Reset");
		submitPanel.add(resetButton);

		backButton = new JButton("Back");
		submitPanel.add(backButton);

		return submitPanel;
	}

	/**
	 * Creates the visible version of the flight table
	 */
	private JScrollPane generateFlightsTable() {
		this.flightsTable = new JTable(new WholeFlightTableModel(new ArrayList<WholeFlight>()));

		JScrollPane scrollPane = new JScrollPane(this.flightsTable);
		this.flightsTable.setFillsViewportHeight(true);
		this.flightsTable.getTableHeader().setReorderingAllowed(false);

		return scrollPane;
	}

	private JComboBox<String> makePlanBox() {
		PlanService serv = new PlanService(db);
		List<String> l = serv.listPlans(sc.getUser().id);
		String[] plans = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			plans[i] = l.get(i);
		}
		return new JComboBox<String>(plans);
	}

	/**
	 * Returns a new table based on the user's input
	 */
	private WholeFlightTableModel search() {
		PlanService serv = new PlanService(db);
		List<WholeFlight> data = serv.listPlanContents((String) planBox.getSelectedItem(), sc.getUser().id);
		return new WholeFlightTableModel(data);
	}

	/**
	 * Resets any input fields the user may have filled
	 */
	private void clearFields() {
		planBox.setSelectedIndex(0);
	}

	/**
	 * Adds action listeners to all buttons
	 */
	private void startListening() {

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Welcome Screen");
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
				flightsTable.setModel(new WholeFlightTableModel(new ArrayList<WholeFlight>()));
				clearFields();
			}
		});

		addPlanButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Add Plan Screen");
			}
		});

		addFlightToPlanButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.switchScreen("Add Flight To Plan Screen");
				sc.addFlightToPlanPage.setPlanName((String) planBox.getSelectedItem());
			}
		});

		changeOrderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (flightsTable.getSelectedRowCount() != 1
						|| flightsTable.getSelectedRowCount() == -1) {
					JOptionPane.showMessageDialog(null, "Cannot change order of flights");

				} else {
					PlanService serv = new PlanService(db);
					String planName = (String) planBox.getSelectedItem();
					WholeFlight f = serv.listPlanContents(planName,
							sc.getUser().id).get(flightsTable.getSelectedRow());
					serv.createPlan(planName, Integer.parseInt(f.getID()), sc.getUser().id,
							Integer.parseInt(orderField.getText()));
				}
			}
		});

	}
}
