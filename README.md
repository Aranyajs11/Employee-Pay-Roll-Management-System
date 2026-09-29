# Employee Payroll Management System

**B.Sc. (BSTCs) – Semester V – Java Programming Mini-Project (REVA University)**

## 1. Objective / Problem Statement
Companies must calculate salaries for permanent (full-time) and hourly (part-time) staff, keep employee records and issue payslips. This console application (plain Java, no GUI/database/libraries) stores employees in an `Employee[]` array, calculates payroll polymorphically and prints formatted payslips, while demonstrating all 21 required Java/OOP concepts.

## 2. Features
- Add full-time and part-time employees (with validation)
- Display all employees; search by ID (exact) and by name (partial, case-insensitive)
- Payroll report with total and average net pay
- Formatted payslip per employee
- Total employee count (stored + static object counter)

## 3. Folder Structure
```
EmployeePayrollManagement/
└── src/com/college/
    ├── model/    Person, Employee, FullTimeEmployee, PartTimeEmployee, Department, Payable
    ├── service/  EmployeeService
    └── app/      Main
```

## 4. Class Descriptions
| Class | Description |
|---|---|
| `Person` | Name, age, phone; constructors; encapsulation |
| `Employee` | Abstract; ID, department, designation, deduction; static `employeeCount`; abstract `calculateSalary()`; final `generatePayslip()`; implements `Payable` |
| `FullTimeEmployee` | Basic + HRA + DA + other allowance |
| `PartTimeEmployee` | Hours × hourly rate |
| `Department` | Enum with display name and cost-centre code |
| `Payable` | Interface: `calculateGrossPay()`, `calculateNetPay()`, `CURRENCY` |
| `EmployeeService` | `Employee[100]` storage, add/display/search, payroll, payslip |
| `Main` | Scanner menu, input validation only |

## 5. Payroll Formulas
- **Full-time:** HRA = 20% of Basic; DA = 10% of Basic; Gross = Basic + HRA + DA + Other Allowance; Net = Gross − Deduction
- **Part-time:** Gross = Hours Worked × Hourly Rate; Net = Gross − Deduction
- Pay grade: Gross ≥ 80000 → A, ≥ 40000 → B, otherwise C

## 6. Compile & Run
```
cd EmployeePayrollManagement/src
javac com/college/model/*.java com/college/service/*.java com/college/app/Main.java
java com.college.app.Main
```

## 7. Sample Input / Output
See `SAMPLE_OUTPUT.txt` (full run). Extract:
```
Enter your choice: 6
ID       Name                 Type              Gross    Deduction          Net
E101     Rahul Sharma         Full-Time      70000.00      4000.00     66000.00
P201     Meena Iyer           Part-Time      20000.00      1000.00     19000.00
Total Gross Payroll : Rs. 90000.00
Total Net Payroll   : Rs. 85000.00
Average Net Pay     : Rs. 42500.00
```

## 8. Validation
Empty ID/name/designation, duplicate ID, age < 18, negative salary/allowance/hours/rate/deduction, deduction greater than gross, invalid menu choice, non-numeric input, department out of range; all search input is trimmed.

## 9. OOP Concepts Used
Encapsulation, inheritance (multilevel), abstraction, interface, polymorphism (overriding + dynamic binding), overloading (methods and constructors), static members, `this`/`super`, `final`, enum, casting, packages, arrays of objects.

## 10. 21-Point Traceability Table
| # | Requirement | File/Class | Method/Field | Demonstration |
|---|---|---|---|---|
| 1 | Encapsulation | `Person`, `Employee`, `FullTimeEmployee`, `PartTimeEmployee` | private `name`, `age`, `phone`, `employeeId`, `deduction`, `basicSalary`, `hoursWorked`…; getters/setters | Fields private; validated setters such as `setAge()`, `setDeduction()` |
| 2 | Data types, instance/local/static, final | `Person`, `Employee`, `FullTimeEmployee`, `Payable` | `String name`, `int age`, `double deduction`, `char grade` (local in `getPayGrade`), `boolean isMatch`; static `employeeCount`; `final MIN_AGE`, `HRA_RATE`, `DA_RATE`, `MAX_EMPLOYEES`, `CURRENCY` | Each variable kind used in payroll logic |
| 3 | Operators + precedence | `FullTimeEmployee`, `EmployeeService`, `Employee` | `computeGross()`; `totalGross += gross`; `deduction > gross`; `gross >= 80000`; `\|\|` in subclass/`Person` constructors | `basic + basic * HRA_RATE + basic * DA_RATE + other` (`*` before `+`); relational, logical (`\|\|`, `!`), assignment (`+=`, `++`) |
| 4 | Type conversion / casting | `Employee`, `EmployeeService` | `generatePayslip()`, `calculatePayroll()` | `(int) Math.round(net)` (long→int narrowing); `totalNet / (double) count` (int→double) |
| 5 | Payroll enum | `Department` | `HR, IT, FINANCE, SALES, OPERATIONS`; `getDisplayName()`, `getCostCentre()` | Chosen in `Main.readDepartment()`; cost centre printed on payslip |
| 6 | if/else, switch, loops, break, continue, return | `Main`, `EmployeeService` | `Main.main()` (while + switch); `readNonEmpty()` (do-while); `searchEmployee(String)` (`break`); `searchEmployee(String,boolean)` (`continue`); `for` loops; many `return` | All control statements used in real logic |
| 7 | `Employee[]` array of objects | `EmployeeService` | `private final Employee[] employees = new Employee[MAX_EMPLOYEES]` | Filled by `addEmployee()`, traversed in display/search/payroll |
| 8 | Scanner + printf/String.format | `Main`, `Employee`, `EmployeeService` | `Scanner scanner`, `readLine()`; `System.out.printf` in `readDepartment()`; `String.format` in `generatePayslip()`, `calculatePayroll()` | Input via Scanner, formatted tables and payslip |
| 9 | Constructor overloading | `Person`, `Employee`, `FullTimeEmployee`, `PartTimeEmployee` | `Person()`/`Person(name,age,phone)`; two `Employee` constructors; 9-arg and 8-arg constructors in both subclasses | Shorter constructors delegate with `this(...)` |
| 10 | Method overloading | `EmployeeService`, `FullTimeEmployee` | `searchEmployee(String)` / `searchEmployee(String, boolean)`; `computeGross(double,double)` / `computeGross(double)` | Same name, different parameter lists |
| 11 | Static field/method | `Employee` | `private static int employeeCount`; `getEmployeeCount()` | Incremented in constructor; shown by menu option 8 |
| 12 | Explicit `this` | `Person`, `Employee`, `FullTimeEmployee` | `this("Unknown", MIN_AGE, "N/A")`; `this.name = name`; `this.employeeId = …` | Constructor chaining and field/parameter disambiguation |
| 13 | String methods | `EmployeeService`, `Employee`, `Person`, `Main` | `trim()`, `equalsIgnoreCase()`, `contains()`, `toLowerCase()`, `toUpperCase()`, `isEmpty()` | ID/name search, ID normalisation, empty-input checks |
| 14 | Inheritance | `Person` → `Employee` → `FullTimeEmployee`/`PartTimeEmployee` | `extends` clauses | Three-level hierarchy |
| 15 | `super` | `Employee`, `FullTimeEmployee`, `PartTimeEmployee` | `super(name, age, phone)`; `super(employeeId, …)`; `super.toString()` | Parent constructor and method reuse |
| 16 | Overriding + dynamic binding | `Employee` + subclasses; `EmployeeService` | `calculateSalary()`, `getEmployeeType()`, `earningsDetails()`, `toString()`; `calculatePayroll()` | One `Employee e` reference reassigned per element; `e.calculateSalary()` runs the Full-time or Part-time version at runtime |
| 17 | Abstract Employee + `calculateSalary()` | `Employee` | `public abstract double calculateSalary()` | Cannot be instantiated; subclasses must implement |
| 18 | Payable interface | `Payable`, `Employee`, `EmployeeService` | `calculateGrossPay()`, `calculateNetPay()`; `Payable p = e;` in `calculatePayroll()` | Net pay obtained through interface reference |
| 19 | Override `toString()`/`equals()` | `Person`, `Employee`, `FullTimeEmployee`, `PartTimeEmployee` | `toString()`, `equals(Object)`, `hashCode()` | Used by search output and duplicate detection |
| 20 | Meaningful `final` | `Employee` | `public final String generatePayslip()`; `final String employeeId` | Comment explains: payslip layout is company-wide and must not be altered by subclasses |
| 21 | ≥ 2 custom packages | all files | `com.college.model`, `com.college.service`, `com.college.app` | Cross-package `import` statements in service and app |
