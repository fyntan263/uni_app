package com.elfyntan.uni_app.perfomance.service;

import com.elfyntan.uni_app.employee.domain.Employee;
import com.elfyntan.uni_app.employee.repository.EmployeeRepository;
import com.elfyntan.uni_app.exceptions.InvalidIdException;
import com.elfyntan.uni_app.perfomance.domain.Performance;
import com.elfyntan.uni_app.perfomance.dto.PerformanceDTO;
import com.elfyntan.uni_app.perfomance.repository.PerformanceRepository;
import com.elfyntan.uni_app.utils.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PerformanceServiceImpl implements PerformanceService{
    private final PerformanceRepository performanceRepository;
    private final EmployeeRepository employeeRepository;

    public PerformanceServiceImpl(PerformanceRepository performanceRepository, EmployeeRepository employeeRepository) {
        this.performanceRepository = performanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<PerformanceDTO> getAllPerformances() {
        return performanceRepository.findAll().stream()
                .map(Mapper::toPerformanceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PerformanceDTO> getPerformanceById(String id) {
        return performanceRepository.findById(id)
                .map(Mapper::toPerformanceDTO);
    }

    @Override
    public PerformanceDTO createPerformance(PerformanceDTO performanceDTO) {
        Performance performance = Mapper.toPerformance(performanceDTO);
        setReferences(performance, performanceDTO);
        Performance savedPerformance = performanceRepository.save(performance);
        return Mapper.toPerformanceDTO(savedPerformance);
    }

    @Override
    public PerformanceDTO updatePerformance(String id, PerformanceDTO performanceDTO) {
        Optional<Performance> performanceOpt = performanceRepository.findById(id);
        if (performanceOpt.isPresent()) {
            Performance existingPerformance = performanceOpt.get();
            existingPerformance.setReviewPeriod(performanceDTO.getReviewPeriod());
            existingPerformance.setPerformanceRating(performanceDTO.getPerformanceRating());
            existingPerformance.setComments(performanceDTO.getComments());
            existingPerformance.setReviewDate(performanceDTO.getReviewDate());
            setReferences(existingPerformance, performanceDTO);
            Performance updatedPerformance = performanceRepository.save(existingPerformance);
            return Mapper.toPerformanceDTO(updatedPerformance);
        } else {
            throw new InvalidIdException("Performance ID not found: " + id);
        }
    }

    @Override
    public void deletePerformance(String id) {
        if (!performanceRepository.existsById(id)) {
            throw new InvalidIdException("Performance ID not found: " + id);
        }
        performanceRepository.deleteById(id);
    }

    @Override
    public List<PerformanceDTO> getPerformancesByEmployee(String employeeId) {
        return performanceRepository.findByEmployeeId(employeeId).stream()
                .map(Mapper::toPerformanceDTO)
                .collect(Collectors.toList());
    }

    private void setReferences(Performance performance, PerformanceDTO performanceDTO) {
        Employee employee = employeeRepository.findById(performanceDTO.getEmployeeId())
                .orElseThrow(() -> new InvalidIdException("Invalid employee ID: " + performanceDTO.getEmployeeId()));
        performance.setEmployee(employee);
    }
}
