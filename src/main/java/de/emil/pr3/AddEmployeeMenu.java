package de.emil.pr3;

import de.emil.pr3.databases.EmployeeDatabase;
import java.sql.SQLException;


public class AddEmployeeMenu {


    public static void addEmployeeInformation(InputReader reader) {
        String firstName = reader.readString("Vorname: ");
        String lastName = reader.readString("Nachname: ");

        try (EmployeeDatabase db = new EmployeeDatabase()) {
            db.createNewEmployee(firstName, lastName, 0);
            System.out.println("Employee added successfully!");
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}