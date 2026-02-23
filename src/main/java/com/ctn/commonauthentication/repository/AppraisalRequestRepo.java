package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.AppraisalRequestInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppraisalRequestRepo extends JpaRepository<AppraisalRequestInformation, Long> {

    @Query("SELECT a FROM AppraisalRequestInformation a " +
            "JOIN UserInvoice u ON LOWER(u.userName) = LOWER(a.customerEmail) " +
            "WHERE u.userId = :userId " +
            "ORDER BY a.createdAt DESC")
    List<AppraisalRequestInformation> findAllByUserIdAndCustomerEmailOrderedByDate(@Param("userId") Integer userId);

    @Query("SELECT a.appraisalid FROM AppraisalRequestInformation a " +
            "JOIN UserInvoice u ON LOWER(u.userName) = LOWER(a.customerEmail) " +
            "WHERE u.userId = :userId " +
            "ORDER BY a.createdAt DESC")
    List<Long> findAppraisalIdByUserIdAndCustomerEmailOrderedByDate(@Param("userId") Integer userId);
}
