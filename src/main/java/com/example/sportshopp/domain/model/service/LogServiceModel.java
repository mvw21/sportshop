package com.example.sportshopp.domain.model.service;


import java.time.LocalDateTime;

public class LogServiceModel extends BaseServiceModel{
    private String username;
    private String description;
    private LocalDateTime time;

    public LogServiceModel() {
        this.time = LocalDateTime.now();
    }

    public LogServiceModel(String username, String description) {
        this.username = username;
        this.description = description;
        this.time = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
