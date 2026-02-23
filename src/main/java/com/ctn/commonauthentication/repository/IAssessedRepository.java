package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.Assessed;
import com.ctn.commonauthentication.entity.AssessedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAssessedRepository extends JpaRepository<Assessed, AssessedId> {

    @Query("SELECT a FROM Assessed a WHERE a.id.shopid = :shopid")
    List<Assessed> findByShopid(@Param("shopid") Integer shopid);

    @Query("SELECT CASE WHEN a.adminPurchasePrice IS NOT NULL THEN true ELSE false END " +
            "FROM Assessed a WHERE a.id = :id")
    boolean hasAdminAmount(@Param("id") AssessedId id);

    @Query("SELECT CASE WHEN a.finalOffer IS NOT NULL THEN true ELSE false END " +
            "FROM Assessed a WHERE a.id = :id")
    boolean hasFinalOffer(@Param("id") AssessedId id);
}
