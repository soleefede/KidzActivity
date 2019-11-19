package edu.cmu.andrew.karim.server.managers;

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
                    FindIterable<Document> reviewDocs = reviewCollection.find(eq("bookingId", bookingId));
                    for(Document reviewDoc: reviewDocs){
                        int ratings = Integer.parseInt(reviewDoc.get("ratings").toString());
                        sumRatings += ratings;
                        countRatings++;
                    }
                }
                avgRatings = sumRatings/countRatings;

                //Post aveRatings back.


            }
            return new ArrayList<>(rankingList);
        } catch(Exception e){
            throw handleException("Get Ranking List", e);
        }
    }

//    public ArrayList<Activity> getActivityListSorted(String sortby) throws AppException {
//        try{
//            ArrayList<Activity> activityList = new ArrayList<>();
//            BasicDBObject sortParams = new BasicDBObject();
//            sortParams.put(sortby, 1);
//            FindIterable<Document> activityDocs = activityCollection.find().sort(sortParams);
//            for(Document activityDoc: activityDocs) {
//                Activity activity = new Activity(
//                        activityDoc.getString("activityId").toString(),
//                        activityDoc.getString("activityName").toString(),
//                        activityDoc.getString("activityProviderId"),
//                        activityDoc.getString("effectiveDate"),
//                        activityDoc.getString("endDate"),
//                        activityDoc.getString("activityCategory"),
//                        activityDoc.getString("description"),
//                        activityDoc.getString("photo"),
//                        activityDoc.getDouble("price"),
//                        activityDoc.getString("currency"),
//                        activityDoc.getString("publishStatus")
//                );
//                activityList.add(activity);
//            }
//            return new ArrayList<>(activityList);
//        } catch(Exception e){
//            throw handleException("Get Activity List", e);
//        }
//    }
//
//    public ArrayList<Activity> getActivityListFiltered(String activityCategory) throws AppException {
//        try{
//            ArrayList<Activity> activityList = new ArrayList<>();
//            // BasicDBObject filterParams = new BasicDBObject();
//            //filterParams.put(filterby, 1);
//            FindIterable<Document> activityDocs = activityCollection.find().filter(Filters.eq("activityCategory",activityCategory));
//            for(Document activityDoc: activityDocs) {
//                Activity activity = new Activity(
//                        activityDoc.getString("activityId").toString(),
//                        activityDoc.getString("activityName").toString(),
//                        activityDoc.getString("activityProviderId"),
//                        activityDoc.getString("effectiveDate"),
//                        activityDoc.getString("endDate"),
//                        activityDoc.getString("activityCategory"),
//                        activityDoc.getString("description"),
//                        activityDoc.getString("photo"),
//                        activityDoc.getDouble("price"),
//                        activityDoc.getString("currency"),
//                        activityDoc.getString("publishStatus")
//                );
//                activityList.add(activity);
//            }
//            return new ArrayList<>(activityList);
//        } catch(Exception e){
//            throw handleException("Get Activity List", e);
//        }
//    }
//
//
//
//    public ArrayList<Activity> getActivityListPaginated(Integer offset, Integer count) throws AppException {
//        try{
//            ArrayList<Activity> activityList = new ArrayList<>();
//            BasicDBObject sortParams = new BasicDBObject();
//            sortParams.put("activityId", 1);
//            // FindIterable<Document> activityDocs = activityCollection.find().sort(sortParams).skip(offset).limit(count);
//            FindIterable<Document> activityDocs = activityCollection.find().skip(offset).limit(count);
//            for(Document activityDoc: activityDocs) {
//                Activity activity = new Activity(
//                        activityDoc.getString("activityId").toString(),
//                        activityDoc.getString("activityName").toString(),
//                        activityDoc.getString("activityProviderId"),
//                        activityDoc.getString("effectiveDate"),
//                        activityDoc.getString("endDate"),
//                        activityDoc.getString("activityCategory"),
//                        activityDoc.getString("description"),
//                        activityDoc.getString("photo"),
//                        activityDoc.getDouble("price"),
//                        activityDoc.getString("currency"),
//                        activityDoc.getString("publishStatus")
//                );
//                activityList.add(activity);
//            }
//            return new ArrayList<>(activityList);
//        } catch(Exception e){
//            throw handleException("Get Activity List", e);
//        }
//    }
}
