package org.fasttrackit.healthcare.service;
import org.fasttrackit.healthcare.domain.Chat;
import org.fasttrackit.healthcare.persistance.ChatRepository;
import org.fasttrackit.healthcare.transfer.SaveChatRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository){
        this.chatRepository = chatRepository;
    }

    public Chat createChat (SaveChatRequest request){
        LOGGER.info("Creating chat {}", request);
        Chat chat = new Chat();
        chat.setPatientId(request.getPatientId());
        chat.setMessageDate(request.getMessageDate());
        chat.setMessageSent(request.getMessageSent());
        chat.setMessageReceived(request.getMessageReceived());
        return chatRepository.save(chat);
    }

}
