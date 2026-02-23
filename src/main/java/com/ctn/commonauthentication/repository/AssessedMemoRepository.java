package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.AssessedMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessedMemoRepository extends JpaRepository<AssessedMemo, AssessedMemo.AssessedMemoId> {
    List<AssessedMemo> findAllById_AppraisalIdAndId_ShopId(long appraisalID, int shopID);

}
