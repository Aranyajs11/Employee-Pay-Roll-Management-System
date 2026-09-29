package com.college.app;

import com.college.model.Department;
import com.college.model.Employee;
import com.college.model.FullTimeEmployee;
import com.college.model.PartTimeEmployee;
import com.college.model.Person;
import com.college.service.EmployeeService;

import java.util.Scanner;

/**
 * REQ 6  - if/else, switch, while/do-while/for loops, return.
 * REQ 8  - Scanner input + printf/String.format output.
 * REQ 21 - Third custom package (com.college.app).
 * Main only handles input/output; business logic lives in the model/service classes.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final EmployeeService service = new EmployeeService();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readMenuChoice();
            switch (choice) {
                case 1:
                    addFullTimeEmployee();
                    break;
                case 2:
                    addPartTimeEmployee();
                    break;
                case 3:
                    service.displayAllEmployees();
                    break;
                case 4:
                    searchById();
                    break;
                case 5:
                    searchByName();
                    break;
                case 6:
                    service.calculatePayroll();
                    break;
                case 7:
                    generatePayslip();
                    break;
                case 8:
                    displayTotals();
                    break;
                case 9:
                    System.out.println("Thank you for using the Employee Payroll Management System.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("========== EMPLOYEE PAYROLL MANAGEMENT SYSTEM ==========");
        System.out.println("1. Add Full-Time Employee");
        System.out.println("2. Add Part-Time Employee");
        System.out.println("3. Display All Employees");
        System.out.println("4. Search Employee by ID");
        System.out.println("5. Search Employee by Name");
        System.out.println("6. Calculate Payroll");
        System.out.println("7. Generate Payslip");
        System.out.println("8. Display Total Employees");
        System.out.println("9. Exit");
        System.out.println("========================================================");
    }

    // ---------------------------------------------------------------- menu actions

    private static void addFullTimeEmployee() {
        System.out.println("--- Add Full-Time Employee ---");
        if (service.isFull()) {
            System.out.println("Employee storage is full (" + EmployeeService.MAX_EMPLOYEES + ").");
            return;
        }
        String id = readNewId();
        String name = readNonEmpty("Name: ");
        int age = readInt("Age (minimum " + Person.MIN_AGE + "): ", Person.MIN_AGE);
        String phone = readLine("Phone (Enter to skip): ");
        Department dept = readDepartment();
        String designation = readNonEmpty("Designation: ");
        double basic = readNonNegativeDouble("Basic salary: ");
        double other = readNonNegativeDouble("Other allowance: ");
        double deduction = readNonNegativeDouble("Deduction: ");

        double gross = FullTimeEmployee.computeGross(basic, other);
        if (deduction > gross) {
            System.out.println("Deduction cannot exceed gross pay (" + String.format("%.2f", gross) + "). Employee not added.");
            return;
        }
        try {
            Employee e = new FullTimeEmployee(id, name, age, phone, dept, designation, basic, other, deduction);
            service.addEmployee(e);
            System.out.println("Full-time employee added successfully.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Could not add employee: " + ex.getMessage());
        }
    }

    private static void addPartTimeEmployee() {
        System.out.println("--- Add Part-Time Employee ---");
        if (service.isFull()) {
            System.out.println("Employee storage is full (" + EmployeeService.MAX_EMPLOYEES + ").");
            return;
        }
        String id = readNewId();
        String name = readNonEmpty("Name: ");
        int age = readInt("Age (minimum " + Person.MIN_AGE + "): ", Person.MIN_AGE);
        String phone = readLine("Phone (Enter to skip): ");
        Department dept = readDepartment();
        String designation = readNonEmpty("Designation: ");
        double hours = readNonNegativeDouble("Hours worked: ");
        double rate = readNonNegativeDouble("Hourly rate: ");
        double deduction = readNonNegativeDouble("Deduction: ");

        double gross = PartTimeEmployee.computeGross(hours, rate);
        if (deduction > gross) {
            System.out.println("Deduction cannot exceed gross pay (" + String.format("%.2f", gross) + "). Employee not added.");
            return;
        }
        try {
            Employee e = new PartTimeEmployee(id, name, age, phone, dept, designation, hours, rate, deduction);
            service.addEmployee(e);
            System.out.println("Part-time employee added successfully.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Could not add employee: " + ex.getMessage());
        }
    }

    private static void searchById() {
        String id = readNonEmpty("Enter Employee ID to search: ");
        Employee e = service.searchEmployee(id);
        if (e == null) {
            System.out.println("No employee found with ID '" + id + "'.");
        } else {
            System.out.println("Employee found:");
            System.out.println(e);
        }
    }

    private static void searchByName() {
        String name = readNonEmpty("Enter name (or part of a name): ");
        Employee[] results = service.searchEmployee(name, true);
        if (results.length == 0) {
            System.out.println("No employee found matching '" + name + "'.");
            return;
        }
        System.out.println(results.length + " employee(s) found:");
        for (Employee e : results) {
            System.out.println(e);
        }
    }

    private static void generatePayslip() {
        String id = readNonEmpty("Enter Employee ID for payslip: ");
        if (!service.generatePayslip(id)) {
            System.out.println("No employee found with ID '" + id + "'.");
        }
    }

    private static void displayTotals() {
        System.out.println("Total employees in system : " + service.getTotalEmployees());
        System.out.println("  Full-time employees     : " + service.getFullTimeCount());
        System.out.println("  Part-time employees     : " + service.getPartTimeCount());
        System.out.println("Employee objects created (static count): " + Employee.getEmployeeCount());
    }

    // ---------------------------------------------------------------- input helpers

    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println();
            System.out.println("Input ended. Exiting.");
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    private static String readNonEmpty(String prompt) {
        String value;
        do {
            value = readLine(prompt);
            if (value.isEmpty()) {
                System.out.println("This field cannot be empty.");
            }
        } while (value.isEmpty());
        return value;
    }

    private static String readNewId() {
        while (true) {
            String id = readNonEmpty("Employee ID: ");
            if (service.idExists(id)) {
                System.out.println("Employee ID '" + id + "' already exists. Enter a different ID.");
                continue;
            }
            return id;
        }
    }

    private static int readInt(String prompt, int min) {
        while (true) {
            String text = readLine(prompt);
            try {
                int value = Integer.parseInt(text);
                if (value < min) {
                    System.out.println("Value must be at least " + min + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readNonNegativeDouble(String prompt) {
        while (true) {
            String text = readLine(prompt);
            try {
                double value = Double.parseDouble(text);
                if (value < 0) {
                    System.out.println("Value cannot be negative.");
                    continue;
                }
                return value;
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static int readMenuChoice() {
        String text = readLine("Enter your choice: ");
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            return -1;                       // handled by the default case of the switch
        }
    }

    private static Department readDepartment() {
        Department[] values = Department.values();
        System.out.println("Departments:");
        for (int i = 0; i < values.length; i++) {
            System.out.printf("  %d. %s%n", i + 1, values[i].getDisplayName());
        }
        int choice;
        do {
            choice = readInt("Choose department (1-" + values.length + "): ", 1);
            if (choice > values.length) {
                System.out.println("Please choose a number between 1 and " + values.length + ".");
            }
        } while (choice > values.length);
        return values[choice - 1];
    }
}
