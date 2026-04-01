import java.sql.*;

public class GameDAO {

    public void viewGames() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM GAMES");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("game_id") + " | " +
                    rs.getString("game_name") + " | " +
                    rs.getString("genre")
                );
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addGame(int id, String name, String genre) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO GAMES VALUES (?, ?, ?)"
            );

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, genre);

            ps.executeUpdate();
            System.out.println("Game Added!");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}