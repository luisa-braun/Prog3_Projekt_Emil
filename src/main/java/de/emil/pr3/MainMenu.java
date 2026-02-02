package de.emil.pr3;


import de.emil.pr3.databanks.EmployeeDatabank;

public class MainMenu extends UserInterface{


    private static final int VIEW_WORKERS = 1;
    private static final int ADD_WORKERS = 2;
    private static final int DELETE_WORKERS = 3;
    private static final int VIEW_WORKSCHEDULE = 4;
    private static final int GENERATE_WORKSCHEDULE = 5;
    private static final int END_PROGRAM = 6;

    @Override
    public void start() {
        int funktion = 0;
        do {
            try {
                showMenu();
                funktion = readFunction();
                executeFunction(funktion);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
                e.printStackTrace(System.out);
            }
        }
        while (funktion != END_PROGRAM);

    }

    private int readFunction() {
        return inputReader.readPositivIntegerInput();
    }

    private void executeFunction(int funktion) {
        switch (funktion) {
            case VIEW_WORKERS:
                try(EmployeeDatabank db = new EmployeeDatabank()) {
                    System.out.println(db.getListOfEmployees());
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case ADD_WORKERS:
                AddEmployeeMenu.addEmployeeInformation();
                break;
            case DELETE_WORKERS:
                try(EmployeeDatabank db = new EmployeeDatabank()) {
                    System.out.println(db.getListOfEmployees());
                    db.deleteEmployeeById(inputReader.readPositivIntegerInput());
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case VIEW_WORKSCHEDULE:
                ShiftPlanner.showEmptyPlan();
                break;
            case GENERATE_WORKSCHEDULE:
                //placeHolderSchedule();
                break;
            case END_PROGRAM:
                System.out.println("You have finished the Program. Goodbye.");
                break;
            default:
                System.out.println("Invalid option selected.");
                break;
        }
    }

    private void showMenu() {
        System.out.println("===== Main Menu =====");
        System.out.println("1. View Workers");
        System.out.println("2. Add Workers");
        System.out.println("3. Delete Workers");
        System.out.println("4. View Schedule template");
        System.out.println("5. Generate Weekly Work schedule");
        System.out.println("6. End Program");
        System.out.print(" Please select an option: ");

    }

}
