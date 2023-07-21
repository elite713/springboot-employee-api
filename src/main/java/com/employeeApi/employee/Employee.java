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
    private String name;
    private String role;
    private String email;
    private LocalDate dob;
    @Transient
    private Integer age;

    public Employee() {
    }

    public Employee(String name,
                    String role,
                    String email,
                    LocalDate dob) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.dob = dob;
    }

    public Long getId(){
        return this.id;
    }
    public String  getName(){
        return this.name;
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
        this.name = name;
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
               Objects.equals(this.name, employee.name) &&
               Objects.equals(this.role, employee.role) &&
               Objects.equals(email, employee.email) &&
               Objects.equals(dob, employee.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, role, email, dob, age);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }
}
