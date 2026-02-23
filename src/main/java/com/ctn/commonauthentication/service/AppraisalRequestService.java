package com.ctn.commonauthentication.service;

import com.ctn.commonauthentication.entity.AppraisalRequestInformation;

import java.util.Optional;

public interface AppraisalRequestService {
    Optional<AppraisalRequestInformation> getAppraisalInformation(Integer userId);
}
