package com.example.blps_lab4.delegators;

import com.example.blps_lab4.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("versionByIdGetter")
@RequiredArgsConstructor
public class VersionByIdGetter implements JavaDelegate {
    private final VersionService versionService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int versionId = (int) delegateExecution.getVariable("version_id");
        delegateExecution.setVariable("version_code", versionService.getSectionCodeByVersionId(versionId));
    }
}