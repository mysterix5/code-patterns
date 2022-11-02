package com.github.mysterix5.template.statistics;

import com.github.mysterix5.template.model.StatisticsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatisticsRepository extends MongoRepository<StatisticsEntity, String> {
    Optional<StatisticsEntity> findByName(String name);
}
