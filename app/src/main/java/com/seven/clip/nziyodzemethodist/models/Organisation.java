package com.seven.clip.nziyodzemethodist.models;

import java.util.List;

public class Organisation {
    private List<User> members;
    private String name;
    private String organisationId;

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public Organisation(List<User> members, String name, String organisationId) {

        this.members = members;
        this.name = name;
        this.organisationId = organisationId;
    }

    public Organisation() {

    }
}
