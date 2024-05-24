package com.elfyntan.uni_app.employee.repository;

import com.elfyntan.uni_app.employee.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    boolean existsByContactInfo(String contactInfo);

    Optional<Employee> findByContactInfo(String contactInfo);

    List<Employee> findByDepartmentId(String departmentId);

    List<Employee> findByManagerId(String managerId);

    @Query("{ 'status': ?0 }")
    List<Employee> findByStatus(String status);
}
