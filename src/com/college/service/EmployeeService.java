package com.college.service;

import com.college.model.Employee;
import com.college.model.FullTimeEmployee;
import com.college.model.Payable;
import com.college.model.PartTimeEmployee;

import java.util.Arrays;

/**
 * REQ 7  - Actual Employee[] array of objects.
 * REQ 10 - Overloaded search methods.
 * REQ 13 - String methods: trim, equalsIgnoreCase, contains, toLowerCase.
 * REQ 16 - Dynamic binding in calculatePayroll().
 * REQ 18 - Payable interface reference.
 * REQ 21 - Second custom package (com.college.service).
 */
public class EmployeeService {

    public static final int MAX_EMPLOYEES = 100;

    private final Employee[] employees = new Employee[MAX_EMPLOYEES];
    private int count = 0;

    public boolean isFull() {
        return count >= employees.length;
    }

    public boolean idExists(String id) {
        return searchEmployee(id) != null;
    }

    public boolean addEmployee(Employee e) {
        if (e == null || isFull() || idExists(e.getEmployeeId())) {
            return false;
        }
        employees[count] = e;
        count++;
        return true;
    }

    public int getTotalEmployees() {
        return count;
    }

    public int getFullTimeCount() {
        int total = 0;
        for (int i = 0; i < count; i++) {
            if (employees[i] instanceof FullTimeEmployee) {
                total++;
            }
        }
        return total;
    }

    public int getPartTimeCount() {
        int total = 0;
        for (int i = 0; i < count; i++) {
            if (employees[i] instanceof PartTimeEmployee) {
                total++;
            }
        }
        return total;
    }

    public void displayAllEmployees() {
        if (count == 0) {
            System.out.println("No employees have been added yet.");
            return;
        }
        System.out.println(String.format("%-8s %-20s %-24s %-20s %-10s",
                "ID", "Name", "Department", "Designation", "Type"));
        System.out.println("-------------------------------------------------------------------------------------");
        for (int i = 0; i < count; i++) {
            Employee e = employees[i];
            System.out.println(String.format("%-8s %-20s %-24s %-20s %-10s",
                    e.getEmployeeId(), e.getName(), e.getDepartment().getDisplayName(),
                    e.getDesignation(), e.getEmployeeType()));
        }
    }

    /** Overload 1: exact ID match (trimmed, case-insensitive). Returns null if not found. */
    public Employee searchEmployee(String id) {
        if (id == null) {
            return null;
        }
        String key = id.trim();
        Employee found = null;
        for (int i = 0; i < count; i++) {
            if (employees[i].getEmployeeId().equalsIgnoreCase(key)) {
                found = employees[i];
                break;                      // stop as soon as the ID is found
            }
        }
        return found;
    }

    /** Overload 2: name search - exact or partial (contains), case-insensitive. */
    public Employee[] searchEmployee(String name, boolean partialMatch) {
        String key = (name == null) ? "" : name.trim().toLowerCase();
        Employee[] matches = new Employee[count];
        int found = 0;
        for (int i = 0; i < count; i++) {
            String empName = employees[i].getName().toLowerCase();
            boolean isMatch;
            if (partialMatch) {
                isMatch = empName.contains(key);
            } else {
                isMatch = empName.equalsIgnoreCase(key);
            }
            if (!isMatch) {
                continue;                   // skip non-matching employees
            }
            matches[found++] = employees[i];
        }
        return Arrays.copyOf(matches, found);
    }

    /**
     * Payroll report. One Employee reference is reused for every element; the
     * JVM picks FullTimeEmployee's or PartTimeEmployee's calculateSalary() at
     * runtime (dynamic binding). Net pay is obtained via a Payable reference.
     */
    public void calculatePayroll() {
        if (count == 0) {
            System.out.println("No employees available for payroll.");
            return;
        }
        double totalGross = 0;
        double totalNet = 0;
        System.out.println(String.format("%-8s %-20s %-10s %12s %12s %12s",
                "ID", "Name", "Type", "Gross", "Deduction", "Net"));
        System.out.println("---------------------------------------------------------------------------");
        Employee e;
        for (int i = 0; i < count; i++) {
            e = employees[i];
            double gross = e.calculateSalary();          // dynamic binding
            Payable p = e;                               // interface reference
            double net = p.calculateNetPay();
            totalGross += gross;
            totalNet += net;
            System.out.println(String.format("%-8s %-20s %-10s %12.2f %12.2f %12.2f",
                    e.getEmployeeId(), e.getName(), e.getEmployeeType(),
                    gross, e.getDeduction(), net));
        }
        System.out.println("---------------------------------------------------------------------------");
        double averageNet = totalNet / (double) count;   // int widened to double
        System.out.println(String.format("Total Gross Payroll : %s %.2f", Payable.CURRENCY, totalGross));
        System.out.println(String.format("Total Net Payroll   : %s %.2f", Payable.CURRENCY, totalNet));
        System.out.println(String.format("Average Net Pay     : %s %.2f", Payable.CURRENCY, averageNet));
    }

    /** Prints the payslip of one employee; returns false if the ID does not exist. */
    public boolean generatePayslip(String id) {
        Employee e = searchEmployee(id);
        if (e == null) {
            return false;
        }
        System.out.print(e.generatePayslip());
        return true;
    }
}
