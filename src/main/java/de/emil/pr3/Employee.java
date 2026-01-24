package de.emil.pr3;

import java.util.Objects;

public class Employee {
    private final String firstName;
    private final String lastName;
    private final int id;
    private int workHoursCapacity;

    public Employee(int id, String firstName, String lastName) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }
        if (Objects.isNull(firstName) || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be blank");
        }
        if (Objects.isNull(lastName) || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be blank");
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.workHoursCapacity = 0;
    }

    public void setWorkHoursCapacity(int workHoursCapacity) throws IllegalArgumentException {
        if (workHoursCapacity < 0) {
            throw new IllegalArgumentException("Work hours capacity cannot be negative");
        }
        if (workHoursCapacity > 60) {
            throw new IllegalArgumentException("Work hours capacity cannot be more than 60 hours");
        }
        this.workHoursCapacity = workHoursCapacity;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getWorkHoursCapacity() {
        return workHoursCapacity;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + id;
    }
}
