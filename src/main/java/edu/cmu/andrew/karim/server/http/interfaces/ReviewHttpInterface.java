package edu.cmu.andrew.karim.server.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.http.exceptions.HttpBadRequestException;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.managers.ReviewManager;
import edu.cmu.andrew.karim.server.models.Review;
import edu.cmu.andrew.karim.server.utils.AppLogger;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


@Path("/review")
public class ReviewHttpInterface extends HttpInterface {

    private ObjectWriter ow;
    private MongoCollection<Document> reviewCollection = null;

    public ReviewHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postReview(Object request) {
        String reviewId;
        String parentId;
        String bookingId;
        String ratings;
        String reviewComments;
        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            Review newreview = new Review(
                    json.getString("reviewId"),
                    json.getString("parentId"),
                    json.getString("reviewDate"),
                    json.getString("bookingId"),
                    json.getString("ratings"),
                    json.getString("reviewComments")
            );

            ReviewManager.getInstance().createReview(newreview);
            return new AppResponse("Insert Successful");

        } catch (Exception e) {
            throw handleException("POST users", e);
        }

    }

    @GET
    //@Path("/{activityId}")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getReviews(@Context HttpHeaders headers, @QueryParam("bookingId") String bookingId ,@QueryParam("activityId") String activityId) {

        try {
            AppLogger.info("Got an API call");
            ArrayList<Review> reviews = null;

            if (bookingId != null)
                //reviews = ReviewManager.getInstance().getReviewBooking(activityId);
                reviews = ReviewManager.getInstance().getReviewList();
            else if (activityId != null)
                reviews = ReviewManager.getInstance().getReviewActivity(activityId);
            else
                reviews = ReviewManager.getInstance().getReviewList();

            if (reviews != null)
                return new AppResponse(reviews);
            else
                throw new HttpBadRequestException(0, "Problem with getting availability");
        } catch (Exception e) {
            throw handleException("GET /availability/{activityId}", e);
        }

    }
}
