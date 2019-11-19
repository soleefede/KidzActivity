package edu.cmu.andrew.karim.server.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.http.exceptions.HttpBadRequestException;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.http.utils.PATCH;
import edu.cmu.andrew.karim.server.managers.AvailabilityManager;
import edu.cmu.andrew.karim.server.models.Availability;
import edu.cmu.andrew.karim.server.utils.AppLogger;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


@Path("/availability")
public class AvailabilityHttpInterface extends HttpInterface {

    private ObjectWriter ow;
    private MongoCollection<Document>  availabilityCollection = null;

    public AvailabilityHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @POST
    @Path("/{activityId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postAvailability(Object request,@PathParam("activityId") String activityId){

        try{
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            Availability newavailability = new Availability(
                    json.getString("availabilityId"),
                    activityId,
                    json.getString("startDate"),
                    json.getString("endDate"),
                    json.getString("availabilityDate"),
                    json.getString("timeSlot"),
                    json.getInt("noOfSeats"),
                    json.getInt("seatsAvailable")
            );


            AvailabilityManager.getInstance().createAvailability(newavailability);
            return new AppResponse("Insert Successful");

        }catch (Exception e){
            throw handleException("POST availability", e);
        }

    }

/*
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getActivity(@Context HttpHeaders headers, @QueryParam("sortby") String sortby, @QueryParam("offset") Integer offset,
                                   @QueryParam("count") Integer count,@QueryParam("category") String activityCategory ){
        try{
            AppLogger.info("Got an API call");
            ArrayList<Activity> activities = null;

            if(sortby != null)
                activities = ActivityManager.getInstance().getActivityListSorted(sortby);
            else if (activityCategory != null)
                activities = ActivityManager.getInstance().getActivityListFiltered(activityCategory);
            else if(offset != null && count != null)
                activities = ActivityManager.getInstance().getActivityListPaginated(offset, count);
            else
                activities  = ActivityManager.getInstance().getActivityList();

            if( activities  != null)
                return new AppResponse( activities );
            else
                throw new HttpBadRequestException(0, "Problem with getting  activities ");
        }catch (Exception e){
            throw handleException("GET / activities", e);
        }
    }
*/
    @GET
    @Path("/{activityId}")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getActivityAvailability(@Context HttpHeaders headers, @PathParam("activityId") String activityId){

        try{
            AppLogger.info("Got an API call");
            ArrayList<Availability> availability = AvailabilityManager.getInstance().getActivityAvailability(activityId);

            if(availability != null)
                return new AppResponse(availability);
            else
                throw new HttpBadRequestException(0, "Problem with getting availability");
        }catch (Exception e){
            throw handleException("GET /availability/{activityId}", e);
        }

    }


    @PATCH
    @Path("/{availabilityId}")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public AppResponse patchActivity(Object request, @PathParam("availabilityId") String availabilityId){

        JSONObject json = null;

        try{
            json = new JSONObject(ow.writeValueAsString(request));

            AvailabilityManager.getInstance().updateAvailability(availabilityId,json.getString("action"),json.getInt("noOfSeats"));

        }catch (Exception e){
            throw handleException("PATCH activity/{activityId}", e);
        }

        return new AppResponse("Update Successful");
    }

    /*

    @DELETE
    @Path("/{activityId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public AppResponse deleteUsers(@PathParam("activityId") String activityId){

        try{
            ActivityManager.getInstance().deleteActivity(activityId);
            return new AppResponse("Delete Successful");
        }catch (Exception e){
            throw handleException("DELETE users/{userId}", e);
        }

    }

 */
}
