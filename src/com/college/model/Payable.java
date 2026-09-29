package com.college.model;

/**
 * REQ 18 - Payroll-related interface.
 * Implemented by Employee (and therefore by FullTimeEmployee / PartTimeEmployee).
 * The payroll report accesses every employee through a Payable reference.
 */
public interface Payable {

    // Interface constants are implicitly public static final
    String CURRENCY = "Rs.";

    double calculateGrossPay();

    double calculateNetPay();
}
