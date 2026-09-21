package com.devi.project.model;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
@Entity//table entity + JPA/Hibernate configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id//tells jpa primary of table
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto genaration
    private int id;
    private String name;
    private String description;
    private String brand;
    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;
    private String category;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private LocalDateTime createdAt;
    @Min(value = 1, message = "Quantity must be greater than  0")
    private int quantity;

    private String imageType;//to store image type (e.g., "image/jpeg")
    private String imageName;//to store image name
    @Lob//to store large object (image data)
    private byte[] imageData;//to store image data as byte array

}
