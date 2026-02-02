package de.emil.pr3.databases;

import de.emil.pr3.ShiftAssignment;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ScheduleDatabase extends Databank {

    public ScheduleDatabase() throws SQLException {
        super();
    }

    ScheduleDatabase(String url)  throws SQLException {
        super(url);
    }

    public void setupDatabase() {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            DSLContext create = DSL.using(connection, SQLDialect.SQLITE);


            create.execute("""
            CREATE TABLE IF NOT EXISTS SAVED_SCHEDULES (
                WEEK_ID TEXT,
                SHIFT_IDENTIFIER TEXT,
                EMPLOYEE_ID INTEGER,
                PRIMARY KEY (WEEK_ID, SHIFT_IDENTIFIER, EMPLOYEE_ID),
                FOREIGN KEY(EMPLOYEE_ID) REFERENCES EMPLOYEE(ID)
            );
        """);


        } catch (SQLException e) {
            System.err.println("Database setup failed: " + e.getMessage());
        }
    }

    public void saveGeneratedSchedule(String weekId, List<ShiftAssignment> schedule) {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            DSLContext createLocal = DSL.using(connection, SQLDialect.SQLITE);
            for (ShiftAssignment sa : schedule) {
                for (de.emil.pr3.jooq.tables.pojos.Employee emp : sa.employees()) {
                    createLocal.insertInto(DSL.table("SAVED_SCHEDULES"))
                            .columns(DSL.field("WEEK_ID"), DSL.field("SHIFT_IDENTIFIER"), DSL.field("EMPLOYEE_ID"))
                            .values(weekId, sa.shift().identifier(), emp.getId())
                            .execute();
                }
            }
            System.out.println("Success: " + weekId + " is now saved in the databank.");
        } catch (SQLException e) {
            System.err.println("Save Error: " + e.getMessage());
        }
    }

    @Override
    void validateId(int id) throws IllegalArgumentException {

    }
}
