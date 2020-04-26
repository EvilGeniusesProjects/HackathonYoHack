package com.evilgeniuses.hackathonyohack.models;

public class User {
    public String userID;
    public String userUsername;
    public String userUsernameSearch;
    public String userProfileImageURL;
    public String userName;
    public String userLastname;
    public String userEmail;
    public String userPassword;
    public String userStatus;
    public String userTeam;
    public String userCategory;
    public String userPhoneNumber;
    public String userTelegram;
    public String userInstagram;
    public String userVK;

    public String getUserAbilities() {
        return userAbilities;
    }

    public void setUserAbilities(String userAbilities) {
        this.userAbilities = userAbilities;
    }

    public String userAbilities;


    public User(String userID, String userUsername, String userUsernameSearch, String userProfileImageURL, String userName, String userLastname, String userEmail, String userPassword, String userStatus, String userTeam, String userCategory, String userPhoneNumber, String userTelegram, String userInstagram, String userVK, boolean generalСhatActivity, String userAbilities) {
        this.userID = userID;
        this.userUsername = userUsername;
        this.userUsernameSearch = userUsernameSearch;
        this.userProfileImageURL = userProfileImageURL;
        this.userName = userName;
        this.userLastname = userLastname;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userStatus = userStatus;
        this.userTeam = userTeam;
        this.userCategory = userCategory;
        this.generalСhatActivity = generalСhatActivity;

        this.userPhoneNumber = userPhoneNumber;
        this.userTelegram = userTelegram;
        this.userInstagram = userInstagram;
        this.userVK = userVK;
        this.userAbilities = userAbilities;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserTelegram() {
        return userTelegram;
    }

    public void setUserTelegram(String userTelegram) {
        this.userTelegram = userTelegram;
    }

    public String getUserInstagram() {
        return userInstagram;
    }

    public void setUserInstagram(String userInstagram) {
        this.userInstagram = userInstagram;
    }

    public String getUserVK() {
        return userVK;
    }

    public void setUserVK(String userVK) {
        this.userVK = userVK;
    }




    public boolean isGeneralСhatActivity() {
        return generalСhatActivity;
    }

    public void setGeneralСhatActivity(boolean generalСhatActivity) {
        this.generalСhatActivity = generalСhatActivity;
    }

    public boolean generalСhatActivity;



    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserUsernameSearch() {
        return userUsernameSearch;
    }

    public void setUserUsernameSearch(String userUsernameSearch) {
        this.userUsernameSearch = userUsernameSearch;
    }

    public String getUserProfileImageURL() {
        return userProfileImageURL;
    }

    public void setUserProfileImageURL(String userProfileImageURL) {
        this.userProfileImageURL = userProfileImageURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserTeam() {
        return userTeam;
    }

    public void setUserTeam(String userTeam) {
        this.userTeam = userTeam;
    }
}
