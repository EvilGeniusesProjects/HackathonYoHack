package com.evilgeniuses.hackathonyohack.models;

public class Team {

    public String teamProfileImageURL;
    public String teamName;
    public String teamStatus;
    public String teamPassword;
    public String teamIdea;


    public String hackathonTasksYouWork;
    public String hackathonCurrentProgress;
    public String hackathonWhatAreYouGoingToDo;
    public String hackathonWhatQuestionsDoYouHave;
    public String hackathonWhatKindOfMentorsDoYouNeed;

    public Team() {
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

    public String getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(String teamStatus) {
        this.teamStatus = teamStatus;
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



    public Team(String teamProfileImageURL, String teamName, String teamStatus, String teamPassword, String teamIdea, String hackathonTasksYouWork, String hackathonCurrentProgress, String hackathonWhatAreYouGoingToDo, String hackathonWhatQuestionsDoYouHave, String hackathonWhatKindOfMentorsDoYouNeed) {
        this.teamProfileImageURL = teamProfileImageURL;
        this.teamName = teamName;
        this.teamStatus = teamStatus;
        this.teamPassword = teamPassword;
        this.teamIdea = teamIdea;
        this.hackathonTasksYouWork = hackathonTasksYouWork;
        this.hackathonCurrentProgress = hackathonCurrentProgress;
        this.hackathonWhatAreYouGoingToDo = hackathonWhatAreYouGoingToDo;
        this.hackathonWhatQuestionsDoYouHave = hackathonWhatQuestionsDoYouHave;
        this.hackathonWhatKindOfMentorsDoYouNeed = hackathonWhatKindOfMentorsDoYouNeed;
    }

}
