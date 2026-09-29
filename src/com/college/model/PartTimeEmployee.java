package com.college.model;

/**
 * REQ 14/15/16 - Subclass of Employee for hourly staff.
 * Gross = Hours Worked x Hourly Rate ; Net = Gross - Deduction
 */
public class PartTimeEmployee extends Employee {

    public static final double DEFAULT_HOURLY_RATE = 200.0;

    private double hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(String employeeId, String name, int age, String phone,
                            Department department, String designation,
                            double hoursWorked, double hourlyRate, double deduction) {
        super(employeeId, name, age, phone, department, designation, deduction);
        if (hoursWorked < 0 || hourlyRate < 0) {
            throw new IllegalArgumentException("Hours and hourly rate cannot be negative.");
        }
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    // REQ 9 - overloaded constructor: uses the default hourly rate
    public PartTimeEmployee(String employeeId, String name, int age, String phone,
                            Department department, String designation,
                            double hoursWorked, double deduction) {
        this(employeeId, name, age, phone, department, designation,
                hoursWorked, DEFAULT_HOURLY_RATE, deduction);
    }

    public static double computeGross(double hours, double rate) {
        return hours * rate;
    }

    @Override
    public double calculateSalary() {
        return computeGross(hoursWorked, hourlyRate);
    }

    @Override
    public String getEmployeeType() {
        return "Part-Time";
    }

    @Override
    protected String earningsDetails() {
        return String.format("  %-24s: %15.2f hrs%n", "Hours Worked", hoursWorked)
                + earningLine("Hourly Rate", hourlyRate)
                + earningLine("Earned (Hours x Rate)", calculateSalary());
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        if (hoursWorked < 0) {
            throw new IllegalArgumentException("Hours cannot be negative.");
        }
        this.hoursWorked = hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("Hourly rate cannot be negative.");
        }
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String toString() {
        return super.toString()
                + String.format(" | Part-Time | %.1f hrs @ %.2f", hoursWorked, hourlyRate);
    }
}
