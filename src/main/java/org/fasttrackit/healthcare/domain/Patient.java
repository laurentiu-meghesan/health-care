package org.fasttrackit.healthcare.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
//import java.time.Period;

@Entity
public class Patient {

    @Id
    private long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private LocalDate birthDate;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    /*    private LocalDate currentDate = LocalDate.now();
    private int patientAge = patientAge(getBirthDate(), currentDate);

    private static int patientAge(LocalDate birthDate, LocalDate currentDate) {
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

    public int getPatientAge() {
        return patientAge;
    }*/

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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", profile=" + profile +
                '}';
    }
}
