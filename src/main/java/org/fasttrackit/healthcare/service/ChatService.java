package org.fasttrackit.healthcare.service;

import org.fasttrackit.healthcare.domain.Chat;
import org.fasttrackit.healthcare.domain.Doctor;
import org.fasttrackit.healthcare.domain.Patient;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.persistance.ChatRepository;
import org.fasttrackit.healthcare.transfer.chat.ChatResponse;
import org.fasttrackit.healthcare.transfer.chat.SaveChatRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatService.class);

    private final ChatRepository chatRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public ChatService(ChatRepository chatRepository, DoctorService doctorService, PatientService patientService) {
        this.chatRepository = chatRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @Transactional
    public ChatResponse createChat(SaveChatRequest request) {
        LOGGER.info("Creating chat {}", request);

        Doctor doctor = doctorService.findDoctor(request.getDoctorId());
        Patient patient = patientService.findPatient(request.getPatientId());

        Chat chat = new Chat();
        chat.setMessageDate(request.getMessageDate());
        chat.setMessageSent(request.getMessageSent());
        chat.setMessageReceived(request.getMessageReceived());
        chat.setDoctor(doctor);
        chat.setPatient(patient);

        Chat savedChat = chatRepository.save(chat);

        return mapChatResponse(savedChat);
    }

    public ChatResponse getChat(long id) {
        LOGGER.info("Retrieving message {}", id);

        Chat chat = findChat(id);

        return mapChatResponse(chat);
    }

    public Chat findChat(long id) {
        return chatRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Message" + id + " not found."));
    }

    private ChatResponse mapChatResponse(Chat chat) {
        ChatResponse chatDto = new ChatResponse();
        chatDto.setId(chat.getId());
        chatDto.setDoctorId(chat.getDoctor().getId());
        chatDto.setPatientId(chat.getPatient().getId());
        chatDto.setMessageDate(chat.getMessageDate());
        chatDto.setMessageSent(chat.getMessageSent());
        chatDto.setMessageReceived(chat.getMessageReceived());
        return chatDto;
    }

    @Transactional
    public Page<ChatResponse> getChats(long patientId, Pageable pageable) {
        LOGGER.info("Retrieving messages for patient {}", patientId);

        Page<Chat> chatsPage = chatRepository.findByPatientIdOrderByMessageDateDesc(patientId, pageable);

        List<ChatResponse> chatDtos = new ArrayList<>();

        for (Chat chat : chatsPage.getContent()) {
            ChatResponse chatDto = mapChatResponse(chat);

            chatDtos.add(chatDto);
        }

        return new PageImpl<>(chatDtos, pageable, chatsPage.getTotalElements());
    }

    public ChatResponse updateChat(long id, SaveChatRequest request) {
        LOGGER.info("Updated chat {}: {}", id, request);
        Chat chat = findChat(id);

        BeanUtils.copyProperties(request, chat);
        Chat savedChat = chatRepository.save(chat);

        return mapChatResponse(savedChat);
    }

    public void deleteChat(long id) {
        LOGGER.info("Deleting chat {}", id);
        chatRepository.deleteById(id);
    }
}
