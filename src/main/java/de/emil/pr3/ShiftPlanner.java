package de.emil.pr3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ShiftPlanner {

    static class Worker {
        String name;
        Worker(String name) { this.name = name; }
    }

    static class Shift {
        String name;
        int duration;
        int staffRequired;

        Shift(String name, int duration, int staffRequired) {
            this.name = name;
            this.duration = duration;
            this.staffRequired = staffRequired;
        }
    }

    public static void generateAndShowPlan() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        List<Worker> workers = new ArrayList<>();
        workers.add(new Worker("Alice"));
        workers.add(new Worker("Niko"));
        workers.add(new Worker("Robin"));
        workers.add(new Worker("Bob"));
        workers.add(new Worker("Jeff"));

        List<Shift> shifts = List.of(
                new Shift("Morning Shift", 6, 2),
                new Shift("Afternoon Shift", 4, 1)
        );

        System.out.println("\n================= GENERATED WEEKLY SCHEDULE =================");
        System.out.printf("%-12s | %-16s | %-10s | %-15s%n", "Day", "Shift", "Duration", "Assigned Staff");
        System.out.println("-------------------------------------------------------------------");

        for (String day : days) {
            if (day.equals("Saturday") || day.equals("Sunday")) {
                System.out.printf("%-12s | %-45s |%n", day, "OFF - Weekend");
            } else {

                Collections.shuffle(workers);
                int workerIndex = 0;
                for (Shift shift : shifts) {
                    StringBuilder assigned = new StringBuilder();

                    for (int i = 0; i < shift.staffRequired; i++) {
                        if (workerIndex < workers.size()) {
                            assigned.append(workers.get(workerIndex).name).append(" ");
                            workerIndex++;
                        }
                    }
                    String dayLabel = (shift.name.equals("Morning Shift")) ? day : "";
                    System.out.printf("%-12s | %-16s | %d Hours   | %-15s%n",
                            dayLabel, shift.name, shift.duration, assigned);
                }
            }
            System.out.println("-------------------------------------------------------------------");
        }
    }
}