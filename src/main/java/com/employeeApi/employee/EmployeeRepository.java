package com.employeeApi.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.email=?1")
    Optional<Employee> findEmployeeByEmail(String email);

//    @Query ("SELECT s FROM Student s WHERE s.email=?1")
//    Optional<Student> findStudentByEmail(String email);
}
