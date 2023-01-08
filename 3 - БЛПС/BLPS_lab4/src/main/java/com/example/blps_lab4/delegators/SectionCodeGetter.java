package com.example.blps_lab4.delegators;

import com.example.blps_lab4.service.ArticleService;
import com.example.blps_lab4.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("sectionCodeGetter")
@RequiredArgsConstructor
public class SectionCodeGetter implements JavaDelegate {
    private final SectionService sectionService;
    private final ArticleService articleService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        int articleId = articleService.getIdByName((String) delegateExecution.getVariable("article_name"));
        int sectionIndex = (int) delegateExecution.getVariable("section_index");
        delegateExecution.setVariable("section_code", sectionService.getSectionCodeByArticleIdAndIndex(articleId, sectionIndex));
    }
}
