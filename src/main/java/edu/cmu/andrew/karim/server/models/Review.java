package edu.cmu.andrew.karim.server.models;

public class Review {
    String reviewId;
    String parentId;
    String bookingId;
    String ratings;
    String reviewComments;

    public Review(String reviewId, String parentId, String bookingId, String ratings, String reviewComments)
    {

        this.reviewId = reviewId;
        this.parentId = parentId;
        this.bookingId = bookingId;
        this.reviewComments = reviewComments;


    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getReviewComments() {
        return reviewComments;
    }

    public void setReviewComments(String reviewComments) {
        this.reviewComments = reviewComments;
    }
}
