package com.elfyntan.uni_app.position.repository;

import com.elfyntan.uni_app.position.domain.JobPosition;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPositionRepository extends MongoRepository<JobPosition, String> {
}
