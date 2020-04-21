package org.fasttrackit.healthcare.transfer.chat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class SaveChatRequest {

    private Long patientId;
    private Long doctorId;
    private String messageSent;
    private String messageReceived;
    private LocalDateTime messageDate;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
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
                ", doctorId=" + doctorId +
                ", messageSent='" + messageSent + '\'' +
                ", messageReceived='" + messageReceived + '\'' +
                ", messageDate=" + messageDate +
                '}';
    }
}
