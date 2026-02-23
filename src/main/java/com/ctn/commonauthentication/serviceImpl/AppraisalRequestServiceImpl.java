package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.entity.AppraisalRequestInformation;
import com.ctn.commonauthentication.repository.AppraisalRequestRepo;
import com.ctn.commonauthentication.service.AppraisalRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppraisalRequestServiceImpl implements AppraisalRequestService {

    private final AppraisalRequestRepo appraisalRequestRepo;

    @Override
    public Optional<AppraisalRequestInformation> getAppraisalInformation(Integer userId) {
        try {
            List<AppraisalRequestInformation> results = appraisalRequestRepo.findAllByUserIdAndCustomerEmailOrderedByDate(userId);

            if (results.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(results.get(0));
        } catch (Exception e) {
            log.error("Error occurred while fetching appraisal information for userId: {}", userId, e);
            return Optional.empty();
        }
    }
}
