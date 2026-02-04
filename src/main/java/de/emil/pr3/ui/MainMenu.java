package de.emil.pr3.ui;


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
        return inputReader.readPositiveInteger();
    }

    private void executeFunction(int funktion) {
        switch (funktion) {
            case VIEW_WORKERS:
                try(EmployeeDatabase db = new EmployeeDatabase()) {
                    System.out.println(db.getListOfEmployees());
                } catch (IllegalArgumentException | SQLException e){
                    System.out.println(e.getMessage());
                }
                break;
            case ADD_WORKERS:
                AddEmployeeMenu.addEmployeeInformation(this.inputReader);
                break;
            case DELETE_WORKERS:
                try(EmployeeDatabase db = new EmployeeDatabase()) {
                    System.out.println(db.getListOfEmployees());
                    // TODO: noch Befehl ausgeben, dass ID eingegeben werden soll
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
                System.out.println("Sie haben das Programm beendet.");
                break;
            default:
                System.out.println("Nicht verfügbare option eingegeben. Bitte geben sie eine angezeigten Nummern an.");
                break;
        }
    }

    private void showMenu() {
        System.out.println("===== Hauptmenü =====");
        System.out.println("1. Momentane Arbeiter im System");
        System.out.println("2. Arbeiter dem System hinzufügen");
        System.out.println("3. Arbeiter aus dem System entfernen");
        System.out.println("4. Besichtige momentane wöchentlichen Schichten");
        System.out.println("5. Generiere wöchentlichen Arbeitsplan");
        System.out.println("6. Beende Programm");
        System.out.print(" Bitte wählen sie eine Option aus: ");

    }

}
