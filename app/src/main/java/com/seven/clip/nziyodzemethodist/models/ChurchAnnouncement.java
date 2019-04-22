package com.seven.clip.nziyodzemethodist.models;

import com.google.firebase.firestore.model.value.ReferenceValue;

import java.util.Date;

public class ChurchAnnouncement {
    private String shortDescription;
    private String detailedDescription;
    private Date publishedDate;
    private String userId;
    private String churchId;

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }

    public String getTargetOrganisationId() {
        return targetOrganisationId;
    }

    public void setTargetOrganisationId(String targetOrganisationId) {
        this.targetOrganisationId = targetOrganisationId;
    }

    public ChurchAnnouncement(String shortDescription, String detailedDescription, Date publishedDate, String userId, String churchId, String targetOrganisationId) {

        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.publishedDate = publishedDate;
        this.userId = userId;
        this.churchId = churchId;
        this.targetOrganisationId = targetOrganisationId;
    }

    public ChurchAnnouncement() {

    }

    private String targetOrganisationId;
}
