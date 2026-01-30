package de.emil.pr3;

import de.emil.pr3.jooq.tables.pojos.Employee;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeIteratorTest {

    @Test
    void iteratorCyclesThroughEmployees() {

        List<Employee> employees = List.of(
                new Employee(1, "Max", "Mustermann", 40),
                new Employee(2, "Erika", "Musterfrau", 40)
        );

        Iterator<Employee> iterator = new EmployeeIterator(employees);

        assertEquals(1, iterator.next().getId());
        assertEquals(2, iterator.next().getId());
        assertEquals(1, iterator.next().getId());
        assertEquals(2, iterator.next().getId());
    }

    @Test
    void constructorFailsForEmptyList() {
        List<Employee> employees = List.of();

        assertThrows(IllegalArgumentException.class,
                () -> new EmployeeIterator(employees));
    }

}
