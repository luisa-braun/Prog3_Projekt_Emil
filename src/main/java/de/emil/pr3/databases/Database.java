package de.emil.pr3.databases;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Database implements AutoCloseable {
    public static final String DATABASE_URL = "jdbc:sqlite:db/work_schedule_manager.db";
    public static final String TEST_URL = "jdbc:sqlite::memory:";
    final String connectionUrl;
    final Connection connection;
    final DSLContext create;

    public Database() throws SQLException {
        this(DATABASE_URL);
    }

    Database(String url) throws SQLException {
        if (!url.equals(DATABASE_URL) && !url.equals(TEST_URL)){
            System.err.println("[WARNING] The Databank url " + url + " was not recognised by the System.");
        }
        this.connectionUrl = url;
        this.connection = DriverManager.getConnection(this.connectionUrl);
        Settings settings = new Settings()
                .withExecuteLogging(false)
                .withRenderSchema(false);
        this.create = DSL.using(this.connection, SQLDialect.SQLITE, settings);
        this.create.execute("PRAGMA foreign_keys = ON");
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new SQLException("error while closing database connection", e);
            }
        }
    }
}
