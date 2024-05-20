package pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.Statement;
import java.util.List;
import java.util.*;

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
        if (!isUserAlreadyRegistered(userId, flightId)) {
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
        } else {
            System.out.println("Kullanıcı bu uçuşa zaten kayıtlı.");
            return false;
        }
    }
    
    public static boolean isUserAlreadyRegistered(int userId, int flightId) {
        String query = "SELECT COUNT(*) FROM user_flights WHERE user_id = ? AND flight_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, flightId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static String assignFirstAvailableSeat(int userId, int flightId) {
        String findEmptySeatQuery = "SELECT id, seat_number FROM seats WHERE flight_id = ? AND available = TRUE LIMIT 1";
        String assignSeatQuery = "UPDATE seats SET available = FALSE WHERE id = ?";
        String updateUserFlightQuery = "UPDATE user_flights SET seat_choice = ? WHERE user_id = ? AND flight_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement findSeatStmt = conn.prepareStatement(findEmptySeatQuery)) {
                findSeatStmt.setInt(1, flightId);
                ResultSet rs = findSeatStmt.executeQuery();
                if (rs.next()) {
                    int seatId = rs.getInt("id");
                    String seatNumber = rs.getString("seat_number");

                    try (PreparedStatement assignSeatStmt = conn.prepareStatement(assignSeatQuery)) {
                        assignSeatStmt.setInt(1, seatId);
                        assignSeatStmt.executeUpdate();
                    }

                    try (PreparedStatement updateUserFlightStmt = conn.prepareStatement(updateUserFlightQuery)) {
                        updateUserFlightStmt.setString(1, seatNumber);
                        updateUserFlightStmt.setInt(2, userId);
                        updateUserFlightStmt.setInt(3, flightId);
                        updateUserFlightStmt.executeUpdate();
                        return "" + seatNumber + ". Koltuk tanımlandı. Birazdan değiştirebilirsiniz.";
                    }
                } else {
                    return "Boş koltuk yok.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Koltuk ataması sırasında bir hata oluştu.";
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
                    return "Nereden: " + departure + " , Nereye: " + arrival + " Tarih: " + date.toString();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Uçuş detayları bulunamadı.";
    }
    
    public static List<String> getOccupiedSeats(int flightId) {
        List<String> occupiedSeats = new ArrayList<>();
        String query = "SELECT seat_number FROM seats WHERE flight_id = ? AND available = FALSE";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, flightId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                occupiedSeats.add(String.valueOf(rs.getInt("seat_number")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return occupiedSeats;
    }
    
    public static boolean updateUserSeat(int userId, int flightId, String newSeatNumber) {
        String findSeatQuery = "SELECT id FROM seats WHERE flight_id = ? AND seat_number = ?";
        String freeOldSeatQuery = "UPDATE seats SET available = TRUE WHERE id = ?";
        String assignNewSeatQuery = "UPDATE seats SET available = FALSE WHERE id = ?";
        String updateUserFlightQuery = "UPDATE user_flights SET seat_choice = ? WHERE user_id = ? AND flight_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            int oldSeatId = -1;
            int newSeatId = -1;

            String findOldSeatQuery = "SELECT s.id FROM seats s JOIN user_flights uf ON s.seat_number = uf.seat_choice AND s.flight_id = uf.flight_id WHERE uf.user_id = ? AND uf.flight_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(findOldSeatQuery)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, flightId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    oldSeatId = rs.getInt("id");
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(findSeatQuery)) {
                stmt.setInt(1, flightId);
                stmt.setString(2, newSeatNumber);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    newSeatId = rs.getInt("id");
                } else {
                    return false;
                }
            }

            conn.setAutoCommit(false);

            if (oldSeatId != -1) {
                try (PreparedStatement stmt = conn.prepareStatement(freeOldSeatQuery)) {
                    stmt.setInt(1, oldSeatId);
                    stmt.executeUpdate();
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(assignNewSeatQuery)) {
                stmt.setInt(1, newSeatId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(updateUserFlightQuery)) {
                stmt.setString(1, newSeatNumber);
                stmt.setInt(2, userId);
                stmt.setInt(3, flightId);
                stmt.executeUpdate();
            }

            conn.commit();
            conn.setAutoCommit(true);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
