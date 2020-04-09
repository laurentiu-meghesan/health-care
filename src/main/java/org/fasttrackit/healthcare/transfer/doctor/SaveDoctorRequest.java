package org.fasttrackit.healthcare.transfer.doctor;

import javax.validation.constraints.NotNull;

public class SaveDoctorRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String officeAddress;
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

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    @Override
    public String toString() {
        return "SaveDoctorRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", officeAddress='" + officeAddress + '\'' +
                ", profileId=" + profileId +
                '}';
    }
}
