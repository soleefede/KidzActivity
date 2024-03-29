package edu.cmu.andrew.karim.server.managers;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import edu.cmu.andrew.karim.server.exceptions.AppException;
import edu.cmu.andrew.karim.server.exceptions.AppInternalServerException;
import edu.cmu.andrew.karim.server.exceptions.AppUnauthorizedException;
import edu.cmu.andrew.karim.server.models.Activity;
import edu.cmu.andrew.karim.server.models.Ranking;
import edu.cmu.andrew.karim.server.models.Session;
import edu.cmu.andrew.karim.server.models.User;
import edu.cmu.andrew.karim.server.utils.MongoPool;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActivityManager extends Manager {
    public static ActivityManager _self;
    private MongoCollection<Document> activityCollection;


    public ActivityManager() {
        this.activityCollection = MongoPool.getInstance().getCollection("activity");
    }

    public static ActivityManager getInstance(){
        if (_self == null)
            _self = new ActivityManager();
        return _self;
    }


    public void createActivity(@Context HttpHeaders headers,Activity activity) throws AppException {

        try{
            Session session = SessionManager.getInstance().getSessionForToken(headers);
            ArrayList<User> user = UserManager.getInstance().getUserById(session.getUserId());
            if(!session.getUserId().equals(activity.getUpdateUser()))
                throw new AppUnauthorizedException(70,"Invalid user id");

            JSONObject json = new JSONObject(activity);

            Document newDoc = new Document()
                    .append("activityId", activity.getActivityId())
                    .append("activityName", activity.getActivityName())
                    .append("activityProviderId",activity.getActivityProviderId())
                    .append("effectiveDate",activity.getEffectiveDate())
                    .append("endDate",activity.getEndDate())
                    .append("activityCategory",activity.getActivityCategory())
                    .append("description",activity.getDescription())
                    .append("photo",activity.getPhoto())
                    .append("price",activity.getPrice())
                    .append("currency",activity.getCurrency())
                    .append("publishStatus",activity.getPublishStatus())
                    .append("updateUser", activity.getUpdateUser());
            if (newDoc != null)
                activityCollection.insertOne(newDoc);
            else
                throw new AppInternalServerException(0, "Failed to create new activity");

        }catch(Exception e){
            throw handleException("Create Activity", e);
        }

    }

    public ArrayList<Activity> getActivityList() throws AppException {
        try{
            ArrayList<Activity> activityList = new ArrayList<>();
            FindIterable<Document> activityDocs = activityCollection.find();
            for(Document activityDoc: activityDocs) {
                Activity activity = new Activity(
                        activityDoc.getString("activityId").toString(),
                        activityDoc.getString("activityName").toString(),
                        activityDoc.getString("activityProviderId"),
                        activityDoc.getString("effectiveDate"),
                        activityDoc.getString("endDate"),
                        activityDoc.getString("activityCategory"),
                        activityDoc.getString("description"),
                        activityDoc.getString("photo"),
                        activityDoc.getDouble("price"),
                        activityDoc.getString("currency"),
                        activityDoc.getString("publishStatus"),
                        activityDoc.getString("avgRating"),
                        activityDoc.getString("updateUser")
                );
                activityList.add(activity);
            }
            return new ArrayList<>(activityList);
        } catch(Exception e){
            throw handleException("Get Activity List", e);
        }
    }


    public ArrayList<Ranking> getActivityListByDistance(String location) throws AppException {
        try{
            ArrayList<Activity> activityList = new ArrayList<>();
            ArrayList<Ranking> rankedList = new ArrayList<>();
            FindIterable<Document> activityDocs = activityCollection.find();
            for(Document activityDoc: activityDocs) {
                Activity activity = new Activity(
                        activityDoc.getString("activityId").toString(),
                        activityDoc.getString("activityName").toString(),
                        activityDoc.getString("activityProviderId"),
                        activityDoc.getString("effectiveDate"),
                        activityDoc.getString("endDate"),
                        activityDoc.getString("activityCategory"),
                        activityDoc.getString("description"),
                        activityDoc.getString("photo"),
                        activityDoc.getDouble("price"),
                        activityDoc.getString("currency"),
                        activityDoc.getString("publishStatus"),
                        activityDoc.getString("avgRating"),
                        activityDoc.getString("updateUser")
                );
                activityList.add(activity);
            }

            rankedList = RankingManager.getInstance().calculateDistance(location);

            return new ArrayList<>(rankedList);
        } catch(Exception e){
            throw handleException("Get Activity List", e);
        }
    }


    public ArrayList<Activity> getActivityListSorted(String sortby) throws AppException {
        try{
            ArrayList<Activity> activityList = new ArrayList<>();
            BasicDBObject sortParams = new BasicDBObject();
            sortParams.put(sortby, 1);
            FindIterable<Document> activityDocs = activityCollection.find().sort(sortParams);
            for(Document activityDoc: activityDocs) {
                Activity activity = new Activity(
                        activityDoc.getString("activityId").toString(),
                        activityDoc.getString("activityName").toString(),
                        activityDoc.getString("activityProviderId"),
                        activityDoc.getString("effectiveDate"),
                        activityDoc.getString("endDate"),
                        activityDoc.getString("activityCategory"),
                        activityDoc.getString("description"),
                        activityDoc.getString("photo"),
                        activityDoc.getDouble("price"),
                        activityDoc.getString("currency"),
                        activityDoc.getString("publishStatus"),
                        activityDoc.getString("avgRating"),
                        activityDoc.getString("updateUser")
                );
                activityList.add(activity);
            }
            return new ArrayList<>(activityList);
        } catch(Exception e){
            throw handleException("Get Activity List", e);
        }
    }

    public ArrayList<Activity> getActivityListFiltered(String activityCategory) throws AppException {
        try{
            ArrayList<Activity> activityList = new ArrayList<>();
           // BasicDBObject filterParams = new BasicDBObject();
            //filterParams.put(filterby, 1);
            FindIterable<Document> activityDocs = activityCollection.find().filter(Filters.eq("activityCategory",activityCategory));
            for(Document activityDoc: activityDocs) {
                Activity activity = new Activity(
                        activityDoc.getString("activityId").toString(),
                        activityDoc.getString("activityName").toString(),
                        activityDoc.getString("activityProviderId"),
                        activityDoc.getString("effectiveDate"),
                        activityDoc.getString("endDate"),
                        activityDoc.getString("activityCategory"),
                        activityDoc.getString("description"),
                        activityDoc.getString("photo"),
                        activityDoc.getDouble("price"),
                        activityDoc.getString("currency"),
                        activityDoc.getString("publishStatus"),
                        activityDoc.getString("avgRating"),
                        activityDoc.getString("updateUser")
                );
                activityList.add(activity);
            }
            return new ArrayList<>(activityList);
        } catch(Exception e){
            throw handleException("Get Activity List", e);
        }
    }



    public ArrayList<Activity> getActivityListPaginated(Integer offset, Integer count) throws AppException {
        try{
            ArrayList<Activity> activityList = new ArrayList<>();
            BasicDBObject sortParams = new BasicDBObject();
            sortParams.put("activityId", 1);
           // FindIterable<Document> activityDocs = activityCollection.find().sort(sortParams).skip(offset).limit(count);
            FindIterable<Document> activityDocs = activityCollection.find().skip(offset).limit(count);
            for(Document activityDoc: activityDocs) {
                Activity activity = new Activity(
                        activityDoc.getString("activityId").toString(),
                        activityDoc.getString("activityName").toString(),
                        activityDoc.getString("activityProviderId"),
                        activityDoc.getString("effectiveDate"),
                        activityDoc.getString("endDate"),
                        activityDoc.getString("activityCategory"),
                        activityDoc.getString("description"),
                        activityDoc.getString("photo"),
                        activityDoc.getDouble("price"),
                        activityDoc.getString("currency"),
                        activityDoc.getString("publishStatus"),
                        activityDoc.getString("avgRating"),
                        activityDoc.getString("updateUser")
                );
                activityList.add(activity);
            }
            return new ArrayList<>(activityList);
        } catch(Exception e){
            throw handleException("Get Activity List", e);
        }
    }

    public ArrayList<Activity> getActivityById(String activityId) throws AppException {
        try{
            ArrayList<Activity> activityList = new ArrayList<>();
            FindIterable<Document> activityDocs = activityCollection.find();
            for(Document activityDoc: activityDocs) {
                if(activityDoc.getString("activityId").toString().equals(activityId)) {
                    Activity activity = new Activity(
                            activityDoc.getString("activityId").toString(),
                            activityDoc.getString("activityName").toString(),
                            activityDoc.getString("activityProviderId"),
                            activityDoc.getString("effectiveDate"),
                            activityDoc.getString("endDate"),
                            activityDoc.getString("activityCategory"),
                            activityDoc.getString("description"),
                            activityDoc.getString("photo"),
                            activityDoc.getDouble("price"),
                            activityDoc.getString("currency"),
                            activityDoc.getString("publishStatus"),
                            activityDoc.getString("avgRating"),
                            activityDoc.getString("updateUser")
                    );
                    activityList.add(activity);
                }
            }
            return new ArrayList<>(activityList);
        } catch(Exception e){
            throw handleException("Get Activity List", e);
        }
    }

    public void updateActivity( @Context HttpHeaders headers,Activity activity) throws AppException {
        try {

            Session session = SessionManager.getInstance().getSessionForToken(headers);
          //  ArrayList<User> user = UserManager.getInstance().getUserById(session.getUserId());
            if(!session.getUserId().equals(activity.getUpdateUser()))
                throw new AppUnauthorizedException(70,"Invalid user id");

            Bson filter = new Document("activityId", new String(activity.getActivityId()));
            Bson newValue = new Document()
                    .append("activityId", activity.getActivityId())
                    .append("activityName", activity.getActivityName())
                    .append("activityProviderId",activity.getActivityProviderId())
                    .append("effectiveDate",activity.getEffectiveDate())
                    .append("endDate",activity.getEndDate())
                    .append("activityCategory",activity.getActivityCategory())
                    .append("description",activity.getDescription())
                    .append("photo",activity.getPhoto())
                    .append("price",activity.getPrice())
                    .append("currency",activity.getCurrency())
                    .append("publishStatus",activity.getPublishStatus())
                    .append("avgRating" , activity.getAvgRating())
                    .append ("updateUser", activity.getUpdateUser());

            Bson updateOperationDocument = new Document("$set", newValue);

            if (newValue != null)
                activityCollection.updateOne(filter, updateOperationDocument);
            else
                throw new AppInternalServerException(0, "Failed to update activity details");

        } catch(Exception e) {
            throw handleException("Update Activity", e);
        }
    }

    //End dating the activity with today's date
    public void deleteActivity(String activityId) throws AppException {
        try {
            ArrayList<Activity> activity = getActivityById(activityId);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            // Get the date today using Calendar object.
            Date today = Calendar.getInstance().getTime();
            // Using DateFormat format method we can create a string
             // representation of a date with the defined format.
            String reportDate = df.format(today);

            if (activity != null)
                activityCollection.updateOne(Filters.eq("activityId",activityId),Updates.set("endDate",reportDate));
            else
                throw new AppInternalServerException(0, "Failed to update activity details");

        } catch(Exception e) {
            throw handleException("End Date Activity", e);
        }
    }

}  //Class end
