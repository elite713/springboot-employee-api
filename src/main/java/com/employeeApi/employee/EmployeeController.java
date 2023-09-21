package com.employeeApi.employee;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "api/v2/employees")
public class EmployeeController {
    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    private final EmployeeModelAssembler assembler;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeModelAssembler assembler){
        this.employeeService = employeeService;
        this.assembler = assembler;
    }

    @GetMapping
    CollectionModel<EntityModel<Employee>> getAllEmployees(){
       List<EntityModel<Employee>> allEmployees = employeeService.getEmployees().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

            return CollectionModel.of(allEmployees,
                    linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<?>  registerNewEmployee(@RequestBody Employee employee){

        EntityModel<Employee> entityModel = assembler.toModel(employeeService.addNewEmployee(employee));


        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // Single Item

    @GetMapping(path = "{id}")
    EntityModel<Employee> getOneEmployee(@PathVariable("id") Long id){

        log.info("in getOneEmployee");

       Employee employee = employeeService.getEmployeeById(id);

       return assembler.toModel(employee);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return  ResponseEntity.noContent().build();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        EntityModel<Employee> entityModel = assembler.toModel(employeeService.updateEmployee(newEmployee, id));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
