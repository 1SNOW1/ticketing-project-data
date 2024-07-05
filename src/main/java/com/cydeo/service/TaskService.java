package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;

import java.util.List;

public interface TaskService {

    List<TaskDTO> listAllTasks();
    void save(TaskDTO dto);
    void update(TaskDTO dto);
    void delete(Long id);
    TaskDTO findById(Long id);

    void deleteByProject(ProjectDTO projectDTO);


    int totalUnfinishedTaskCount(String projectCode);

    int totalFinishedTaskCount(String projectCode);

    void completeByProject(ProjectDTO projectDTO);

    List<TaskDTO> listAllTasksByStatusIsNot(Status status);

    List<TaskDTO> listAllTasksByStatus(Status status);

    List<TaskDTO> listAllNonCompletedAssignedEmployee(UserDTO assignedManager);
}
