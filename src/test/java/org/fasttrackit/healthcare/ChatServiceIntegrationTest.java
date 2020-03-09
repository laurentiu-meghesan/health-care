package org.fasttrackit.healthcare;

import org.fasttrackit.healthcare.domain.Chat;
import org.fasttrackit.healthcare.exception.ResourceNotFoundException;
import org.fasttrackit.healthcare.service.ChatService;
import org.fasttrackit.healthcare.transfer.SaveChatRequest;
import org.fasttrackit.healthcare.transfer.SaveProfileRequest;
import org.junit.jupiter.api.Assertions;
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
    void createChat_whenValidRequest_thenChatIsCreated() {
        createChat();
    }

    @Test
    void createChat_whenMissingPatientId_thenExceptionIsThrown() {
        SaveChatRequest request = new SaveChatRequest();
        request.setMessageDate(LocalDateTime.of(2020, 03, 01, 10, 42));
        request.setMessageSent("Hello!");
        request.setMessageReceived("Yuhu!");

        try {
            chatService.createChat(request);
        } catch (Exception e) {
            assertThat(e, notNullValue());
            assertThat("Unexpected exception type.", e instanceof NullPointerException);
        }
    }

    @Test
    void getChat_whenExistingChat_thenReturnChat() {
        Chat chat = createChat();

        Chat response = chatService.getChat(chat.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(chat.getId()));
        assertThat(response.getPatientId(), is(chat.getPatientId()));
        assertThat(response.getMessageDate(), is(chat.getMessageDate()));
        assertThat(response.getMessageSent(), is(chat.getMessageSent()));
        assertThat(response.getMessageReceived(), is(chat.getMessageReceived()));

    }

    @Test
    void getChat_whenNonExistingChat_thenThrowResourceNotFoundException() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> chatService.getChat(21520));
    }

    @Test
    void updateChat_whenValidRequest_thenReturnUpdatedChat() {
        Chat chat = createChat();

        SaveChatRequest request = new SaveChatRequest();
        request.setPatientId(chat.getPatientId());
        request.setMessageDate(chat.getMessageDate().minusDays(3));
        request.setMessageSent(chat.getMessageSent() + " updated.");
        request.setMessageReceived(chat.getMessageReceived() + " updated.");

        Chat updatedChat = chatService.updateChat(chat.getId(), request);

        assertThat(updatedChat, notNullValue());
        assertThat(updatedChat.getId(), is(chat.getId()));
        assertThat(updatedChat.getPatientId(), is(chat.getPatientId()));
        assertThat(updatedChat.getMessageDate(), is(request.getMessageDate()));
        assertThat(updatedChat.getMessageSent(), is(request.getMessageSent()));
        assertThat(updatedChat.getMessageReceived(), is(request.getMessageReceived()));
    }

    @Test
    void deleteChat_whenExistingChat_thenChatDoesNotExistAnymore() {
        Chat chat = createChat();

        chatService.deleteChat(chat.getId());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> chatService.getChat(chat.getId()));
    }

    private Chat createChat() {
        SaveChatRequest request = new SaveChatRequest();
        request.setMessageDate(LocalDateTime.of(2020, 03, 05, 19, 30));
        request.setPatientId(10L);
        request.setMessageSent("Ciao!");
        request.setMessageReceived("Hello!");

        Chat chat = chatService.createChat(request);

        assertThat(chat, notNullValue());
        assertThat(chat.getId(), greaterThan(0L));
        assertThat(chat.getMessageDate(), is(request.getMessageDate()));
        assertThat(chat.getMessageSent(), is(request.getMessageSent()));
        assertThat(chat.getMessageReceived(), is(request.getMessageReceived()));
        return chat;
    }

}
