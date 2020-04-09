package org.fasttrackit.healthcare.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String email;
    private boolean isDoctor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }
}
