package edu.cmu.andrew.karim.server.models;

import edu.cmu.andrew.karim.server.managers.RankingManager;
import org.glassfish.hk2.api.Rank;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.Comparator;

public class Ranking implements Comparable<Ranking>{

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
//    int distance;
    String distance;
    String avgRating;

    public Ranking(String activityId, String activityName, String activityProviderId, String effectiveDate, String endDate, String activityCategory, String description, String photo, double price, String currency, String publishStatus, String distance, String avgRating) {
        super();
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
        this.distance = distance;
        this.avgRating = avgRating;
    }

    //    public String getAvgRating() {
//        return avgRating;
//    }
//
//    public void setAvgRating(String avgRating) {
//        this.avgRating = avgRating;
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

//    public int getDistance() {
//        return distance;
//    }
//
//    public void setDistance(int distance) {
//        this.distance = distance;
//    }

    public String getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(String avgRating) {
        this.avgRating = avgRating;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    

    @Override
  public int compareTo(Ranking ranking) {
      return Integer.parseInt(this.getDistance() )- Integer.parseInt(ranking.getDistance()) ;
    }
}
