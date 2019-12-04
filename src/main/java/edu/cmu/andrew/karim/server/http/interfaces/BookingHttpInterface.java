package edu.cmu.andrew.karim.server.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.http.exceptions.HttpBadRequestException;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.http.utils.PATCH;
import edu.cmu.andrew.karim.server.managers.BookingManager;
import edu.cmu.andrew.karim.server.models.Booking;
import edu.cmu.andrew.karim.server.utils.AppLogger;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


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
            System.out.println("I am here ");
            Booking newbooking = new Booking(
                    json.getString("bookingId"),
                    json.getString("bookingDate"),
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

    @GET
   
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getBooking(@Context HttpHeaders headers, @QueryParam("activityId") String activityId){

        try{
            AppLogger.info("Got an API call");
            ArrayList<Booking> bookings  = null;

            if(activityId != null)
                bookings = BookingManager.getInstance().getBookingActivity(activityId);
            else
               bookings = BookingManager.getInstance().getBookingList();

            if( bookings != null)
                return new AppResponse(bookings);
            else
                throw new HttpBadRequestException(0, "Problem with getting availability");
        }catch (Exception e){
            throw handleException("GET /availability/{activityId}", e);
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
