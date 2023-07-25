package com.employeeApi.employee;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "api/v2/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    CollectionModel<EntityModel<Employee>> getAllEmployees(){
       List<EntityModel<Employee>> allEmployees = employeeService.getEmployees().stream()
                .map(employee -> EntityModel.of(employee,
                        linkTo(methodOn(EmployeeController.class).getOneEmployee(employee.getId())).withSelfRel(),
                        linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees")))
                .collect(Collectors.toList());

            return CollectionModel.of(allEmployees,
                    linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());
    }






    @PostMapping
    public void registerNewEmployee(@RequestBody Employee employee){
         employeeService.addNewEmployee(employee);
    }

    // Single Item

    @GetMapping(path = "{id}")
    EntityModel<Employee> getOneEmployee(@PathVariable("id") Long id){

       Employee employee = employeeService.getEmployee(id);

       return EntityModel.of(employee,
            linkTo(methodOn(EmployeeController.class).getOneEmployee(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
    }

    @DeleteMapping(path = "{id}")
    public void deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
    }

    @PutMapping(path = "{id}")
    Employee updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return employeeService.updateEmployee(newEmployee, id);
    }
}
