package edu.cmu.andrew.karim.server.models;

public class ActivityProvider {

    String id = null;
    String activityProviderId = null;
    String businessName = null;
    String entityName = null;
    String einNumber = null;
    String ssn = null;
    String address1 = null;
    String address2 = null;
    String city = null;
    String state = null;
    String phoneNumber = null;
    String email = null;
    String commissionPercentage = null;
    String bankName = null;
    String bankAccNumber = null;
    String pinCode = null;

    public ActivityProvider(String id, String activityProviderId, String businessName, String entityName, String einNumber, String ssn, String address1, String address2, String city, String state, String phoneNumber, String email, String commissionPercentage, String bankName, String bankAccNumber, String pinCode) {
        this.id = id;
        this.activityProviderId = activityProviderId;
        this.businessName = businessName;
        this.entityName = entityName;
        this.einNumber = einNumber;
        this.ssn = ssn;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.commissionPercentage = commissionPercentage;
        this.bankName = bankName;
        this.bankAccNumber = bankAccNumber;
        this.pinCode = pinCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityProviderId() {
        return activityProviderId;
    }

    public void setActivityProviderId(String activityProviderId) {
        this.activityProviderId = activityProviderId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEinNumber() {
        return einNumber;
    }

    public void setEinNumber(String einNumber) {
        this.einNumber = einNumber;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
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

    public String getCommissionPercentage() {
        return commissionPercentage;
    }

    public void setCommissionPercentage(String commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccNumber() {
        return bankAccNumber;
    }

    public void setBankAccNumber(String bankAccNumber) {
        this.bankAccNumber = bankAccNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

}
