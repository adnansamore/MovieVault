package app;

import java.sql.Connection;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Connection conn;
        try {
            conn = Database.getConnection();
            System.out.println("Database connection successful.");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            System.exit(1);
            return;
        }

        Scanner sc = new Scanner(System.in);
        Person ps = new Person(conn);
        Movie ms = new Movie(conn);

        while (true) {
            System.out.print("> ");
            String line = sc.nextLine().trim();

            try {
                if (line.startsWith("l")) {
                    boolean verbose = line.contains("-v");
                    String titleRegex = extract(line, "-t");
                    String directorRegex = extract(line, "-d");
                    String actorRegex = extract(line, "-a");
                    String orderBy = null;
                    if (line.contains("-la"))
                        orderBy = "ASC";
                    if (line.contains("-ld"))
                        orderBy = "DESC";
                    if (line.contains("-la") && line.contains("-ld"))
                        throw new RuntimeException("Cannot use -la and -ld together");

                    ms.listMovies(verbose, titleRegex, directorRegex, actorRegex, orderBy);
                }

                // command to add person
                else if (line.equals("a -p")) {
                    String name = "";
                    while (true) {
                        System.out.print("Name: ");
                        name = sc.nextLine();
                        if (name.equals("exit"))
                            break;
                        if (!name.isEmpty())
                            break;
                        System.out.println("- Name cannot be empty");
                    }
                    if (name.equals("exit"))
                        continue;

                    String nat = "";
                    while (true) {
                        System.out.print("Nationality: ");
                        nat = sc.nextLine();
                        if (nat.equals("exit"))
                            break;
                        if (!nat.isEmpty())
                            break;
                        System.out.println("- Nationality cannot be empty");
                    }
                    if (nat.equals("exit"))
                        continue;

                    ps.addPerson(name, nat);
                }

                // command to add movie
                else if (line.equals("a -m")) {
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    if (title.equals("exit"))
                        continue;

                    int len;
                    while (true) {
                        System.out.print("Length (hh:mm:ss): ");
                        try {
                            len = Utils.hhmmssToSeconds(sc.nextLine());
                            break;
                        } catch (Exception e) {
                            System.out.println("- Bad input format; Expected (hh:mm:ss), try again!");
                        }
                    }

                    String director;
                    while (true) {
                        System.out.print("Director: ");
                        director = sc.nextLine();
                        if (director.equals("exit"))
                            break;
                        if (ps.exists(director))
                            break;
                        System.out.println(String.format("-  We could not find Director: %s, try again!", director));

                    }
                    if (director.equals("exit"))
                        continue;

                    List<String> actors = new ArrayList<>();
                    while (true) {
                        System.out.print("Actor (or exit): ");
                        String a = sc.nextLine();
                        if (a.equals("exit"))
                            break;
                        if (!ps.exists(a))
                            System.out.println(String.format("-  We could not find actor: %s, try again!", a));

                        else
                            actors.add(a);
                    }

                    ms.addMovie(title, len, director, actors);
                }

                // command to delete person
                else if (line.startsWith("d -p")) {
                    String name = line.substring(4).trim();
                    if (name.isEmpty()) {
                        System.out.println("Usage: d -p <name>");
                    } else {
                        ps.deletePerson(name);
                        System.out.println("Deleted " + name);
                    }
                }

                else if (line.equals("help") || line.equals("h") || line.equals("?")) {
                    printHelp();
                }

                else if (line.startsWith("quit")) {
                    break;
                }

                else
                    System.out.println("Unknown command");

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static String extract(String line, String flag) {
        int i = line.indexOf(flag);
        if (i == -1)
            return null;
        int start = line.indexOf("\"", i);
        int end = line.indexOf("\"", start + 1);
        if (start == -1 || end == -1)
            throw new RuntimeException("Regex must be quoted");
        return line.substring(start + 1, end);
    }

    private static void printHelp() {
        System.out.println("""
                Available commands:
                  l [options]              List movies
                    -v                     Verbose (show actors)
                    -t "<regex>"           Filter by title (regex, quoted)
                    -d "<regex>"           Filter by director (regex, quoted)
                    -a "<regex>"           Filter by actor (regex, quoted)
                    -la                    Order by length ascending
                    -ld                    Order by length descending
                  a -p                     Add a person
                    During prompts enter 'exit' to cancel and return to menu
                  a -m                     Add a movie
                    During prompts enter 'exit' to cancel and return to menu
                  d -p <name>              Delete a person by name
                  help | h | ?             Show command manual
                  quit                     Exit the program
                """);
    }

}
