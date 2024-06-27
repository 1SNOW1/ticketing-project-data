package com.cydeo.service;

import com.cydeo.dto.RoleDTO;

import java.time.LocalDate;
import java.util.List;

public interface RoleService {

   List<RoleDTO> listAllRoles();
   RoleDTO findById(Long id);
}
