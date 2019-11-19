package edu.cmu.andrew.karim.server.models;

public class Booking {
    String bookingId;
    String parentId;
    String activityId;
    String paymentId;
    String availabilityId;
    int noOfSeats;
    String kidName;
    String bookingStatus;
    String confirmStatus;

    public Booking(String bookingId, String parentId, String activityId,
                   String availabilityId,String paymentId, int noOfSeats, String kidName, String bookingStatus,
                   String confirmStatus) {
        this.bookingId = bookingId;
        this.parentId = parentId;
        this.activityId = activityId;
        this.paymentId = paymentId;
        this.availabilityId = availabilityId;
        this.noOfSeats = noOfSeats;
        this.kidName = kidName;
        this.bookingStatus = bookingStatus;
        this.confirmStatus = confirmStatus;

    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(String availabilityId) {
        this.availabilityId = availabilityId;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getKidName() {
        return kidName;
    }

    public void setKidName(String kidName) {
        this.kidName = kidName;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }


}
