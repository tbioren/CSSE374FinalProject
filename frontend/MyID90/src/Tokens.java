import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;

/**
 * Constructs a JLabel to display the number of tokens a user has
 */
class Tokens {
	DbConnectionService dbService;
	ScreenController sc;

	Tokens(DbConnectionService dbService, ScreenController sc) {
		this.dbService = dbService;
		this.sc = sc;
	}

	/**
	 * @returns a Jlabel that tells how many tokens the user has
	 */
	JLabel makeLabel() {
		if (sc.getUser().isDependent) {
			return new JLabel("");
		}
		Connection conn = dbService.getConnection();
		CallableStatement cs;
		try {
			// cs = conn.prepareCall("{call GetTokens ?}");
			cs = conn.prepareCall("{call GetTokens(?)}");
			cs.setString(1, sc.getUser().username);
			ResultSet rs = cs.executeQuery();
			rs.next();
			int numTokens = rs.getInt(1); // This should get the number of tokens
			return new JLabel("You have: " + numTokens + " tokens.");
		} catch (SQLException e) {
			e.printStackTrace();
			return new JLabel("Error getting number of tokens");
		}
	}
}
