package de.emil.pr3;

import static de.emil.pr3.jooq.tables.Employee.EMPLOYEE;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class EmployeeDatabank {
    private final String url = "jdbc:sqlite:db/work_schedule_manager.db";
    DSLContext create;

    public void start() {
        try (Connection connection = DriverManager.getConnection(url)) {
            create = DSL.using(connection,  SQLDialect.SQLITE);

            Result<Record> result = create.select().from(EMPLOYEE).fetch();

            List<Employee> employees = new ArrayList<>();
            for (Record r : result) {
                Employee employee = new Employee(
                        r.get(EMPLOYEE.ID, Integer.class),
                        r.get(EMPLOYEE.FIRST_NAME, String.class),
                        r.get(EMPLOYEE.LAST_NAME, String.class)
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void createNewEmployee(String firstName, String lastName) throws IllegalArgumentException{
        try (Connection connection = DriverManager.getConnection(url)) {
            create = DSL.using(connection,  SQLDialect.SQLITE);

            Result<Record> result = create.select().from(EMPLOYEE).fetch();

            if (Objects.isNull(firstName) || firstName.trim().isEmpty()) {
                throw new IllegalArgumentException("First name cannot be blank");
            }
            if (Objects.isNull(lastName) || lastName.trim().isEmpty()) {
                throw new IllegalArgumentException("Last name cannot be blank");
            }
            create.insertInto(DSL.table((Collection<?>) EMPLOYEE), //error?
                            DSL.field(EMPLOYEE.FIRST_NAME),
                            DSL.field(EMPLOYEE.LAST_NAME))
                    .values(firstName, lastName)
                    .execute();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}