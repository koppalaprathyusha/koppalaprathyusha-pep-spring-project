package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Create a new message
     * @param message The message to create
     * @return The created message with its ID, or null if creation fails
     */
    public Message createMessage(Message message) {
        // Check if the user exists
        Optional<Account> account = accountRepository.findById(message.getPostedBy());
        if (account.isEmpty()) {
            return null; // User doesn't exist
        }
        
        // Save the message
        return messageRepository.save(message);
    }

    /**
     * Get all messages
     * @return List of all messages
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Get a message by its ID
     * @param messageId The ID of the message to retrieve
     * @return The message with the specified ID, or null if not found
     */
    public Message getMessageById(Integer messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        return message.orElse(null);
    }

    /**
     * Delete a message by its ID
     * @param messageId The ID of the message to delete
     * @return The number of rows affected (1 if successful, 0 if not found)
     */
    public Integer deleteMessageById(Integer messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    /**
     * Update a message text by its ID
     * @param messageId The ID of the message to update
     * @param messageText The new message text
     * @return The number of rows affected (1 if successful, 0 if not found)
     */
    public Integer updateMessageById(Integer messageId, String messageText) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    /**
     * Get all messages by a specific user
     * @param accountId The ID of the user account
     * @return List of messages by the specified user
     */
    public List<Message> getMessagesByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
