import javax.swing.*;
import java.awt.*;

public class ScreenController extends JFrame {

	CardLayout cl = new CardLayout();
	DbConnectionService db;
	JPanel mainPanel = new JPanel(cl);
	WelcomeScreen welScreen;
	FulfillRequest fulfillScreen;
	User user;
	FulfillRequestInput fulfillInputScreen;
	CreateFlightsPage createFlightsPage;
	UpdateFlightInput updateFlightInputPage;
	CreateDestinationPage createDestinationPage;
	AddFlightToPlanPage addFlightToPlanPage;

	public ScreenController(DbConnectionService db) {
		this.db = db;
		user = new User("", 0, true);
		setTitle("MyID90");
		setSize(1500, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(cl);
		addScreens();
		add(mainPanel); // Add the main panel to the screen
		setVisible(true);
		switchScreen("Login Screen");
	}

	public void updateUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	private void addScreens() {
		JPanel loginScreen = new LoginScreen(db, this);
		mainPanel.add(loginScreen, "Login Screen");

		JPanel registerScreen = new RegisterScreen(db, this);
		mainPanel.add(registerScreen, "Register Screen");

		JPanel welcomeScreen = new WelcomeScreen(db, this);
		mainPanel.add(welcomeScreen, "Welcome Screen");
		welScreen = (WelcomeScreen) welcomeScreen;

		JPanel manageRequestScreen = new ManageRequestScreen(db, this);
		mainPanel.add(manageRequestScreen, "Manage Request Screen");

		JPanel submitRequestScreen = new SubmitRequestScreen(db, this);
		mainPanel.add(submitRequestScreen, "Submit Request Screen");

		JPanel fulfillRequest = new FulfillRequest(db, this);
		mainPanel.add(fulfillRequest, "Fulfill Request");
		fulfillScreen = (FulfillRequest) fulfillRequest;

		JPanel manageDependentsScreen = new ManageDependentsScreen(db, this);
		mainPanel.add(manageDependentsScreen, "Manage Dependents Screen");

		JPanel fulfillRequestInput = new FulfillRequestInput(db, this);
		mainPanel.add(fulfillRequestInput, "Fulfill Request Input");
		fulfillInputScreen = (FulfillRequestInput) fulfillRequestInput;

		JPanel createFlightScreen = new CreateFlightsPage(db, this);
		mainPanel.add(createFlightScreen, "Create Flight Screen");
		createFlightsPage = (CreateFlightsPage) createFlightScreen;

		JPanel deleteFlightScreen = new DeleteFlight(db, this);
		mainPanel.add(deleteFlightScreen, "Delete Flight Screen");

		JPanel updateFlightSelect = new UpdateFlightSelect(db, this);
		mainPanel.add(updateFlightSelect, "Update Flight Select");

		JPanel updateFlightInput = new UpdateFlightInput(db, this);
		mainPanel.add(updateFlightInput, "Update Flight Input");
		updateFlightInputPage = (UpdateFlightInput) updateFlightInput;

		JPanel createDestinationScreen = new CreateDestinationPage(db, this);
		mainPanel.add(createDestinationScreen, "Create Destination Screen");
		createDestinationPage = (CreateDestinationPage) createDestinationScreen;

		JPanel deleteDestinationScreen = new DeleteDestinationPage(db, this);
		mainPanel.add(deleteDestinationScreen, "Delete Destination Screen");

		JPanel viewFlightScreen = new ViewFlightPage(db, this);
		mainPanel.add(viewFlightScreen, "View Flight Screen");

		JPanel parsingScreen = new ParsingScreen(db, this);
		mainPanel.add(parsingScreen, "Parsing Screen");

		JPanel planScreen = new PlanPage(db, this);
		mainPanel.add(planScreen, "Plan Screen");

		JPanel addPlanScreen = new AddPlanPage(db, this);
		mainPanel.add(addPlanScreen, "Add Plan Screen");

		JPanel addFlightToPlanScreen = new AddFlightToPlanPage(db, this);
		mainPanel.add(addFlightToPlanScreen, "Add Flight To Plan Screen");
		addFlightToPlanPage = (AddFlightToPlanPage) addFlightToPlanScreen;
	}

	public void switchScreen(String screen) {
		mainPanel.removeAll();
		addScreens();
		cl.show(mainPanel, screen);
	}
}
