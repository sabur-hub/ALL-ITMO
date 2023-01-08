package com.example.blps_lab4.delegators;

import com.example.blps_lab4.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("declinedCleaner")
@RequiredArgsConstructor
public class DeclinedCleaner implements JavaDelegate {

    private final VersionService versionService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        versionService.deleteDeclinedVersions();
    }
}
