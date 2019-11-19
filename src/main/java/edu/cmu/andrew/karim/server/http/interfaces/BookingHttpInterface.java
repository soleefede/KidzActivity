package edu.cmu.andrew.karim.server.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.http.utils.PATCH;
import edu.cmu.andrew.karim.server.managers.BookingManager;
import edu.cmu.andrew.karim.server.models.Booking;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/booking")
public class BookingHttpInterface extends HttpInterface {

    private ObjectWriter ow;
    private MongoCollection<Document> bookingCollection = null;

    public BookingHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postBooking(Object request) {

        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            Booking newbooking = new Booking(
                    json.getString("bookingId"),
                    json.getString("parentId"),
                    json.getString("activityId"),
                    json.getString("availabilityId"),
                    "",
                    json.getInt("noOfSeats"),
                    json.getString("kidName"),
                    json.getString("bookingStatus"),
                    json.getString("confirmStatus")
            );

            BookingManager.getInstance().createBooking(newbooking);
            return new AppResponse("Insert Successful");

        } catch (Exception e) {
            throw handleException("POST users", e);
        }

    }

    @PATCH
    @Path("/{bookingId}")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public AppResponse patchBooking(Object request, @PathParam("bookingId") String bookingId){

        JSONObject json = null;

        try{
            json = new JSONObject(ow.writeValueAsString(request));

            BookingManager.getInstance().updateBooking(bookingId,json.getString("paymentId"),json.getString("bookingStatus"),json.getString("confirmStatus"));

        }catch (Exception e){
            throw handleException("PATCH booking/{bookingId}", e);
        }

        return new AppResponse("Update Successful");
    }


}
