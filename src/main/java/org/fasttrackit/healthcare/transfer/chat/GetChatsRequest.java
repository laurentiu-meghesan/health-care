package org.fasttrackit.healthcare.transfer.chat;

public class GetChatsRequest {
    private Long patientId;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return "GetChatsRequest{" +
                "patientId=" + patientId +
                '}';
    }
}
