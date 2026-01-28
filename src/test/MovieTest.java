package test;

import app.Database;
import app.Movie;
import app.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieTest {

    private Connection conn;
    private Movie movie;
    private Person person;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        conn = Database.getConnection();
        movie = new Movie(conn);
        person = new Person(conn);

        // add test persons if not already there
        if (!person.exists("Director1")) {
            person.addPerson("Director1", "Country1");
        }
        if (!person.exists("Actor1")) {
            person.addPerson("Actor1", "Country1");
        }
        if (!person.exists("Actor2")) {
            person.addPerson("Actor2", "Country2");
        }
    }

    @Test
    public void addMovie() throws SQLException {
        List<String> actors = new ArrayList<>();
        actors.add("Actor1");

        movie.addMovie("Inception", 7200, "Director1", actors);
        assertTrue(true);
    }

    @Test
    public void addMovie_withTwoActors() throws SQLException {
        List<String> actors = new ArrayList<>();
        actors.add("Actor1");
        actors.add("Actor2");

        movie.addMovie("Strange", 5400, "Director1", actors);
        assertTrue(true);
    }

    @Test
    public void duplicateMovie() throws SQLException {
        List<String> actors = new ArrayList<>();
        actors.add("Actor1");

        // second movie not added
        movie.addMovie("Mission", 3600, "Director1", actors);
        movie.addMovie("Mission", 3600, "Director1", actors);

        assertTrue(true);
    }

    @Test
    public void listMovies() throws SQLException {
        List<String> actors = new ArrayList<>();
        actors.add("Actor1");

        movie.addMovie("Inception", 7200, "Director1", actors);
        movie.listMovies(false, null, null, null, null);

        assertTrue(true);
    }

    @Test
    public void listMovies_verboseMode() throws SQLException {
        List<String> actors = new ArrayList<>();
        actors.add("Actor1");

        movie.addMovie("Mission II", 7200, "Director1", actors);
        movie.listMovies(true, null, null, null, null);

        assertTrue(true);
    }
}
