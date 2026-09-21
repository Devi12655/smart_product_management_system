package com.devi.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devi.project.model.*;
import java.util.Optional;
//springdata jpa ->user entity ne manage kosam i want repo so user ,primary
public interface UserRepo extends JpaRepository<User,Integer> {
Optional <User> findByUsername(String username);//optional.isempty() ->no user 
}
