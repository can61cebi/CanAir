import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseFetch {
    private static final String url = "jdbc:mariadb://localhost:3306/CANAIR";
    private static final String user = "root";
    private static final String password = "3126353Can@";

    public static String fetchUserNameById(int userId) {
        String query = "SELECT name FROM users WHERE id = ?";
        String userName = "Kullanıcı Bulunamadı";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userName = rs.getString("name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            userName = "Hata: " + e.getMessage();
        }

        return userName;
    }
}
