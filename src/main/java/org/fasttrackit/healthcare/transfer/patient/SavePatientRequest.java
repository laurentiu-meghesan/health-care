package org.fasttrackit.healthcare.transfer.patient;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class SavePatientRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private LocalDate birthDate;
    private long profileId;

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "SavePatientRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", profileId=" + profileId +
                '}';
    }
}
