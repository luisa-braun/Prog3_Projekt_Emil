package de.emil.pr3.databases;

import static de.emil.pr3.jooq.tables.Employee.EMPLOYEE;
import static org.jooq.impl.DSL.*;
import static org.jooq.impl.SQLDataType.*;

import de.emil.pr3.jooq.tables.pojos.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class EmployeeDatabase extends Database implements EmployeeDatabaseInterface {

    public EmployeeDatabase() throws SQLException {
        this(DATABASE_URL);
    }

    EmployeeDatabase(String url) throws SQLException {
        super(url);
        setupEmployeeTable();
    }

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
        return create.insertInto(EMPLOYEE)
                    .columns(EMPLOYEE.FIRST_NAME,
                            EMPLOYEE.LAST_NAME,
                            EMPLOYEE.WORK_HOURS_CAPACITY)
                    .values(firstName.trim(), lastName.trim(), workHoursCapacity)
                    .returning(EMPLOYEE.ID,
                            EMPLOYEE.FIRST_NAME,
                            EMPLOYEE.LAST_NAME,
                            EMPLOYEE.WORK_HOURS_CAPACITY)
                    .fetchOneInto(Employee.class);
    }

    /**
     * @param id of the employee that shall be deleted.
     * @return the deleted Employee.
     * @throws IllegalArgumentException if id is invalid or does not exist.
     */
    public Employee deleteEmployeeById(int id) throws IllegalArgumentException {
        validateId(id);
        Employee deletedEmployee = create.deleteFrom(EMPLOYEE)
                    .where(EMPLOYEE.ID.eq(id))
                    .returning(EMPLOYEE.ID,
                            EMPLOYEE.FIRST_NAME,
                            EMPLOYEE.LAST_NAME,
                            EMPLOYEE.WORK_HOURS_CAPACITY)
                    .fetchOneInto(Employee.class);

        if (Objects.isNull(deletedEmployee)) {
            throw new IllegalArgumentException("Employee with ID " + id + " does not exist");
        }
        System.out.println("You deleted " + deletedEmployee);
        return deletedEmployee;
    }

    /**
     * @return a List of all Employees ordered ascending by their id.
     */
    public List<Employee> getListOfEmployees() {
        return create.select(EMPLOYEE.ID,
                            EMPLOYEE.FIRST_NAME,
                            EMPLOYEE.LAST_NAME,
                            EMPLOYEE.WORK_HOURS_CAPACITY)
                    .from(EMPLOYEE)
                    .orderBy(EMPLOYEE.ID.asc())
                    .fetchInto(Employee.class);
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
        return create
                    .update(EMPLOYEE)
                    .set(EMPLOYEE.WORK_HOURS_CAPACITY,
                            workHoursCapacity)
                    .where(EMPLOYEE.ID.eq(id))
                    .returning(EMPLOYEE.ID,
                            EMPLOYEE.FIRST_NAME,
                            EMPLOYEE.LAST_NAME,
                            EMPLOYEE.WORK_HOURS_CAPACITY)
                    .fetchOneInto(Employee.class);
    }

    /**
     * @param id that shall be validated.
     * @throws IllegalArgumentException if the id is not valid does not exist.
     */
    @Override
    public void validateId(int id) throws IllegalArgumentException {
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
        if(workHoursCapacity > 60) {
            throw new IllegalArgumentException("Work hours capacity cannot be more than 60 hours");
        }
    }

    private void setupEmployeeTable() {
        create.createTableIfNotExists(EMPLOYEE)
                .column(EMPLOYEE.ID,
                        INTEGER.identity(true))
                .column(EMPLOYEE.FIRST_NAME,
                        VARCHAR.nullable(false))
                .column(EMPLOYEE.LAST_NAME,
                        VARCHAR.nullable(false))
                .column(EMPLOYEE.WORK_HOURS_CAPACITY,
                        INTEGER)
                .constraints(primaryKey(EMPLOYEE.ID),
                        check(trim(EMPLOYEE.FIRST_NAME).ne("")),
                        check(trim(EMPLOYEE.LAST_NAME).ne("")),
                        check(EMPLOYEE.WORK_HOURS_CAPACITY.between(0, 60)))
                .execute();
    }
}
