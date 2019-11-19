package edu.cmu.andrew.karim.server.managers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import edu.cmu.andrew.karim.server.exceptions.AppException;
import edu.cmu.andrew.karim.server.exceptions.AppInternalServerException;
import edu.cmu.andrew.karim.server.models.Activity;
import edu.cmu.andrew.karim.server.models.Availability;
import edu.cmu.andrew.karim.server.utils.MongoPool;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import java.util.ArrayList;

public class AvailabilityManager extends Manager {
    public static AvailabilityManager _self;
    private MongoCollection<Document> availabilityCollection;


    public AvailabilityManager() {
        this.availabilityCollection = MongoPool.getInstance().getCollection("availability");
    }

    public static AvailabilityManager getInstance(){
        if (_self == null)
            _self = new AvailabilityManager();
        return _self;
    }


    public void createAvailability(Availability availability) throws AppException {
        Activity activity ;
        try{
            JSONObject json = new JSONObject(availability);

            Document newDoc = new Document()
                    .append("availabilityId", availability.getAvailabilityId())
                    .append("activityId", availability.getActivityId())
                    .append("startDate",availability.getStartDate())
                    .append("endDate",availability.getEndDate())
                    .append("availabilityDate",availability.getAvailabilityDate())
                    .append("timeSlot",availability.getTimeSlot())
                    .append("noOfSeats",availability.getNoOfSeats())
                    .append("seatsAvailable",availability.getSeatsAvailable());


            ArrayList<Activity> activityValid = ActivityManager.getInstance().getActivityById(availability.getActivityId());

            if (newDoc != null) {
                if (activityValid.size()!= 0) {
                   // System.out.println(activityValid.size());
                    availabilityCollection.insertOne(newDoc);
                }
                else
                    throw new AppInternalServerException(0, "Invalid Activity Id");

            }
            else
                throw new AppInternalServerException(0, "Failed to create new activity");

        }catch(Exception e){
            throw handleException("Create Activity", e);
        }

    }

    public ArrayList<Availability> getActivityAvailability(String activityId) throws AppException {
        try{
            ArrayList<Availability> availabilityList = new ArrayList<>();
            FindIterable<Document> availabilityDocs = availabilityCollection.find().filter(Filters.eq("activityId",activityId));

            for(Document availabilityDoc: availabilityDocs) {
                Availability availability = new Availability(
                        availabilityDoc.getString("availabilityId").toString(),
                        availabilityDoc.getString("activityId").toString(),
                        availabilityDoc.getString("startDate"),
                        availabilityDoc.getString("endDate"),
                        availabilityDoc.getString("availabilityDate"),
                        availabilityDoc.getString("timeSlot"),
                        availabilityDoc.getInteger("noOfSeats"),
                        availabilityDoc.getInteger("seatsAvailable")
                );
                availabilityList.add(availability);
            }
            return new ArrayList<>(availabilityList);
        } catch(Exception e){
            throw handleException("Get Activity Availability List", e);
        }
    }

    public void updateAvailability( String availabilityId, String action , int noOfSeats) throws AppException {
        try {

            Bson filter = new Document("availabilityId", availabilityId);
            int seatChange =  action .equals("add") ? noOfSeats : noOfSeats*-1 ;
            FindIterable<Document> availabilityDocs = availabilityCollection.find().filter(Filters.eq("availabilityId",availabilityId));
            for(Document availabilityDoc: availabilityDocs) {

                Bson newValue = new Document()
                        .append("availabilityId", availabilityId)
                        .append("activityId", availabilityDoc.getString("activityId").toString())

                        .append("startDate", availabilityDoc.getString("startDate"))
                        .append("endDate", availabilityDoc.getString("endDate"))
                        .append("timeSlot", availabilityDoc.getString("timeSlot"))
                        .append("availabilityDate", availabilityDoc.getString("availabilityDate"))
                        .append("noOfSeats",availabilityDoc.getInteger("noOfSeats"))
                        .append("seatsAvailable", availabilityDoc.getInteger("seatsAvailable") + seatChange );



                Bson updateOperationDocument = new Document("$set", newValue);

                if (newValue != null)
                    availabilityCollection.updateOne(filter, updateOperationDocument);
                else
                    throw new AppInternalServerException(0, "Failed to update activity details");
            }

        } catch(Exception e) {
            throw handleException("Update Availability", e);
        }
    }

/*
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
                        activityDoc.getString("publishStatus")
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
                        activityDoc.getString("publishStatus")
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
                        activityDoc.getString("publishStatus")
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
                            activityDoc.getString("publishStatus")
                    );
                    activityList.add(activity);
                }
            }
            return new ArrayList<>(activityList);
        } catch(Exception e){
            throw handleException("Get Activity List", e);
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
    }*/

}  //Class end
