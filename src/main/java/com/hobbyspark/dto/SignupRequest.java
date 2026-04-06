package com.hobbyspark.dto;

import jakarta.validation.constraints.NotBlank;

public class SignupRequest {
    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    public SignupRequest() {}

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}