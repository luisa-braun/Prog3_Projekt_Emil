package de.emil.pr3.ui;


import de.emil.pr3.jooq.tables.Employee;
import de.emil.pr3.schedule.*;
import de.emil.pr3.databases.EmployeeDatabase;


import java.sql.SQLException;

public class MainMenu extends UserInterface{


    private static final int VIEW_WORKERS = 1;
    private static final int ADD_WORKERS = 2;
    private static final int DELETE_WORKERS = 3;
    private static final int VIEW_WORKSCHEDULE = 4;
    private static final int GENERATE_WORKSCHEDULE = 5;
    private static final int END_PROGRAM = 6;

    @Override
    public void start() {
        int function = 0;
        do {
            try {
                showMenu();
                function = readFunction();
                executeFunction(function);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
                e.printStackTrace(System.out);
            }
        }
        while (function != END_PROGRAM);

    }

    private int readFunction() {
        return inputReader.readPositiveInteger();
    }

    private void executeFunction(int function) {
        switch (function) {
            case VIEW_WORKERS:
                try(EmployeeDatabase db = new EmployeeDatabase()) {
                    PrintEmployeeListMenu.printListOfEmployees(db.getListOfEmployees());
                } catch (IllegalArgumentException | SQLException e){
                    System.out.println(e.getMessage());
                }
                break;
            case ADD_WORKERS:
                AddEmployeeMenu.addEmployeeInformation(this.inputReader);
                break;
            case DELETE_WORKERS:
                try(EmployeeDatabase db = new EmployeeDatabase()) {
                    PrintEmployeeListMenu.printListOfEmployees(db.getListOfEmployees());
                    System.out.println("Please enter the ID of the Employee:");
                    db.deleteEmployeeById(inputReader.readPositiveInteger());
                } catch (IllegalArgumentException | SQLException e){
                    System.out.println(e.getMessage());
                }
                break;
            case VIEW_WORKSCHEDULE:
                WorkSchedulePrinter.showEmptyPlan();
                break;
            case GENERATE_WORKSCHEDULE:
                try(EmployeeDatabase db = new EmployeeDatabase()) {
                    WorkScheduleGenerator generator = new WorkScheduleGenerator(db.getListOfEmployees());
                    WorkSchedulePrinter.showSchedule(generator.generateWeeklySchedule(WorkScheduleWeeklyTemplate.getWeeklyTemplate()));
                }
                catch (IllegalArgumentException | SQLException e){
                    System.out.println(e.getMessage());
                }
                break;
            case END_PROGRAM:
                System.out.println("You have ended the Programm.");
                break;
            default:
                System.out.println("You entered a non viable option. Please enter one of the displayed Numbers.");
                break;
        }
    }

    private void showMenu() {
        System.out.println("===== Main Menu =====");
        System.out.println("1. View current Employees in the System");
        System.out.println("2. Add Employee to the System");
        System.out.println("3. Delete Employee from the System");
        System.out.println("4. View Schedule Template");
        System.out.println("5. Generate weekly Schedule");
        System.out.println("6. End Programm");
        System.out.print(" Please choose an option: ");

    }
}
