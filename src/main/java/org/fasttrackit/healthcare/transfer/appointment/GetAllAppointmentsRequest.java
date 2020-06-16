package org.fasttrackit.healthcare.transfer.appointment;

import java.time.LocalDateTime;

public class GetAllAppointmentsRequest {

    private LocalDateTime appointmentDate;

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    @Override
    public String toString() {
        return "GetAllAppointmentsRequest{" +
                "appointmentDate=" + appointmentDate +
                '}';
    }
}
