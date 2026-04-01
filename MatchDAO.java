import java.sql.*;

public class MatchDAO {

    public void viewMatches() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            String query = "SELECT m.match_id, p.username, g.game_name, m.score, m.match_date " +
                           "FROM MATCHES m " +
                           "JOIN PLAYERS p ON m.player_id = p.player_id " +
                           "JOIN GAMES g ON m.game_id = g.game_id";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println(
                    rs.getInt("match_id") + " | " +
                    rs.getString("username") + " | " +
                    rs.getString("game_name") + " | " +
                    rs.getInt("score") + " | " +
                    rs.getDate("match_date")
                );
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMatch(int id, int playerId, int gameId, int score, String date) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO MATCHES VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'))"
            );

            ps.setInt(1, id);
            ps.setInt(2, playerId);
            ps.setInt(3, gameId);
            ps.setInt(4, score);
            ps.setString(5, date);

            ps.executeUpdate();
            System.out.println("Match Added!");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public void updateMatch(int id, int pid, int gid, int score, String date) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
            "UPDATE MATCHES SET player_id=?, game_id=?, score=?, match_date=TO_DATE(?, 'YYYY-MM-DD') WHERE match_id=?"
        );
        ps.setInt(1, pid);
        ps.setInt(2, gid);
        ps.setInt(3, score);
        ps.setString(4, date);
        ps.setInt(5, id);
        ps.executeUpdate();
    }

    public void deleteMatch(int id) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
            "DELETE FROM MATCHES WHERE match_id=?"
        );
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}