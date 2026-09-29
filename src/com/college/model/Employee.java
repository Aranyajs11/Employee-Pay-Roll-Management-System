package com.college.model;

/**
 * REQ 17 - Abstract class with abstract method calculateSalary().
 * REQ 18 - Implements the Payable interface.
 * REQ 11 - Static field employeeCount + static method getEmployeeCount().
 * REQ 15 - Constructor calls super(...); toString() calls super.toString().
 * REQ 19 - Overrides toString(), equals() and hashCode().
 * REQ 20 - generatePayslip() is a final method.
 */
public abstract class Employee extends Person implements Payable {

    // REQ 11 - static variable shared by all employees (counts objects created)
    private static int employeeCount = 0;

    private final String employeeId;   // an ID never changes once assigned
    private Department department;
    private String designation;
    private double deduction;

    public Employee(String employeeId, String name, int age, String phone,
                    Department department, String designation, double deduction) {
        super(name, age, phone);                       // REQ 15 - super constructor
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be empty.");
        }
        if (department == null) {
            throw new IllegalArgumentException("Department is required.");
        }
        if (designation == null || designation.trim().isEmpty()) {
            throw new IllegalArgumentException("Designation cannot be empty.");
        }
        if (deduction < 0) {
            throw new IllegalArgumentException("Deduction cannot be negative.");
        }
        this.employeeId = employeeId.trim().toUpperCase();
        this.department = department;
        this.designation = designation.trim();
        this.deduction = deduction;
        employeeCount++;                                // only after every check passed
    }

    // REQ 9 - overloaded constructor, reuses the main one through this(...)
    public Employee(String employeeId, String name, Department department, String designation) {
        this(employeeId, name, MIN_AGE, "N/A", department, designation, 0.0);
    }

    // ---------- abstract methods (each subclass supplies its own version) ----------
    public abstract double calculateSalary();      // gross salary

    public abstract String getEmployeeType();

    protected abstract String earningsDetails();   // earnings lines for the payslip

    // ---------- Payable implementation ----------
    @Override
    public double calculateGrossPay() {
        return calculateSalary();                   // dynamic binding
    }

    @Override
    public double calculateNetPay() {
        return calculateGrossPay() - deduction;     // Net = Gross - Deduction
    }

    // REQ 4 - char returned from a numeric comparison
    public char getPayGrade() {
        double gross = calculateGrossPay();
        char grade;
        if (gross >= 80000) {
            grade = 'A';
        } else if (gross >= 40000) {
            grade = 'B';
        } else {
            grade = 'C';
        }
        return grade;
    }

    /**
     * REQ 20 - FINAL method. The payslip layout is a company-wide standard, so
     * subclasses must not change it. They only customise the earnings section
     * by overriding earningsDetails().
     */
    public final String generatePayslip() {
        double gross = calculateGrossPay();
        double net = calculateNetPay();
        // REQ 4 - explicit cast: Math.round returns long, narrowed to int for display
        int roundedNet = (int) Math.round(net);

        StringBuilder sb = new StringBuilder();
        sb.append(line('=')).append(String.format("%n"));
        sb.append(String.format("%32s%n", "EMPLOYEE PAYSLIP"));
        sb.append(line('=')).append(String.format("%n"));
        sb.append(String.format("%-16s: %s%n", "Employee ID", employeeId));
        sb.append(String.format("%-16s: %s%n", "Name", getName()));
        sb.append(String.format("%-16s: %s (%s)%n", "Department",
                department.getDisplayName(), department.getCostCentre()));
        sb.append(String.format("%-16s: %s%n", "Designation", designation));
        sb.append(String.format("%-16s: %s%n", "Employee Type", getEmployeeType()));
        sb.append(line('-')).append(String.format("%n"));
        sb.append(String.format("EARNINGS%n"));
        sb.append(earningsDetails());                   // overridden in subclasses
        sb.append(line('-')).append(String.format("%n"));
        sb.append(String.format("%-26s: %s %12.2f%n", "Gross Pay", CURRENCY, gross));
        sb.append(String.format("%-26s: %s %12.2f%n", "Deductions", CURRENCY, deduction));
        sb.append(String.format("%-26s: %s %12.2f%n", "NET PAY", CURRENCY, net));
        sb.append(String.format("%-26s: %s %d%n", "Net Pay (rounded)", CURRENCY, roundedNet));
        sb.append(String.format("%-26s: %c%n", "Pay Grade", getPayGrade()));
        sb.append(line('=')).append(String.format("%n"));
        return sb.toString();
    }

    protected static String earningLine(String label, double amount) {
        return String.format("  %-24s: %s %12.2f%n", label, CURRENCY, amount);
    }

    private static String line(char ch) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 48; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    // ---------- static method ----------
    public static int getEmployeeCount() {
        return employeeCount;
    }

    // ---------- getters / setters ----------
    public String getEmployeeId() {
        return employeeId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        if (deduction < 0) {
            throw new IllegalArgumentException("Deduction cannot be negative.");
        }
        this.deduction = deduction;
    }

    // ---------- Object overrides ----------
    @Override
    public String toString() {
        return String.format("ID: %-6s | %s | %s | %s", employeeId, super.toString(),
                department.getDisplayName(), designation);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) obj;
        return this.employeeId.equalsIgnoreCase(other.employeeId);
    }

    @Override
    public int hashCode() {
        return employeeId.toLowerCase().hashCode();
    }
}
