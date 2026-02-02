package de.emil.pr3.databases;

import de.emil.pr3.ShiftAssignment;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static de.emil.pr3.jooq.tables.Employee.EMPLOYEE;
import static de.emil.pr3.jooq.tables.Shift.SHIFT;
import static de.emil.pr3.jooq.tables.ShiftEmployee.SHIFT_EMPLOYEE;
import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.*;

public class ScheduleDatabase extends Database {
    private String[] weekDays = {"MONTAG", "DIENSTAG", "MITTWOCH", "DONNERSTAG", "FREITAG", "SAMSTAG", "SONNTAG"};

    public ScheduleDatabase() throws SQLException {
        this(DATABASE_URL);
    }

    ScheduleDatabase(String url)  throws SQLException {
        super(url);
        setupShiftTable();
        setupShiftEmployeeTable();
    }


/*
                ShiftAssignment(Shift shift, List< de.emil.pr3.jooq.tables.pojos.Employee > employees)
                Shift(String name, String identifier, String start, String end, int duration, int requiredStaff)
            shifts.add(new Shift("Morning Shift", "Mo-1", "8:00", "14:00", 6, 2));
            shifts.add(new Shift("Afternoon Shift", "Mo-2", "14:30", "18:30", 4, 1));
            shifts.add(new Shift("Morning Shift", "Di-1", "8:00", "14:00", 6, 2));
            shifts.add(new Shift("Afternoon Shift", "Di-2", "14:30", "18:30", 4, 1));
            shifts.add(new Shift("Morning Shift", "Mi-1", "8:00", "14:00", 6, 2));
            shifts.add(new Shift("Afternoon Shift", "Mi-2", "14:30", "18:30", 4, 1));
            shifts.add(new Shift("Morning Shift", "Do-1", "8:00", "14:00", 6, 2));
            shifts.add(new Shift("Afternoon Shift", "Do-2", "14:30", "18:30", 4, 1));
            shifts.add(new Shift("Morning Shift", "Fr-1", "8:00", "14:00", 6, 2));
            shifts.add(new Shift("Afternoon Shift", "Fr-2", "14:30", "18:30", 4, 1));
*/

        public void OLDsaveGeneratedSchedule(String weekId, List<ShiftAssignment> schedule) {
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
        private void setupShiftTable() {
            create.createTableIfNotExists(SHIFT)
                    .column(SHIFT.SHIFT_ID,
                            INTEGER.identity(true))
                    .column(SHIFT.WEEK_START_DATE,
                            DATE.nullable(false))
                    .column(SHIFT.WEEKDAY,
                            VARCHAR.nullable(false))
                    .column(SHIFT.SHIFT_NAME,
                            VARCHAR.nullable(false))
                    .constraints(check(SHIFT.WEEK_START_DATE.isNotNull()),
                            check(upper(SHIFT.WEEKDAY).in(weekDays)),
                            check(trim(SHIFT.SHIFT_NAME).ne("")))
                    .execute();//java.sql.Date.valueOf(String.valueOf(SHIFT.WEEK_START_DATE)) / (Date) SHIFT.WEEK_START_DATE
        }

        private void setupShiftEmployeeTable() {
            create.createTableIfNotExists(SHIFT_EMPLOYEE)
                    .column(SHIFT_EMPLOYEE.SHIFT_ID,
                            INTEGER.nullable(false))
                    .column(SHIFT_EMPLOYEE.EMPLOYEE_ID,
                            INTEGER.nullable(false))
                    .constraints(primaryKey(SHIFT_EMPLOYEE.SHIFT_ID,
                                    SHIFT_EMPLOYEE.EMPLOYEE_ID),
                            foreignKey(SHIFT_EMPLOYEE.SHIFT_ID)
                                    .references(SHIFT, SHIFT.SHIFT_ID)
                                    .onDeleteCascade(),
                            foreignKey(SHIFT_EMPLOYEE.EMPLOYEE_ID)
                                    .references(EMPLOYEE, EMPLOYEE.ID)
                                    .onDeleteCascade())
                    .execute();
        }
    }
