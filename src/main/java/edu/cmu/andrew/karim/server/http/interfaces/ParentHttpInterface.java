package edu.cmu.andrew.karim.server.http.interfaces;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.http.exceptions.HttpBadRequestException;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.http.utils.PATCH;
import edu.cmu.andrew.karim.server.managers.ParentManager;
import edu.cmu.andrew.karim.server.models.Parent;
import edu.cmu.andrew.karim.server.utils.AppLogger;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


@Path("/parent")
public class ParentHttpInterface extends HttpInterface {

    private ObjectWriter ow;
    private MongoCollection<Document> parentCollection = null;

    public ParentHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postParent(Object request){

        try{
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            Parent newparent = new Parent(
                    null,
                    json.getString("parentId"),
                    json.getString("fistName"),
                    json.getString("lastName"),
                    json.getString("phoneNumber"),
                    json.getString("email"),
                    json.getString("address1"),
                    json.getString("address2"),
                    json.getString("city"),
                    json.getString("state"),
                    json.getString("zipCode"),
                    json.getString("country"),
                    json.getString("kidsAge"),
                    json.getString("location"),
                    json.getString("activityCategory")
            );
            ParentManager.getInstance().createParent(newparent);
            return new AppResponse("Insert Successful");

        }catch (Exception e){
            throw handleException("POST parents", e);
        }

    }

    @GET
    @Path("/{parentId}")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getSingleParent(@Context HttpHeaders headers, @PathParam("parentId") String parentId){

        try{
            AppLogger.info("Got an API call");
            ArrayList<Parent> parents = ParentManager.getInstance().getParentById(parentId);

            if(parents != null)
                return new AppResponse(parents);
            else
                throw new HttpBadRequestException(0, "Problem with getting parents");
        }catch (Exception e){
            throw handleException("GET /parent/{parentId}", e);
        }


    }


    @PATCH
    @Path("/{parentId}")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public AppResponse patchParent(Object request, @PathParam("parentId") String parentId){

        JSONObject json = null;

        try{
            json = new JSONObject(ow.writeValueAsString(request));
            Parent parent = new Parent(
                    parentId,
                    json.getString("parentId"),
                    json.getString("firstName"),
                    json.getString("lastName"),
                    json.getString("phoneNumber"),
                    json.getString("email"),
                    json.getString("address1"),
                    json.getString("address2"),
                    json.getString("city"),
                    json.getString("state"),
                    json.getString("zipCode"),
                    json.getString("country"),
                    json.getString("kidsAge"),
                    json.getString("location"),
                    json.getString("activityCategory")
            );

            ParentManager.getInstance().updateParent(parent);

        }catch (Exception e){
            throw handleException("PATCH parent/{parentId}", e);
        }

        return new AppResponse("Update Successful");
    }




    @DELETE
    @Path("/{parentId}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public AppResponse deleteParent(@PathParam("parentId") String parentId){

        try{
            ParentManager.getInstance().deleteParent(parentId);
            return new AppResponse("Delete Successful");
        }catch (Exception e){
            throw handleException("DELETE parent/{parentId}", e);
        }

    }

}
