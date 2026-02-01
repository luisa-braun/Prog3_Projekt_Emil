package de.emil.pr3;

import static de.emil.pr3.jooq.tables.Employee.EMPLOYEE;
import de.emil.pr3.jooq.tables.pojos.Employee;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class EmployeeDatabank implements EmployeeDatabankInterface {
    public static final String CONNECTION_URL = "jdbc:sqlite:db/work_schedule_manager.db";
    DSLContext create;

    /**
     * @param firstName of the employee that shall be created.
     * @param lastName  of the employee that shall be created.
     * @return the newly created Employee.
     * @throws IllegalArgumentException if firstName or lastName is null or empty.
     */
    public Employee createNewEmployee(String firstName, String lastName, int workHoursCapacity) throws IllegalArgumentException {
        validateName(firstName);
        validateName(lastName);
        validateWorkHoursCapacity(workHoursCapacity);
        Employee createdEmployee = null;

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            setupDslContext(connection);

            createdEmployee = create.insertInto(EMPLOYEE)
                    .columns(EMPLOYEE.FIRST_NAME,
                            EMPLOYEE.LAST_NAME,
                            EMPLOYEE.WORK_HOURS_CAPACITY)
                    .values(firstName.trim(), lastName.trim(), workHoursCapacity)
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
     * @param id of the employee that shall be deleted.
     * @return the deleted Employee.
     * @throws IllegalArgumentException if id is invalid or does not exist.
     */
    public Employee deleteEmployeeById(int id) throws IllegalArgumentException {
        validateId(id);
        Employee deletedEmployee = null;

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            setupDslContext(connection);

            deletedEmployee = create.deleteFrom(EMPLOYEE)
                    .where(EMPLOYEE.ID.eq(id))
                    .returning(EMPLOYEE.ID,
                            EMPLOYEE.FIRST_NAME,
                            EMPLOYEE.LAST_NAME,
                            EMPLOYEE.WORK_HOURS_CAPACITY)
                    .fetchOneInto(Employee.class);

            if (Objects.isNull(deletedEmployee)) {
                throw new IllegalArgumentException("Employee with ID " + id + " does not exist");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return deletedEmployee;
    }

    /**
     * @return a List of all Employees ordered ascending by their id.
     */
    public List<Employee> getListOfEmployees() {
        List<Employee> listOfEmployees = null;

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            setupDslContext(connection);

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
     * @param id of the employee of which the workHoursCapacity shall be changed.
     * @param workHoursCapacity it shall be changed to.
     * @return the newly created Employee.
     * @throws IllegalArgumentException if id is invalid or does not exist.
     * @throws IllegalArgumentException if workHoursCapacity does not lie between 0 and 60.
     */
    public Employee updateWorkHoursCapacity(int id, int workHoursCapacity) throws IllegalArgumentException {
        validateId(id);
        validateWorkHoursCapacity(workHoursCapacity);
        Employee changedEmployee = null;

        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            setupDslContext(connection);

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
     * @param id that shall be validated.
     * @throws IllegalArgumentException if the id is not valid does not exist.
     */
    public void validateId(int id) throws IllegalArgumentException {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
            setupDslContext(connection);

            if (id < 0) {
                throw new IllegalArgumentException("ID can not be negative");
            }
            boolean idExists = create.fetchExists(
                    create.selectOne()
                            .from(EMPLOYEE)
                            .where(EMPLOYEE.ID.eq(id)));

            if (!idExists) {
                throw new IllegalArgumentException("ID is invalid or does not exist");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param name that shall be validated.
     * @throws IllegalArgumentException if the name is null or empty.
     */
    private void validateName(String name) throws IllegalArgumentException {
        if (Objects.isNull(name) || name.trim().isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }

    /**
     * @param workHoursCapacity that shall be validated.
     * @throws IllegalArgumentException if the workHoursCapacity does not lie between 0 and 60.
     */
    private void validateWorkHoursCapacity(int workHoursCapacity) throws IllegalArgumentException {
        if (workHoursCapacity < 0) {
            throw new IllegalArgumentException("Work hours capacity cannot be negative");
        }
        if(workHoursCapacity >60) {
            throw new IllegalArgumentException("Work hours capacity cannot be more than 60 hours");
        }
    }

    private void setupDslContext(Connection connection) {
        Settings settings = new Settings()
                .withExecuteLogging(false)
                .withRenderSchema(false);
        create = DSL.using(connection, SQLDialect.SQLITE, settings);
    }

    public void setupDatabase() {
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
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
        try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
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
}
