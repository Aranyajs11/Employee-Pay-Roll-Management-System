# Viva Questions & Answers – Employee Payroll Management System

1. **What is inheritance here?** `Employee` extends `Person`; `FullTimeEmployee` and `PartTimeEmployee` extend `Employee`, reusing name/age/ID fields.
2. **Why is `Employee` abstract?** A generic employee has no salary formula; the abstract `calculateSalary()` forces each subclass to define its own.
3. **Can we create `new Employee()`?** No, an abstract class cannot be instantiated; we use its subclasses.
4. **What is the `Payable` interface for?** It declares `calculateGrossPay()` and `calculateNetPay()`; `Employee` implements it, and payroll uses a `Payable` reference.
5. **Interface vs abstract class?** An interface defines only a contract (plus constants); an abstract class can hold fields, constructors and shared code.
6. **What is polymorphism?** One `Employee` reference can hold different subclass objects and behave differently.
7. **What is dynamic binding? Where is it?** The method is chosen at runtime by the actual object. In `calculatePayroll()`, `e.calculateSalary()` runs the full-time or part-time formula depending on the object.
8. **Overloading vs overriding?** Overloading: same name, different parameters, same class (`searchEmployee(id)` / `searchEmployee(name, true)`). Overriding: subclass redefines a parent method (`calculateSalary()`).
9. **Constructor overloading example?** `PartTimeEmployee` has one constructor with hourly rate and another using `DEFAULT_HOURLY_RATE`.
10. **Use of `this`?** Refers to the current object: `this.name = name`, and `this(...)` calls another constructor.
11. **Use of `super`?** `super(name, age, phone)` calls the parent constructor; `super.toString()` calls the parent method.
12. **What is static in this project?** `employeeCount` belongs to the class, not to any object; `getEmployeeCount()` is a static method.
13. **Why is `generatePayslip()` final?** So no subclass can change the standard payslip layout; subclasses only customise `earningsDetails()`.
14. **Why is `employeeId` final?** An ID is assigned once and never changes.
15. **What is an enum? Which one is used?** A fixed set of constants; `Department` holds HR, IT, FINANCE, SALES, OPERATIONS with a display name and cost centre.
16. **Explain casting in the code.** `(int) Math.round(net)` narrows long to int; `totalNet / (double) count` avoids integer division.
17. **Why packages?** They organise code: `model` (data classes), `service` (logic), `app` (user interface); classes from other packages need `import`.
18. **Why `Employee[]` instead of only ArrayList?** It demonstrates an array of objects; the size is fixed at 100 and tracked with `count`.
19. **Calculate a full-time net pay.** Basic 50000: HRA 10000, DA 5000, other 5000 → gross 70000; deduction 4000 → net 66000.
20. **Calculate a part-time net pay.** 80 hours × 250 = 20000; deduction 1000 → net 19000.
