import java.sql.*;

public class PlayerDAO {

    public void viewPlayers() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM PLAYERS");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("player_id") + " | " +
                    rs.getString("username") + " | " +
                    rs.getInt("player_level") + " | " +
                    rs.getString("country")
                );
            }

            con.close();
        } catch (Exception e) {
            System.out.println("Error fetching players");
        }
    }

    // 🔥 YOUR METHOD MUST BE INSIDE THIS CLASS
    public void addPlayer(int id, String username, int level, String country) {

        Connection con = null;

        try {
            con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Connection error");
                return;
            }

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO PLAYERS VALUES (?, ?, ?, ?)"
            );

            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setInt(3, level);
            ps.setString(4, country);

            ps.executeUpdate();
            System.out.println("Player Added!");

        } catch (Exception e) {
            System.out.println("Error adding player");
        }
    }
}