package edu.cmu.andrew.karim.server.managers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import edu.cmu.andrew.karim.server.exceptions.AppException;
import edu.cmu.andrew.karim.server.exceptions.AppInternalServerException;
import edu.cmu.andrew.karim.server.models.Review;
import edu.cmu.andrew.karim.server.utils.MongoPool;
import org.bson.Document;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewManager extends Manager {
    public static ReviewManager _self;
    private MongoCollection<Document> reviewCollection;


    public ReviewManager() {
        this.reviewCollection = MongoPool.getInstance().getCollection("review");
    }

    public static ReviewManager getInstance() {
        if (_self == null)
            _self = new ReviewManager();
        return _self;
    }


    public void createReview(Review review) throws AppException {

        try {
            JSONObject json = new JSONObject(review);

            Document newDoc = new Document()
                    .append("reviewId", review.getReviewId())
                    .append("parentId", review.getParentId())
                    .append("reviewDate", review.getReviewDate())
                    .append("bookingId", review.getBookingId())
                    .append("ratings", review.getRatings())
                    .append("reviewComments", review.getReviewComments());


            if (newDoc != null)
                reviewCollection.insertOne(newDoc);
            else
                throw new AppInternalServerException(0, "Failed to create new review");

        } catch (Exception e) {
            throw handleException("Create Review", e);
        }

    }


    public ArrayList<Review> getReviewList() throws AppException {
        try{
            ArrayList<Review> reviewList = new ArrayList<>();
            FindIterable<Document> reviewDocs = reviewCollection.find();
            for(Document reviewDoc: reviewDocs) {
                Review review = new Review(
                        reviewDoc.getString("reviewId").toString(),
                        reviewDoc.getString("parentId"),
                        reviewDoc.getString("reviewDate").toString(),
                        reviewDoc.getString("bookingId"),
                        reviewDoc.getString("ratings"),
                        reviewDoc.getString("reviewComments")
                );
                reviewList.add(review);
            }
            return new ArrayList<>(reviewList);
        } catch(Exception e){
            throw handleException("Get Review List", e);
        }
    }

    public ArrayList<Review> getReviewBooking(String bookingId ) throws AppException {
        try{
            ArrayList<Review> reviewList = new ArrayList<>();
            //  FindIterable<Document> reviewDocs = reviewCollection.find();
            FindIterable<Document> reviewDocs = reviewCollection.find().filter(Filters.eq("bookingId",bookingId));

            for(Document reviewDoc: reviewDocs) {
                Review review = new Review(
                        reviewDoc.getString("reviewId").toString(),
                        reviewDoc.getString("parentId"),
                        reviewDoc.getString("reviewDate").toString(),
                        reviewDoc.getString("bookingId"),
                        reviewDoc.getString("ratings"),
                        reviewDoc.getString("reviewComments")
                );
                reviewList.add(review);
            }
            return new ArrayList<>(reviewList);
        } catch(Exception e){
            throw handleException("Get Review List", e);
        }
    }

    public ArrayList<Review> getReviewActivity(String activityId ) throws AppException {
        try{
            ArrayList<Review> reviewList = new ArrayList<>();
            FindIterable<Document> reviewDocs = reviewCollection.find();
            //FindIterable<Document> reviewDocs = reviewCollection.find().filter(Filters.eq("bookingId",bookingId));

            for(Document reviewDoc: reviewDocs) {
                Review review = new Review(
                        reviewDoc.getString("reviewId").toString(),
                        reviewDoc.getString("parentId"),
                        reviewDoc.getString("reviewDate").toString(),
                        reviewDoc.getString("bookingId"),
                        reviewDoc.getString("ratings"),
                        reviewDoc.getString("reviewComments")
                );
                reviewList.add(review);
            }
            return new ArrayList<>(reviewList);
        } catch(Exception e){
            throw handleException("Get Review List", e);
        }
    }



}