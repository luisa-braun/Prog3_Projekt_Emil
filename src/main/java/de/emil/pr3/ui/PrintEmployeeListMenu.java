package de.emil.pr3.ui;

import de.emil.pr3.jooq.tables.pojos.Employee;

import java.util.List;

public class PrintEmployeeListMenu {

    public static void printListOfEmployees(List<Employee> employees) {
        System.out.println("\n------------------------------------ LIST OF ALL EMPLOYEES --------------------------------------");
        System.out.printf("%-19s | %-24s | %-24s | %-19s%n",
                "ID", "First name", "Last name", "Workhours this week");
        System.out.println("-------------------------------------------------------------------------------------------------");

        for (Employee employee : employees) {
            System.out.printf("%-19s | %-24s | %-24s | %d hours%n",
                    employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getWorkHoursCapacity());
        }
        System.out.println("-------------------------------------------------------------------------------------------------\n");

    }
}
