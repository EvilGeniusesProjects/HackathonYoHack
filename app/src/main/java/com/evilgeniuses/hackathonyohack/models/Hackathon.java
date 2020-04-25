package com.evilgeniuses.hackathonyohack.models;

public class Hackathon {


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

    public String getHackathonTasksYouWork() {
        return hackathonTasksYouWork;
    }

    public void setHackathonTasksYouWork(String hackathonTasksYouWork) {
        this.hackathonTasksYouWork = hackathonTasksYouWork;
    }

    public String getHackathonCurrentProgress() {
        return hackathonCurrentProgress;
    }

    public void setHackathonCurrentProgress(String hackathonCurrentProgress) {
        this.hackathonCurrentProgress = hackathonCurrentProgress;
    }

    public String getHackathonWhatAreYouGoingToDo() {
        return hackathonWhatAreYouGoingToDo;
    }

    public void setHackathonWhatAreYouGoingToDo(String hackathonWhatAreYouGoingToDo) {
        this.hackathonWhatAreYouGoingToDo = hackathonWhatAreYouGoingToDo;
    }

    public String getHackathonWhatQuestionsDoYouHave() {
        return hackathonWhatQuestionsDoYouHave;
    }

    public void setHackathonWhatQuestionsDoYouHave(String hackathonWhatQuestionsDoYouHave) {
        this.hackathonWhatQuestionsDoYouHave = hackathonWhatQuestionsDoYouHave;
    }

    public String getHackathonWhatKindOfMentorsDoYouNeed() {
        return hackathonWhatKindOfMentorsDoYouNeed;
    }

    public void setHackathonWhatKindOfMentorsDoYouNeed(String hackathonWhatKindOfMentorsDoYouNeed) {
        this.hackathonWhatKindOfMentorsDoYouNeed = hackathonWhatKindOfMentorsDoYouNeed;
    }

    public String hackathonAbout;
    public String hackathonDate;
    public String hackathonLogo;
    public String hackathonName;

    public String hackathonTasksYouWork;
    public String hackathonCurrentProgress;
    public String hackathonWhatAreYouGoingToDo;
    public String hackathonWhatQuestionsDoYouHave;
    public String hackathonWhatKindOfMentorsDoYouNeed;

    public Hackathon() {

    }

    public Hackathon(String hackathonAbout, String hackathonDate, String hackathonLogo, String hackathonName, String hackathonTasksYouWork, String hackathonCurrentProgress, String hackathonWhatAreYouGoingToDo, String hackathonWhatQuestionsDoYouHave, String hackathonWhatKindOfMentorsDoYouNeed) {
        this.hackathonAbout = hackathonAbout;
        this.hackathonDate = hackathonDate;
        this.hackathonLogo = hackathonLogo;
        this.hackathonName = hackathonName;
        this.hackathonTasksYouWork = hackathonTasksYouWork;
        this.hackathonCurrentProgress = hackathonCurrentProgress;
        this.hackathonWhatAreYouGoingToDo = hackathonWhatAreYouGoingToDo;
        this.hackathonWhatQuestionsDoYouHave = hackathonWhatQuestionsDoYouHave;
        this.hackathonWhatKindOfMentorsDoYouNeed = hackathonWhatKindOfMentorsDoYouNeed;
    }
}
