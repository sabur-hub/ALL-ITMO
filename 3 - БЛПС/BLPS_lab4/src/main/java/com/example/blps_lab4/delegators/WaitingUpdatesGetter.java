package com.example.blps_lab4.delegators;

import com.example.blps_lab4.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("waitingUpdatesGetter")
@RequiredArgsConstructor
public class WaitingUpdatesGetter implements JavaDelegate {
    private final VersionService versionService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        delegateExecution.setVariable("waitingUpdates", versionService.getListOfWaitingUpdates());
    }
}
