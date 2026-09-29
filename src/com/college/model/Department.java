package com.college.model;

/**
 * REQ 5 - Payroll-related enum.
 * Each department carries a display name and a payroll cost-centre code
 * that is printed on payslips.
 */
public enum Department {
    HR("Human Resources", "CC-101"),
    IT("Information Technology", "CC-102"),
    FINANCE("Finance & Accounts", "CC-103"),
    SALES("Sales & Marketing", "CC-104"),
    OPERATIONS("Operations", "CC-105");

    private final String displayName;
    private final String costCentre;

    Department(String displayName, String costCentre) {
        this.displayName = displayName;
        this.costCentre = costCentre;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCostCentre() {
        return costCentre;
    }
}
