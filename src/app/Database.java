package app;

import java.sql.*;

public class Database {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Explicitly loading H2 driver
        Class.forName("org.h2.Driver");

        Connection conn = DriverManager.getConnection(
                "jdbc:h2:mem:moviedb;DB_CLOSE_DELAY=-1", "sa", "");
        createTables(conn);
        return conn;
    }

    private static void createTables(Connection conn) throws SQLException {
        Statement st = conn.createStatement();

        // People table
        st.execute("CREATE TABLE IF NOT EXISTS person (" +
                "name VARCHAR(255) PRIMARY KEY," +
                "nationality VARCHAR(100) NOT NULL)");

        // Movie table
        st.execute("CREATE TABLE IF NOT EXISTS movie (" +
                "id IDENTITY PRIMARY KEY," +
                "title VARCHAR(255) NOT NULL," +
                "director VARCHAR(255) NOT NULL," +
                "length INT NOT NULL," +
                "UNIQUE(title, director)," +
                "FOREIGN KEY(director) REFERENCES person(name))");

        // Actors table
        st.execute("CREATE TABLE IF NOT EXISTS actor (" +
                "movie_id BIGINT," +
                "actor VARCHAR(255)," +
                "PRIMARY KEY(movie_id, actor)," +
                "FOREIGN KEY(movie_id) REFERENCES movie(id)," +
                "FOREIGN KEY(actor) REFERENCES person(name))");
    }
}
