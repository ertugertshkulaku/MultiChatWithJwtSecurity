package com.example.Authentication.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LogInRequest {
    @NotBlank
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}