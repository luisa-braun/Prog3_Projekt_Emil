package de.emil.pr3;

import static de.emil.pr3.jooq.tables.Employee.EMPLOYEE;
import de.emil.pr3.jooq.tables.pojos.Employee;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class EmployeeDatabank implements EmployeeDatabankHelper {
    public static final String CONNECTION_URL = "jdbc:sqlite:db/work_schedule_manager.db";
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
        Employee createdEmployee = null;

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            create = DSL.using(connection, SQLDialect.SQLITE);

            createdEmployee = create.insertInto(EMPLOYEE)
                    .columns(EMPLOYEE.FIRST_NAME,
                            EMPLOYEE.LAST_NAME)
                    .values(firstName, lastName)
                    .returning(EMPLOYEE.ID,
                            EMPLOYEE.FIRST_NAME,
                            EMPLOYEE.LAST_NAME,
                            EMPLOYEE.WORK_HOURS_CAPACITY)
                    .fetchOneInto(Employee.class);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return createdEmployee;
    }

    /**
     *  @param id of the employee that shall be deleted.
     *  @throws IllegalArgumentException if id is invalid or does not exist.
     *  @return the deleted Employee.
     */
    public Employee deleteEmployeeById(int id) throws IllegalArgumentException {
        if (!idIsValidAndExists(id)){
            throw new IllegalArgumentException("ID is invalid or does not exist");
        }
        Employee deletedEmployee = null;

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            create = DSL.using(connection, SQLDialect.SQLITE);

            deletedEmployee = create.deleteFrom(EMPLOYEE)
                    .where(EMPLOYEE.ID.eq(id))
                    .returning(EMPLOYEE.ID,
                            EMPLOYEE.FIRST_NAME,
                            EMPLOYEE.LAST_NAME,
                            EMPLOYEE.WORK_HOURS_CAPACITY)
                    .fetchOneInto(Employee.class);

            if (Objects.isNull(deletedEmployee)){
                throw new IllegalArgumentException("Employee with ID " + id + " does not exist");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return deletedEmployee;
    }

    /**
     *  @return a List of all Employees ordered ascending by their id.
     */
    public List<Employee> getListOfEmployees() {
        List<Employee> listOfEmployees = null;

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            create = DSL.using(connection, SQLDialect.SQLITE);

            listOfEmployees = create.select(EMPLOYEE.ID,
                            EMPLOYEE.FIRST_NAME,
                            EMPLOYEE.LAST_NAME)
                    .from(EMPLOYEE)
                    .orderBy(EMPLOYEE.ID.asc())
                    .fetchInto(Employee.class);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return listOfEmployees;
    }

    /**
     *  @param id of the employee of which the workHoursCapacity shall be changed.
     *  @param workHoursCapacity it shall be changed to.
     *  @throws IllegalArgumentException if id is invalid or does not exist.
     *  @throws IllegalArgumentException if workHoursCapacity does not lie between 0 and 60.
     *  @return the newly created Employee.
     */
    public Employee updateWorkHoursCapacity(int id, int workHoursCapacity) {
        if (!idIsValidAndExists(id)){
            throw new IllegalArgumentException("ID is invalid or does not exist");
        }
        if (workHoursCapacity < 0) {
            throw new IllegalArgumentException("Work hours capacity cannot be negative");
        }
        if (workHoursCapacity > 60) {
            throw new IllegalArgumentException("Work hours capacity cannot be more than 60 hours");
        }
        Employee changedEmployee = null;

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            create = DSL.using(connection, SQLDialect.SQLITE);

            changedEmployee = create
                    .update(EMPLOYEE)
                    .set(EMPLOYEE.WORK_HOURS_CAPACITY,
                            workHoursCapacity)
                    .where(EMPLOYEE.ID.eq(id))
                    .returning(EMPLOYEE.ID,
                            EMPLOYEE.FIRST_NAME,
                            EMPLOYEE.LAST_NAME,
                            EMPLOYEE.WORK_HOURS_CAPACITY)
                    .fetchOneInto(Employee.class);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return changedEmployee;
    }

    /**
     *  @param id that shall be checked.
     *  @return true if the id is valid and exists, false otherwise.
     */
    public boolean idIsValidAndExists(int id) {
        boolean result = false;

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            create = DSL.using(connection, SQLDialect.SQLITE);

            if (id > 0) {
                result = create.fetchExists(
                        create.selectOne()
                                .from(EMPLOYEE)
                                .where(EMPLOYEE.ID.eq(id)));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}