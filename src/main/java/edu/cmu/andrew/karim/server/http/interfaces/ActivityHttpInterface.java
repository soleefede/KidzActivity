package edu.cmu.andrew.karim.server.http.interfaces;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.http.exceptions.HttpBadRequestException;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.http.utils.PATCH;
import edu.cmu.andrew.karim.server.managers.ActivityManager;
import edu.cmu.andrew.karim.server.models.Activity;
import edu.cmu.andrew.karim.server.models.User;
import edu.cmu.andrew.karim.server.managers.UserManager;
import edu.cmu.andrew.karim.server.utils.*;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


@Path("/activity")
public class ActivityHttpInterface extends HttpInterface {

    private ObjectWriter ow;
    private MongoCollection<Document>  activityCollection = null;

    public ActivityHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postActivity(Object request){

        try{
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            Activity newactivity = new Activity(
                    json.getString("activityId"),
                    json.getString("activityName"),
                    json.getString("activityProviderId"),
                    json.getString("effectiveDate"),
                    json.getString("endDate"),
                    json.getString("activityCategory"),
                    json.getString("description"),
                    json.getString("photo"),
                    json.getDouble("price"),
                    json.getString("currency"),
                    json.getString("publishStatus")
                    );
            ActivityManager.getInstance().createActivity(newactivity);
            return new AppResponse("Insert Successful");

        }catch (Exception e){
            throw handleException("POST users", e);
        }

    }


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

    @GET
    @Path("/{activityId}")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getSingleActivity(@Context HttpHeaders headers, @PathParam("activityId") String activityId){

        try{
            AppLogger.info("Got an API call");
            ArrayList<Activity> activity = ActivityManager.getInstance().getActivityById(activityId);

            if(activity != null)
                return new AppResponse(activity);
            else
                throw new HttpBadRequestException(0, "Problem with getting activity");
        }catch (Exception e){
            throw handleException("GET /activity/{activityId}", e);
        }

    }


    @PATCH
    @Path("/{activityId}")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public AppResponse patchActivity(Object request, @PathParam("activityId") String activityId){

        JSONObject json = null;

        try{
            json = new JSONObject(ow.writeValueAsString(request));
            Activity activity = new Activity(
                    activityId,
                    json.getString("activityName"),
                    json.getString("activityProviderId"),
                    json.getString("effectiveDate"),
                    json.getString("endDate"),
                    json.getString("activityCategory"),
                    json.getString("description"),
                    json.getString("photo"),
                    json.getDouble("price"),
                    json.getString("currency"),
                    json.getString("publishStatus")
            );

            ActivityManager.getInstance().updateActivity(activity);

        }catch (Exception e){
            throw handleException("PATCH activity/{activityId}", e);
        }

        return new AppResponse("Update Successful");
    }

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
}
