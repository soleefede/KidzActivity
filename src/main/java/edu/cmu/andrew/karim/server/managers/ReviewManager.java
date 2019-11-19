package edu.cmu.andrew.karim.server.managers;

import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.exceptions.AppException;
import edu.cmu.andrew.karim.server.exceptions.AppInternalServerException;
import edu.cmu.andrew.karim.server.models.Review;
import edu.cmu.andrew.karim.server.utils.MongoPool;
import org.bson.Document;
import org.json.JSONObject;

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
}
