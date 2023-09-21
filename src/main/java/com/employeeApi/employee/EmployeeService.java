package com.employeeApi.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee addNewEmployee(Employee employee){

        Optional<Employee>  employeeOptional = employeeRepository.findEmployeeByEmail(employee.getEmail());

        if(employeeOptional.isPresent()){
            throw new EmployeeNotFoundException("Email not found");
        }
        return employeeRepository.save(employee);

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

    public Employee updateEmployee(Employee employeeUpdate, Long Id){

        Employee employee = getEmployeeById(Id);

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

//    public void sendEmail(String to, String name, String subject, String message){
//
//        emailService.send(to,name,subject,message);
//
//    }

//    public String sendEmail(String to, String name, String subject, String message){
//
//        try {
//            emailService.send(to,name,subject,message);
//        }catch(Exception e) {
//            return "fail";
//        }
//        return "ok";
//    }


}
