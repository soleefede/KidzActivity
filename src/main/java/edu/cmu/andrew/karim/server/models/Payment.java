package edu.cmu.andrew.karim.server.models;

public class Payment {
    String paymentId;
    int noOfSeats;
    float activityPrice;
    float totalPrice;
    String paymentIdExternal;
    String paymentStatus;

    public Payment(String paymentId, int noOfSeats, float activityPrice, float totalPrice, String paymentIdExternal,
                   String paymentStatus) {

        this.paymentId = paymentId;
       this.noOfSeats = noOfSeats;
       this.activityPrice = activityPrice;
       this.totalPrice = totalPrice;
       this.paymentIdExternal = paymentIdExternal;
       this.paymentStatus = paymentStatus;

    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public float getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(float activityPrice) {
        this.activityPrice = activityPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentIdExternal() {
        return paymentIdExternal;
    }

    public void setPaymentIdExternal(String paymentIdExternal) {
        this.paymentIdExternal = paymentIdExternal;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


}
