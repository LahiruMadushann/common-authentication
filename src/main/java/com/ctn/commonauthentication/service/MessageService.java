package com.ctn.commonauthentication.service;

import com.ctn.commonauthentication.dto.MessageDTO;
import com.ctn.commonauthentication.entity.Message;

import java.util.List;

public interface MessageService {
    public List<MessageDTO> getMessagesForReceiver(Long receiverId);
    public Integer getBuyer(Integer shopId);
    public Message sendMessage(Message message);

}
