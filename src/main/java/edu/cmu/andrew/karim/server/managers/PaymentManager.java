package edu.cmu.andrew.karim.server.managers;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import edu.cmu.andrew.karim.server.exceptions.AppException;
import edu.cmu.andrew.karim.server.exceptions.AppInternalServerException;
import edu.cmu.andrew.karim.server.models.Payment;
import edu.cmu.andrew.karim.server.utils.MongoPool;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

public class PaymentManager extends Manager {
    public static PaymentManager _self;
    private MongoCollection<Document> paymentCollection;


    public PaymentManager() {
        this.paymentCollection = MongoPool.getInstance().getCollection("payment");
    }

    public static PaymentManager getInstance() {
        if (_self == null)
            _self = new PaymentManager();
        return _self;
    }


    public void createPayment(Payment payment) throws AppException {

        try {
            JSONObject json = new JSONObject(payment);

            Document newDoc = new Document()
                    .append("paymentId", payment.getPaymentId())
                    .append("noOfSeats", payment.getNoOfSeats())
                    .append("activityPrice", payment.getActivityPrice())
                    .append("totalPrice", payment.getTotalPrice())
                    .append("paymentIdExternal", payment.getPaymentIdExternal())
                    .append("paymentStatus", payment.getPaymentStatus());


            if (newDoc != null)
                paymentCollection.insertOne(newDoc);
            else
                throw new AppInternalServerException(0, "Failed to create new payment");

        } catch (Exception e) {
            throw handleException("Create Payment", e);
        }

    }

    public void updatePayment( String paymentId, String paymentIdExternal , String paymentStatus) throws AppException {
        try {

            Bson filter = new Document("paymentId", paymentId);
           // int seatChange =  action .equals("add") ? noOfSeats : noOfSeats*-1 ;
            FindIterable<Document> paymentDocs = paymentCollection.find().filter(Filters.eq("paymentId",paymentId));
            for(Document paymentDoc: paymentDocs) {

                Bson newValue = new Document()
                        .append("paymentId", paymentId)
                        .append("noOfSeats", paymentDoc.getInteger("noOfSeats"))

                        .append("activityPrice", paymentDoc.getDouble("activityPrice"))
                        .append("totalPrice", paymentDoc.getDouble("totalPrice"))
                        .append("paymentIdExternal", paymentDoc.getString("paymentIdExternal"))
                        .append("paymentStatus", paymentDoc.getString("paymentStatus"))
                       ;



                Bson updateOperationDocument = new Document("$set", newValue);

                if (newValue != null)
                    paymentCollection.updateOne(filter, updateOperationDocument);
                else
                    throw new AppInternalServerException(0, "Failed to update payment details");
            }

        } catch(Exception e) {
            throw handleException("Update Payment", e);
        }
    }

}

