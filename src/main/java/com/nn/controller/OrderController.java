package com.nn.controller;

import com.nn.empty.Order;
import com.nn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @ClassName OrderController
 * @Author nn
 * @Date 2020/7/22 11:12
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/queryById/{id}")
    public Object queryByName(@PathVariable("id")Integer id) throws ExecutionException, InterruptedException {
        Order order = orderService.queryById(id);

        return order;
    }
}
