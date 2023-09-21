package com.employeeApi.employee;
import com.employeeApi.customerOrder.Order;
import com.employeeApi.customerOrder.OrderRepository;


import com.employeeApi.customerOrder.Status;
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
    CommandLineRunner initDatabase(EmployeeRepository repository, OrderRepository orderRepository){
        return args -> {
            log.info("Preloading " + repository.save(new Employee(
                    "Vilama",
                    "Palma",
                    "Marketing",
                    "vilma@gmail.com",
                    LocalDate.of(2000, Month.MARCH, 10))));
            log.info("Preloading " + repository.save(new Employee(
                    "Juan",
                    "Garcia",
                    "Software Engineer",
                    "garcia@gmail.com",
                    LocalDate.of(2012, Month.JANUARY,
                            5))));

            log.info("Preloading " + orderRepository.save(new Order(
                    "MacBook Pro", Status.COMPLETED)));
            log.info("Preloading " + orderRepository.save(new Order(
                    "iPhone", Status.IN_PROGRESS)));

            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
        };
    }
}


