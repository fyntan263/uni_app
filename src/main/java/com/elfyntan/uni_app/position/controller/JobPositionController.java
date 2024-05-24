package com.elfyntan.uni_app.position.controller;

import com.elfyntan.uni_app.position.dto.JobPositionDTO;
import com.elfyntan.uni_app.position.service.JobPositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class JobPositionController {

        private final JobPositionService positionService;


        @GetMapping
        public List<JobPositionDTO> getAllPositions() {
            return positionService.getAllJobPositions();
        }

        @GetMapping("/{id}")
        public ResponseEntity<JobPositionDTO> getPositionById(@PathVariable String id) {
            Optional<JobPositionDTO> positionDTO = positionService.getJobPositionById(id);
            return positionDTO.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<JobPositionDTO> createPosition(@Valid @RequestBody JobPositionDTO positionDTO) {
            JobPositionDTO createdPosition = positionService.createJobPosition(positionDTO);
            return ResponseEntity.ok(createdPosition);
        }

        @PutMapping("/{id}")
        public ResponseEntity<JobPositionDTO> updatePosition(@PathVariable String id, @Valid @RequestBody JobPositionDTO positionDTO) {
            JobPositionDTO updatedPosition = positionService.updateJobPosition(id, positionDTO);
            if (updatedPosition != null) {
                return ResponseEntity.ok(updatedPosition);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePosition(@PathVariable String id) {
            positionService.deleteJobPosition(id);
            return ResponseEntity.noContent().build();
        }

}
