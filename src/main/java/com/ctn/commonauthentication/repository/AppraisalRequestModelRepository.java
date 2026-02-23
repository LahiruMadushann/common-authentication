package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.dto.AppraisalDetailsDto;
import com.ctn.commonauthentication.entity.AppraisalRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppraisalRequestModelRepository extends JpaRepository<AppraisalRequestModel, Long> {
    @Query("""
    SELECT new com.ctn.commonauthentication.dto.AppraisalDetailsDto(
        a.param,
        a.carMaker,
        a.carType,
        a.carModelYear,
        a.carTraveledDistance,
        a.grade,
        (SELECT b.bodyType FROM BodyType b WHERE b.carType = a.carType ORDER BY b.bodyTypeId LIMIT 1),
        a.bodyColor,
        a.customerPostNumber,
        a.customerMunicipalities,
        a.customerPrefecture,
        a.runnable,
        a.accident,
        a.desireDate
    )
    FROM AppraisalRequestModel a
    WHERE a.appraisalId = :appraisalId
    """)
    AppraisalDetailsDto findAppraisalDetailsDtoByAppraisalId(@Param("appraisalId") Long appraisalId);
}
