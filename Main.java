import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PlayerDAO pdao = new PlayerDAO();
        GameDAO gdao = new GameDAO();
        MatchDAO mdao = new MatchDAO();

        while (true) {
            System.out.println("\n===== GAME MANAGEMENT SYSTEM =====");
            System.out.println("1. View Players");
            System.out.println("2. View Games");
            System.out.println("3. View Matches");
            System.out.println("4. Add Player");
            System.out.println("5. Add Game");
            System.out.println("6. Add Match");
            System.out.println("7. Exit");

            int ch = sc.nextInt();

            switch (ch) {

                case 1:
                    pdao.viewPlayers();
                    break;

                case 2:
                    gdao.viewGames();
                    break;

                case 3:
                    mdao.viewMatches();
                    break;

                case 4:
                    System.out.print("ID: ");
                    int pid = sc.nextInt();
                    System.out.print("Username: ");
                    String uname = sc.next();
                    System.out.print("Level: ");
                    int lvl = sc.nextInt();
                    System.out.print("Country: ");
                    String country = sc.next();

                    pdao.addPlayer(pid, uname, lvl, country);
                    break;

                case 5:
                    System.out.print("Game ID: ");
                    int gid = sc.nextInt();
                    System.out.print("Game Name: ");
                    String gname = sc.next();
                    System.out.print("Genre: ");
                    String genre = sc.next();

                    gdao.addGame(gid, gname, genre);
                    break;

                case 6:
                    System.out.print("Match ID: ");
                    int mid = sc.nextInt();
                    System.out.print("Player ID: ");
                    int mpid = sc.nextInt();
                    System.out.print("Game ID: ");
                    int mgid = sc.nextInt();
                    System.out.print("Score: ");
                    int score = sc.nextInt();
                    System.out.print("Date (YYYY-MM-DD): ");
                    String date = sc.next();

                    mdao.addMatch(mid, mpid, mgid, score, date);
                    break;

                case 7:
                    System.exit(0);
            }
        }
    }
}