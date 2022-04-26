package com.yuan.web;

import com.yuan.pojo.Product;
import com.yuan.service.CategoryService;
import com.yuan.service.ProductImageService;
import com.yuan.service.ProductService;
import com.yuan.tools.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductImageService productImageService;

    @GetMapping(value = "/products/{cid}/get")
    public PageNavigator<Product> list(@PathVariable("cid")int cid,
                                       @RequestParam(value = "start", defaultValue = "0")int start,
                                       @RequestParam(value = "size", defaultValue = "5")int size)throws Exception{
        start = start < 0 ? 0 : start;
        PageNavigator<Product> page = productService.list(cid, start, size, 5);
        productImageService.setFirstProductImages(page.getContent());
        return page;
    }

    @GetMapping("/products/{id}")
    public Product get(@PathVariable("id")int id)throws Exception{
        return productService.get(id);
    }

    @PostMapping("/products")
    public void add(@RequestBody Product bean)throws Exception{
        productService.add(bean);
    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable("id")int id, HttpServletRequest request)throws Exception{
        productService.delete(id);
        return null;
    }

    @PutMapping("/products")
    public void update(@RequestBody Product bean) throws Exception{
        productService.update(bean);
    }
}
