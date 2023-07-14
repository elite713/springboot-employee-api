package com.employeeApi.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;


@Configuration
public class EmployeeConfig { // Class to Load DataBase
    private static final Logger log = LoggerFactory.getLogger(EmployeeConfig.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Employee(
                    "Vilma Palma",
                    "Marketing",
                    "vilma@gmail.com",
                    LocalDate.of(2000, Month.MARCH, 10))));
            log.info("Preloading " + repository.save(new Employee("Nelson Birma",
                    "Software Ingennier",
                    "nelson@gmail.com",
                    LocalDate.of(2012, Month.JANUARY,
                            5))));
        };
    }
}


//    Employee nelson = new Employee(
//            "Nelson Birma",
//            "Software Ingennier",
//            "nelson@gmail.com",
//            LocalDate.of(2012, Month.JANUARY,
//                    5)
//    );
//    Employee vilma = new Employee(
//            "Vilma Palma",
//            "Marketing",
//            "vilma@gmail.com",
//            LocalDate.of(2000, Month.MARCH, 10)