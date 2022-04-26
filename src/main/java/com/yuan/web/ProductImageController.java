package com.yuan.web;

import com.yuan.pojo.Product;
import com.yuan.pojo.ProductImage;
import com.yuan.service.CategoryService;
import com.yuan.service.ProductImageService;
import com.yuan.service.ProductService;
import com.yuan.tools.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/productImages/{pid}/get")
    public List<ProductImage> list(@RequestParam("type")String type, @PathVariable("pid")int pid)throws Exception{
        Product product =productService.get(pid);
        if(ProductImageService.type_single.equals(type)){
            List<ProductImage> singles = productImageService.listSingleProductImages(product);
            return singles;
        }
        else if(ProductImageService.type_detail.equals(type)){
            List<ProductImage> details = productImageService.listDetailProductImages(product);
            return details;
        }
        else
            return new ArrayList<>();
    }

    @PostMapping(value = "/productImages")
    public void add(@RequestParam("pid")int pid, @RequestParam("type")String type, MultipartFile image, HttpServletRequest request)throws Exception{
        ProductImage productImage = new ProductImage();
        Product product = productService.get(pid);
        productImage.setProduct(product);
        productImage.setType(type);

        productImageService.add(productImage);
        String folder = "img/";
        if(ProductImageService.type_single.equals(productImage.getType()))
            folder += "productSingle";
        else
            folder += "productDetail";

        File imageFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder, productImage.getId()+".jpg");
        String fileName = file.getName();
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try{
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }catch (IOException e){
            e.printStackTrace();
        }

        if(ProductImageService.type_single.equals(productImage.getType())){
            String imageFolder_small = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }
    }

    @DeleteMapping(value = "/productImages/{id}")
    public String delete(@PathVariable("id")int id, HttpServletRequest request){
        ProductImage bean = productImageService.get(id);
        productImageService.delete(id);

        String folder = "img/";
        if(ProductImageService.type_single.equals(bean.getType()))
            folder += "productSingle";
        else
            folder += "productDetail";

        File imageFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder, bean.getId() + ".jpg");
        String fileName = file.getName();
        file.delete();
        if(ProductImageService.type_single.equals(bean.getType())){
            String imageFolder_small = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.delete();
            f_middle.delete();
        }
        return null;
    }
}
