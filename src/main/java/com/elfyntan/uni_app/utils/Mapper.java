package com.elfyntan.uni_app.utils;


import com.elfyntan.uni_app.department.domain.Department;
import com.elfyntan.uni_app.department.dto.DepartmentDTO;
import com.elfyntan.uni_app.employee.domain.Address;
import com.elfyntan.uni_app.employee.domain.Employee;
import com.elfyntan.uni_app.employee.dto.AddressDTO;
import com.elfyntan.uni_app.employee.dto.EmployeeDTO;
import com.elfyntan.uni_app.perfomance.domain.Performance;
import com.elfyntan.uni_app.perfomance.dto.PerformanceDTO;
import com.elfyntan.uni_app.position.domain.JobPosition;
import com.elfyntan.uni_app.position.dto.JobPositionDTO;

public class Mapper {


    public static Employee toEmployee(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setDob(dto.getDob());
        employee.setGender(dto.getGender());
        employee.setHireDate(dto.getHireDate());
        employee.setSalary(dto.getSalary());
        employee.setContactInfo(dto.getContactInfo());
        employee.setAddress(toAddress(dto.getAddress()));
        employee.setStatus(dto.getStatus());
        return employee;
    }

    public static EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setDob(employee.getDob());
        dto.setGender(employee.getGender());
        dto.setHireDate(employee.getHireDate());
        dto.setSalary(employee.getSalary());
        dto.setContactInfo(employee.getContactInfo());
        dto.setAddress(toAddressDTO(employee.getAddress()));
        dto.setStatus(employee.getStatus());

        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getId());
        }

        if (employee.getJobPosition() != null) {
            dto.setJobPositionId(employee.getJobPosition().getId());
        }

        if (employee.getManager() != null) {
            dto.setManagerId(employee.getManager().getId());
        }

        return dto;
    }

    public static Address toAddress(AddressDTO dto) {
        if (dto == null) {
            return null;
        }
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPostalCode(dto.getPostalCode());
        return address;
    }

    public static AddressDTO toAddressDTO(Address address) {
        if (address == null) {
            return null;
        }
        AddressDTO dto = new AddressDTO();
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPostalCode(address.getPostalCode());
        return dto;
    }

    public static Department toDepartment(DepartmentDTO dto) {
        Department department = new Department();
        department.setDepartmentName(dto.getDepartmentName());
        department.setLocation(dto.getLocation());
        return department;
    }

    public static DepartmentDTO toDepartmentDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentName(department.getDepartmentName());
        dto.setLocation(department.getLocation());
        return dto;
    }

    public static JobPosition toJobPosition(JobPositionDTO dto) {
        JobPosition jobPosition = new JobPosition();
        jobPosition.setPositionName(dto.getPositionName());
        jobPosition.setSalaryRange(dto.getSalaryRange());
        return jobPosition;
    }

    public static JobPositionDTO toJobPositionDTO(JobPosition jobPosition) {
        JobPositionDTO dto = new JobPositionDTO();
        dto.setPositionName(jobPosition.getPositionName());
        dto.setSalaryRange(jobPosition.getSalaryRange());
        return dto;
    }

    public static Performance toPerformance(PerformanceDTO dto) {
        Performance performance = new Performance();
        performance.setReviewPeriod(dto.getReviewPeriod());
        performance.setPerformanceRating(dto.getPerformanceRating());
        performance.setComments(dto.getComments());
        performance.setReviewDate(dto.getReviewDate());
        return performance;
    }

    public static PerformanceDTO toPerformanceDTO(Performance performance) {
        PerformanceDTO dto = new PerformanceDTO();
        dto.setReviewPeriod(performance.getReviewPeriod());
        dto.setPerformanceRating(performance.getPerformanceRating());
        dto.setComments(performance.getComments());
        dto.setReviewDate(performance.getReviewDate());
        dto.setEmployeeId(performance.getEmployee().getId());
        return dto;
    }
}