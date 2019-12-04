package edu.cmu.andrew.karim.server.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.http.exceptions.HttpBadRequestException;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.http.utils.PATCH;
import edu.cmu.andrew.karim.server.managers.PaymentManager;
import edu.cmu.andrew.karim.server.models.Payment;
import edu.cmu.andrew.karim.server.utils.AppLogger;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


@Path("/payment")
public class PaymentHttpInterface extends HttpInterface {

    private ObjectWriter ow;
    private MongoCollection<Document> paymentCollection = null;

    public PaymentHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postPayment(Object request) {

        try {
            JSONObject json = null;
            json = new JSONObject(ow.writeValueAsString(request));

            Payment newpayment = new Payment(
                    json.getString("paymentId"),
                    json.getInt("noOfSeats"),
                    json.getInt("activityPrice"),
                    json.getInt("totalPrice"),
                    json.getString("paymentIdExternal"),
                    json.getString("paymentStatus")
            );

            PaymentManager.getInstance().createPayment(newpayment);
            return new AppResponse("Insert Successful");

        } catch (Exception e) {
            throw handleException("POST users", e);
        }

    }


    @GET
    @Path("/{bookingId}")
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse getPayments(@Context HttpHeaders headers, @PathParam("bookingId") String bookingId){

        try{
            AppLogger.info("Got an API call");
            ArrayList<Payment> payments  = null;

            if(("PYMT" + bookingId)!= null)
                //System.out.println("I am here : " +  );
                payments = PaymentManager.getInstance().getPayments("PYMT" + bookingId );

            if( payments != null)
                return new AppResponse(payments);
            else
                throw new HttpBadRequestException(0, "Problem with getting payments");
        }catch (Exception e){
            throw handleException("GET /availability/{activityId}", e);
        }

    }

    @PATCH
    @Path("/{paymentId}")
    @Consumes({ MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_JSON})
    public AppResponse patchPayment(Object request, @PathParam("paymentId") String paymentId){

        JSONObject json = null;

        try{
            json = new JSONObject(ow.writeValueAsString(request));

            PaymentManager.getInstance().updatePayment(paymentId,json.getString("paymentIdExternal"),json.getString("paymentStatus"));

        }catch (Exception e){
            throw handleException("PATCH payment/{paymentId}", e);
        }

        return new AppResponse("Update Successful");
    }


}
