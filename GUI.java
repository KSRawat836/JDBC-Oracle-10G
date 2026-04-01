import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GUI extends JFrame {

    JTable table;
    DefaultTableModel model;

    PlayerDAO pdao = new PlayerDAO();
    GameDAO gdao = new GameDAO();
    MatchDAO mdao = new MatchDAO();

    String currentView = "PLAYERS";

    public GUI() {

        setTitle("Game Management System");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(9, 1, 10, 10));
        sidebar.setBackground(new Color(20, 20, 20));
        sidebar.setPreferredSize(new Dimension(180, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton btnPlayers = createButton("Players");
        JButton btnGames = createButton("Games");
        JButton btnMatches = createButton("Matches");
        JButton btnAddPlayer = createButton("Add Player");
        JButton btnAddGame = createButton("Add Game");
        JButton btnAddMatch = createButton("Add Match");
        JButton btnUpdate = createButton("Update");
        JButton btnDelete = createButton("Delete");

        sidebar.add(btnPlayers);
        sidebar.add(btnGames);
        sidebar.add(btnMatches);
        sidebar.add(btnAddPlayer);
        sidebar.add(btnAddGame);
        sidebar.add(btnAddMatch);
        sidebar.add(btnUpdate);
        sidebar.add(btnDelete);

        add(sidebar, BorderLayout.WEST);

        // ===== HEADER =====
        JLabel title = new JLabel("Game Management Dashboard", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setOpaque(true);
        title.setBackground(new Color(30, 30, 30));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel();
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(70, 70, 70));
        table.setBackground(new Color(45, 45, 45));
        table.setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(90, 130, 200));

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(new Color(45, 45, 45));
        add(scroll, BorderLayout.CENTER);

        // ===== ACTIONS =====
        btnPlayers.addActionListener(e -> loadPlayers());
        btnGames.addActionListener(e -> loadGames());
        btnMatches.addActionListener(e -> loadMatches());

        btnAddPlayer.addActionListener(e -> addPlayer());
        btnAddGame.addActionListener(e -> addGame());
        btnAddMatch.addActionListener(e -> addMatch());

        btnUpdate.addActionListener(e -> updateData());
        btnDelete.addActionListener(e -> deleteData());

        setVisible(true);
    }

    JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(50, 50, 50));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(70, 130, 180));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(50, 50, 50));
            }
        });

        return btn;
    }

    // ===== LOAD =====
    void loadPlayers() {
        currentView = "PLAYERS";
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM PLAYERS");

            model.setRowCount(0);
            model.setColumnIdentifiers(new String[]{"ID", "Username", "Level", "Country"});

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt(1), rs.getString(2),
                        rs.getInt(3), rs.getString(4)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading players");
        }
    }

    void loadGames() {
        currentView = "GAMES";
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM GAMES");

            model.setRowCount(0);
            model.setColumnIdentifiers(new String[]{"ID", "Name", "Genre"});

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt(1), rs.getString(2), rs.getString(3)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading games");
        }
    }

    void loadMatches() {
        currentView = "MATCHES";
        try {
            Connection con = DBConnection.getConnection();

            String q = "SELECT m.match_id, p.username, g.game_name, m.score, m.match_date " +
                    "FROM MATCHES m " +
                    "JOIN PLAYERS p ON m.player_id = p.player_id " +
                    "JOIN GAMES g ON m.game_id = g.game_id";

            ResultSet rs = con.createStatement().executeQuery(q);

            model.setRowCount(0);
            model.setColumnIdentifiers(new String[]{"Match ID", "Player", "Game", "Score", "Date"});

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4), rs.getDate(5)
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading matches");
        }
    }

    // ===== ADD =====
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
            String date = JOptionPane.showInputDialog("Enter Date YYYY-MM-DD");

            mdao.addMatch(id, pid, gid, score, date);
            loadMatches();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
    }

    // ===== UPDATE =====
    void updateData() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first");
            return;
        }

        try {
            if (currentView.equals("PLAYERS")) {
                int id = (int) model.getValueAt(row, 0);
                String name = JOptionPane.showInputDialog("Username", model.getValueAt(row, 1));
                int level = Integer.parseInt(JOptionPane.showInputDialog("Level", model.getValueAt(row, 2)));
                String country = JOptionPane.showInputDialog("Country", model.getValueAt(row, 3));

                pdao.updatePlayer(id, name, level, country);
                loadPlayers();
            }

            else if (currentView.equals("GAMES")) {
                int id = (int) model.getValueAt(row, 0);
                String name = JOptionPane.showInputDialog("Game Name", model.getValueAt(row, 1));
                String genre = JOptionPane.showInputDialog("Genre", model.getValueAt(row, 2));

                gdao.updateGame(id, name, genre);
                loadGames();
            }

            else if (currentView.equals("MATCHES")) {
                int id = (int) model.getValueAt(row, 0);

                int pid = Integer.parseInt(JOptionPane.showInputDialog("Player ID"));
                int gid = Integer.parseInt(JOptionPane.showInputDialog("Game ID"));
                int score = Integer.parseInt(JOptionPane.showInputDialog("Score"));
                String date = JOptionPane.showInputDialog("Date YYYY-MM-DD");

                mdao.updateMatch(id, pid, gid, score, date);
                loadMatches();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update failed");
        }
    }

    // ===== DELETE =====
    void deleteData() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row first");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?");

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int id = (int) model.getValueAt(row, 0);

            if (currentView.equals("PLAYERS")) {
                pdao.deletePlayer(id);
                loadPlayers();
            }

            else if (currentView.equals("GAMES")) {
                gdao.deleteGame(id);
                loadGames();
            }

            else if (currentView.equals("MATCHES")) {
                mdao.deleteMatch(id);
                loadMatches();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Delete failed");
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}

