package com.cydeo.repository;

import com.cydeo.entity.Task;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {



}
