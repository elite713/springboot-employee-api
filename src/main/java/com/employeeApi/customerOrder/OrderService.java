package com.employeeApi.customerOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order addNewOrder(Order order){

        order.setStatus(Status.IN_PROGRESS);
        return orderRepository.save(order);
    }

    public Order deleteOrder(Long id){

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if(order.getStatus() == Status.CANCELLED || order.getStatus() == Status.COMPLETED){
            throw new IllegalStateException("You can't Cancel an order that is in the " + order.getStatus() + " status");
        }

        if (order.getStatus() == Status.IN_PROGRESS){
            order.setStatus(Status.CANCELLED);
            return orderRepository.save(order);
        }
        return order;
    }

    public Order completedOrder(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new OrderNotFoundException(id));

        if(order.getStatus() == Status.COMPLETED || order.getStatus() == Status.CANCELLED){
            throw new IllegalStateException("You can't Complete an order that is in the " + order.getStatus() + " status");
        }
        if(order.getStatus() == Status.IN_PROGRESS){
            order.setStatus(Status.COMPLETED);
            return orderRepository.save(order);
        }
        return order;
    }

}
