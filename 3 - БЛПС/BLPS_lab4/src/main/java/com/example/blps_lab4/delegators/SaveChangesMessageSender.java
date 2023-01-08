package com.example.blps_lab4.delegators;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.inject.Named;

@Named("saveChangesMessageSender")
@RequiredArgsConstructor
public class SaveChangesMessageSender implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.getProcessEngineServices().getRuntimeService()
                .createMessageCorrelation("saveChangesMessage")
                .setVariable("section_code", delegateExecution.getVariable("section_code"))
                .setVariable("section_index", delegateExecution.getVariable("section_index"))
                .correlate();
    }
}
