package com.devi.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.devi.project.model.Product;
@Repository//Spring Data JPA implementation/proxy ->Spring Bean,curd methods->jpa
public interface ProductRepo extends JpaRepository<Product,Integer> {// //Entity class ,primary key
    //JPQL for writing query sql like pattern search
    //define queryJPQL (@Query) java code(+ concat)
     @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")//converting every text into lower case and then comparing
     Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);//The LOWER() function converts both the database values and the search keyword into lowercase, ensuring case-insensitive comparison
    //query parameter ↔ Java method parameter link
}
