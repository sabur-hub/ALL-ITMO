package com.example.blps_lab4.service;

import com.example.blps_lab4.model.SectionEntity;

public interface SectionService {
    Integer getIdByArticleIdAndIndex(Integer articleId, Integer index);

    String getSectionCodeByArticleIdAndIndex(Integer articleId, Integer index);

    SectionEntity getSectionByArticleIdAndIndex(Integer articleId, Integer index);
}
