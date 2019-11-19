package edu.cmu.andrew.karim.server.managers;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import edu.cmu.andrew.karim.server.exceptions.AppException;
import edu.cmu.andrew.karim.server.exceptions.AppInternalServerException;
import edu.cmu.andrew.karim.server.models.Parent;
import edu.cmu.andrew.karim.server.utils.MongoPool;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParentManager extends Manager{

    public static ParentManager _self;
    private MongoCollection<Document> parentCollection;


    public ParentManager() {
        this.parentCollection = MongoPool.getInstance().getCollection("parent");
    }

    public static ParentManager getInstance(){
        if (_self == null)
            _self = new ParentManager();
        return _self;
    }


    public void createParent(Parent parent) throws AppException {

        try{
            JSONObject json = new JSONObject(parent);

            Document newDoc = new Document()
                    .append("parentId", parent.getParentId())
                    .append("firstName", parent.getFirstName())
                    .append("lastName", parent.getLastName())
                    .append("phoneNumber", parent.getPhoneNumber())
                    .append("email", parent.getEmail())
                    .append("address1", parent.getAddress1())
                    .append("address2", parent.getAddress2())
                    .append("city", parent.getCity())
                    .append("state", parent.getState())
                    .append("zipCode", parent.getZipCode())
                    .append("country", parent.getCountry())
                    .append("kidsAge", parent.getKidsAge())
                    .append("location", parent.getLocation())
                    .append("activityCategory", parent.getActivityCategory());

            if (newDoc != null)
                parentCollection.insertOne(newDoc);
            else
                throw new AppInternalServerException(0, "Failed to create a new parent");

        }catch(Exception e){
            throw handleException("Create parent", e);
        }

    }

    public void updateParent(Parent parent) throws AppException {
        try {

            Bson filter = new Document("parentId", new String(parent.getParentId()));
            Bson newValue = new Document()
                    .append("parentId", parent.getParentId())
                    .append("firstName", parent.getFirstName())
                    .append("lastName", parent.getLastName())
                    .append("phoneNumber", parent.getPhoneNumber())
                    .append("email", parent.getEmail())
                    .append("address1", parent.getAddress1())
                    .append("address2", parent.getAddress2())
                    .append("city", parent.getCity())
                    .append("state", parent.getState())
                    .append("zipCode", parent.getZipCode())
                    .append("country", parent.getCountry())
                    .append("kidsAge", parent.getKidsAge())
                    .append("location", parent.getLocation())
                    .append("activityCategory", parent.getActivityCategory());
            Bson updateOperationDocument = new Document("$set", newValue);

            if (newValue != null)
                parentCollection.updateOne(filter, updateOperationDocument);
            else
                throw new AppInternalServerException(0, "Failed to update parent details");

        } catch(Exception e) {
            throw handleException("Update parent", e);
        }
    }

    public void deleteParent(String parentId) throws AppException {
        try {
            Bson filter = new Document("parentId", parentId);
            parentCollection.deleteOne(filter);
        }catch (Exception e){
            throw handleException("Delete parent", e);
        }
    }

    public ArrayList<Parent> getParentList() throws AppException {
        try{
            ArrayList<Parent> parentList = new ArrayList<>();
            FindIterable<Document> parentDocs = parentCollection.find();
            for(Document parentDoc: parentDocs) {
                Parent parent = new Parent(
                        parentDoc.getObjectId("_id").toString(),
                        parentDoc.getString("parentId"),
                        parentDoc.getString("firstName"),
                        parentDoc.getString("lastName"),
                        parentDoc.getString("phoneNumber"),
                        parentDoc.getString("email"),
                        parentDoc.getString("address1"),
                        parentDoc.getString("address2"),
                        parentDoc.getString("city"),
                        parentDoc.getString("state"),
                        parentDoc.getString("zipCode"),
                        parentDoc.getString("country"),
                        parentDoc.getString("kidsAge"),
                        parentDoc.getString("location"),
                        parentDoc.getString("activityCategory")
                );
                parentList.add(parent);
            }
            return new ArrayList<>(parentList);
        } catch(Exception e){
            throw handleException("Get parent List", e);
        }
    }

    public ArrayList<Parent> getParentById(String parentId) throws AppException {
        try {
            ArrayList<Parent> parentList = new ArrayList<>();
            FindIterable<Document> parentDocs = parentCollection.find();
            for (Document parentDoc : parentDocs) {
                if (parentDoc.getString("parentId").toString().equals(parentId)) {
                    Parent parent = new Parent(
                            parentDoc.getObjectId("_id").toString(),
                            parentDoc.getString("parentId"),
                            parentDoc.getString("firstName"),
                            parentDoc.getString("lastName"),
                            parentDoc.getString("phoneNumber"),
                            parentDoc.getString("email"),
                            parentDoc.getString("address1"),
                            parentDoc.getString("address2"),
                            parentDoc.getString("city"),
                            parentDoc.getString("state"),
                            parentDoc.getString("zipCode"),
                            parentDoc.getString("country"),
                            parentDoc.getString("kidsAge"),
                            parentDoc.getString("location"),
                            parentDoc.getString("activityCategory")
                    );
                    parentList.add(parent);
                }
            }
            return new ArrayList<>(parentList);
        } catch (Exception e) {
            throw handleException("Get parent List", e);
        }
    }
}
