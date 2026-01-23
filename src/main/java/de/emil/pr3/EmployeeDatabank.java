package de.emil.pr3;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabank {
    private final String url = "jdbc:sqlite:db/work_schedule_manager.db";

    public void start() {
        try (Connection conn = DriverManager.getConnection(url)) {
            DSLContext create = DSL.using(conn);

            Result<Record> result = create.select().from("author").fetch();

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
}
