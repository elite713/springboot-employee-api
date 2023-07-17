package com.employeeApi.employee;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v2/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getEmployees();
    }

    @PostMapping
    public void registerNewEmployee(@RequestBody Employee employee){
         employeeService.addNewEmployee(employee);
    }

    // Single Item

    @GetMapping(path = "{id}")
    Employee getOneEmployee(@PathVariable("id") Long id){
      return employeeService.getEmployee(id);
    }

    @DeleteMapping(path = "{id}")
    void deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
    }

    @PutMapping(path = "{id}")
    Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return employeeService.updateEmployee(newEmployee, id);
    }



}
