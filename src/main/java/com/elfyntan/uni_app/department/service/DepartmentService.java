package com.elfyntan.uni_app.department.service;

import com.elfyntan.uni_app.department.dto.DepartmentDTO;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();
    Optional<DepartmentDTO> getDepartmentById(String id);
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO);
    void deleteDepartment(String id);
}
