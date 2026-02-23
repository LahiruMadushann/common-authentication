package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.HeadBranchMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BranchMapRepo extends JpaRepository<HeadBranchMap, Integer> {
    @Query("SELECT h.branch FROM HeadBranchMap h WHERE h.headBranchId = :id")
    List<Integer> findBranchesByHeadBranchId(@Param("id") Integer id);

    List<HeadBranchMap> findAllByHeadBranchId(int headBranchId);
}
