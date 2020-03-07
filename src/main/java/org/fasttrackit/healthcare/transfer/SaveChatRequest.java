package org.fasttrackit.healthcare.transfer;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class SaveChatRequest {
    @NotNull
    private Long patientId;
    @NotNull
    private String messageSent;
    @NotNull
    private String messageReceived;
    @NotNull
    private LocalDateTime messageDate;

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
        return "SaveChatRequest{" +
                "patientId=" + patientId +
                ", messageSent='" + messageSent + '\'' +
                ", messageReceived='" + messageReceived + '\'' +
                ", messageDate=" + messageDate +
                '}';
    }
}
