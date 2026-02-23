package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Object[]> findMessagesWithReceiverNameByReceiverId(@Param("receiverId") Long receiverId);
    Optional<Message> findTopBySenderIdAndReceiverIdOrderByTimeStampDesc(Long senderId, Long receiverId);
}
