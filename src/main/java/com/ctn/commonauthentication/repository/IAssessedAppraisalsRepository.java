package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.AssessedAppraisal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IAssessedAppraisalsRepository extends JpaRepository<AssessedAppraisal, Long> {

    @Query("SELECT a FROM AssessedAppraisal a WHERE CAST(a.appraisalid AS string ) = :appraisal")
    List<AssessedAppraisal> findAllByAppraisalid(@Param("appraisal") String appraisal);

}
