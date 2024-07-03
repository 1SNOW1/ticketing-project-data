package com.cydeo.repository;

import com.cydeo.entity.Task;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {

   //JPQL
   @Query("select count(t) from Task t where t.project.projectCode = ?1 and t.taskStatus <> 'COMPLETE'")
   int totalUnFinishedTaskCount(String projectCode);
   @Query("select count(t) from Task t where t.project.projectCode = ?1 and t.taskStatus = 'COMPLETE'")
   int totalFinishedTaskCount(String projectCode);


}
