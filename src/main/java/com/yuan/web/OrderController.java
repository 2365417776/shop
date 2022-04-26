package com.yuan.web;

import com.yuan.pojo.Order;
import com.yuan.service.OrderItemService;
import com.yuan.service.OrderService;
import com.yuan.tools.PageNavigator;
import com.yuan.tools.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/orders")
    public PageNavigator<Order> list(
            @RequestParam(value = "start", defaultValue = "0")int start,
            @RequestParam(value = "size", defaultValue = "5")int size)throws Exception{
        start = start < 0 ? 0 : start;
        PageNavigator<Order> page = orderService.list(start, size, 5);
        orderItemService.fill(page.getContent());
        orderService.removeOrderFromOrderItem(page.getContent());
        return page;
    }
    @PutMapping("deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable int oid)throws IOException{
        Order order = orderService.get(oid);
        order.setDeliveryDate(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.update(order);
        return Result.success();
    }
}
