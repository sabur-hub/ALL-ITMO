package com.example.blps_lab4.delegators;

import com.example.blps_lab4.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("statusDeclineSetter")
@RequiredArgsConstructor
public class StatusDeclineSetter implements JavaDelegate {
    private final VersionService versionService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        versionService.decline((int) delegateExecution.getVariable("version_id"));
    }
}
