package com.elfyntan.uni_app.perfomance.controller;

import com.elfyntan.uni_app.perfomance.dto.PerformanceDTO;
import com.elfyntan.uni_app.perfomance.service.PerformanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/performances")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;

    @GetMapping
    public List<PerformanceDTO> getAllPerformances() {
        return performanceService.getAllPerformances();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceDTO> getPerformanceById(@PathVariable String id) {
        Optional<PerformanceDTO> performanceDTO = performanceService.getPerformanceById(id);
        return performanceDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PerformanceDTO> createPerformance(@Valid @RequestBody PerformanceDTO performanceDTO) {
        try {
            PerformanceDTO createdPerformance = performanceService.createPerformance(performanceDTO);
            return ResponseEntity.ok(createdPerformance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerformanceDTO> updatePerformance(@PathVariable String id, @Valid @RequestBody PerformanceDTO performanceDTO) {
        try {
            PerformanceDTO updatedPerformance = performanceService.updatePerformance(id, performanceDTO);
            if (updatedPerformance != null) {
                return ResponseEntity.ok(updatedPerformance);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformance(@PathVariable String id) {
        try {
            performanceService.deletePerformance(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/employee/{employeeId}")
    public List<PerformanceDTO> getPerformancesByEmployee(@PathVariable String employeeId) {
        return performanceService.getPerformancesByEmployee(employeeId);
    }
}