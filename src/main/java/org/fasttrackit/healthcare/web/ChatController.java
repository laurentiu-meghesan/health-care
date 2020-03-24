package org.fasttrackit.healthcare.web;

import org.fasttrackit.healthcare.domain.Chat;
import org.fasttrackit.healthcare.service.ChatService;
import org.fasttrackit.healthcare.transfer.chat.GetChatsRequest;
import org.fasttrackit.healthcare.transfer.chat.SaveChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<Chat> createChat(@Valid @RequestBody SaveChatRequest request) {
        Chat chat = chatService.createChat(request);
        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChat(@PathVariable long id) {
        Chat chat = chatService.getChat(id);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Chat>> getChats(GetChatsRequest request, Pageable pageable) {
        Page<Chat> chats = chatService.getChats(request, pageable);
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChat(@PathVariable long id) {
        chatService.deleteChat(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
