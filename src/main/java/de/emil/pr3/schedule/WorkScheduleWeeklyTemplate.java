package de.emil.pr3.schedule;

import de.emil.pr3.shift.Shift;

import java.util.ArrayList;
import java.util.List;

public class WorkScheduleWeeklyTemplate {

    public static List<Shift> getWeeklyTemplate() {
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
}