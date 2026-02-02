package de.emil.pr3;

import de.emil.pr3.databanks.EmployeeDatabank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddEmployeeMenu {

    public static void addEmployeeInformation() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String vorname = readString("Vorname: ", reader);
        String nachname = readString("Nachname: ", reader);

        try(EmployeeDatabank db = new EmployeeDatabank()) {
            db.createNewEmployee(vorname, nachname, 0);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static String readString(String prompt, BufferedReader reader){
        System.out.print(prompt);
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Lesen der Eingabe!", e);
        }
    }
}
