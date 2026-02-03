package de.emil.pr3.shift;

import de.emil.pr3.jooq.tables.pojos.Employee;

import java.util.List;

public record ShiftAssignment(Shift shift, List<Employee> employees) {

}
