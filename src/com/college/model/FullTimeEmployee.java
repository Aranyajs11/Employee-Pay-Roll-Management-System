package com.college.model;

/**
 * REQ 14/15/16 - Subclass of Employee: super(...) constructor call and
 * overriding of calculateSalary(), getEmployeeType(), earningsDetails(), toString().
 * Gross = Basic + HRA + DA + Other Allowance ; Net = Gross - Deduction
 */
public class FullTimeEmployee extends Employee {

    public static final double HRA_RATE = 0.20;   // 20% of basic
    public static final double DA_RATE = 0.10;    // 10% of basic

    private double basicSalary;
    private double otherAllowance;

    public FullTimeEmployee(String employeeId, String name, int age, String phone,
                            Department department, String designation,
                            double basicSalary, double otherAllowance, double deduction) {
        super(employeeId, name, age, phone, department, designation, deduction);
        if (basicSalary < 0 || otherAllowance < 0) {
            throw new IllegalArgumentException("Salary and allowance cannot be negative.");
        }
        this.basicSalary = basicSalary;
        this.otherAllowance = otherAllowance;
    }

    // REQ 9 - overloaded constructor (no other allowance)
    public FullTimeEmployee(String employeeId, String name, int age, String phone,
                            Department department, String designation,
                            double basicSalary, double deduction) {
        this(employeeId, name, age, phone, department, designation, basicSalary, 0.0, deduction);
    }

    // REQ 10 - overloaded static helpers
    public static double computeGross(double basic, double other) {
        // REQ 3 - precedence: * is evaluated before +, so HRA and DA are
        // calculated first and then everything is added together.
        return basic + basic * HRA_RATE + basic * DA_RATE + other;
    }

    public static double computeGross(double basic) {
        return computeGross(basic, 0.0);
    }

    public double getHra() {
        return basicSalary * HRA_RATE;
    }

    public double getDa() {
        return basicSalary * DA_RATE;
    }

    @Override
    public double calculateSalary() {
        return computeGross(basicSalary, otherAllowance);
    }

    @Override
    public String getEmployeeType() {
        return "Full-Time";
    }

    @Override
    protected String earningsDetails() {
        return earningLine("Basic Salary", basicSalary)
                + earningLine("HRA (20% of Basic)", getHra())
                + earningLine("DA (10% of Basic)", getDa())
                + earningLine("Other Allowance", otherAllowance);
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        if (basicSalary < 0) {
            throw new IllegalArgumentException("Basic salary cannot be negative.");
        }
        this.basicSalary = basicSalary;
    }

    public double getOtherAllowance() {
        return otherAllowance;
    }

    public void setOtherAllowance(double otherAllowance) {
        if (otherAllowance < 0) {
            throw new IllegalArgumentException("Allowance cannot be negative.");
        }
        this.otherAllowance = otherAllowance;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Full-Time | Basic: %.2f", basicSalary);
    }
}
