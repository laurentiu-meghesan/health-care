package org.fasttrackit.healthcare.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private long patientId;
    @NotNull
    private String messageSent;
    @NotNull
    private String messageReceived;
    @NotNull
    private LocalDateTime messageDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getMessageSent() {
        return messageSent;
    }

    public void setMessageSent(String messageSent) {
        this.messageSent = messageSent;
    }

    public String getMessageReceived() {
        return messageReceived;
    }

    public void setMessageReceived(String messageReceived) {
        this.messageReceived = messageReceived;
    }

    public LocalDateTime getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(LocalDateTime messageDate) {
        this.messageDate = messageDate;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", messageSent='" + messageSent + '\'' +
                ", messageReceived='" + messageReceived + '\'' +
                ", messageDate=" + messageDate +
                '}';
    }
}
