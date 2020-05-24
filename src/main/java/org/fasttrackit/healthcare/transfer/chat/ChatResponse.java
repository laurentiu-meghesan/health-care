package org.fasttrackit.healthcare.transfer.chat;

import java.time.LocalDateTime;

public class ChatResponse {

    private long id;
    private long patientId;
    private long doctorId;
    private String messageSent;
    private String messageReceived;
    private LocalDateTime messageDate;
    private LocalDateTime messageReceivedDate;

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

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
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

    public LocalDateTime getMessageReceivedDate() {
        return messageReceivedDate;
    }

    public void setMessageReceivedDate(LocalDateTime messageReceivedDate) {
        this.messageReceivedDate = messageReceivedDate;
    }

    @Override
    public String toString() {
        return "ChatResponse{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", messageSent='" + messageSent + '\'' +
                ", messageReceived='" + messageReceived + '\'' +
                ", messageDate=" + messageDate +
                ", messageReceivedDate=" + messageReceivedDate +
                '}';
    }
}
