package com.seven.clip.nziyodzemethodist.models;

import com.google.firebase.auth.FirebaseUser;

public class User {
    private String uId;
    private String email;
    private String displayName;
    private String username;
    private String churchId;
    private String organizationId;
    private boolean inChoir;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public boolean isInChoir() {
        return inChoir;
    }

    public void setInChoir(boolean inChoir) {
        this.inChoir = inChoir;
    }

    public User() {

    }

    public User(FirebaseUser firebaseUser){
        uId = firebaseUser.getUid();
        email = firebaseUser.getEmail();
        displayName = firebaseUser.getDisplayName();
    }
}
