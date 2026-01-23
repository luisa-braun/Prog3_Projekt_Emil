package de.emil.pr3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EmployeeTest {
    @Test
    public void fistNameShouldNotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Employee(1,null, "lastName"));
    }

    @Test
    public void fistNameShouldNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Employee(1, "   ", "LastName"));
    }

    @Test
    public void lastNameShouldNotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Employee(1, "firstName", null));
    }

    @Test
    public void lastNameShouldNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Employee(1, "firstName", "   "));
    }

    @Test
    public void workHoursCapacityShouldNotBeNegative() {
        Employee employee = new Employee(1, "firstName", "lastName");
        assertThrows(IllegalArgumentException.class, () -> employee.setWorkHoursCapacity(-1));
    }

    @Test
    public void workHoursCapacityShouldNotBeGreaterThanSixty() {
        Employee employee = new Employee(1, "firstName", "lastName");
        assertThrows(IllegalArgumentException.class, () -> employee.setWorkHoursCapacity(61));
    }

    @Test
    public void workHoursCapacityShouldBeAbleToBeZero() {
        Employee employee = new Employee(1, "firstName", "lastName");
        employee.setWorkHoursCapacity(0);
        assertEquals(0, employee.getWorkHoursCapacity());
    }

    @Test
    public void workHoursCapacityShouldBeAbleToBeSixty() {
        Employee employee = new Employee(1, "firstName", "lastName");
        employee.setWorkHoursCapacity(60);
        assertEquals(60, employee.getWorkHoursCapacity());
    }
}
