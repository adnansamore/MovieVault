package app;

import java.sql.*;
import java.util.*;

public class Movie {
    private Connection conn;

    public Movie(Connection conn) {
        this.conn = conn;
    }

    public void addMovie(String title, int length, String director, List<String> actors) throws SQLException {
        // Check if movie with same title and director already exists
        PreparedStatement check = conn.prepareStatement(
                "SELECT COUNT(*) FROM movie WHERE title=? AND director=?");
        check.setString(1, title);
        check.setString(2, director);
        ResultSet rsm = check.executeQuery();
        rsm.next();
        if (rsm.getInt(1) > 0) {
            System.out
                    .println("- Movie with title \"" + title + "\" and director \"" + director + "\" already exists!");
            return;
        }

        // Inserting movie details
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO movie(title,director,length) VALUES(?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, title);
        ps.setString(2, director);
        ps.setInt(3, length);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        long movieId = rs.getLong(1);

        // Inserting actors
        for (String actor : actors) {
            PreparedStatement pa = conn.prepareStatement(
                    "INSERT INTO actor(movie_id, actor) VALUES(?,?)");
            pa.setLong(1, movieId);
            pa.setString(2, actor);
            pa.executeUpdate();
        }

        System.out.println("Movie \"" + title + "\" added successfully!");
    }

    public void listMovies(boolean verbose, String titleRegex, String directorRegex, String actorRegex, String orderBy)
            throws SQLException {
        String order = "ORDER BY title";
        if ("ASC".equals(orderBy))
            order = "ORDER BY length ASC, title";
        if ("DESC".equals(orderBy))
            order = "ORDER BY length DESC, title";

        ResultSet rs = conn.createStatement()
                .executeQuery("SELECT * FROM movie " + order);

        while (rs.next()) {
            String title = rs.getString("title");
            String director = rs.getString("director");
            long id = rs.getLong("id");
            int len = rs.getInt("length");

            if (titleRegex != null && !title.matches(titleRegex))
                continue;
            if (directorRegex != null && !director.matches(directorRegex))
                continue;
            if (actorRegex != null && !hasActor(id, actorRegex))
                continue;

            System.out.println(title + " by " + director + ", " + Utils.secondsToHHMMSS(len));
            if (verbose) {
                printActors(id);
            }
        }
    }

    // helper to list movies having actors (-a) flag
    private boolean hasActor(long movieId, String regex) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "SELECT actor FROM actor WHERE movie_id=?");
        ps.setLong(1, movieId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            if (rs.getString(1).matches(regex))
                return true;
        }
        return false;
    }


    private void printActors(long movieId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "SELECT actor FROM actor WHERE movie_id=?");
        ps.setLong(1, movieId);
        ResultSet rs = ps.executeQuery();

        System.out.println("\tStarring:");
        while (rs.next()) {
            System.out.println("\t\t- " + rs.getString(1));
        }
    }
}
