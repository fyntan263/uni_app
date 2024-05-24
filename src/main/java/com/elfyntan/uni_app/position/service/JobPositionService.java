package com.elfyntan.uni_app.position.service;

import com.elfyntan.uni_app.position.dto.JobPositionDTO;

import java.util.List;
import java.util.Optional;

public interface JobPositionService {
    List<JobPositionDTO> getAllJobPositions();
    Optional<JobPositionDTO> getJobPositionById(String id);
    JobPositionDTO createJobPosition(JobPositionDTO positionDTO);
    JobPositionDTO updateJobPosition(String id, JobPositionDTO positionDTO);
    void deleteJobPosition(String id);
}
