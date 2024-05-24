package com.elfyntan.uni_app.perfomance.repository;

import com.elfyntan.uni_app.perfomance.domain.Performance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PerformanceRepository extends MongoRepository<Performance, String> {
    List<Performance> findByEmployeeId(String employeeId);
}