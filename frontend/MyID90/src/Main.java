import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import java.net.ConnectException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		FlatLightLaf.setup();
		DbConnectionService dbConnectionService = new DbConnectionService();
		dbConnectionService.connect();
		System.out.println(dbConnectionService.getConnection());
		ScreenController screenController = new ScreenController(dbConnectionService);
	}
}
