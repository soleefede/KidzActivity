package edu.cmu.andrew.karim.server.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.http.exceptions.HttpBadRequestException;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.http.utils.PATCH;
import edu.cmu.andrew.karim.server.managers.ActivityProviderManager;
import edu.cmu.andrew.karim.server.models.ActivityProvider;
import edu.cmu.andrew.karim.server.utils.AppLogger;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/activityProvider")

public class ActivityProviderHttpInterface extends HttpInterface{

    private ObjectWriter ow;
    private MongoCollection<Document> activityProviderCollection = null;

    public ActivityProviderHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postActivityProvider(Object request){

        try{
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            ActivityProvider newactivityprovider = new ActivityProvider(
                    null,
                    json.getString("activityProviderId"),
                    json.getString("businessName"),
                    json.getString("entityName"),
                    json.getString("einNumber"),
                    json.getString("ssn"),
                    json.getString("address1"),
                    json.getString("address2"),
                    json.getString("city"),
                    json.getString("state"),
                    json.getString("phoneNumber"),
                    json.getString("email"),
                    json.getString("commissionPercentage"),
                    json.getString("bankName"),
                    json.getString("bankAccNumber"),
                    json.getString("pinCode")
            );
            ActivityProviderManager.getInstance().createActivityProvider(newactivityprovider);
            return new AppResponse("Insert Successful");

        }catch (Exception e){
            throw handleException("POST users", e);
        }

    }

    //Sorting: http://localhost:8080/api/users?sortby=riderBalance
    //Pagination: http://localhost:8080/api/users?offset=1&count=2
    //Sorting and Pagination are in the same function.I
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getActivityProvider(@Context HttpHeaders headers, @QueryParam("sortby") String sortby, @QueryParam("offset") Integer offset,
                                @QueryParam("count") Integer count, @QueryParam("entityName") String entityName){
        try{
            AppLogger.info("Got an API call");
            ArrayList<ActivityProvider> activityProviders = null;

            if(sortby != null)
                activityProviders = ActivityProviderManager.getInstance().getActivityProviderSorted(sortby);
            else if (entityName != null)
                activityProviders = ActivityProviderManager.getInstance().getActivityProviderFiltered(entityName);
            else if(offset != null && count != null)
                activityProviders = ActivityProviderManager.getInstance().getActivityProviderListPaginated(offset, count);
            else
                activityProviders = ActivityProviderManager.getInstance().getActivityProviderList();

            if(activityProviders != null)
                return new AppResponse(activityProviders);
            else
                throw new HttpBadRequestException(0, "Problem with getting activity providers");
        }catch (Exception e){
            throw handleException("GET /activityProvider", e);
        }
    }


    /*
  //http://server.com/api/users?begin=11&count=10
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getUsersPaginated(@Context HttpHeaders headers){

        try{
            AppLogger.info("Got an API call");
            ArrayList<User> users = UserManager.getInstance().getUserList();

            if(users != null)
                return new AppResponse(users);
            else
                throw new HttpBadRequestException(0, "Problem with getting users");
        }catch (Exception e){
            throw handleException("GET /users", e);
        }
    }*/

    @GET
    @Path("/{activityProviderId}")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getSingleUser(@Context HttpHeaders headers, @PathParam("activityProviderId") String activityProviderId){

        try{
            AppLogger.info("Got an API call");
            ArrayList<ActivityProvider> activityProviders = ActivityProviderManager.getInstance().getActivityProviderById(activityProviderId);

            if(activityProviders != null)
                return new AppResponse(activityProviders);
            else
                throw new HttpBadRequestException(0, "Problem with getting Activity providers");
        }catch (Exception e){
            throw handleException("GET /activityProvider/{activityProviderId}", e);
        }


    }


    @PATCH
    @Path("/{activityProviderId}")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public AppResponse patchUsers(Object request, @PathParam("activityProviderId") String activityProviderId){

        JSONObject json = null;

        try{
            json = new JSONObject(ow.writeValueAsString(request));
            ActivityProvider activityProvider = new ActivityProvider(
                    activityProviderId,
                    json.getString("activityProviderId"),
                    json.getString("businessName"),
                    json.getString("entityName"),
                    json.getString("einNumber"),
                    json.getString("ssn"),
                    json.getString("address1"),
                    json.getString("address2"),
                    json.getString("city"),
                    json.getString("state"),
                    json.getString("phoneNumber"),
                    json.getString("email"),
                    json.getString("commissionPercentage"),
                    json.getString("bankName"),
                    json.getString("bankAccNumber"),
                    json.getString("pinCode")
            );

            ActivityProviderManager.getInstance().updateActivityProvider(activityProvider);

        }catch (Exception e){
            throw handleException("PATCH activityProvider/{activityProviderId}", e);
        }

        return new AppResponse("Update Successful");
    }




    @DELETE
    @Path("/{activityProviderId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public AppResponse deleteUsers(@PathParam("activityProviderId") String activityProviderId){

        try{
            ActivityProviderManager.getInstance().deleteActivityProvider(activityProviderId);
            return new AppResponse("Delete Successful");
        }catch (Exception e){
            throw handleException("DELETE activityProvider/{activityProviderId}", e);
        }

    }


}
