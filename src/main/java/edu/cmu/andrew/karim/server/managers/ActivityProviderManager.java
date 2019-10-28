package edu.cmu.andrew.karim.server.managers;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import com.mongodb.client.model.Sorts;
import edu.cmu.andrew.karim.server.exceptions.AppException;
import edu.cmu.andrew.karim.server.exceptions.AppInternalServerException;
import edu.cmu.andrew.karim.server.models.ActivityProvider;
import edu.cmu.andrew.karim.server.utils.MongoPool;
import edu.cmu.andrew.karim.server.utils.AppLogger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityProviderManager extends Manager{

    public static ActivityProviderManager _self;
    private MongoCollection<Document> activityProviderCollection;


    public ActivityProviderManager() {
        this.activityProviderCollection = MongoPool.getInstance().getCollection("activityProvider");
    }

    public static ActivityProviderManager getInstance(){
        if (_self == null)
            _self = new ActivityProviderManager();
        return _self;
    }


    public void createActivityProvider(ActivityProvider activityProvider) throws AppException {

        try{
            JSONObject json = new JSONObject(activityProvider);

            Document newDoc = new Document()
                    .append("activityProviderId",activityProvider.getActivityProviderId())
                    .append("businessName", activityProvider.getBusinessName())
                    .append("entityName", activityProvider.getEntityName())
                    .append("einNumber", activityProvider.getEinNumber())
                    .append("ssn", activityProvider.getSsn())
                    .append("address1", activityProvider.getAddress1())
                    .append("address2", activityProvider.getAddress2())
                    .append("city", activityProvider.getCity())
                    .append("state", activityProvider.getState())
                    .append("phoneNumber", activityProvider.getPhoneNumber())
                    .append("email", activityProvider.getEmail())
                    .append("commissionPercentage", activityProvider.getCommissionPercentage())
                    .append("bankName", activityProvider.getBankName())
                    .append("bankAccNumber", activityProvider.getBankAccNumber())
                    .append("pinCode", activityProvider.getPinCode());

            if (newDoc != null)
                activityProviderCollection.insertOne(newDoc);
            else
                throw new AppInternalServerException(0, "Failed to create new activity provider");

        }catch(Exception e){
            throw handleException("Create activity provider", e);
        }

    }

    public void updateActivityProvider(ActivityProvider activityProvider) throws AppException {
        try {

            Bson filter = new Document("_id", new ObjectId(activityProvider.getId()));
            Bson newValue = new Document()
                    .append("activityProviderId", activityProvider.getActivityProviderId())
                    .append("businessName", activityProvider.getBusinessName())
                    .append("entityName", activityProvider.getEntityName())
                    .append("einNumber", activityProvider.getEinNumber())
                    .append("ssn", activityProvider.getSsn())
                    .append("address1", activityProvider.getAddress1())
                    .append("address2", activityProvider.getAddress2())
                    .append("city", activityProvider.getCity())
                    .append("state", activityProvider.getState())
                    .append("phoneNumber", activityProvider.getPhoneNumber())
                    .append("email", activityProvider.getEmail())
                    .append("commissionPercentage", activityProvider.getCommissionPercentage())
                    .append("bankName", activityProvider.getBankName())
                    .append("bankAccNumber", activityProvider.getBankAccNumber())
                    .append("pinCode", activityProvider.getPinCode());
            Bson updateOperationDocument = new Document("$set", newValue);

            if (newValue != null)
                activityProviderCollection.updateOne(filter, updateOperationDocument);
            else
                throw new AppInternalServerException(0, "Failed to update activity provider details");

        } catch(Exception e) {
            throw handleException("Update Activity provider", e);
        }
    }

    public void deleteActivityProvider(String activityProviderId) throws AppException {
        try {
            Bson filter = new Document("_id", new ObjectId(activityProviderId));
            activityProviderCollection.deleteOne(filter);
        }catch (Exception e){
            throw handleException("Delete Activity provider", e);
        }
    }

    public ArrayList<ActivityProvider> getActivityProviderList() throws AppException {
        try{
            ArrayList<ActivityProvider> activityProviderList = new ArrayList<>();
            FindIterable<Document> activityProviderDocs = activityProviderCollection.find();
            for(Document activityProviderDoc: activityProviderDocs) {
                ActivityProvider activityProvider = new ActivityProvider(
                        activityProviderDoc.getObjectId("_id").toString(),
                        activityProviderDoc.getString("activityProviderId"),
                        activityProviderDoc.getString("businessName"),
                        activityProviderDoc.getString("entityName"),
                        activityProviderDoc.getString("einNumber"),
                        activityProviderDoc.getString("ssn"),
                        activityProviderDoc.getString("address1"),
                        activityProviderDoc.getString("address2"),
                        activityProviderDoc.getString("city"),
                        activityProviderDoc.getString("state"),
                        activityProviderDoc.getString("phoneNumber"),
                        activityProviderDoc.getString("email"),
                        activityProviderDoc.getString("commissionPercentage"),
                        activityProviderDoc.getString("bankName"),
                        activityProviderDoc.getString("bankAccNumber"),
                        activityProviderDoc.getString("pinCode")
                );
                activityProviderList.add(activityProvider);
            }
            return new ArrayList<>(activityProviderList);
        } catch(Exception e){
            throw handleException("Get Activity provider List", e);
        }
    }

    //1 and -1
    //2 parameters: 1st sorted and 2nd sorted
    public ArrayList<ActivityProvider> getActivityProviderSorted(String sortby) throws AppException {
        try{
            ArrayList<ActivityProvider> activityProviderList = new ArrayList<>();
            BasicDBObject sortParams = new BasicDBObject();
            sortParams.put(sortby, 1);
            FindIterable<Document> activityProviderDocs = activityProviderCollection.find().sort(sortParams);
            for(Document activityProviderDoc: activityProviderDocs) {
                ActivityProvider activityProvider = new ActivityProvider(
                        activityProviderDoc.getObjectId("_id").toString(),
                        activityProviderDoc.getString("activityProviderId"),
                        activityProviderDoc.getString("businessName"),
                        activityProviderDoc.getString("entityName"),
                        activityProviderDoc.getString("einNumber"),
                        activityProviderDoc.getString("ssn"),
                        activityProviderDoc.getString("address1"),
                        activityProviderDoc.getString("address2"),
                        activityProviderDoc.getString("city"),
                        activityProviderDoc.getString("state"),
                        activityProviderDoc.getString("phoneNumber"),
                        activityProviderDoc.getString("email"),
                        activityProviderDoc.getString("commissionPercentage"),
                        activityProviderDoc.getString("bankName"),
                        activityProviderDoc.getString("bankAccNumber"),
                        activityProviderDoc.getString("pinCode")
                );
                activityProviderList.add(activityProvider);
            }
            return new ArrayList<>(activityProviderList);
        } catch(Exception e){
            throw handleException("Get Activity provider List", e);
        }
    }

    public ArrayList<ActivityProvider> getActivityProviderFiltered(String entityName) throws AppException {
        try{
            ArrayList<ActivityProvider> activityProviderList = new ArrayList<>();
            FindIterable<Document> activityProviderDocs = activityProviderCollection.find().filter(Filters.eq("entityName",entityName));
            for(Document activityProviderDoc: activityProviderDocs) {
                ActivityProvider activityProvider = new ActivityProvider(
                        activityProviderDoc.getObjectId("_id").toString(),
                        activityProviderDoc.getString("activityProviderId"),
                        activityProviderDoc.getString("businessName"),
                        activityProviderDoc.getString("entityName"),
                        activityProviderDoc.getString("einNumber"),
                        activityProviderDoc.getString("ssn"),
                        activityProviderDoc.getString("address1"),
                        activityProviderDoc.getString("address2"),
                        activityProviderDoc.getString("city"),
                        activityProviderDoc.getString("state"),
                        activityProviderDoc.getString("phoneNumber"),
                        activityProviderDoc.getString("email"),
                        activityProviderDoc.getString("commissionPercentage"),
                        activityProviderDoc.getString("bankName"),
                        activityProviderDoc.getString("bankAccNumber"),
                        activityProviderDoc.getString("pinCode")
                );
                activityProviderList.add(activityProvider);
            }
            return new ArrayList<>(activityProviderList);
        } catch(Exception e){
            throw handleException("Get Activity provider List", e);
        }
    }



    //offset: where to pick up the list, count: how many items are displayed. -> add count to the offset to move to the next page.
    public ArrayList<ActivityProvider> getActivityProviderListPaginated(Integer offset, Integer count) throws AppException {
        try{
            ArrayList<ActivityProvider> activityProviderList = new ArrayList<>();
            BasicDBObject sortParams = new BasicDBObject();
            sortParams.put("activityProviderId", 1);
            FindIterable<Document> activityProviderDocs = activityProviderCollection.find().sort(sortParams).skip(offset).limit(count);
            for(Document activityProviderDoc: activityProviderDocs) {
                ActivityProvider activityProvider = new ActivityProvider(
                        activityProviderDoc.getObjectId("_id").toString(),
                        activityProviderDoc.getString("activityProviderId"),
                        activityProviderDoc.getString("businessName"),
                        activityProviderDoc.getString("entityName"),
                        activityProviderDoc.getString("einNumber"),
                        activityProviderDoc.getString("ssn"),
                        activityProviderDoc.getString("address1"),
                        activityProviderDoc.getString("address2"),
                        activityProviderDoc.getString("city"),
                        activityProviderDoc.getString("state"),
                        activityProviderDoc.getString("phoneNumber"),
                        activityProviderDoc.getString("email"),
                        activityProviderDoc.getString("commissionPercentage"),
                        activityProviderDoc.getString("bankName"),
                        activityProviderDoc.getString("bankAccNumber"),
                        activityProviderDoc.getString("pinCode")
                );
                activityProviderList.add(activityProvider);
            }
            return new ArrayList<>(activityProviderList);
        } catch(Exception e){
            throw handleException("Get Activity provider List", e);
        }
    }

    public ArrayList<ActivityProvider> getActivityProviderById(String activityProviderId) throws AppException {
        try{
            ArrayList<ActivityProvider> activityProviderList = new ArrayList<>();
            FindIterable<Document> activityProviderDocs = activityProviderCollection.find();
            for(Document activityProviderDoc: activityProviderDocs) {
                if(activityProviderDoc.getObjectId("activityProviderId").toString().equals(activityProviderId)) {
                    ActivityProvider activityProvider = new ActivityProvider(
                            activityProviderDoc.getObjectId("_id").toString(),
                            activityProviderDoc.getString("activityProviderId"),
                            activityProviderDoc.getString("businessName"),
                            activityProviderDoc.getString("entityName"),
                            activityProviderDoc.getString("einNumber"),
                            activityProviderDoc.getString("ssn"),
                            activityProviderDoc.getString("address1"),
                            activityProviderDoc.getString("address2"),
                            activityProviderDoc.getString("city"),
                            activityProviderDoc.getString("state"),
                            activityProviderDoc.getString("phoneNumber"),
                            activityProviderDoc.getString("email"),
                            activityProviderDoc.getString("commissionPercentage"),
                            activityProviderDoc.getString("bankName"),
                            activityProviderDoc.getString("bankAccNumber"),
                            activityProviderDoc.getString("pinCode")
                    );
                    activityProviderList.add(activityProvider);
                }
            }
            return new ArrayList<>(activityProviderList);
        } catch(Exception e){
            throw handleException("Get Activity provider List", e);
        }
    }

}
