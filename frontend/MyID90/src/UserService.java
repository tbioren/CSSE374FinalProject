import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.Callable;

public class UserService {
    private static final Random RANDOM = new SecureRandom();
    private static final Base64.Encoder enc = Base64.getEncoder();
    private static final Base64.Decoder dec = Base64.getDecoder();
    private DbConnectionService dbService;

    public UserService(DbConnectionService dbService) {
        this.dbService = dbService;
    }

    public boolean login(String username, String password) {
        if(username.isBlank() || password.isBlank()) return false;
        final String sproc = "Exec GetUser @Username = ?";
        try {
            CallableStatement smt = dbService.getConnection().prepareCall(sproc);
            smt.setString(1, username);
            ResultSet rs = smt.executeQuery();
            rs.next();
            String salt = rs.getString("Salt");
            byte[] decodedSalt = dec.decode(salt);
            String dbHash = rs.getString("PasswordHash");
            String hashLocal = hashPassword(decodedSalt, password);
            return hashLocal.equals(dbHash);
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getIsDependent(String username) {
        if(username.isBlank()) return false;
        final String sproc = "EXEC isDependent @Username = ?";
        try {
            CallableStatement smt = dbService.getConnection().prepareCall(sproc);
            smt.setString(1, username);
            ResultSet rs = smt.executeQuery();
            return rs.next();
        } catch(SQLException e) {
//            e.printStackTrace();
            return false;
        }
    }

    public int getID(String username) {
        final String sproc = "Exec GetID @Username = ?";
        try {
            CallableStatement smt = dbService.getConnection().prepareCall(sproc);
            smt.setString(1, username);
            ResultSet rs = smt.executeQuery();
            rs.next();
            return rs.getInt("ID");
        } catch(SQLException e) {}
        return -1;
    }

    public boolean registerEmployee(String username, String password, String FirstName, String LastName, String employeeAirline) {
        if(password.isBlank()) return false;
        final String createUserEmployeeSproc = "EXEC CreateUserEmployee @Username = ?, @Salt = ?, @PasswordHash = ?, @FirstName = ?, @LastName = ?, @EmployeeAirline = ?";
        byte[] salt = getNewSalt();
        String saltString = getStringFromBytes(salt);
        String passwordHash = hashPassword(salt, password);
        try {
            CallableStatement smt = dbService.getConnection().prepareCall(createUserEmployeeSproc);
            smt.setString(1, username);
            smt.setString(2, saltString);
            smt.setString(3, passwordHash);
            smt.setString(4, FirstName);
            smt.setString(5, LastName);
            smt.setString(6, employeeAirline);
            return smt.executeUpdate() > 0;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerDependent(String username, String password, String FirstName, String LastName, String employeeUsername, int addCode) {
        final String CreateUserDependentSproc = "EXEC CreateUserDependent @Username = ?, @Salt = ?, @PasswordHash = ?," +
                "@FirstName = ?, @LastName = ?, @EmployeeUsername = ?, @AddCode = ?";
        byte[] salt = getNewSalt();
        String saltString = getStringFromBytes(salt);
        String passwordHash = hashPassword(salt, password);
        try {
            CallableStatement smt = dbService.getConnection().prepareCall(CreateUserDependentSproc);
            smt.setString(1, username);
            smt.setString(2, saltString);
            smt.setString(3, passwordHash);
            smt.setString(4, FirstName);
            smt.setString(5, LastName);
            smt.setString(6, employeeUsername);
            smt.setInt(7, addCode);
            return smt.executeUpdate() > 0;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private byte[] getNewSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    private String getStringFromBytes(byte[] data) {
        return enc.encodeToString(data);
    }

    private String hashPassword(byte[] salt, String password) {

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f;
        byte[] hash = null;
        try {
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
            e.printStackTrace();
        }
        return getStringFromBytes(hash);
    }
}