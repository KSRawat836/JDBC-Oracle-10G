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
public void updateGame(int id, String name, String genre) throws Exception {
    Connection con = DBConnection.getConnection();

    PreparedStatement ps = con.prepareStatement(
        "UPDATE GAMES SET game_name=?, genre=? WHERE game_id=?"
    );

    ps.setString(1, name);
    ps.setString(2, genre);
    ps.setInt(3, id);

    ps.executeUpdate();
}

public void deleteGame(int id) throws Exception {
    Connection con = DBConnection.getConnection();

    PreparedStatement ps = con.prepareStatement(
        "DELETE FROM GAMES WHERE game_id=?"
    );

    ps.setInt(1, id);

    ps.executeUpdate();
}
}