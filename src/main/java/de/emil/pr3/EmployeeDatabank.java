package de.emil.pr3;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeDatabank {
    private final String url = "jdbc:sqlite:db/work_schedule_manager.db";
    DSLContext create;

    public EmployeeDatabank() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url)) {
            create = DSL.using(connection, SQLDialect.SQLITE);
            Result<Record> result = create.select().from("employee").fetch();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public void start() {
        try (Connection connection = DriverManager.getConnection(url)) {
            create = DSL.using(connection);

            Result<Record> result = create.select().from("employee").fetch();

            List<Employee> employees = new ArrayList<>();
            for (Record r : result) {
                Employee employee = new Employee(
                        r.get("id", Integer.class),
                        r.get("first_name", String.class),
                        r.get("last_name", String.class)
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createNewEmployee(String firstName, String lastName) throws IllegalArgumentException{
        if (Objects.isNull(firstName) || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be blank");
        }
        if (Objects.isNull(lastName) || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be blank");
        }
        create.insertInto(DSL.table("employee"),
                        DSL.field("first_name"),
                        DSL.field("last_name"))
                .values(firstName, lastName)
                .execute();
    }
}
