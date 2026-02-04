package de.emil.pr3.ui;

import de.emil.pr3.databases.EmployeeDatabase;

import java.sql.SQLException;


public class AddEmployeeMenu {


    public static void addEmployeeInformation(InputReader reader) {
        String firstName = reader.readString("Vorname: ");
        String lastName = reader.readString("Nachname: ");
        System.out.print("Wöchentliche Arbeitsstunden: ");
        int weeklyWorkCapacity = reader.readPositiveInteger();

        try (EmployeeDatabase db = new EmployeeDatabase()) {
            db.createNewEmployee(firstName, lastName, weeklyWorkCapacity);
            System.out.println("Arbeiter erfolgreich dem System hinzugefügt: " + firstName + " " + lastName + ", Arbeitsstunden: " + weeklyWorkCapacity);
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}