package com.employeeApi.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    public final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long id){
        return getEmployeeById(id);
    }

    private Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public void addNewEmployee(Employee employee){

        Optional<Employee>  employeeOptional = employeeRepository.findEmployeeByEmail(employee.getEmail());

        if(employeeOptional.isPresent()){
            throw new EmployeeNotFoundException("Email not found");
        }
        employeeRepository.save(employee);
        System.out.println((employee));
    }
    public void deleteEmployee(Long employeeId){
        boolean exists = employeeRepository.existsById(employeeId);
        if(!exists){
            throw new EmployeeNotFoundException(
                    "Employee with id " + employeeId + " doesn't exists "
            );
        }
        employeeRepository.deleteById(employeeId);
    }

    public Employee updateEmployee(Employee employeeUpdate, Long employeeId){

        Employee employee = getEmployeeById(employeeId);

        if(employeeUpdate.getName() !=null && employeeUpdate.getName().length() > 0 &&
                !Objects.equals(employee.getName(), employeeUpdate.getName())){
            employee.setName(employeeUpdate.getName());
        }

        if(employeeUpdate.getRole() !=null && employeeUpdate.getRole().length() > 0 &&
                !Objects.equals(employee.getRole(), employeeUpdate.getRole())){
            employee.setRole(employeeUpdate.getRole());
        }

        if(employeeUpdate.getEmail() !=null && employeeUpdate.getEmail().length() > 0 && !Objects.equals(employee.getEmail(), employeeUpdate.getEmail())){
            Optional<Employee> employeeOptional = employeeRepository.findEmployeeByEmail(employeeUpdate.getEmail());
            if(employeeOptional.isPresent()){
                throw new EmployeeNotFoundException("Email taken");
            }
            employee.setEmail(employeeUpdate.getEmail());
        }

        if(employeeUpdate.getDob() !=null && !Objects.equals(employee.getDob(),employeeUpdate.getDob()) ){
            employee.setDob(employeeUpdate.getDob());
        }
       return employeeRepository.save(employee) ;
    }

}
