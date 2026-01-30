package de.emil.pr3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import de.emil.pr3.jooq.tables.pojos.Employee;

public class WorkScheduleGenerator {

    private final EmployeeDatabank employeeDatabank;

    public WorkScheduleGenerator(EmployeeDatabank employeeDatabank){
        this.employeeDatabank = employeeDatabank;
    }

    public List<ShiftAssignment> generateWeeklySchedule(List<Shift> shifts) {
        List<Employee> employees = employeeDatabank.getListOfEmployees();

        if (employees.isEmpty()){
            throw new IllegalStateException("No employees available");
        }

        List<ShiftAssignment> schedule = new ArrayList<>();
        Iterator<Employee> iterator = new EmployeeIterator(employeeDatabank.getListOfEmployees());

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
