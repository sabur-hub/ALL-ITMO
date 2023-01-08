package com.example.blps_lab4.repositories;

import com.example.blps_lab4.model.VersionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VersionRepo extends CrudRepository<VersionEntity, Integer> {
    List<VersionEntity> findAllByStatus(String status);

    List<VersionEntity> findAllBySectionId(Integer sectionId);

    List<VersionEntity> findAllBySectionIdAndStatus(Integer sectionId, String status);

    @Transactional
    void deleteAllByStatus(String status);
}
