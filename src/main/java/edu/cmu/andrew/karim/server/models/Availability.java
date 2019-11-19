package edu.cmu.andrew.karim.server.models;

public class Availability {

    String availabilityId =null;
    String activityId =null;
    String  startDate  =null;
    String  endDate  =null;
    String availabilityDate =null;
    String timeSlot =null;
    int noOfSeats;
    int seatsAvailable;

    public Availability(String availabilityId, String activityId, String startDate, String endDate,
                    String availabilityDate, String timeSlot, int noOfSeats, int seatsAvailable) {
        this.availabilityId = availabilityId;
        this.activityId = activityId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.endDate = endDate;
        this.availabilityDate = availabilityDate;
        this.timeSlot = timeSlot;
        this.noOfSeats = noOfSeats;
        this.seatsAvailable = seatsAvailable;

    }
    public String getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(String availabilityId) {
        this.availabilityId = availabilityId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(String availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }


}
