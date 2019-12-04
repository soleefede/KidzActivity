package edu.cmu.andrew.karim.server.managers;

import com.fasterxml.jackson.core.JsonParser;
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
import jdk.nashorn.internal.parser.JSONParser;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;

public class RankingManager extends Manager{

    public static RankingManager _self;
    private ObjectWriter ow;
    private MongoCollection<Document> activityCollection;
    private MongoCollection<Document> bookingCollection;
    private MongoCollection<Document> reviewCollection;
    private MongoCollection<Document> activityProviderCollection;


    public RankingManager() {
        this.activityCollection = MongoPool.getInstance().getCollection("activity");
        this.bookingCollection = MongoPool.getInstance().getCollection("booking");
        this.reviewCollection = MongoPool.getInstance().getCollection("review");
        this.activityProviderCollection = MongoPool.getInstance().getCollection("activityProvider");
    }

    public static RankingManager getInstance(){
        if (_self == null)
            _self = new RankingManager();
        return _self;
    }


    public ArrayList<Ranking> getRankingList() throws AppException {
        try {

            // Return a list
            ArrayList<Ranking> rankingList = new ArrayList<>();
            FindIterable<Document> rankingDocs = activityCollection.find();
            for (Document rankingDoc : rankingDocs) {

                // Calculate average rating from Booking and Review
                String activityId = rankingDoc.getString("activityId").toString();
                System.out.println("activityId///:" +activityId);
                int sumRatings = 0;
                int countRatings = 0;
                int avgRatings = 0;

                FindIterable<Document> bookingDocs = bookingCollection.find(eq("activityId", activityId));
                for (Document bookingDoc : bookingDocs) {
                    String bookingId = bookingDoc.getString("bookingId").toString();
                    Document reviewDoc = reviewCollection.find(eq("bookingId", bookingId)).first();
                    if (reviewDoc != null) {
                        sumRatings += Integer.parseInt(reviewDoc.getString("ratings"));
                        countRatings++;
                        System.out.println("reviewDoc is not empty.");
                    }


                    if (countRatings > 0) {
                        avgRatings = sumRatings / countRatings;
                    }
                    String avgRating = Double.toString(avgRatings);
                    System.out.println("ActivityId:"+activityId+", avgrating:"+avgRating);
                    System.out.println("count:"+countRatings);

                    // Post aveRatings back
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
                            .append("avgRating", avgRating);

                    Bson updateOperationDocument = new Document("$set", newValue);
                    if (newValue != null) {
                        activityCollection.updateOne(filter, updateOperationDocument);
                    } else
                        throw new AppInternalServerException(0, "Failed to update average reviews");
                }

                }
            return rankingList;
        } catch (Exception e) {
            throw handleException("Get Ranking List", e);
        }
    }


    public ArrayList<Ranking> calculateDistance (String location) throws AppException {
        try {

            // Return a list
            String distance = "";
            ArrayList<Ranking> rankingList = new ArrayList<>();
            ArrayList<Activity> activityList = new ArrayList<>();
            FindIterable<Document> activityDocs = activityCollection.find();
            for (Document activityDoc : activityDocs) {
                distance="";

                // Get Activity and Activity Provider Address
                String activityId = activityDoc.getString("activityId");
                System.out.println(activityId);
                String address = "";
                Document activityAddressDoc = activityProviderCollection.find(eq("activityProviderId", activityDoc.getString("activityProviderId"))).first();
                System.out.println("Test, activityProviderId: "+ activityDoc.getString("activityProviderId"));
                if (activityAddressDoc != null) {
                    address = activityAddressDoc.getString("address1");
                    address += "," + activityAddressDoc.getString("city");
                    address += "," + activityAddressDoc.getString("state");
                    // Get coordinates of Activity Provider
                    System.out.println("address:"+address);
                    String key = "AIzaSyB7smrK4e9AABXv1cLCosoTkArFjpm_Z0k";
                    String destination = location;
                    URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + address + "&destinations=" + destination + "&key=" + key);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Content-Type", "application/json");
                    int status = con.getResponseCode();

                    // Parse Json Data
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer content = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    System.out.println(content);

                    String[] parser1 = content.toString().split(":");
                    int count = 0;


                    for (String s: parser1){
                        if (count == 6) {
                            distance = parser1[count].substring(2,parser1[count].indexOf("m")-1);
//                            if (!(distance.indexOf(".")==-1)) {
//                                distance = distance.substring(0, distance.indexOf(".") - 1);
//                            }
                            distance = distance.replace(",","");
                            float a = Float.parseFloat(distance);
                            int b= (int)a;
                            distance = String.valueOf(b);
                            System.out.println(a);
                        }
                        count++;


                        }
                    in.close();
                    con.disconnect();

                    System.out.println("Ranking obj:"+distance);
                    Ranking ranking = new Ranking(
                            activityId,
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
                            distance,
                            activityDoc.getString("avgRating")
                    );
                    rankingList.add(ranking);


                }
            }
            Collections.sort(rankingList);
            return rankingList;

        } catch (Exception e) {
            throw handleException("Get Ranking List", e);
        }
    }
}


