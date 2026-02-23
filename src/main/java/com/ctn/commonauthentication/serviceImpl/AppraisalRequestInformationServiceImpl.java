package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.dto.UpdatePhotosDTO;
import com.ctn.commonauthentication.entity.AppraisalRequestInformation;
import com.ctn.commonauthentication.repository.AppraisalRequestRepo;
import com.ctn.commonauthentication.service.AppraisalRequestInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppraisalRequestInformationServiceImpl implements AppraisalRequestInformationService {
    
    private final AppraisalRequestRepo repository;

    @Override
    public Optional<AppraisalRequestInformation> updatePhotos(Long appraisalId, UpdatePhotosDTO dto) {
        return repository.findById(appraisalId).map(appraisalRequestInformation -> {
            appraisalRequestInformation.setPhotoBefore(dto.getPhotoBefore());
            appraisalRequestInformation.setPhotoAfter(dto.getPhotoAfter());
            appraisalRequestInformation.setInspectionCertPhoto(dto.getInspectionCertPhoto());
            return repository.save(appraisalRequestInformation);
        });
    }
}
