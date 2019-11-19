package edu.cmu.andrew.karim.server.models;

public class Ranking {

    String activityId;
    String activityName;
    String activityProviderId;
    String effectiveDate;
    String endDate;
    String activityCategory;
    String description;
    String photo;
    double price;
    String currency;
    String publishStatus;
    double aveReview;

    public Ranking(String activityId, String activityName, String activityProviderId, String effectiveDate, String endDate, String activityCategory, String description, String photo, double price, String currency, String publishStatus) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.activityProviderId = activityProviderId;
        this.effectiveDate = effectiveDate;
        this.endDate = endDate;
        this.activityCategory = activityCategory;
        this.description = description;
        this.photo = photo;
        this.price = price;
        this.currency = currency;
        this.publishStatus = publishStatus;
        //this.aveReview = aveReview;
    }

//    public double getAveReview() {
//        return aveReview;
//    }
//
//    public void setAveReview(double aveReview) {
//        this.aveReview = aveReview;
//    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityProviderId() {
        return activityProviderId;
    }

    public void setActivityProviderId(String activityProviderId) {
        this.activityProviderId = activityProviderId;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(String activityCategory) {
        this.activityCategory = activityCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }
}
