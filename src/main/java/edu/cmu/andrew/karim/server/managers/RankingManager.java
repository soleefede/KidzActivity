package edu.cmu.andrew.karim.server.managers;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import edu.cmu.andrew.karim.server.exceptions.AppException;
import edu.cmu.andrew.karim.server.exceptions.AppInternalServerException;
import edu.cmu.andrew.karim.server.models.Ranking;
import edu.cmu.andrew.karim.server.models.Booking;
import edu.cmu.andrew.karim.server.models.Activity;
import edu.cmu.andrew.karim.server.models.Review;
import edu.cmu.andrew.karim.server.managers.ActivityManager;
import edu.cmu.andrew.karim.server.utils.MongoPool;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

public class RankingManager extends Manager{

    public static RankingManager _self;
    private ObjectWriter ow;
    private MongoCollection<Document> activityCollection;
    private MongoCollection<Document> bookingCollection;
    private MongoCollection<Document> reviewCollection;


    public RankingManager() {
        this.activityCollection = MongoPool.getInstance().getCollection("activity");
        this.bookingCollection = MongoPool.getInstance().getCollection("booking");
        this.reviewCollection = MongoPool.getInstance().getCollection("review");
    }

    public static RankingManager getInstance(){
        if (_self == null)
            _self = new RankingManager();
        return _self;
    }


    public ArrayList<Ranking> getRankingList() throws AppException {
        try{
            ArrayList<Ranking> rankingList = new ArrayList<>();
            FindIterable<Document> rankingDocs = activityCollection.find();
            for(Document rankingDoc: rankingDocs) {
                Ranking ranking = new Ranking(
                        rankingDoc.getString("activityId").toString(),
                        rankingDoc.getString("activityName").toString(),
                        rankingDoc.getString("activityProviderId"),
                        rankingDoc.getString("effectiveDate"),
                        rankingDoc.getString("endDate"),
                        rankingDoc.getString("activityCategory"),
                        rankingDoc.getString("description"),
                        rankingDoc.getString("photo"),
                        rankingDoc.getDouble("price"),
                        rankingDoc.getString("currency"),
                        rankingDoc.getString("publishStatus")
                );
                rankingList.add(ranking);

                String activityId = rankingDoc.getString("activityId").toString();
                int sumRatings = 0;
                int countRatings = 0;
                int avgRatings = 0;

                FindIterable<Document> bookingDocs = bookingCollection.find(eq("activityId", activityId));
                for(Document bookingDoc: bookingDocs){
                    String bookingId = bookingDoc.getString("bookingId").toString();
                    System.out.println(bookingId);
                    Document reviewDoc = reviewCollection.find(eq("bookingId", bookingId)).first();
                    if (reviewDoc!=null) {
                        sumRatings += Integer.parseInt(reviewDoc.getString("ratings"));
                       countRatings++;
                    }
                }
                if (countRatings > 0) {
                    avgRatings = sumRatings / countRatings;
                }
                String avgRating = Double.toString(avgRatings);
                System.out.println(activityId + " " + sumRatings + " " + avgRatings + " " + avgRating);
                //Post aveRatings back.
                Bson filter = new Document("activityId", activityId);
                Document activityDoc = activityCollection.find(eq("activityId", activityId)).first();
                Bson newValue = new Document()
                        .append("activityId", activityDoc.getString("activityId"))
                        .append("activityName", activityDoc.getString("activityName"))
                        .append("activityProviderId", activityDoc.getString("activityProviderId"))
                        .append("effectiveDate", activityDoc.getString("effectiveDate"))
                        .append("endDate", activityDoc.getString("endDate"))
                        .append("activityCategory", activityDoc.getString("activityCategory"))
                        .append("description", activityDoc.getString("description"))
                        .append("photo", activityDoc.getString("photo"))
                        .append("price", activityDoc.getDouble("price"))
                        .append("currency", activityDoc.getString("currency"))
                        .append("publishStatus", activityDoc.getString("publishStatus"))
                        .append("avgRating" , avgRating);

                System.out.println("Before updating" +  " " + avgRating);

                Bson updateOperationDocument = new Document("$set", newValue);
                if (newValue != null) {
                    System.out.println(activityDoc.getString("activityId") + " I am here ");
                    activityCollection.updateOne(filter, updateOperationDocument);
                }
                else
                    throw new AppInternalServerException(0, "Failed to update average reviews");

            }
            return rankingList;
        } catch(Exception e){
            throw handleException("Get Ranking List", e);
        }
    }
}
