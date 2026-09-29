package com.college.model;

/**
 * REQ 1  - Encapsulation: private fields with public getters/setters.
 * REQ 9  - Constructor overloading: default and parameterized constructors.
 * REQ 12 - Explicit "this" (this(...) chaining and this.field = value).
 * REQ 14 - Top of the hierarchy Person -> Employee -> FullTime/PartTime.
 */
public class Person {

    // REQ 2 - final constant
    public static final int MIN_AGE = 18;

    private String name;   // instance variables of different types
    private int age;
    private String phone;

    // Default constructor chains to the parameterized one
    public Person() {
        this("Unknown", MIN_AGE, "N/A");
    }

    public Person(String name, int age, String phone) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (age < MIN_AGE) {
            throw new IllegalArgumentException("Age must be at least " + MIN_AGE + ".");
        }
        this.name = name.trim();
        this.age = age;
        this.phone = (phone == null || phone.trim().isEmpty()) ? "N/A" : phone.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < MIN_AGE) {
            throw new IllegalArgumentException("Age must be at least " + MIN_AGE + ".");
        }
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return name + " (Age " + age + ", Phone " + phone + ")";
    }
}
