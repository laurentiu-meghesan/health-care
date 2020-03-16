package org.fasttrackit.healthcare.service;

import org.fasttrackit.healthcare.domain.Chat;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.persistance.ChatRepository;
import org.fasttrackit.healthcare.transfer.chat.SaveChatRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Chat createChat(SaveChatRequest request) {
        LOGGER.info("Creating chat {}", request);
        Chat chat = new Chat();
        chat.setPatientId(request.getPatientId());
        chat.setMessageDate(request.getMessageDate());
        chat.setMessageSent(request.getMessageSent());
        chat.setMessageReceived(request.getMessageReceived());
        return chatRepository.save(chat);
    }

    public Chat getChat(long id) {
        LOGGER.info("Retrieving message {}", id);

        return chatRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Message" + id + " not found."));
    }

    public Chat updateChat(long id, SaveChatRequest request) {
        LOGGER.info("Updated chat {}: {}", id, request);
        Chat chat = getChat(id);

        BeanUtils.copyProperties(request, chat);
        return chatRepository.save(chat);
    }

    public void deleteChat(long id) {
        LOGGER.info("Deleting chat {}", id);
        chatRepository.deleteById(id);
    }
}
