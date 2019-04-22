package com.seven.clip.nziyodzemethodist.models;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class Church {
    private String churchId;
    private String circuit;
    private String society;
    private GeoPoint location;
    private String address;
    private String city;
    private String district;
    private List<String> organisationIds;

    public Church(String churchId, String circuit, String society, GeoPoint location, String address, String city, String district, List<String> organisationIds, List<User> members) {
        this.churchId = churchId;
        this.circuit = circuit;
        this.society = society;
        this.location = location;
        this.address = address;
        this.city = city;
        this.district = district;
        this.organisationIds = organisationIds;
        this.members = members;
    }

    public String getChurchId() {

        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }

    public String getCircuit() {
        return circuit;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public List<String> getOrganisationIds() {
        return organisationIds;
    }

    public void setOrganisationIds(List<String> organisationIds) {
        this.organisationIds = organisationIds;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public Church() {

    }

    private List<User> members;


}
