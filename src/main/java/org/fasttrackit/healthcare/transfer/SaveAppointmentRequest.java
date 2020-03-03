package org.fasttrackit.healthcare.transfer;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class SaveAppointmentRequest {
    @NotNull
    private LocalDateTime appointmentDate;
    @NotNull
    private long patientID;
    @NotNull
    private String symptoms;
    @NotNull
    private String diagnostic;
    @NotNull
    private String treatment;
    private String recommendations;

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public long getPatientID() {
        return patientID;
    }

    public void setPatientID(long patientID) {
        this.patientID = patientID;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    @Override
    public String toString() {
        return "SaveAppointmentRequest{" +
                "appointmentDate=" + appointmentDate +
                ", patientID=" + patientID +
                ", symptoms='" + symptoms + '\'' +
                ", diagnostic='" + diagnostic + '\'' +
                ", treatment='" + treatment + '\'' +
                ", recommendations='" + recommendations + '\'' +
                '}';
    }
}
