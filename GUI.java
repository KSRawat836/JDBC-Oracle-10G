import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class GUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    PlayerDAO pdao = new PlayerDAO();
    GameDAO gdao = new GameDAO();
    MatchDAO mdao = new MatchDAO();

    public GUI() {

        setTitle("🎮 Game Management System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // DARK THEME
        UIManager.put("control", new Color(40, 40, 40));
        UIManager.put("info", new Color(40, 40, 40));
        UIManager.put("nimbusBase", new Color(18, 30, 49));
        UIManager.put("text", Color.WHITE);

        // HEADER
        JLabel title = new JLabel("GAME MANAGEMENT SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(30, 30, 30));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(title, BorderLayout.NORTH);

        // TABLE
        model = new DefaultTableModel();
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // BUTTON PANEL
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3, 10, 10));
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnPlayers = createButton("View Players");
        JButton btnGames = createButton("View Games");
        JButton btnMatches = createButton("View Matches");
        JButton btnAddPlayer = createButton("Add Player");
        JButton btnAddGame = createButton("Add Game");
        JButton btnAddMatch = createButton("Add Match");

        panel.add(btnPlayers);
        panel.add(btnGames);
        panel.add(btnMatches);
        panel.add(btnAddPlayer);
        panel.add(btnAddGame);
        panel.add(btnAddMatch);

        add(panel, BorderLayout.SOUTH);

        // ACTIONS
        btnPlayers.addActionListener(e -> loadPlayers());
        btnGames.addActionListener(e -> loadGames());
        btnMatches.addActionListener(e -> loadMatches());

        btnAddPlayer.addActionListener(e -> addPlayer());
        btnAddGame.addActionListener(e -> addGame());
        btnAddMatch.addActionListener(e -> addMatch());

        setVisible(true);
    }

    // Button Styling
    JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(60, 63, 65));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        return btn;
    }

    // ================= LOAD DATA =================

    void loadPlayers() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PLAYERS");

            model.setRowCount(0);
            model.setColumnIdentifiers(new String[]{"ID", "Username", "Level", "Country"});

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4)
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading players");
        }
    }

    void loadGames() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM GAMES");

            model.setRowCount(0);
            model.setColumnIdentifiers(new String[]{"ID", "Name", "Genre"});

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading games");
        }
    }

    void loadMatches() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            String q = "SELECT m.match_id, p.username, g.game_name, m.score, m.match_date " +
                    "FROM MATCHES m " +
                    "JOIN PLAYERS p ON m.player_id = p.player_id " +
                    "JOIN GAMES g ON m.game_id = g.game_id";

            ResultSet rs = st.executeQuery(q);

            model.setRowCount(0);
            model.setColumnIdentifiers(new String[]{"Match ID", "Player", "Game", "Score", "Date"});

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDate(5)
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading matches");
        }
    }

    // ================= ADD DATA =================

    void addPlayer() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter ID"));
            String name = JOptionPane.showInputDialog("Enter Username");
            int level = Integer.parseInt(JOptionPane.showInputDialog("Enter Level"));
            String country = JOptionPane.showInputDialog("Enter Country");

            pdao.addPlayer(id, name, level, country);
            loadPlayers();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
    }

    void addGame() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Game ID"));
            String name = JOptionPane.showInputDialog("Enter Game Name");
            String genre = JOptionPane.showInputDialog("Enter Genre");

            gdao.addGame(id, name, genre);
            loadGames();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
    }

    void addMatch() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Match ID"));
            int pid = Integer.parseInt(JOptionPane.showInputDialog("Enter Player ID"));
            int gid = Integer.parseInt(JOptionPane.showInputDialog("Enter Game ID"));
            int score = Integer.parseInt(JOptionPane.showInputDialog("Enter Score"));
            String date = JOptionPane.showInputDialog("Enter Date (YYYY-MM-DD)");

            mdao.addMatch(id, pid, gid, score, date);
            loadMatches();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}