package com.ctn.commonauthentication.controller;

import com.ctn.commonauthentication.dto.MessageDTO;
import com.ctn.commonauthentication.entity.Message;
import com.ctn.commonauthentication.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sns.model.ResourceNotFoundException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/message")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<MessageDTO>> getMessagesForReceiver(@PathVariable Long receiverId){
        List<MessageDTO> messages = messageService.getMessagesForReceiver(receiverId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/buyer")
    public ResponseEntity<?> getUserId(@RequestParam Integer shopid) {
        try {
            Integer id = messageService.getBuyer(shopid);
            if (id == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for shopId: " + shopid);
            }
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the user ID.");
        }
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message){
        try {
            Message messageNew = messageService.sendMessage(message);
            return ResponseEntity.ok(messageNew);
        } catch(ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

    }
}
