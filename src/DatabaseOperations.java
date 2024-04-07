import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseOperations {
    private static final String url = "jdbc:mariadb://localhost:3306/CANAIR";
    private static final String user = "root";
    private static final String password = "3126353Can@";

    public static boolean registerUser(String userName, String userPassword) {
        String insertUserQuery = "INSERT INTO users (name) VALUES (?)";
        String insertPasswordQuery = "INSERT INTO passwords (user_id, password) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement stmtUser = conn.prepareStatement(insertUserQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmtUser.setString(1, userName);
                int affectedRows = stmtUser.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Kullanıcı oluşturulamadı.");
                }

                try (ResultSet generatedKeys = stmtUser.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long userId = generatedKeys.getLong(1);

                        try (PreparedStatement stmtPassword = conn.prepareStatement(insertPasswordQuery)) {
                            stmtPassword.setLong(1, userId);
                            stmtPassword.setString(2, userPassword);
                            stmtPassword.executeUpdate();
                            return true;
                        }
                    } else {
                        throw new SQLException("Kullanıcı ID'si alınamadı.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean authenticateUser(String userName, String password) {
        String userQuery = "SELECT id FROM users WHERE name = ?";
        String passwordQuery = "SELECT password FROM passwords WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement stmtUser = conn.prepareStatement(userQuery)) {
                stmtUser.setString(1, userName);
                try (ResultSet rsUser = stmtUser.executeQuery()) {
                    if (rsUser.next()) {
                        long userId = rsUser.getLong("id");

                        try (PreparedStatement stmtPassword = conn.prepareStatement(passwordQuery)) {
                            stmtPassword.setLong(1, userId);
                            try (ResultSet rsPassword = stmtPassword.executeQuery()) {
                                if (rsPassword.next()) {
                                    String dbPassword = rsPassword.getString("password");
                                    return password.equals(dbPassword);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
