package de.emil.pr3.databanks;

import org.jooq.DSLContext;
import org.junit.jupiter.api.*;

import java.util.List;

import static de.emil.pr3.jooq.tables.Employee.EMPLOYEE;
import de.emil.pr3.jooq.tables.pojos.Employee;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDatabankTest {
    private static EmployeeDatabank databank;
    private static DSLContext create;

    @BeforeAll
    static void setupDatabase() throws Exception {
        databank = new EmployeeDatabank(EmployeeDatabank.TEST_URL);
        create = databank.create;
    }

    @BeforeEach
    void setupTestData() {
        create.insertInto(EMPLOYEE)
                .columns(EMPLOYEE.FIRST_NAME,
                        EMPLOYEE.LAST_NAME,
                        EMPLOYEE.WORK_HOURS_CAPACITY)
                .values("Max", "Mustermann", 40)
                .values("Erika", "Neuland", null)
                .execute();
    }

    @AfterEach
    void cleanupTestData() {
        create.deleteFrom(EMPLOYEE).execute();
    }

    @AfterAll
    static void teardown() throws Exception {
        databank.close();
    }

    @Test
    void createNewEmployee_shouldFailOnNullFirstName() {
        assertThrows(IllegalArgumentException.class,
                () -> databank.createNewEmployee(null, "Doe", 0));
    }

    @Test
    void createNewEmployee_shouldFailOnEmptyFirstName() {
        assertThrows(IllegalArgumentException.class,
                () -> databank.createNewEmployee("", "Doe", 0));
    }

    @Test
    void createNewEmployee_shouldFailOnNullLastName() {
        assertThrows(IllegalArgumentException.class,
                () -> databank.createNewEmployee("John", null, 0));
    }

    @Test
    void createNewEmployee_shouldFailOnEmptyLastName() {
        assertThrows(IllegalArgumentException.class,
                () -> databank.createNewEmployee("John", "", 0));
    }

    @Test
    void createNewEmployee_shouldCreateAndInsertEmployee() {
        Employee employee = databank.createNewEmployee("John", "Doe", 0);

        assertNotNull(employee);
        assertNotNull(employee.getId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());

        assertEquals(3, create.fetchCount(EMPLOYEE));
    }

    @Test
    void deleteEmployeeById_shouldFailIfIdNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> databank.deleteEmployeeById(-5));
    }

    @Test
    void deleteEmployeeById_shouldFailIfIdNotFound() {
        assertThrows(IllegalArgumentException.class,
                () -> databank.deleteEmployeeById(9999));
    }

    @Test
    void deleteEmployeeById_shouldDeleteAndReturnEmployee() {
        Integer id = create.select(EMPLOYEE.ID)
                .from(EMPLOYEE)
                .limit(1)
                .fetchOneInto(Integer.class);
        assertNotNull(id);
        Employee deleted = databank.deleteEmployeeById(id);

        assertNotNull(deleted);
        assertThrows(IllegalArgumentException.class, () -> databank.validateId(id));
    }

    @Test
    void findAllEmployeesSortedById_shouldReturnFullList() {
        List<Employee> employees = databank.getListOfEmployees();

        assertEquals(2, employees.size());
    }

    @Test
    void findAllEmployeesSortedById_shouldReturnSortedList() {
        List<Employee> employees = databank.getListOfEmployees();

        assertTrue(employees.get(0).getId() < employees.get(1).getId());
    }

    @Test
    void updateWorkHoursCapacity_shouldFailIfEmployeeNotFound() {
        assertThrows(IllegalArgumentException.class,
                () -> databank.updateWorkHoursCapacity(9999, 40));
    }

    @Test
    void updateWorkHoursCapacity_shouldFailOnNegativeCapacity() {
        Integer id = create.select(EMPLOYEE.ID)
                .from(EMPLOYEE)
                .limit(1)
                .fetchOneInto(Integer.class);

        assertNotNull(id);
        assertThrows(IllegalArgumentException.class,
                () -> databank.updateWorkHoursCapacity(id, -5));
    }

    @Test
    void updateWorkHoursCapacity_shouldFailOnCapacityAboveSixty() {
        Integer id = create.select(EMPLOYEE.ID)
                .from(EMPLOYEE)
                .limit(1)
                .fetchOneInto(Integer.class);

        assertNotNull(id);
        assertThrows(IllegalArgumentException.class,
                () -> databank.updateWorkHoursCapacity(id, 65));
    }

    @Test
    void updateWorkHoursCapacity_shouldUpdateValue() {
        Integer id = create.select(EMPLOYEE.ID)
                .from(EMPLOYEE)
                .where(EMPLOYEE.FIRST_NAME.eq("Max"))
                .fetchOneInto(Integer.class);

        assertNotNull(id);
        Employee updatedEmployee = databank.updateWorkHoursCapacity(id, 35);

        assertNotNull(updatedEmployee);
        assertEquals(35, updatedEmployee.getWorkHoursCapacity());
    }

    @Test
    void validateId_shouldFailOnMissingId() {
        assertThrows(IllegalArgumentException.class, () -> databank.validateId(99999));
    }

    @Test
    void validateId_shouldFailIfIdZero() {
        assertThrows(IllegalArgumentException.class, () -> databank.validateId(0));
    }

    @Test
    void validateId_shouldFailOnNegativeId() {
        assertThrows(IllegalArgumentException.class, () -> databank.validateId(-1));
    }

    @Test
    void validateId_shouldNotFailOnExistingId() {
        Integer id = create.select(EMPLOYEE.ID)
                .from(EMPLOYEE)
                .limit(1)
                .fetchOneInto(Integer.class);

        assertNotNull(id);
        databank.validateId(id);
    }
}