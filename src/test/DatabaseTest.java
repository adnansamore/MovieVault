package test;

import app.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseTest {

    private Connection conn;

    @Before
    public void setup() throws SQLException, ClassNotFoundException {
        conn = Database.getConnection();
    }

    @Test
    public void connection_notNull() {
        assertNotNull(conn);
    }

    @Test
    public void personTable_exists() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM person LIMIT 1");
        assertNotNull(rs);
    }

    @Test
    public void movieTable_exists() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM movie LIMIT 1");
        assertNotNull(rs);
    }

    @Test
    public void actorTable_exists() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM actor LIMIT 1");
        assertNotNull(rs);
    }
}
