package com.elfyntan.uni_app.employee.controller;

import com.elfyntan.uni_app.employee.dto.AddressDTO;
import com.elfyntan.uni_app.employee.dto.EmployeeDTO;
import com.elfyntan.uni_app.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testGetAllEmployees() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");

        when(employeeService.getAllEmployees()).thenReturn(List.of(employeeDTO));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");

        when(employeeService.getEmployeeById("1")).thenReturn(Optional.of(employeeDTO));

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")));

        verify(employeeService, times(1)).getEmployeeById("1");
    }

    @Test
    void testGetEmployeeByIdNotFound() throws Exception {
        when(employeeService.getEmployeeById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).getEmployeeById("1");
    }

    @Test
    void testCreateEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setContactInfo("john.doe@example.com");
        employeeDTO.setDob(java.time.LocalDate.now());
        employeeDTO.setGender("Male");
        employeeDTO.setHireDate(java.time.LocalDate.now());
        employeeDTO.setSalary(50000.0);
        employeeDTO.setDepartmentId("dept1");
        employeeDTO.setJobPositionId("pos1");
        employeeDTO.setStatus("Active");
        employeeDTO.setAddress(new AddressDTO("123 Main St", "City", "State", "12345"));

        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")));

        verify(employeeService, times(1)).createEmployee(any(EmployeeDTO.class));
    }

    @Test
    void testCreateEmployeeWithInvalidData() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"\",\"lastName\":\"Doe\",\"contactInfo\":\"john.doe@example.com\"}"))
                .andExpect(status().isBadRequest());

        verify(employeeService, times(0)).createEmployee(any(EmployeeDTO.class));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");
        employeeDTO.setContactInfo("john.doe@example.com");
        employeeDTO.setDob(java.time.LocalDate.now());
        employeeDTO.setGender("Male");
        employeeDTO.setHireDate(java.time.LocalDate.now());
        employeeDTO.setSalary(50000.0);
        employeeDTO.setDepartmentId("dept1");
        employeeDTO.setJobPositionId("pos1");
        employeeDTO.setStatus("Active");
        employeeDTO.setAddress(new AddressDTO("123 Main St", "City", "State", "12345"));

        when(employeeService.updateEmployee(eq("1"), any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")));

        verify(employeeService, times(1)).updateEmployee(eq("1"), any(EmployeeDTO.class));
    }

//    @Test
//    void testUpdateEmployeeWithInvalidId() throws Exception {
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        employeeDTO.setFirstName("John");
//        employeeDTO.setLastName("Doe");
//        employeeDTO.setContactInfo("john.doe@example.com");
//        employeeDTO.setDob(java.time.LocalDate.now());
//        employeeDTO.setGender("Male");
//        employeeDTO.setHireDate(java.time.LocalDate.now());
//        employeeDTO.setSalary(50000.0);
//        employeeDTO.setDepartmentId("dept1");
//        employeeDTO.setJobPositionId("pos1");
//        employeeDTO.setStatus("Active");
//        employeeDTO.setAddress(new AddressDTO("123 Main St", "City", "State", "12345"));
//
//        when(employeeService.updateEmployee(eq("1"), any(EmployeeDTO.class))).thenThrow(new InvalidIdException("Employee ID not found: 1"));
//
//        mockMvc.perform(put("/api/employees/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(employeeDTO)))
//                .andExpect(status().isNotFound());
//
//        verify(employeeService, times(1)).updateEmployee(eq("1"), any(EmployeeDTO.class));
//    }

    @Test
    void testDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee("1");

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).deleteEmployee("1");
    }

//    @Test
//    void testDeleteEmployeeWithInvalidId() throws Exception {
//        doThrow(new InvalidIdException("Employee ID not found: 1")).when(employeeService).deleteEmployee("1");
//
//        mockMvc.perform(delete("/api/employees/1"))
//                .andExpect(status().isNotFound());
//
//        verify(employeeService, times(1)).deleteEmployee("1");
//    }

    @Test
    void testGetEmployeesByDepartment() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");

        when(employeeService.getEmployeesByDepartment("dept1")).thenReturn(List.of(employeeDTO));

        mockMvc.perform(get("/api/employees/department/dept1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")));

        verify(employeeService, times(1)).getEmployeesByDepartment("dept1");
    }

    @Test
    void testGetEmployeesByManager() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName("John");
        employeeDTO.setLastName("Doe");

        when(employeeService.getEmployeesByManager("manager1")).thenReturn(List.of(employeeDTO));

        mockMvc.perform(get("/api/employees/manager/manager1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")));

        verify(employeeService, times(1)).getEmployeesByManager("manager1");
    }

    @Test
    void testChangeEmployeeStatus() throws Exception {
        doNothing().when(employeeService).changeEmployeeStatus("1", "Inactive");

        mockMvc.perform(patch("/api/employees/1/status")
                        .param("status", "Inactive"))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).changeEmployeeStatus("1", "Inactive");
    }

//    @Test
//    void testChangeEmployeeStatusWithInvalidId() throws Exception {
//        doThrow(new InvalidIdException("Employee ID not found: 1")).when(employeeService).changeEmployeeStatus("1", "Inactive");
//
//        mockMvc.perform(patch("/api/employees/1/status")
//                        .param("status", "Inactive"))
//                .andExpect(status().isNotFound());
//
//        verify(employeeService, times(1)).changeEmployeeStatus("1", "Inactive");
//    }
}
