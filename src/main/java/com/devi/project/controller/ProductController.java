package com.devi.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.devi.project.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import com.devi.project.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@CrossOrigin
@RestController/*handles incoming HTTP requests and sends responses back to the client, This handles restapi request.
GET http://localhost:8080/products*/
@RequestMapping("/api/v1")//base path for all the endpoints in this controller
public class ProductController {
   // @Autowired

    private ProductService service;
ProductController(ProductService service){
    this.service=service;
}
 @Value("${app.name}")//dont hardcide in java takes from config
    private String appName;

    @Value("${app.version}")
    private String version;
 

    @GetMapping("/info")
    public String getInfo() {
        return appName + " " + version;
    }
    @RequestMapping("/")//map to class or method.
    public String getProducts() {
        return "List of products";
    }
    @GetMapping("/products")//response entity  actualdata+statuscode+hearder(additional info),url query parameter /products?name=Laptop
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
       Page<Product> p=service.getAllProducts(page, size);
        // Logic to retrieve all products from the database
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
    Product p= service.getProduct(id);
    if(p!=null){
        return new ResponseEntity<>(p,HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    
}
@PostMapping("/product")//requestpart ->multipleparts of requestbody(F:json->product obj)multipart/form-data.
public ResponseEntity<?> addProduct(@Valid@RequestPart Product product,@RequestPart MultipartFile image)throws IOException {
    
    Product p=service.addProduct(product,image);

    return new ResponseEntity<>(p,HttpStatus.CREATED);
   

}
@GetMapping("/product/{id}/image")
public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id) {//value store in the path variable id is passed to the method parameter id

    Product p = service.getProduct(id);

    if (p == null || p.getImageData() == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    String type = p.getImageType();

    // 🔥 Fix: handle null MIME type
    if (type == null || type.isEmpty()) {
        type = "image/jpeg"; // default fallback
    }

    return ResponseEntity.ok()
            .contentType(MediaType.valueOf(type))
            .body(p.getImageData());
}//full update
@PutMapping("/product/{id}") 
public ResponseEntity<String> updateProduct(@PathVariable int id,@Valid @RequestPart Product product,@RequestPart(required = false) MultipartFile image)throws IOException {
    Product existingProduct = null;

    existingProduct = service.updateProduct(id,product,image);
    // Logic to update the product
    if(existingProduct!=null)
     return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
    else
           return new ResponseEntity<>("not found",HttpStatus.NOT_FOUND);
  
}
//partial update
@PatchMapping ("/product/{id}")
public ResponseEntity<String> partialUpdate(@PathVariable int id,@RequestBody Product p){
    Product pt=service.partialUpdate(id,p);
    if(p==null)
        return  new ResponseEntity<String>("not found", HttpStatus.NOT_FOUND);
    return  new ResponseEntity<String>(" updated sucessfully", HttpStatus.OK);
}
@DeleteMapping("/product/{id}")
public ResponseEntity<String> deleteProduct(@PathVariable int id) {
    Product p= service.getProduct(id);
    if(p==null){
        return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    }
    // Logic to delete the product
    service.deleteProduct(id);
    return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
}
@GetMapping("/products/search")
public ResponseEntity<Page<Product>> searchProducts(@RequestParam String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    System.out.println("Searching for products with keyword: " + keyword); // Debug log which will show the keyword being searched
    Page<Product> products = service.searchProducts(keyword, page,size);
    return new ResponseEntity<>(products, HttpStatus.OK);
}
}

