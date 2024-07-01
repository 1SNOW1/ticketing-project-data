package com.cydeo.repository;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    //get user based on username
    User findByUserName(String username);

    @Transactional
        //it does a try%catch behind the scene to make sure everything goes well
    void deleteByUserName(String username);




}
