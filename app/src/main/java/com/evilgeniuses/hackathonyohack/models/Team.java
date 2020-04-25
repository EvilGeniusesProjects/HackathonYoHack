package com.evilgeniuses.hackathonyohack.models;

public class Team {
    public String teamProfileImageURL;
    public String teamName;
    public String teamStatus;
    public String teamPassword;
    public String teamIdea;


    public Team() {
    }

    public Team(String teamProfileImageURL, String teamName, String teamStatus, String teamPassword, String teamIdea) {
        this.teamProfileImageURL = teamProfileImageURL;
        this.teamName = teamName;
        this.teamStatus = teamStatus;
        this.teamPassword = teamPassword;
        this.teamIdea = teamIdea;
    }

    public String getTeamProfileImageURL() {
        return teamProfileImageURL;
    }

    public void setTeamProfileImageURL(String teamProfileImageURL) {
        this.teamProfileImageURL = teamProfileImageURL;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamPassword() {
        return teamPassword;
    }

    public void setTeamPassword(String teamPassword) {
        this.teamPassword = teamPassword;
    }

    public String getTeamIdea() {
        return teamIdea;
    }

    public void setTeamIdea(String teamIdea) {
        this.teamIdea = teamIdea;
    }

    public String getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(String teamStatus) {
        this.teamStatus = teamStatus;
    }
}
