package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        Project project = projectRepository.findByProjectCode(code);
        return projectMapper.convertToDto(project);
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project> projectsList = projectRepository.findAll(Sort.by("projectCode"));

        return projectsList.stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);

    }

    @Override
    public void save(ProjectDTO dto) {

        dto.setProjectStatus(Status.OPEN);
        Project project = projectMapper.convertToEntity(dto);
        //project.setProjectStatus(Status.OPEN);

        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO dto) {
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());

        Project convertedProject = projectMapper.convertToEntity(dto);

        convertedProject.setId(project.getId());

        convertedProject.setProjectStatus(project.getProjectStatus());

        projectRepository.save(convertedProject);

    }

    @Override
    public void delete(String code) {

        //we need a functionality here that changes the projectCode once we delete a project
        //that way the projectCode can be reused
        Project project = projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);

        project.setProjectCode(project.getProjectCode() + "-" + project.getId());

        projectRepository.save(project);

        taskService.deleteByProject(projectMapper.convertToDto(project));
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        //hey db give me all projects assigned to manager that logged in to system,
        //the userdto below is hardcoded later it will be replaced when security is introduced
        UserDTO currentUserDTO = userService.findByUserName("harold@manager.com");

        //now I have gotten the user from the UI,
        // which returns me in dto, but DB doesn't accept dtos, it accepts entities.
        //thus I have to use the mapper to convert the currentDto into an entity
        User user = userMapper.convertToEntity(currentUserDTO);

        //method that gets all the projects that is assigned to a specific manager
        List<Project> list = projectRepository.getProjectsByAssignedManager(user);


        //projectDto has 2 fields that is not included in the project table in db,
        // but the UI needs those 2 fields, namely unfinishedTaskCounts and completedTaskCounts
        //so, I get every project in the list one by one from DB and convert them into dtos,
        // and then I add the two fields to each one of them
        return list.stream().map(project -> {

            ProjectDTO obj = projectMapper.convertToDto(project);

            obj.setUnfinishedTaskCounts(taskService.totalUnfinishedTaskCount(project.getProjectCode()));
            obj.setCompleteTaskCounts(taskService.totalFinishedTaskCount(project.getProjectCode()));

            return obj;


        }
            ).collect(Collectors.toList());
    }
}
