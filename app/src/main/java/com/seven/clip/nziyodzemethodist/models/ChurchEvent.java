package com.seven.clip.nziyodzemethodist.models;

import java.util.Date;

public class ChurchEvent {
    private String shortDescription;
    private String detailedDescription;
    private Date publishedDate;
    private Date startDate;
    private Date endDate;
    private User publisher;
    private String targetChurchId;
    private String targetOrganisationId;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public String getTargetChurchId() {
        return targetChurchId;
    }

    public void setTargetChurchId(String targetChurchId) {
        this.targetChurchId = targetChurchId;
    }

    public String getTargetOrganisationId() {
        return targetOrganisationId;
    }

    public void setTargetOrganisationId(String targetOrganisationId) {
        this.targetOrganisationId = targetOrganisationId;
    }

    public ChurchEvent(String shortDescription, String detailedDescription, Date publishedDate, Date startDate, Date endDate, User publisher, String targetChurchId, String targetOrganisationId) {

        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.publishedDate = publishedDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.publisher = publisher;
        this.targetChurchId = targetChurchId;
        this.targetOrganisationId = targetOrganisationId;
    }

    public ChurchEvent() {

    }
}
