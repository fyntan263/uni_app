package com.elfyntan.uni_app.department.repository;

import com.elfyntan.uni_app.department.domain.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {
}
