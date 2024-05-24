package com.elfyntan.uni_app.perfomance.service;


import com.elfyntan.uni_app.perfomance.dto.PerformanceDTO;

import java.util.List;
import java.util.Optional;

public interface PerformanceService {
    List<PerformanceDTO> getAllPerformances();
    Optional<PerformanceDTO> getPerformanceById(String id);
    PerformanceDTO createPerformance(PerformanceDTO performanceDTO);
    PerformanceDTO updatePerformance(String id, PerformanceDTO performanceDTO);
    void deletePerformance(String id);
    List<PerformanceDTO> getPerformancesByEmployee(String employeeId);
}