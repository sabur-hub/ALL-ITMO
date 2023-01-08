package com.example.blps_lab4.service;

import com.example.blps_lab4.model.SectionEntity;
import com.example.blps_lab4.model.VersionEntity;

import java.util.List;

public interface VersionService {
    void saveChangesByAuthorizedUser(String newText, String username, Integer sectionId);

    List<VersionEntity> getListOfWaitingUpdates();

    String getTextOfLastUpdateBySection(SectionEntity section);

    String getTextOfLastApprovedVersion(SectionEntity section);

    void approve(Integer id) throws Exception;

    void decline(Integer id) throws Exception;

    void returnToLastApproved(Integer id) throws Exception;

    void deleteDeclinedVersions();

    String getSectionCodeByVersionId(Integer versionId) throws Exception;
}
