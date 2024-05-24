package com.elfyntan.uni_app.department.service;

import com.elfyntan.uni_app.department.domain.Department;
import com.elfyntan.uni_app.department.dto.DepartmentDTO;
import com.elfyntan.uni_app.department.repository.DepartmentRepository;
import com.elfyntan.uni_app.exceptions.InvalidIdException;
import com.elfyntan.uni_app.utils.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
        private final DepartmentRepository departmentRepository;

        public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
            this.departmentRepository = departmentRepository;
        }

        @Override
        public List<DepartmentDTO> getAllDepartments() {
            return departmentRepository.findAll().stream()
                    .map(Mapper::toDepartmentDTO)
                    .collect(Collectors.toList());
        }

        @Override
        public Optional<DepartmentDTO> getDepartmentById(String id) {
            return departmentRepository.findById(id)
                    .map(Mapper::toDepartmentDTO);
        }

        @Override
        public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
            Department department = Mapper.toDepartment(departmentDTO);
            Department savedDepartment = departmentRepository.save(department);
            return Mapper.toDepartmentDTO(savedDepartment);
        }

        @Override
        public DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO) {
            Optional<Department> departmentOpt = departmentRepository.findById(id);
            if (departmentOpt.isPresent()) {
                Department existingDepartment = departmentOpt.get();
                existingDepartment.setDepartmentName(departmentDTO.getDepartmentName());
                existingDepartment.setLocation(departmentDTO.getLocation());
                Department updatedDepartment = departmentRepository.save(existingDepartment);
                return Mapper.toDepartmentDTO(updatedDepartment);
            } else {
                throw new InvalidIdException("Department ID not found: " + id);
            }
        }

        @Override
        public void deleteDepartment(String id) {
            if (!departmentRepository.existsById(id)) {
                throw new InvalidIdException("Department ID not found: " + id);
            }
            departmentRepository.deleteById(id);
        }
    }