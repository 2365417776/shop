package com.yuan.web;

import com.yuan.pojo.Property;
import com.yuan.service.PropertyService;
import com.yuan.tools.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @GetMapping(value = "/properties/{cid}")
    public PageNavigator<Property> list(@PathVariable("cid")int cid,
                               @RequestParam(value = "start", defaultValue = "0")int start,
                               @RequestParam(value = "size", defaultValue = "5")int size)throws Exception{
        start = start < 0? 0:start;
        PageNavigator<Property> page = propertyService.list(cid, start, size, 5);
        return page;
    }

    @PutMapping(value = "/properties")
    public void update(@RequestBody Property bean)throws Exception{
        propertyService.update(bean);
    }

    @GetMapping(value = "/properties/{id}/get")
    public Property get(@PathVariable(value = "id")int id)throws Exception{
        return propertyService.get(id);
    }

    @PostMapping(value = "/properties")
    public void add(@RequestBody Property property)throws Exception{
        propertyService.add(property);
    }
    @DeleteMapping(value = "/properties/{id}")
    public String delete(@PathVariable("id")int id)throws Exception{
        propertyService.delete(id);
        return null;
    }
}
