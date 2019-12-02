package edu.cmu.andrew.karim.server.managers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import edu.cmu.andrew.karim.server.exceptions.AppException;
import edu.cmu.andrew.karim.server.exceptions.AppInternalServerException;
import edu.cmu.andrew.karim.server.http.exceptions.HttpBadRequestException;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.models.Activity;
import edu.cmu.andrew.karim.server.models.ActivityProvider;
import edu.cmu.andrew.karim.server.models.Booking;
import edu.cmu.andrew.karim.server.models.Payment;
import edu.cmu.andrew.karim.server.utils.AppLogger;
import edu.cmu.andrew.karim.server.utils.MongoPool;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

public class BookingManager extends Manager {
    public static BookingManager _self;
    private MongoCollection<Document> bookingCollection;


    public BookingManager() {
        this.bookingCollection = MongoPool.getInstance().getCollection("booking");
    }

    public static BookingManager getInstance() {
        if (_self == null)
            _self = new BookingManager();
        return _self;
    }


    public void createBooking(Booking booking) throws AppException {

        try {
            JSONObject json = new JSONObject(booking);

            Document newDoc = new Document()
                    .append("bookingId", booking.getBookingId())
                    .append("bookingDate",booking.getBookingDate())
                    .append("parentId", booking.getParentId())
                    .append("activityId", booking.getActivityId())
                    .append("availabilityId", booking.getAvailabilityId())
                    .append("paymentId", booking.getPaymentId())
                    .append("noOfSeats", booking.getNoOfSeats())
                    .append("kidName", booking.getKidName())
                    .append("bookingStatus", booking.getBookingStatus())
                    .append("confirmStatus", booking.getConfirmStatus());


            if (newDoc != null)
            {
                bookingCollection.insertOne(newDoc);
                ArrayList<Activity> activityList = ActivityManager.getInstance().getActivityById(booking.getActivityId());
                float price  = (float) activityList.get(0).getPrice();
                Payment payment = new Payment("PYMT" + booking.getBookingId(),booking.getNoOfSeats(),price
                        ,price*booking.getNoOfSeats(),"","New");
                PaymentManager.getInstance().createPayment(payment);
            }

            else
                throw new AppInternalServerException(0, "Failed to create new booking");

        } catch (Exception e) {
            throw handleException("Create Booking", e);
        }

    }

    public ArrayList<Booking> getBookingList() throws AppException {
        try{
            ArrayList<Booking> bookingList = new ArrayList<>();
            FindIterable<Document> bookingDocs = bookingCollection.find();
            for(Document bookingDoc: bookingDocs) {
                Booking booking = new Booking(
                        bookingDoc.getString("bookingId").toString(),
                        bookingDoc.getString("bookingDate"),
                        bookingDoc.getString("parentId").toString(),
                        bookingDoc.getString("activityId"),
                        bookingDoc.getString("availabilityId"),
                        bookingDoc.getString("paymentId"),
                        bookingDoc.getInteger("noOfSeats"),
                        bookingDoc.getString("kidName"),
                        bookingDoc.getString("bookingStatus"),
                        bookingDoc.getString("confirmStatus")
                );
                bookingList.add(booking);
            }
            return new ArrayList<>(bookingList);
        } catch(Exception e){
            throw handleException("Get Booking List", e);
        }
    }

    public ArrayList<Booking> getBookingActivity(String activityId) throws AppException {
        try{
            ArrayList<Booking> bookingList = new ArrayList<>();
            FindIterable<Document> bookingDocs = bookingCollection.find().filter(Filters.eq("bookingId",activityId));

           // FindIterable<Document> bookingDocs = bookingCollection.find();
            for(Document bookingDoc: bookingDocs) {
                Booking booking = new Booking(
                        bookingDoc.getString("bookingId").toString(),
                        bookingDoc.getString("bookingDate"),
                        bookingDoc.getString("parentId").toString(),
                        bookingDoc.getString("activityId"),
                        bookingDoc.getString("availabilityId"),
                        bookingDoc.getString("paymentId"),
                        bookingDoc.getInteger("noOfSeats"),
                        bookingDoc.getString("kidName"),
                        bookingDoc.getString("bookingStatus"),
                        bookingDoc.getString("confirmStatus")
                );
                bookingList.add(booking);
            }
            return new ArrayList<>(bookingList);
        } catch(Exception e){
            throw handleException("Get Booking List", e);
        }
    }



    public void updateBooking(String bookingId, String paymentId, String bookingStatus, String confirmStatus) throws AppException {
        try {

            Bson filter = new Document("bookingId", bookingId);
          //  int seatChange =  action .equals("add") ? noOfSeats : noOfSeats*-1 ;

            FindIterable<Document> bookingDocs = bookingCollection.find().filter(Filters.eq("bookingId",bookingId));
            for(Document bookingDoc: bookingDocs) {

                Bson newValue = new Document()
                        .append("bookingId", bookingId)
                        .append("parentId" ,bookingDoc.getString("parentId").toString())
                        .append("activityId", bookingDoc.getString("activityId").toString())
                        .append("availabilityId", bookingDoc.getString("availabilityId"))
                        .append("paymentId", paymentId.isEmpty() ? bookingDoc.getString("endDate"):paymentId)
                        .append("noOfSeats", bookingDoc.getInteger("noOfSeats"))
                        .append("kidName", bookingDoc.getString("availabilityDate"))
                        .append("bookingStatus",bookingStatus.isEmpty() ? bookingDoc.getString("bookingStatus"):bookingStatus)
                        .append("confirmStatus", confirmStatus.isEmpty() ?bookingDoc.getString("confirmStatus"): confirmStatus);



                Bson updateOperationDocument = new Document("$set", newValue);

                if (newValue != null)
                    bookingCollection.updateOne(filter, updateOperationDocument);
                else
                    throw new AppInternalServerException(0, "Failed to update booking details");
            }

        } catch(Exception e) {
            throw handleException("Update booking", e);
        }
    }


}
