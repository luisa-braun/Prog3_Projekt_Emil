package de.emil.pr3.ui;

import de.emil.pr3.shift.Shift;
import de.emil.pr3.shift.ShiftAssignment;
import de.emil.pr3.schedule.WorkScheduleWeeklyTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class WorkSchedulePrinter {

    public static void showEmptyPlan() {
        String[] dayCodes = {"Mo", "Di", "Mi", "Do", "Fr"};
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        List<Shift> template = WorkScheduleWeeklyTemplate.getWeeklyTemplate();

        System.out.println("\n------------------------------------ WEEKLY SHIFT TEMPLATE --------------------------------------");
        System.out.printf("%-12s | %-15s | %-10s | %-10s | %-10s | %-9s | %-15s%n",
                "Day", "Shift", "Identifier", "Start", "End", "Duration", "Staff Needed");
        System.out.println("-------------------------------------------------------------------------------------------------");


        for (int i = 0; i < dayCodes.length; i++) {
            String dayCode = dayCodes[i];
            String dayLabel = days[i];
            boolean firstRow = true;

            for (Shift s : template) {
                if (s.identifier().startsWith(dayCode)) {
                    String label = firstRow ? dayLabel : "";
                    System.out.printf("%-12s | %-15s | %-10s | %-10s | %-10s | %d hours   | %d Person(s)%n",
                            label, s.name(), s.identifier(), s.start(), s.end(), s.duration(), s.requiredStaff());
                    firstRow = false;
                }
            }
            System.out.println("-------------------------------------------------------------------------------------------------");
        }
    }

    public static void showSchedule(List<ShiftAssignment> schedule) {
        String[] dayCodes = {"Mo", "Di", "Mi", "Do", "Fr"};
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        System.out.println("\n------------------------------------ WEEKLY SHIFT SCHEDULE -------------------------------------------------------------------------------------");
        System.out.printf("%-12s | %-15s | %-10s | %-10s | %-10s | %-9s | %-10s | %-15s%n",
                "Day", "Shift", "Identifier", "Start", "End", "Duration", "Staff Needed", "Assigned Employees");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < dayCodes.length; i++) {
            String dayCode = dayCodes[i];
            String dayLabel = days[i];
            boolean firstRow = true;

            for (ShiftAssignment sa : schedule) {
                if (sa.shift().identifier().startsWith(dayCode)) {
                    String label = firstRow ? dayLabel : "";
                    String employees = sa.employees().isEmpty() ? "-"
                            : sa.employees().stream()
                            .map(e -> e.getFirstName() + " " + e.getLastName())
                            .collect(Collectors.joining(", "));

                    System.out.printf("%-12s | %-15s | %-10s | %-10s | %-10s | %d hours   | %d Person(s) | %s%n",
                            label,
                            sa.shift().name(),
                            sa.shift().identifier(),
                            sa.shift().start(),
                            sa.shift().end(),
                            sa.shift().duration(),
                            sa.shift().requiredStaff(),
                            employees
                    );
                    firstRow = false;
                }
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }


}
