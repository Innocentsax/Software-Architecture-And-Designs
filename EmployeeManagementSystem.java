/*
Secure Employee Management System (Encapsulation)
Design and implement a secure Employee Management System in Java using the principle of encapsulation.

The system must ensure that all sensitive employee data is properly protected and controlled through getters, setters, and validation logic. Direct access to fields must be restricted.

Requirements

1. Create a Class Employee

Fields (must be private):

employeeId (String)

name (String)

salary (double)

department (String)

email (String)

2. Implement Encapsulation Rules

Provide public getters and setters for all fields

Apply the following validation logic inside setters:

Validation Constraints

employeeId:

Cannot be null or empty

Must start with "EMP"

name:

Cannot be empty

Must be at least 3 characters

salary:

Must be greater than 0

Cannot exceed 1,000,000

department:

Must be one of: "HR", "IT", "Finance", "Sales"

email:

Must contain "@" and "."

3. Business Logic Methods

increaseSalary(double percentage)

Increase salary safely

Prevent unrealistic increases (>50%)

displayEmployeeDetails()

Print all employee details

4. Additional Requirements

Prevent invalid data from being stored

Print meaningful error messages when validation fails

Demonstrate usage in a main method

Create multiple employee objects and test edge cases

Expected Output (Example)

Invalid Employee ID
Invalid Salary
Employee Created Successfully

Name: Ali
Salary: 50000

Salary increased successfully

 */

class Employee {

    // Private fields (Encapsulation)
    private String employeeId;
    private String name;
    private double salary;
    private String department;
    private String email;

    // Constructor
    public Employee(String employeeId, String name, double salary,
                    String department, String email) {

        setEmployeeId(employeeId);
        setName(name);
        setSalary(salary);
        setDepartment(department);
        setEmail(email);
    }

    // Employee ID getter and setter
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()
                || !employeeId.startsWith("EMP")) {
            throw new IllegalArgumentException(
                    "Invalid Employee ID. It must start with EMP.");
        }

        this.employeeId = employeeId;
    }

    // Name getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().length() < 3) {
            throw new IllegalArgumentException(
                    "Invalid Name. Name must contain at least 3 characters.");
        }

        this.name = name;
    }

    // Salary getter and setter
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary <= 0 || salary > 1000000
                || Double.isNaN(salary) || Double.isInfinite(salary)) {
            throw new IllegalArgumentException(
                    "Invalid Salary. Salary must be greater than 0 "
                            + "and cannot exceed 1,000,000.");
        }

        this.salary = salary;
    }

    // Department getter and setter
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null ||
                !(department.equals("HR") ||
                        department.equals("IT") ||
                        department.equals("Finance") ||
                        department.equals("Sales"))) {

            throw new IllegalArgumentException(
                    "Invalid Department. Choose HR, IT, Finance, or Sales.");
        }

        this.department = department;
    }

    // Email getter and setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")
                || !email.contains(".")) {
            throw new IllegalArgumentException(
                    "Invalid Email. Email must contain @ and .");
        }

        this.email = email;
    }

    // Business logic: Increase salary
    public void increaseSalary(double percentage) {

        if (percentage <= 0 || percentage > 50
                || Double.isNaN(percentage)
                || Double.isInfinite(percentage)) {
            throw new IllegalArgumentException(
                    "Invalid percentage. Increase must be between 0 and 50%.");
        }

        double newSalary = salary + (salary * percentage / 100);

        // Reuse salary validation
        setSalary(newSalary);

        System.out.println("Salary increased successfully.");
    }

    // Display employee details
    public void displayEmployeeDetails() {

        System.out.println("\nEmployee Details");
        System.out.println("-------------------------");
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + name);
        System.out.println("Salary: " + salary);
        System.out.println("Department: " + department);
        System.out.println("Email: " + email);
    }
}


public class EmployeeManagementSystem {

    public static void main(String[] args) {

        // Test 1: Invalid Employee ID
        try {
            Employee emp1 = new Employee(
                    "123",
                    "Ali",
                    50000,
                    "IT",
                    "ali@gmail.com"
            );

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Test 2: Invalid Salary
        try {
            Employee emp2 = new Employee(
                    "EMP002",
                    "John",
                    1500000,
                    "HR",
                    "john@gmail.com"
            );

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Test 3: Create a valid employee
        try {
            Employee emp3 = new Employee(
                    "EMP003",
                    "Ali",
                    50000,
                    "IT",
                    "ali@gmail.com"
            );

            System.out.println("\nEmployee Created Successfully.");

            emp3.displayEmployeeDetails();

            // Test 4: Increase salary
            emp3.increaseSalary(10);

            System.out.println("Updated Salary: " + emp3.getSalary());

            // Test 5: Invalid salary increase
            try {
                emp3.increaseSalary(60);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            // Test 6: Change employee department
            emp3.setDepartment("Finance");

            System.out.println("Updated Department: "
                    + emp3.getDepartment());

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Test 7: Create another employee
        try {
            Employee emp4 = new Employee(
                    "EMP004",
                    "Michael",
                    75000,
                    "Sales",
                    "michael@gmail.com"
            );

            emp4.displayEmployeeDetails();

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}