package de.emil.pr3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.emil.pr3.jooq.tables.pojos.Employee;

public class WorkScheduleGenerator {

    private final List<Employee> employees;

    public WorkScheduleGenerator(List<Employee> employees){
        if (Objects.isNull(employees) || employees.isEmpty()){
            throw new IllegalStateException("No employees available");
        }
        this.employees = employees;

    }

    public List<ShiftAssignment> generateWeeklySchedule(List<Shift> shifts) {
        List<ShiftAssignment> schedule = new ArrayList<>();
        Iterator<Employee> iterator = new EmployeeIterator(employees);

        for (Shift shift : shifts) {
            List<Employee> assigned = new ArrayList<>();

            for (int i = 0; i < shift.requiredStaff(); i++){
                assigned.add(iterator.next());
            }

            schedule.add(new ShiftAssignment(shift, assigned));
        }

        return schedule;
    }
}
