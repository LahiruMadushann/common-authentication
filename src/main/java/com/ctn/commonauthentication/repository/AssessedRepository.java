package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.dto.FinalOfferShopDetailsDto;
import com.ctn.commonauthentication.entity.Assessed;
import com.ctn.commonauthentication.entity.AssessedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssessedRepository extends JpaRepository<Assessed, AssessedId> {
    @Query("SELECT a FROM Assessed a WHERE a.id.appraisalid = :appraisalId AND a.id.shopid = :shopId")
    List<Assessed> findAllByAppraisalId(@Param("appraisalId") Long appraisalId, @Param("shopId") Integer shopId);

    @Query("SELECT new com.ctn.commonauthentication.dto.FinalOfferShopDetailsDto(" +
            "CAST(fo.createdAt AS timestamp), " +
            "CAST(fo.updatedAt AS timestamp), " +
            "CAST(va.createdAt AS timestamp), " +
            "CAST(va.updatedAt AS timestamp)) " +
            "FROM FinalOfferAudit fo " +
            "FULL JOIN ValueAudit va ON va.id.appraisalid = fo.id.appraisalid AND va.id.shopid = fo.id.shopid " +
            "WHERE (fo.id.shopid = :shopId OR va.id.shopid = :shopId) " +
            "AND (fo.id.appraisalid = :appraisalId OR va.id.appraisalid = :appraisalId)")
    FinalOfferShopDetailsDto findFinalOfferShopDetails(@Param("shopId") Integer shopId, @Param("appraisalId") Long appraisalId);
}
