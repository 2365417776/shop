package com.yuan.web;

import com.yuan.pojo.Category;
import com.yuan.service.CategoryService;
import com.yuan.tools.ImageUtil;
import com.yuan.tools.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping(value = "/categories")
    public PageNavigator<Category> list(
            @RequestParam(value = "start", defaultValue = "0")int start,
            @RequestParam(value = "size",defaultValue = "5")int size)
            throws Exception{
        start = start < 0 ? 0:start;
        PageNavigator<Category> page = categoryService.list(start, size, 5);
        return page;
    }
    @PostMapping(value = "/categories")
    public Object add(Category bean, MultipartFile image, HttpServletRequest request) throws Exception{
        categoryService.add(bean);
        if(image != null)
            saveOrUpdateImageFile(bean, image, request);
        return bean;
    }

    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request) throws IOException{
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, bean.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
//        把上传的图片复制到创建的文件中
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id")int id, HttpServletRequest request)throws Exception{
        categoryService.delete(id);
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();
        return null;
    }

    @GetMapping(value = "/categories/{id}")
    public Category getOne(@PathVariable("id")int id){
        return categoryService.getOne(id);
    }

    @PutMapping(value = "categories/{id}")
    public void update(Category bean, MultipartFile image, HttpServletRequest request)throws Exception{
        String name = request.getParameter("name");
        bean.setName(name);
        categoryService.update(bean);
        if(image != null)
            saveOrUpdateImageFile(bean, image, request);
    }
}
