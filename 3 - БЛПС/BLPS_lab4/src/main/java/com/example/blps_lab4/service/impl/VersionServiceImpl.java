package com.example.blps_lab4.service.impl;

import com.example.blps_lab4.model.SectionEntity;
import com.example.blps_lab4.model.VersionEntity;
import com.example.blps_lab4.repositories.SectionRepo;
import com.example.blps_lab4.repositories.VersionRepo;
import com.example.blps_lab4.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {

    private final VersionRepo versionRepo;
    private final SectionRepo sectionRepo;


    @Override
    public void saveChangesByAuthorizedUser(String newText, String username, Integer sectionId) {
        VersionEntity entity = new VersionEntity();
        entity.setSectionId(sectionId);
        entity.setPersonedited(username);
        entity.setSectiontext(newText);
        entity.setStatus("Ожидает проверки");
        versionRepo.save(entity);
        sectionRepo.updateText(entity.getSectiontext(), entity.getSectionId());
    }

    @Override
    public List<VersionEntity> getListOfWaitingUpdates() {
        return versionRepo.findAllByStatus("Ожидает проверки");
    }

    @Override
    public String getTextOfLastUpdateBySection(SectionEntity section) {
        List<VersionEntity> versionEntities = versionRepo.findAllBySectionId(section.getId());
        return Collections.max(versionEntities).getSectiontext();
    }

    @Override
    public String getTextOfLastApprovedVersion(SectionEntity section) {
        List<VersionEntity> versionEntities = versionRepo.findAllBySectionIdAndStatus(section.getId(), "Принято");
        String text = Collections.max(versionEntities).getSectiontext();
        sectionRepo.updateText(text, section.getId());
        return text;
    }

    @Override
    public void approve(Integer id) throws Exception {
        if (versionRepo.findById(id).isPresent()) {
            VersionEntity entity = versionRepo.findById(id).get();
            entity.setStatus("Принято");
            versionRepo.save(entity);
        } else throw new Exception();
    }

    @Override
    public void decline(Integer id) throws Exception {
        if (versionRepo.findById(id).isPresent()) {
            VersionEntity entity = versionRepo.findById(id).get();
            entity.setStatus("Отклонено");
            versionRepo.save(entity);
        } else throw new Exception();
    }

    @Override
    public void returnToLastApproved(Integer versionId) throws Exception {
        if (versionRepo.findById(versionId).isPresent()) {
            SectionEntity sectionEntity = versionRepo.findById(versionId).get().getSection();
            String text = getTextOfLastApprovedVersion(sectionEntity);
            sectionRepo.updateText(text, sectionEntity.getId());
        } else throw new Exception();
    }

    @Override
    public void deleteDeclinedVersions() {
        versionRepo.deleteAllByStatus("Отклонено");
    }

    @Override
    public String getSectionCodeByVersionId(Integer versionId) throws Exception {
        if (versionRepo.findById(versionId).isPresent()) {
            VersionEntity entity = versionRepo.findById(versionId).get();
            return entity.getSectiontext();
        } else throw new Exception();
    }
}
