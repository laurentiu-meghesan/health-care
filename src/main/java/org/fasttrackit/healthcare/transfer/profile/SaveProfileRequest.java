package org.fasttrackit.healthcare.transfer.profile;

import javax.validation.constraints.NotNull;

public class SaveProfileRequest {
    @NotNull
    private Long userId;
    @NotNull
    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String email;
    private boolean isDoctor;

    public long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
