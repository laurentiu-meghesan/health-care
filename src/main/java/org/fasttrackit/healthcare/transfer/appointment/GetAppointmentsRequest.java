package org.fasttrackit.healthcare.transfer.appointment;

import java.time.LocalDateTime;

public class GetAppointmentsRequest {

    private Long patientId;
    private LocalDateTime searchDate;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(LocalDateTime searchDate) {
        this.searchDate = searchDate;
    }

    @Override
    public String toString() {
        return "GetAppointmentsRequest{" +
                "patientId=" + patientId +
                ", searchDate=" + searchDate +
                '}';
    }
}
