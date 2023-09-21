package com.employeeApi.customerOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
public class OrderController {

    private final OrderService orderService;
    private final OrderModelAssembler assembler;
    @Autowired
    OrderController(OrderService orderService, OrderModelAssembler assembler){
        this.orderService = orderService;
        this.assembler = assembler;
    }

    @GetMapping(path="/orders")
    CollectionModel<EntityModel<Order>> all() {
        List<EntityModel<Order>> orders = orderService.getOrders().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    EntityModel<Order> one(@PathVariable Long id){

        Order order = orderService.getOrderById(id);
        return assembler.toModel(order);
  }

  @PostMapping("/orders")
    ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order){

        Order newOrder = orderService.addNewOrder(order);

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).one(newOrder.getId()))
                        .toUri()).body(assembler.toModel(newOrder));
    }

    @DeleteMapping("/orders/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id){
        Order order;
        try {
            order = orderService.deleteOrder(id);
        } catch (IllegalStateException e){
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail(e.getMessage()));
        }
            return
                    ResponseEntity.ok(assembler.toModel(order));
    }

    @PutMapping("/orders/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id){

        Order order;
        try {
            order = orderService.completedOrder(id);
        } catch (IllegalStateException e){
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE,
                            MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                    .body(Problem.create()
                            .withTitle("Method not allowed")
                            .withDetail(e.getMessage()));

        }
            return
                    ResponseEntity.ok(assembler.toModel(order));
    }
}
