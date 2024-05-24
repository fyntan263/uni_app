package com.elfyntan.uni_app.employee.service;

import com.elfyntan.uni_app.department.domain.Department;
import com.elfyntan.uni_app.department.repository.DepartmentRepository;
import com.elfyntan.uni_app.employee.domain.Employee;
import com.elfyntan.uni_app.employee.dto.EmployeeDTO;
import com.elfyntan.uni_app.employee.repository.EmployeeRepository;
import com.elfyntan.uni_app.exceptions.EmailConflictException;
import com.elfyntan.uni_app.exceptions.InvalidIdException;
import com.elfyntan.uni_app.position.repository.JobPositionRepository;
import com.elfyntan.uni_app.utils.Mapper;
import com.elfyntan.uni_app.position.domain.JobPosition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final JobPositionRepository jobPositionRepository;



    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(Mapper::toEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeDTO> getEmployeeById(String id) {
        return employeeRepository.findById(id)
                .map(Mapper::toEmployeeDTO);
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        validateUniqueEmail(employeeDTO.getContactInfo());
        Employee employee = Mapper.toEmployee(employeeDTO);
        setReferences(employee, employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return Mapper.toEmployeeDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO updateEmployee(String id, EmployeeDTO employeeDTO) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            Employee existingEmployee = employeeOpt.get();
            validateUniqueEmail(employeeDTO.getContactInfo(), id);
            existingEmployee.setFirstName(employeeDTO.getFirstName());
            existingEmployee.setLastName(employeeDTO.getLastName());
            existingEmployee.setDob(employeeDTO.getDob());
            existingEmployee.setGender(employeeDTO.getGender());
            existingEmployee.setHireDate(employeeDTO.getHireDate());
            existingEmployee.setSalary(employeeDTO.getSalary());
            existingEmployee.setContactInfo(employeeDTO.getContactInfo());
            existingEmployee.setAddress(Mapper.toAddress(employeeDTO.getAddress()));
            existingEmployee.setStatus(employeeDTO.getStatus());
            setReferences(existingEmployee, employeeDTO);
            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            return Mapper.toEmployeeDTO(updatedEmployee);
        } else {
            throw new InvalidIdException("Employee ID not found: " + id);
        }
    }

    @Override
    public void deleteEmployee(String id) {
        if (!employeeRepository.existsById(id)) {
            throw new InvalidIdException("Employee ID not found: " + id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartment(String departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(Mapper::toEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesByManager(String managerId) {
        return employeeRepository.findByManagerId(managerId).stream()
                .map(Mapper::toEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void changeEmployeeStatus(String id, String status) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            existingEmployee.setStatus(status);
            employeeRepository.save(existingEmployee);
        } else {
            throw new InvalidIdException("Employee ID not found: " + id);
        }
    }

    private void setReferences(Employee employee, EmployeeDTO employeeDTO) {
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new InvalidIdException("Invalid department ID: " + employeeDTO.getDepartmentId()));
        JobPosition jobPosition = jobPositionRepository.findById(employeeDTO.getJobPositionId())
                .orElseThrow(() -> new InvalidIdException("Invalid job position ID: " + employeeDTO.getJobPositionId()));
        Employee manager = null;
        if (employeeDTO.getManagerId() != null) {
            manager = employeeRepository.findById(employeeDTO.getManagerId())
                    .orElseThrow(() -> new InvalidIdException("Invalid manager ID: " + employeeDTO.getManagerId()));
        }
        employee.setDepartment(department);
        employee.setJobPosition(jobPosition);
        employee.setManager(manager);
    }

    private void validateUniqueEmail(String email) {
        if (employeeRepository.existsByContactInfo(email)) {
            throw new EmailConflictException("Email address is already in use.");
        }
    }

    private void validateUniqueEmail(String email, String currentEmployeeId) {
        Optional<Employee> employee = employeeRepository.findByContactInfo(email);
        if (employee.isPresent() && !employee.get().getId().equals(currentEmployeeId)) {
            throw new EmailConflictException("Email address is already in use.");
        }
    }
}