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

    // YOUR METHOD MUST BE INSIDE THIS CLASS
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
    public void updatePlayer(int id, String name, int level, String country) throws Exception {
    Connection con = DBConnection.getConnection();
    PreparedStatement ps = con.prepareStatement(
        "UPDATE PLAYERS SET username=?, player_level=?, country=? WHERE player_id=?"
    );
    ps.setString(1, name);
    ps.setInt(2, level);
    ps.setString(3, country);
    ps.setInt(4, id);
    ps.executeUpdate();
}

public void deletePlayer(int id) throws Exception {
    Connection con = DBConnection.getConnection();
    PreparedStatement ps = con.prepareStatement(
        "DELETE FROM PLAYERS WHERE player_id=?"
    );
    ps.setInt(1, id);
    ps.executeUpdate();
}
}