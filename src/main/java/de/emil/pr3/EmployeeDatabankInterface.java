package de.emil.pr3;

import de.emil.pr3.jooq.tables.pojos.Employee;
import java.util.List;

public interface EmployeeDatabankInterface {

    Employee createNewEmployee(String firstName, String lastName, int workHoursCapacity) throws IllegalArgumentException;

    Employee deleteEmployeeById(int id) throws IllegalArgumentException;

    List<Employee> getListOfEmployees();

    Employee updateWorkHoursCapacity(int id, int workHoursCapacity);

    void validateId(int id) throws IllegalArgumentException;
}
