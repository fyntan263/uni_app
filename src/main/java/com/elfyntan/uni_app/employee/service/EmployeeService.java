package com.elfyntan.uni_app.employee.service;


import com.elfyntan.uni_app.employee.dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    Optional<EmployeeDTO> getEmployeeById(String id);
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(String id, EmployeeDTO employeeDTO);
    void deleteEmployee(String id);
    List<EmployeeDTO> getEmployeesByDepartment(String departmentId);
    List<EmployeeDTO> getEmployeesByManager(String managerId);
    void changeEmployeeStatus(String id, String status);
}