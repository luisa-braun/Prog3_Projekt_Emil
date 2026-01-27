package de.emil.pr3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShiftPlanner {


    private static List<Shift> getWeeklyTemplate() {
        List<Shift> shifts = new ArrayList<>();
        shifts.add(new Shift("Morning Shift", 6, 2));
        shifts.add(new Shift("Midday Shift", 4, 1));
        return shifts;
    }


    public static void showEmptyPlan() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        List<Shift> template = getWeeklyTemplate();

        System.out.println("\n------------------ WEEKLY SHIFT TEMPLATE -------------------");
        System.out.printf("%-12s | %-15s | %-10s | %-15s%n", "Day", "Shift", "Duration", "Staff Needed");
        System.out.println("------------------------------------------------------------");

        for (String day : days) {
            boolean firstRow = true;
            for (Shift s : template) {
                String dayLabel = firstRow ? day : "";
                System.out.printf("%-12s | %-15s | %d hours   | %d Person(s)%n",
                        dayLabel, s.name(), s.duration(), s.requiredStaff());
                firstRow = false;
            }
            System.out.println("------------------------------------------------------------");
        }
    }
}