package de.emil.pr3.ui;

import de.emil.pr3.databases.EmployeeDatabase;

import java.sql.SQLException;


public class AddEmployeeMenu {


    public static void addEmployeeInformation(InputReader reader) {
        String firstName = reader.readString("First Name: ");
        String lastName = reader.readString("Last Name: ");
        System.out.print("Weekly Work Hours: ");
        int weeklyWorkCapacity = reader.readPositiveInteger();

        try (EmployeeDatabase db = new EmployeeDatabase()) {
            db.createNewEmployee(firstName, lastName, weeklyWorkCapacity);
            System.out.println("Employee added successfully: " + firstName + " " + lastName + ", working hours: " + weeklyWorkCapacity);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}