package app;

import java.sql.*;

public class Person {
    private Connection conn;

    public Person(Connection conn) {
        this.conn = conn;
    }

    // add persons into person table
    public void addPerson(String name, String nationality) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO person VALUES (?, ?)");
        ps.setString(1, name);
        ps.setString(2, nationality);
        ps.executeUpdate();
    }

    // checks if person exists in database or not
    public boolean exists(String name) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM person WHERE name=?");
        ps.setString(1, name);
        return ps.executeQuery().next();
    }

    // deleting person from database while checking constraints.
    public void deletePerson(String name) throws SQLException {
        // Checks if director
        PreparedStatement check = conn.prepareStatement(
                "SELECT 1 FROM movie WHERE director=?");
        check.setString(1, name);
        if (check.executeQuery().next()) {
            throw new RuntimeException("Cannot delete: " + name + " is a director");
        }

        // Delete from actors table
        PreparedStatement delActor = conn.prepareStatement(
                "DELETE FROM actor WHERE actor=?");
        delActor.setString(1, name);
        delActor.executeUpdate();

        // Delete from person table
        PreparedStatement del = conn.prepareStatement(
                "DELETE FROM person WHERE name=?");
        del.setString(1, name);
        if (del.executeUpdate() == 0)
            throw new RuntimeException("Person does not exist");
    }
}
