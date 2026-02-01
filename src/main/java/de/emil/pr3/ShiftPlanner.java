package de.emil.pr3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShiftPlanner {

    private static List<Shift> getWeeklyTemplate() {
        List<Shift> shifts = new ArrayList<>();
        shifts.add(new Shift("Morning Shift", "Mo-1", "8:00", "14:00", 6, 2));
        shifts.add(new Shift("Afternoon Shift", "Mo-2", "14:30", "18:30", 4, 1));
        shifts.add(new Shift("Morning Shift", "Di-1", "8:00", "14:00", 6, 2));
        shifts.add(new Shift("Afternoon Shift", "Di-2", "14:30", "18:30", 4, 1));
        shifts.add(new Shift("Morning Shift", "Mi-1", "8:00", "14:00", 6, 2));
        shifts.add(new Shift("Afternoon Shift", "Mi-2", "14:30", "18:30", 4, 1));
        shifts.add(new Shift("Morning Shift", "Do-1", "8:00", "14:00", 6, 2));
        shifts.add(new Shift("Afternoon Shift", "Do-2", "14:30", "18:30", 4, 1));
        shifts.add(new Shift("Morning Shift", "Fr-1", "8:00", "14:00", 6, 2));
        shifts.add(new Shift("Afternoon Shift", "Fr-2", "14:30", "18:30", 4, 1));
        return shifts;
    }


    public static void showEmptyPlan() {
        String[] dayCodes = {"Mo", "Di", "Mi", "Do", "Fr"};
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        List<Shift> template = getWeeklyTemplate();

        System.out.println("\n------------------------------------ WEEKLY SHIFT TEMPLATE --------------------------------------");
        System.out.printf("%-12s | %-15s | %-10s | %-10s | %-10s | %-9s | %-15s%n", "Day", "Shift", "Identifier", "Start", "End", "Duration", "Staff Needed");
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
}