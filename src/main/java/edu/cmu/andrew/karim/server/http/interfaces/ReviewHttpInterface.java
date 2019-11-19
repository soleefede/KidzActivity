package edu.cmu.andrew.karim.server.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.managers.ReviewManager;
import edu.cmu.andrew.karim.server.models.Review;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


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
}
