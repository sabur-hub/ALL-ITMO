package com.example.blps_lab4.service.impl;

import com.example.blps_lab4.repositories.ArticleRepo;
import com.example.blps_lab4.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepo repo;

    public ArticleServiceImpl(ArticleRepo repo) {
        this.repo = repo;
    }

    @Override
    public Integer getIdByName(String name) {
        return repo.findFirstByArticlename(name).getId();
    }
}
