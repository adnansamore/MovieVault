package test;

import app.Database;
import app.Person;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    private Connection conn;
    private Person person;

    @Before
    public void setup() throws SQLException, ClassNotFoundException {
        conn = Database.getConnection();
        person = new Person(conn);
    }

    @Test
    public void addPerson() throws SQLException {
        person.addPerson("Aadie", "Indian");
        assertTrue(person.exists("Aadie"));
    }

    @Test
    public void addPerson_Exists() throws SQLException {
        person.addPerson("Balas", "Hungarian");
        assertTrue(person.exists("Balas"));
    }

    @Test
    public void checkPerson_notExist() throws SQLException {
        assertFalse(person.exists("Unknown"));
    }

    @Test
    public void deletePerson() throws SQLException {
        person.addPerson("fanni", "Hungarian");
        assertTrue(person.exists("fanni"));

        person.deletePerson("fanni");
        assertFalse(person.exists("fanni"));
    }
}
