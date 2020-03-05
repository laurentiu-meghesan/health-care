package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Chat;
import org.fasttrackit.healthcare.service.ChatService;
import org.fasttrackit.healthcare.transfer.SaveChatRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class ChatServiceIntegrationTest {

    @Autowired
    private ChatService chatService;

    @Test
    void createChat_whenValidRequest_thenChatIsCreated(){
        SaveChatRequest request = new SaveChatRequest();
        request.setMessageDate(LocalDateTime.of(2020, 03,05,19,30));
        request.setPatientId(10L);
        request.setMessageSent("Ciao!");
        request.setMessageReceived("Hello!");

        Chat chat = chatService.createChat(request);

        assertThat(chat, notNullValue());
        assertThat(chat.getId(), greaterThan(0L));
        assertThat(chat.getMessageDate(), is(request.getMessageDate()));
        assertThat(chat.getMessageSent(), is(request.getMessageSent()));
        assertThat(chat.getMessageReceived(), is(request.getMessageReceived()));
    }

}