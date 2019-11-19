package edu.cmu.andrew.karim.server.models;

public class Parent {

    String id = null;
    String parentId = null;
    String firstName = null;
    String lastName = null;
    String phoneNumber = null;
    String email = null;
    String address1 = null;
    String address2 = null;
    String city = null;
    String state = null;
    String zipCode = null;
    String country = null;
    String kidsAge = null;
    String location = null;
    String activityCategory = null;

    public Parent(String id, String parentId, String firstName, String lastName, String phoneNumber, String email, String address1, String address2, String city, String state, String zipCode, String country, String kidsAge, String location, String activityCategory) {
        this.id = id;
        this.parentId = parentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.kidsAge = kidsAge;
        this.location = location;
        this.activityCategory = activityCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getKidsAge() {
        return kidsAge;
    }

    public void setKidsAge(String kidsAge) {
        this.kidsAge = kidsAge;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(String activityCategory) {
        this.activityCategory = activityCategory;
    }
}
