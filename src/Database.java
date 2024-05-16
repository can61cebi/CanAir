package pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.Statement;

public class Database {
    private static final String url = "jdbc:mariadb://localhost:3306/CANAIR?user=root&password=cancebimaria&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String user = "root";
    private static final String password = "cancebimaria";

    public static boolean registerUser(String userName, String userPassword) {
    	String userExistsQuery = "SELECT COUNT(id) FROM users WHERE name = ?";
        String insertUserQuery = "INSERT INTO users (name) VALUES (?)";
        String insertPasswordQuery = "INSERT INTO passwords (user_id, password) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement checkUserStmt = conn.prepareStatement(userExistsQuery)) {
                checkUserStmt.setString(1, userName);
                ResultSet rs = checkUserStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return false;
                }
            }
        
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
    
    public static int findOrCreateFlight(String departureCity, String arrivalCity, Date flightDate) {
        String searchQuery = "SELECT id FROM flights WHERE departure_city = ? AND arrival_city = ? AND flight_date = ?";
        String insertQuery = "INSERT INTO flights (departure_city, arrival_city, flight_date) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement searchStmt = conn.prepareStatement(searchQuery)) {
                searchStmt.setString(1, departureCity);
                searchStmt.setString(2, arrivalCity);
                searchStmt.setDate(3, new java.sql.Date(flightDate.getTime()));
                ResultSet rs = searchStmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertStmt.setString(1, departureCity);
                insertStmt.setString(2, arrivalCity);
                insertStmt.setDate(3, new java.sql.Date(flightDate.getTime()));
                insertStmt.executeUpdate();
                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public static boolean assignFlightToUser(int userId, int flightId) {
        String query = "INSERT INTO user_flights (user_id, flight_id) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, flightId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static int getUserId(String username) {
        String query = "SELECT id FROM users WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public static String getFlightDetails(int flightId) {
        String query = "SELECT departure_city, arrival_city, flight_date FROM flights WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, flightId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String departure = rs.getString("departure_city");
                    String arrival = rs.getString("arrival_city");
                    Date date = rs.getDate("flight_date");
                    return "From: " + departure + " To: " + arrival + " Date: " + date.toString();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Flight details not found.";
    }

}
