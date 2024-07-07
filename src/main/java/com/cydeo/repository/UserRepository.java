package com.cydeo.repository;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findAllByIsDeletedOrderByFirstNameDesc(Boolean deleted);

    //get user based on username
    User findByUserNameAndIsDeleted(String username, Boolean deleted);

    @Transactional
        //it does a try%catch behind the scene to make sure everything goes well
    void deleteByUserName(String username);

    List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description, Boolean deleted);




}
