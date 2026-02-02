package de.emil.pr3.databanks;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Databank implements AutoCloseable {
    public static final String DATABANK_URL = "jdbc:sqlite:db/work_schedule_manager.db";
    public static final String TEST_URL = "jdbc:sqlite::memory:";
    final String connectionUrl;
    final Connection connection;
    final DSLContext create;

    public Databank() throws SQLException {
        this(DATABANK_URL);
    }

    Databank(String url) throws SQLException {
        if (!url.equals(DATABANK_URL) && !url.equals(TEST_URL)){
            System.err.println("[WARNING] The Databank url " + url + " was not recognised by the System.");
        }
        this.connectionUrl = url;
        this.connection = DriverManager.getConnection(this.connectionUrl);
        Settings settings = new Settings()
                .withExecuteLogging(false)
                .withRenderSchema(false);
        this.create = DSL.using(this.connection, SQLDialect.SQLITE, settings);
    }

    abstract void validateId(int id) throws IllegalArgumentException;

    @Override
    public void close() throws Exception{
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
