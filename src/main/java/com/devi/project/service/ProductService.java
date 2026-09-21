package com.devi.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devi.project.repository.ProductRepo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import com.devi.project.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
public class ProductService {
       @Value ("${imageSize}")
        private Long imageSize;
    @Autowired
    private ProductRepo repo;
    public Page<Product> getAllProducts(int page,int size) {
       Pageable pageable = PageRequest.of(page, size);//to retrive data 0th page lo 10 records->request
       // Logic to retrieve all products from the database
        return repo.findAll(pageable); // Placeholder for actual product list
    }
   public Product getProduct(int id){
    return repo.findById(id).orElse(null);
}
public Product addProduct(Product product,MultipartFile image) throws IOException{
    product.setImageType(image.getContentType());//seperate multipart file (img.jpg)
    product.setImageName(image.getOriginalFilename());
    product.setImageData(image.getBytes());
    product.setCreatedAt(LocalDateTime.now());//setter methods where we set the current date and time as the createdAt value for the product. This ensures that whenever a new product is added, it will have a timestamp indicating when it was created.
    return repo.save(product);
    
}
// public Product updateProduct(int id,Product product,MultipartFile image) throws IOException{
//     product.setImageData(image.getBytes());
//     product.setImageType(image.getContentType());
//     product.setImageName(image.getOriginalFilename());
//     return repo.save(product);
// }
public Product updateProduct(int id, Product product, MultipartFile image) throws IOException {
System.out.println("ID received = " + id);
;
    Product existing = repo.findById(id).orElse(null);
System.out.println("Existing = " + existing);
    if (existing == null) 
        return null;

    // ✅ update normal fields
    existing.setName(product.getName());
    existing.setBrand(product.getBrand());
    existing.setCategory(product.getCategory());
    existing.setPrice(product.getPrice());
    existing.setQuantity(product.getQuantity());
    existing.setDescription(product.getDescription());
    existing.setReleaseDate(product.getReleaseDate());

    // 🔥 ONLY update image if new one is provided
    if (image != null && !image.isEmpty()) {//img provide and contain data (not empty)
        if(image.getSize()>imageSize)
             throw new RuntimeException("image size should be less than 5MB");
        existing.setImageData(image.getBytes());
        existing.setImageType(image.getContentType());
        existing.setImageName(image.getOriginalFilename());
    }

   return  repo.save(existing);
}
public Product partialUpdate(int id,Product p){
    Product e=repo.findById(id).orElse(null);
    if(e==null)
        return null;
    e.setPrice(p.getPrice());
    e.setQuantity(p.getQuantity());
    return repo.save(e);
}
public void deleteProduct(int id){
    repo.deleteById(id);
}
public Page<Product> searchProducts(String keyword, int page,int size) {
     Pageable pageable = PageRequest.of(page, size);
    return repo.searchProducts(keyword, pageable);
}
}
