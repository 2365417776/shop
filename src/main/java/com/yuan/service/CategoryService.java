package com.yuan.service;

import com.yuan.dao.CategoryDAO;
import com.yuan.pojo.Category;
import com.yuan.pojo.Product;
import com.yuan.tools.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
   @Autowired
   private CategoryDAO categoryDAO;

   public PageNavigator<Category> list(int start, int size, int navigaterPages){
      Sort sort = new Sort(Sort.Direction.DESC, "id");
      Pageable pageable = new PageRequest(start, size, sort);
      Page<Category> pageFromJPA = categoryDAO.findAll(pageable);
      return new PageNavigator<>(pageFromJPA, navigaterPages);
   }

   public List<Category> list(){
      Sort sort = new Sort(Sort.Direction.DESC, "id");
      return categoryDAO.findAll(sort);
   }

   public void add(Category bean){
      categoryDAO.save(bean);
   }

   public void delete(int id){
      categoryDAO.delete(id);
   }

   public Category getOne(int id){
      return categoryDAO.getOne(id);
   }

   public void update(Category bean){
      categoryDAO.save(bean);
   }

   public void removeCategoryFromProduct(List<Category> categories){
      for (Category category:categories){
         removeCategoryFromProduct(category);
      }
   }

   public void removeCategoryFromProduct(Category category){
      List<Product> products = category.getProducts();
      if(products != null){
         for (Product product:products){
            product.setCategory(null);
         }
      }
      List<List<Product>> productsByRow = category.getProductsByRow();
      if(productsByRow != null){
         for (List<Product> ps : productsByRow){
            for (Product p : ps){
               p.setCategory(null);
            }
         }
      }
   }

}
