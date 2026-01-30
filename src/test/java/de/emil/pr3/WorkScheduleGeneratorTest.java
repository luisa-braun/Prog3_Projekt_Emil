package de.emil.pr3;

import org.junit.jupiter.api.BeforeEach;
import de.emil.pr3.jooq.tables.pojos.Employee;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkScheduleGeneratorTest {
    private WorkScheduleGenerator generator;

    @BeforeEach
    void setup() {
        List<Employee> employees = List.of(
                new Employee(1, "Max", "Mustermann", 40),
                new Employee(2, "Erika", "Musterfrau", 40)
        );

        generator = new WorkScheduleGenerator(
                new FakeEmployeeDatabank(employees)
        );
    }

    @Test
    void eachShiftGetsRequiredStaff() {
        List<Shift> shifts = List.of(
                new Shift("Morning", "Mo-1", "8", "12", 4, 2)
        );

        List<ShiftAssignment> schedule = generator.generateWeeklySchedule(shifts);

        assertEquals(2, schedule.getFirst().employees().size());
    }
}
