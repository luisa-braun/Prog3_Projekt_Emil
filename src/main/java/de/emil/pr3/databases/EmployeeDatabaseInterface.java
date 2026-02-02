package de.emil.pr3.databases;

import de.emil.pr3.jooq.tables.pojos.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDatabaseInterface {

    Employee createNewEmployee(String firstName, String lastName, int workHoursCapacity) throws IllegalArgumentException, SQLException;

    Employee deleteEmployeeById(int id) throws IllegalArgumentException;

    List<Employee> getListOfEmployees();

    Employee updateWorkHoursCapacity(int id, int workHoursCapacity);
}
