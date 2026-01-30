package de.emil.pr3;

import java.util.List;
import de.emil.pr3.jooq.tables.pojos.Employee;

public class FakeEmployeeDatabank extends EmployeeDatabank {
    private final List<Employee> employees;

    FakeEmployeeDatabank(List<Employee> employees){
        this.employees = employees;
    }

    @Override
    public List<Employee> getListOfEmployees() {
        return employees;
    }
}
