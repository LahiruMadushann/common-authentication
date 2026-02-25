package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT DISTINCT m.id, m.senderId, m.receiverId, m.content, m.timeStamp, " +
            "m.isResponded, m.isNotified, m.isFirstTime, m.fileUrl, m.fileName, m.fileType, " +
            "CASE " +
            "    WHEN u.role = 'ROLE_BUYER' THEN s.companyName " +
            "    WHEN u.role = 'ROLE_USER' THEN ar.customerName " +
            "    ELSE 'Unknown' " +
            "END AS receiverName " +
            "FROM Message m " +
            "LEFT JOIN ShopInvoice s ON s.userInvoice.userId = m.senderId " +
            "LEFT JOIN UserInvoice u ON u.userId = m.senderId " +
            "LEFT JOIN AppraisalRequestInformation ar ON ar.customerEmail = u.userName " +
            "WHERE m.senderId = :receiverId " +
            "ORDER BY m.id DESC")
    List<Object[]> findMessagesWithReceiverNameByReceiverId(@Param("receiverId") Long receiverId);

    Optional<Message> findTopBySenderIdAndReceiverIdOrderByTimeStampDesc(Long senderId, Long receiverId);
}
