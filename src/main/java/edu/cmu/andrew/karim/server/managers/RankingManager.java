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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        try{

            // Return a list
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
                        rankingDoc.getString("publishStatus"),
                        0,
                        rankingDoc.getString("avgRating")
                );
                rankingList.add(ranking);

                // Calculate average rating from Booking and Review
                String activityId = rankingDoc.getString("activityId").toString();
                int sumRatings = 0;
                int countRatings = 0;
                int avgRatings = 0;

                FindIterable<Document> bookingDocs = bookingCollection.find(eq("activityId", activityId));
                for(Document bookingDoc: bookingDocs){
                    String bookingId = bookingDoc.getString("bookingId").toString();
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
                        .append("avgRating" , avgRating);

                Bson updateOperationDocument = new Document("$set", newValue);
                if (newValue != null) {
                    activityCollection.updateOne(filter, updateOperationDocument);
                }
                else
                    throw new AppInternalServerException(0, "Failed to update average reviews");

                // Get Activity and Activity Provider Address
//                System.out.println(rankingDoc.getString("activityProviderId"));
                String address="";
                Document activityAddressDoc = activityProviderCollection.find(eq("activityProviderId", rankingDoc.getString("activityProviderId"))).first();
                if (activityAddressDoc != null) {
                    address = activityAddressDoc.getString("address1");
                    address += "," + activityAddressDoc.getString("city");
                    address += "," + activityAddressDoc.getString("state");
                    // Get coordinates of Activity Provider
//                    System.out.println("address:"+address);
//                    String key = "AIzaSyB7smrK4e9AABXv1cLCosoTkArFjpm_Z0k";
//                    String destination= location;
//                    URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+address+"&destinations="+destination+"&key="+key);
//                    System.out.println("url:"+url.toString());
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.setRequestMethod("GET");
//                    con.setRequestProperty("Content-Type", "application/json");
//                    int status = con.getResponseCode();
//                    System.out.println("Status code: "+status);
//                    BufferedReader in = new BufferedReader(
//                            new InputStreamReader(con.getInputStream()));
//                    String inputLine;
//                    StringBuffer content = new StringBuffer();

//                    while ((inputLine = in.readLine()) != null) {
//                        content.append(inputLine);
                    }
//                    String[] parser1 = content.toString().split(":");
//                    int count = 0;
//                    for (String s: parser1){
//                        System.out.println(activityId+":"+s);
//                        count++;
//                        if (count%13 == 8){
//                            String[] distanceString = parser1[count].split(" ");
//                            int distanceValue = Integer.parseInt(distanceString[0]);
//                            Ranking ranking = new Ranking(
//                                rankingDoc.getString("activityId").toString(),
//                                rankingDoc.getString("activityName").toString(),
//                                rankingDoc.getString("activityProviderId"),
//                                rankingDoc.getString("effectiveDate"),
//                                rankingDoc.getString("endDate"),
//                                rankingDoc.getString("activityCategory"),
//                                rankingDoc.getString("description"),
//                                rankingDoc.getString("photo"),
//                                rankingDoc.getDouble("price"),
//                                rankingDoc.getString("currency"),
//                                rankingDoc.getString("publishStatus"),
//                                distanceValue
//                            );
//                            rankingList.add(ranking);
//                            System.out.println(ranking.getDistance());
//                        }
                    }
//                    in.close();
//                    con.disconnect();
//                }
//            }
            return rankingList;
        } catch(Exception e){
            throw handleException("Get Ranking List", e);
        }
    }

    public ArrayList<Ranking> calculateDistance(String location) throws AppException {
        try{

            // Return a list
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
                        rankingDoc.getString("publishStatus"),
                        0,
                        rankingDoc.getString("avgRating")
                );
                rankingList.add(ranking);


                // Get Activity and Activity Provider Address
//                System.out.println(rankingDoc.getString("activityProviderId"));
                String address="";
                Document activityAddressDoc = activityProviderCollection.find(eq("activityProviderId", rankingDoc.getString("activityProviderId"))).first();
                if (activityAddressDoc != null) {
                    address = activityAddressDoc.getString("address1");
                    address += "," + activityAddressDoc.getString("city");
                    address += "," + activityAddressDoc.getString("state");
                    // Get coordinates of Activity Provider
//                    System.out.println("address:"+address);
                    String key = "AIzaSyB7smrK4e9AABXv1cLCosoTkArFjpm_Z0k";
                    String destination= location;
                    URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+address+"&destinations="+destination+"&key="+key);
//                    System.out.println("url:"+url.toString());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Content-Type", "application/json");
                    int status = con.getResponseCode();
//                    System.out.println("Status code: "+status);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer content = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    String[] parser1 = content.toString().split(":");
                    int count = 0;
                    for (String s: parser1){
                        //System.out.println(activityId+":"+s);
                        count++;
//                        if (count%13 == 8){
//                            String[] distanceString = parser1[count].split(" ");
//                            int distanceValue = Integer.parseInt(distanceString[0]);
//                            Ranking ranking = new Ranking(
//                                rankingDoc.getString("activityId").toString(),
//                                rankingDoc.getString("activityName").toString(),
//                                rankingDoc.getString("activityProviderId"),
//                                rankingDoc.getString("effectiveDate"),
//                                rankingDoc.getString("endDate"),
//                                rankingDoc.getString("activityCategory"),
//                                rankingDoc.getString("description"),
//                                rankingDoc.getString("photo"),
//                                rankingDoc.getDouble("price"),
//                                rankingDoc.getString("currency"),
//                                rankingDoc.getString("publishStatus"),
//                                distanceValue
//                            );
//                            rankingList.add(ranking);
//                            System.out.println(ranking.getDistance());
//                        }
                    }
                    in.close();
                    con.disconnect();
                }
            }
            return rankingList;
        } catch(Exception e){
            throw handleException("Get Ranking List", e);
        }
    }
}
