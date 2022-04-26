package com.yuan.web;

import com.yuan.pojo.Product;
import com.yuan.pojo.PropertyValue;
import com.yuan.service.ProductService;
import com.yuan.service.PropertyValueService;
import com.yuan.tools.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
public class PropertyValueController {
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private ProductService productService;

    @GetMapping("/propertyValues/{pid}/get")
    public PageNavigator<PropertyValue> list(
            @PathVariable("pid")int pid,
            @RequestParam(name = "start", defaultValue = "0")int start,
            @RequestParam(name = "size", defaultValue = "10")int size)throws Exception{
        start = start < 0 ? 0 : start;
        Product product = productService.get(pid);
        propertyValueService.init(product);
        PageNavigator<PropertyValue> page = propertyValueService.list(product, start, size, 5);
        return page;
    }

    @PutMapping("/propertyValues")
    public Object update(@RequestBody PropertyValue bean){
        propertyValueService.update(bean);
        return bean;
    }
}
