package com.devi.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data 
@Entity 
@Table (name="users")
public class User {
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)//auto genaration
private int id;
@Column (unique = true,nullable = false)
private  String username;
@Column (nullable = false)
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//password not send through response
private  String password;
@Column (nullable = false)
private String role;
    
}
