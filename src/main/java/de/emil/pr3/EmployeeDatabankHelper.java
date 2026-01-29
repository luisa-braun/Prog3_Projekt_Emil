package de.emil.pr3;

import de.emil.pr3.jooq.tables.pojos.Employee;
import java.util.List;

public interface EmployeeDatabankHelper {

    Employee createNewEmployee(String firstName, String lastName) throws IllegalArgumentException;

    Employee deleteEmployeeById(int id) throws IllegalArgumentException;

    List<Employee> getListOfEmployees();

    Employee updateWorkHoursCapacity(int id, int workHoursCapacity);

    boolean idIsValidAndExists(int id);
}
