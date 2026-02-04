package de.emil.pr3.employee;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import de.emil.pr3.jooq.tables.pojos.Employee;


public class EmployeeIterator implements Iterator<Employee>{

    private final List<Employee> employees;
    private int index = 0;

    public EmployeeIterator(List<Employee> employees){
        if (employees == null || employees.isEmpty()){
            throw new IllegalArgumentException("Employee list must not be empty");
        }
        this.employees = employees;
    }

    @Override
    public boolean hasNext(){
        return index < employees.size() - 1;
    }

    @Override
    public Employee next() {
        if (employees.isEmpty()){
            throw new NoSuchElementException();
        }
        if (!hasNext()){
            throw new NoSuchElementException();
        }

        Employee employee = employees.get(index);
        index = (index + 1) % employees.size();
        return employee;
    }
}