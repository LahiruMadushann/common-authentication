package com.ctn.commonauthentication.serviceImpl;

import com.ctn.commonauthentication.dto.MessageDTO;
import com.ctn.commonauthentication.entity.Message;
import com.ctn.commonauthentication.entity.User;
import com.ctn.commonauthentication.exception.ResourceNotFoundException;
import com.ctn.commonauthentication.repository.MessageRepository;
import com.ctn.commonauthentication.repository.ShopInvoiceRepo;
import com.ctn.commonauthentication.repository.UserMapper;
import com.ctn.commonauthentication.service.IInternalEmailService;
import com.ctn.commonauthentication.service.MessageService;
import jakarta.mail.internet.AddressException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ShopInvoiceRepo shopInvoiceRepo;
    private final MessageRepository messageRepository;
    private final UserMapper userMapper;
    private final IInternalEmailService emailService;

    @Override
    public List<MessageDTO> getMessagesForReceiver(Long receiverId) {
        List<Object[]> results = messageRepository.findMessagesWithReceiverNameByReceiverId(receiverId);

        return results.stream()
                .map(this::mapToMessageDTO)
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(MessageDTO::getCreatedAt))))
                .stream()
                .sorted(Comparator.comparing(MessageDTO::getId).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Integer getBuyer(Integer shopId) {
        try {
            return shopInvoiceRepo.findUserInvoiceById(shopId);
        } catch (Exception e) {
            log.error("Error fetching user for shopId: " + shopId, e);
            return null;
        }
    }

    @Override
    public Message sendMessage(Message message) {
        if (userMapper.findById(message.getReceiverId()) == null ) {
            throw new ResourceNotFoundException("Receiver", "ID", message.getReceiverId());
        }
        if (userMapper.findById(message.getSenderId()) == null) {
            throw new ResourceNotFoundException("Sender", "ID", message.getSenderId());
        }
        log.info("Sender ID: " + userMapper.findById(message.getSenderId()));

        LocalDateTime currentTimestamp = LocalDateTime.now();

        // Find the most recent message sent by the receiver to the sender
        Optional<Message> previousMessageOpt = messageRepository.findTopBySenderIdAndReceiverIdOrderByTimeStampDesc(
                message.getReceiverId(),message.getSenderId()
        );

        // get user by id
        User user = userMapper.findById(message.getReceiverId());

        try {
            emailService.sendNewMessageNotificationEmail(user.getUsername());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }

        // If a previous message exists, check its timestamp and notify if needed
        if (previousMessageOpt.isPresent()) {
            Message previousMessage = previousMessageOpt.get();

            // Check if the time difference is greater than 30 minutes
            Duration duration = Duration.between(previousMessage.getTimeStamp(), currentTimestamp);
            if (duration.toMinutes() > 30 && !previousMessage.isResponded() && !previousMessage.isNotified()) {
                // send the email notification

                try{
//                    emailService.unrespondedMessagesEmail(user.getUsername(),List.of(previousMessage));
                }catch(Exception e){
                    e.printStackTrace();
                }
                log.info("Notified the receiver with ID:" + previousMessage.getReceiverId());
                previousMessage.setNotified(true);
                messageRepository.save(previousMessage);
            }
            // Mark the previous message as responded
            previousMessage.setResponded(true);
            messageRepository.save(previousMessage);
        }else{
            message.setFirstTime(true);
        }
        message.setTimeStamp(currentTimestamp);
        messageRepository.save(message);
        return message;
    }

    private MessageDTO mapToMessageDTO(Object[] result) {
        return new MessageDTO(
                (Long) result[0],
                (Long) result[1],
                (Long) result[2],
                (String) result[3],
                (LocalDateTime) result[4],
                (Boolean) result[5],
                (Boolean) result[6],
                (Boolean) result[7],
                (String) result[8],
                (String) result[9],
                (String) result[10],
                (String) result[11]
        );
    }
}
