package edu.cmu.andrew.karim.server.models;

public class Activity {

 String activityId =null;
    String activityName =null;
    String activityProviderId =null;
    String  effectiveDate  =null;
    String  endDate  =null;
    String activityCategory =null;
    String description =null;
    //int ageLowerLimit ;
    //int ageUpperLimit;
    String photo;
    double price;
    String currency;
    String publishStatus;
    String avgRating;
    String updateUser;

    public Activity(String activityId, String activityName, String activityProviderId, String effectiveDate,
                    String endDate, String activityCategory, String description, String photo, double price, String currency ,String publishStatus
                    ,String avgRating, String updateUser) {
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
        this.avgRating = avgRating;
        this.updateUser = updateUser;
    }

    public  Activity changeActivity(String activityId, String activityName, String activityProviderId, String effectiveDate,
                                          String endDate, String activityCategory, String description, String photo, double price, String currency
            , String publishStatus, String updateUser)
    {
        Activity  activity = null;
        activity.activityId = activityId;
        activity.activityName = activityName;
        activity.activityProviderId = activityProviderId;
        activity.effectiveDate = effectiveDate;
        activity.endDate = endDate;
        activity.activityCategory = activityCategory;
        activity.description = description;
        activity.photo = photo;
        activity.price = price;
        activity.currency = currency;
        activity.publishStatus = publishStatus;
        this.updateUser = updateUser;
        return activity;

    }

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

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
