package com.elfyntan.uni_app.employee.service;

import com.elfyntan.uni_app.department.domain.Department;
import com.elfyntan.uni_app.department.repository.DepartmentRepository;
import com.elfyntan.uni_app.employee.domain.Employee;
import com.elfyntan.uni_app.employee.dto.EmployeeDTO;
import com.elfyntan.uni_app.employee.repository.EmployeeRepository;
import com.elfyntan.uni_app.exceptions.EmailConflictException;
import com.elfyntan.uni_app.exceptions.InvalidIdException;
import com.elfyntan.uni_app.position.domain.JobPosition;
import com.elfyntan.uni_app.position.repository.JobPositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private JobPositionRepository jobPositionRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setFirstName("John");
        employee.setLastName("Doe");

        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        assertEquals(1, employees.size());
        assertEquals("John", employees.get(0).getFirstName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setFirstName("John");
        employee.setLastName("Doe");

        when(employeeRepository.findById("1")).thenReturn(Optional.of(employee));

        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById("1");

        assertTrue(employeeDTO.isPresent());
        assertEquals("John", employeeDTO.get().getFirstName());
        verify(employeeRepository, times(1)).findById("1");
    }

    @Test
    void testCreateEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setContactInfo("john.doe@example.com");
        employeeDTO.setDepartmentId("dept1");
        employeeDTO.setJobPositionId("pos1");

        Department department = new Department();
        department.setId("dept1");

        JobPosition jobPosition = new JobPosition();
        jobPosition.setId("pos1");

        when(departmentRepository.findById("dept1")).thenReturn(Optional.of(department));
        when(jobPositionRepository.findById("pos1")).thenReturn(Optional.of(jobPosition));
        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());

        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);

        assertNotNull(createdEmployee);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testCreateEmployeeWithExistingEmail() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setContactInfo("john.doe@example.com");

        when(employeeRepository.existsByContactInfo("john.doe@example.com")).thenReturn(true);

        assertThrows(EmailConflictException.class, () -> {
            employeeService.createEmployee(employeeDTO);
        });
    }

    @Test
    void testUpdateEmployee() {
        Employee existingEmployee = new Employee();
        existingEmployee.setId("1");
        existingEmployee.setFirstName("John");
        existingEmployee.setLastName("Doe");

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Jane");
        employeeDTO.setLastName("Doe");
        employeeDTO.setContactInfo("jane.doe@example.com");
        employeeDTO.setDepartmentId("dept1");
        employeeDTO.setJobPositionId("pos1");

        Department department = new Department();
        department.setId("dept1");

        JobPosition jobPosition = new JobPosition();
        jobPosition.setId("pos1");

        when(employeeRepository.findById("1")).thenReturn(Optional.of(existingEmployee));
        when(departmentRepository.findById("dept1")).thenReturn(Optional.of(department));
        when(jobPositionRepository.findById("pos1")).thenReturn(Optional.of(jobPosition));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        EmployeeDTO updatedEmployee = employeeService.updateEmployee("1", employeeDTO);

        assertNotNull(updatedEmployee);
        assertEquals("Jane", updatedEmployee.getFirstName());
        verify(employeeRepository, times(1)).findById("1");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployeeWithInvalidId() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("Jane");
        employeeDTO.setLastName("Doe");

        when(employeeRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            employeeService.updateEmployee("1", employeeDTO);
        });
    }

    @Test
    void testDeleteEmployee() {
        when(employeeRepository.existsById("1")).thenReturn(true);
        doNothing().when(employeeRepository).deleteById("1");

        employeeService.deleteEmployee("1");

        verify(employeeRepository, times(1)).existsById("1");
        verify(employeeRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteEmployeeWithInvalidId() {
        when(employeeRepository.existsById("1")).thenReturn(false);

        assertThrows(InvalidIdException.class, () -> {
            employeeService.deleteEmployee("1");
        });
    }

    @Test
    void testGetEmployeesByDepartment() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setFirstName("John");
        employee.setLastName("Doe");

        when(employeeRepository.findByDepartmentId("dept1")).thenReturn(List.of(employee));

        List<EmployeeDTO> employees = employeeService.getEmployeesByDepartment("dept1");

        assertEquals(1, employees.size());
        assertEquals("John", employees.get(0).getFirstName());
        verify(employeeRepository, times(1)).findByDepartmentId("dept1");
    }

    @Test
    void testGetEmployeesByManager() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setFirstName("John");
        employee.setLastName("Doe");

        when(employeeRepository.findByManagerId("manager1")).thenReturn(List.of(employee));

        List<EmployeeDTO> employees = employeeService.getEmployeesByManager("manager1");

        assertEquals(1, employees.size());
        assertEquals("John", employees.get(0).getFirstName());
        verify(employeeRepository, times(1)).findByManagerId("manager1");
    }

    @Test
    void testChangeEmployeeStatus() {
        Employee existingEmployee = new Employee();
        existingEmployee.setId("1");
        existingEmployee.setFirstName("John");
        existingEmployee.setLastName("Doe");
        existingEmployee.setStatus("Active");

        when(employeeRepository.findById("1")).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        employeeService.changeEmployeeStatus("1", "Inactive");

        assertEquals("Inactive", existingEmployee.getStatus());
        verify(employeeRepository, times(1)).findById("1");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testChangeEmployeeStatusWithInvalidId() {
        when(employeeRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(InvalidIdException.class, () -> {
            employeeService.changeEmployeeStatus("1", "Inactive");
        });
    }
}