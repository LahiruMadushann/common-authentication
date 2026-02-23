package com.ctn.commonauthentication.service;

import com.ctn.commonauthentication.dto.UpdatePhotosDTO;
import com.ctn.commonauthentication.entity.AppraisalRequestInformation;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface AppraisalRequestInformationService {
    Optional<AppraisalRequestInformation> updatePhotos(Long appraisalId, UpdatePhotosDTO dto);
}
