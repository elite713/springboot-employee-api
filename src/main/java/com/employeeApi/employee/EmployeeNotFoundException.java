package com.employeeApi.employee;

public class EmployeeNotFoundException extends RuntimeException {
    EmployeeNotFoundException(Long id){
        super("Could not find employee Id: " + id);
    }

    EmployeeNotFoundException(String messageError){
        super(messageError);
    }
}
//When an EmployeeNotFoundException is thrown, this extra tidbit
// of Spring MVC configuration is used to render an HTTP 404