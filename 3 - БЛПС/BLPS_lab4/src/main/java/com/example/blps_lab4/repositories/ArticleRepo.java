package com.example.blps_lab4.repositories;

import com.example.blps_lab4.model.ArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepo extends CrudRepository<ArticleEntity, Integer> {
    ArticleEntity findFirstByArticlename(String name);
}
