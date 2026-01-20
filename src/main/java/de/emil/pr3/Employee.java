package de.emil.pr3;

import java.util.Objects;
import java.util.UUID;

public class Employee {
    private final String firstName;
    private final String lastName;
    private final String id;
    private int workHoursCapacity;

    public Employee(String firstName, String lastName) throws IllegalArgumentException {
        if (Objects.isNull(firstName) || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be blank");
        }
        if (Objects.isNull(lastName) || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be blank");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = UUID.randomUUID().toString();
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getId() {
        return id;
    }

    public int getWorkHoursCapacity() {
        return workHoursCapacity;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + id;
    }
}
