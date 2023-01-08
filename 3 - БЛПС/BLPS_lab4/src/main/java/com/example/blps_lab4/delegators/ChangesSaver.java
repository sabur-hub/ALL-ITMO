package com.example.blps_lab4.delegators;

import com.example.blps_lab4.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("changesSaver")
@RequiredArgsConstructor
public class ChangesSaver implements JavaDelegate {

    private final VersionService versionService;
    private final IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        String newText = (String) delegateExecution.getVariable("section_code");
        int sectionId = (int) delegateExecution.getVariable("section_index");
        String username = identityService.getCurrentAuthentication().getUserId();
        versionService.saveChangesByAuthorizedUser(newText, username, sectionId);
    }
}
