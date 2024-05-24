package com.elfyntan.uni_app.position.service;

import com.elfyntan.uni_app.exceptions.InvalidIdException;
import com.elfyntan.uni_app.position.domain.JobPosition;
import com.elfyntan.uni_app.position.dto.JobPositionDTO;
import com.elfyntan.uni_app.position.repository.JobPositionRepository;
import com.elfyntan.uni_app.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class JobPositionServiceImpl implements JobPositionService {
    private final JobPositionRepository jobPositionRepository;
    @Override
    public List<JobPositionDTO> getAllJobPositions() {
        return jobPositionRepository.findAll().stream()
                .map(Mapper::toJobPositionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<JobPositionDTO> getJobPositionById(String id) {
        return jobPositionRepository.findById(id)
                .map(Mapper::toJobPositionDTO);
    }

    @Override
    public JobPositionDTO createJobPosition(JobPositionDTO jobPositionDTO) {
        JobPosition jobPosition = Mapper.toJobPosition(jobPositionDTO);
        JobPosition savedJobPosition = jobPositionRepository.save(jobPosition);
        return Mapper.toJobPositionDTO(savedJobPosition);
    }

    @Override
    public JobPositionDTO updateJobPosition(String id, JobPositionDTO jobPositionDTO) {
        Optional<JobPosition> jobPositionOpt = jobPositionRepository.findById(id);
        if (jobPositionOpt.isPresent()) {
            JobPosition existingJobPosition = jobPositionOpt.get();
            existingJobPosition.setPositionName(jobPositionDTO.getPositionName());
            existingJobPosition.setSalaryRange(jobPositionDTO.getSalaryRange());
            JobPosition updatedJobPosition = jobPositionRepository.save(existingJobPosition);
            return Mapper.toJobPositionDTO(updatedJobPosition);
        } else {
            throw new InvalidIdException("Job Position ID not found: " + id);
        }
    }

    @Override
    public void deleteJobPosition(String id) {
        if (!jobPositionRepository.existsById(id)) {
            throw new InvalidIdException("Job Position ID not found: " + id);
        }
        jobPositionRepository.deleteById(id);
    }
}