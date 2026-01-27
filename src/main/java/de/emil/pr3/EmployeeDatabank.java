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
import java.util.List;
import java.util.Objects;

public class EmployeeDatabank {
    private final String connectionUrl = "jdbc:sqlite:db/work_schedule_manager.db";
    DSLContext create;

    /**
     *  @param firstName of the employee that shall be created.
     *  @param lastName of the employee that shall be created.
     *  @throws IllegalArgumentException if firstName or lastName is null or empty.
     *  @return the newly created Employee.
     */
    public Employee createNewEmployee(String firstName, String lastName) throws IllegalArgumentException {
        if (Objects.isNull(firstName) || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be blank");
        }
        if (Objects.isNull(lastName) || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be blank");
        }

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            create = DSL.using(connection, SQLDialect.SQLITE);

            return create.insertInto(EMPLOYEE)
                    .columns(EMPLOYEE.FIRST_NAME, EMPLOYEE.LAST_NAME)
                    .values(firstName, lastName)
                    .returning(EMPLOYEE.ID, EMPLOYEE.FIRST_NAME, EMPLOYEE.LAST_NAME)
                    .fetchOneInto(Employee.class);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     *  @param id of the employee that shall be deleted.
     *  @throws IllegalArgumentException if Employee with that id does not exist.
     *  @return the deleted Employee.
     */
    public Employee deleteEmployeeById(int id) throws IllegalArgumentException {
        if (idIsValidAndExists(id)){
            throw new IllegalArgumentException("ID is invalid or does not exist");
        }
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            create = DSL.using(connection, SQLDialect.SQLITE);

            Employee deletedEmployee = create.deleteFrom(EMPLOYEE)
                    .where(EMPLOYEE.ID.eq(id))
                    .returning(EMPLOYEE.ID, EMPLOYEE.FIRST_NAME, EMPLOYEE.LAST_NAME)
                    .fetchOneInto(Employee.class);

            if (Objects.isNull(deletedEmployee)){
                throw new IllegalArgumentException("Employee with ID " + id + " does not exist");
            }
            return deletedEmployee;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     *  @return a List of all Employees ordered ascending by their id.
     */
    public List<Employee> getListOfEmployees() {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            create = DSL.using(connection, SQLDialect.SQLITE);

            return create.select(EMPLOYEE.ID, EMPLOYEE.FIRST_NAME, EMPLOYEE.LAST_NAME)
                    .from(EMPLOYEE)
                    .orderBy(EMPLOYEE.ID.asc())
                    .fetchInto(Employee.class);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     *  @param id that shall be checked.
     *  @return true if the id is valid and exists, false otherwise.
     */
    public boolean idIsValidAndExists(int id) {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            create = DSL.using(connection, SQLDialect.SQLITE);

            if (id <= 0) {
                return false;
            }
            return create.fetchExists(
                    create.selectOne()
                            .from(EMPLOYEE)
                            .where(EMPLOYEE.ID.eq(id)));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /*
    public void remanence (String firstName, String lastName) throws IllegalArgumentException {
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


        create.insertInto(DSL.table(EMPLOYEE), //error?
                            DSL.field(EMPLOYEE.FIRST_NAME),
                            DSL.field(EMPLOYEE.LAST_NAME))
                    .values(firstName, lastName)
                    .execute();


        create.insertInto(EMPLOYEE)
                    .columns(EMPLOYEE.FIRST_NAME, EMPLOYEE.LAST_NAME)
                    .values(firstName, lastName)
                    .returning(EMPLOYEE.ID)
                    .fetchOneInto(Employee.class);

        if (workHoursCapacity < 0) {
            throw new IllegalArgumentException("Work hours capacity cannot be negative");
        }
        if (workHoursCapacity > 60) {
            throw new IllegalArgumentException("Work hours capacity cannot be more than 60 hours");
        }

    }*/
}