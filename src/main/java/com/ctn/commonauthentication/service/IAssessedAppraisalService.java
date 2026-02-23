package com.ctn.commonauthentication.service;

import com.ctn.commonauthentication.entity.Assessed;
import com.ctn.commonauthentication.entity.AssessedId;

import java.util.Optional;

public interface IAssessedAppraisalService {
    public Optional<Assessed> findById(AssessedId id);
    public void updateAssessed(Assessed assessed);
}
