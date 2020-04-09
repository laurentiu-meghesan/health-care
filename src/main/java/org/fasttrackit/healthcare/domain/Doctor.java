package org.fasttrackit.healthcare.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Doctor {

    @Id
    private long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String officeAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Profile profile;


    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return "Doctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", officeAddress='" + officeAddress + '\'' +
                ", profile=" + profile +
                '}';
    }
}
