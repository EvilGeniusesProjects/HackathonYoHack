package com.evilgeniuses.hackathonyohack.models;

public class Hackathon {

    public Hackathon(String hackathonAbout, String hackathonDate, String hackathonLogo, String hackathonName) {
        this.hackathonAbout = hackathonAbout;
        this.hackathonDate = hackathonDate;
        this.hackathonLogo = hackathonLogo;
        this.hackathonName = hackathonName;
    }

    public String getHackathonAbout() {
        return hackathonAbout;
    }

    public void setHackathonAbout(String hackathonAbout) {
        this.hackathonAbout = hackathonAbout;
    }

    public String getHackathonDate() {
        return hackathonDate;
    }

    public void setHackathonDate(String hackathonDate) {
        this.hackathonDate = hackathonDate;
    }

    public String getHackathonLogo() {
        return hackathonLogo;
    }

    public void setHackathonLogo(String hackathonLogo) {
        this.hackathonLogo = hackathonLogo;
    }

    public String getHackathonName() {
        return hackathonName;
    }

    public void setHackathonName(String hackathonName) {
        this.hackathonName = hackathonName;
    }

    public String hackathonAbout;
    public String hackathonDate;
    public String hackathonLogo;
    public String hackathonName;

    public Hackathon() {

    }

}
