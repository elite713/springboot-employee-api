package com.employeeApi.employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table
public class Employee {
    @Id
    @SequenceGenerator(
            name = "Employee_sequence",
            sequenceName = "employee_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )

    private Long id;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private LocalDate dob;
    @Transient
    private Integer age;

    public Employee() {
    }

    public Employee(String firstName,
                    String lastName,
                    String role,
                    String email,
                    LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.dob = dob;
    }

    public Long getId(){
        return this.id;
    }
    public String  getName(){
        return this.firstName + " "+ this.lastName;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return  this.lastName;
    }
    public String getRole(){
        return this.role;
    }
    public String getEmail(){
        return  this.email;
    }
    public LocalDate getDob(){
        return this.dob = dob;
    }
    public Integer getAge(){ return Period.between(this.dob, LocalDate.now()).getYears(); }


    public void setId(Long id){
        this.id = id;
    }
    public void setName(String name){
        String[] parts = name.split(" ");
        this.firstName = parts[0];
        this.lastName = parts[1];
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setRole(String role){
        this.role = role;
    }
    public void setEmail(String email){ this.email = email;}
    public void setDob(LocalDate dob){
        this.dob = dob;
    }
    public void setAge(Integer age){this.age = age; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(this.id, employee.id) &&
               Objects.equals(this.firstName, employee.firstName) &&
               Objects.equals(this.lastName, employee.lastName)&&
               Objects.equals(this.role, employee.role) &&
               Objects.equals(email, employee.email) &&
               Objects.equals(dob, employee.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName,lastName, role, email, dob, age);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }
}
