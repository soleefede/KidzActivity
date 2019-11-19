package edu.cmu.andrew.karim.server.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.http.exceptions.HttpBadRequestException;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.managers.RankingManager;
import edu.cmu.andrew.karim.server.models.Ranking;
import edu.cmu.andrew.karim.server.utils.AppLogger;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/ranking")
public class RankingHttpInterface extends HttpInterface {

    private ObjectWriter ow;
    private MongoCollection<Document> activityCollection = null;

    public RankingHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getActivity(@Context HttpHeaders headers, @QueryParam("sortby") String sortby, @QueryParam("offset") Integer offset,
                                   @QueryParam("count") Integer count, @QueryParam("category") String activityCategory ){
        try{
            AppLogger.info("Got an API call");
            ArrayList<Ranking> rankings = null;

//            if(sortby != null)
//                activities = ActivityManager.getInstance().getActivityListSorted(sortby);
//            else if (activityCategory != null)
//                activities = ActivityManager.getInstance().getActivityListFiltered(activityCategory);
//            else if(offset != null && count != null)
//                activities = ActivityManager.getInstance().getActivityListPaginated(offset, count);
//            else
                rankings  = RankingManager.getInstance().getRankingList();

            if(rankings != null)
                return new AppResponse(rankings);
            else
                throw new HttpBadRequestException(0, "Problem with getting rankings");
        }catch (Exception e){
            throw handleException("GET / ranking", e);
        }
    }

}
