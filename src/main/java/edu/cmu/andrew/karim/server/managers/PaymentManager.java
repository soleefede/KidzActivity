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


            if (newDoc != null) {
                paymentCollection.insertOne(newDoc);
                //String product, String subtotal, String shipping, String tax, String total
                String payPalLink = PayPalPaymentManager.getInstance().processPayment(payment.getPaymentId(), Float.toString(payment.getTotalPrice()),
                        "5","6","100");
                System.out.println(payPalLink);
            }
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
                        .append("paymentStatus", paymentDoc.getString("paymentStatus"));



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

    //begin

/*
    public class CrunchifyPayPalSDKTutorial {
        private static String crunchifyID = "<!---- Add your clientID Key here ---->";
        private static String crunchifySecret = "<!---- Add your clientSecret Key here ---->";

        private static String executionMode = "sandbox"; // sandbox or production

        public static void main(String args[]) {
            CrunchifyPayPalSDKTutorial crunchifyObj = new CrunchifyPayPalSDKTutorial();

            // How to capture PayPal Payment using Java SDK? doCapture() PayPal SDK call.
            crunchifyObj.crunchifyCapturePayPalAPI();
        }

        // This is simple API call which will capture a specified amount for any given
        // Payer or User
        public void crunchifyCapturePayPalAPI() {

            /*
             * Flow would look like this:
             * 1. Create Payer object and set PaymentMethod
             * 2. Set RedirectUrls and set cancelURL and returnURL
             * 3. Set Details and Add PaymentDetails
             * 4. Set Amount
             * 5. Set Transaction
             * 6. Add Payment Details and set Intent to "authorize"
             * 7. Create APIContext by passing the clientID, secret and mode
             * 8. Create Payment object and get paymentID
             * 9. Set payerID to PaymentExecution object
             * 10. Execute Payment and get Authorization
             *
             */
/*
            Payer crunchifyPayer = new Payer();
            crunchifyPayer.setPaymentMethod("paypal");

            // Redirect URLs
            RedirectUrls crunchifyRedirectUrls = new RedirectUrls();
            crunchifyRedirectUrls.setCancelUrl("http://localhost:3000/crunchifyCancel");
            crunchifyRedirectUrls.setReturnUrl("http://localhost:3000/crunchifyReturn");

            // Set Payment Details Object
            Details crunchifyDetails = new Details();
            crunchifyDetails.setShipping("2.22");
            crunchifyDetails.setSubtotal("3.33");
            crunchifyDetails.setTax("1.11");

            // Set Payment amount
            Amount crunchifyAmount = new Amount();
            crunchifyAmount.setCurrency("USD");
            crunchifyAmount.setTotal("6.66");
            crunchifyAmount.setDetails(crunchifyDetails);

            // Set Transaction information
            Transaction crunchifyTransaction = new Transaction();
            crunchifyTransaction.setAmount(crunchifyAmount);
            crunchifyTransaction.setDescription("Crunchify Tutorial: How to Invoke PayPal REST API using Java Client?");
            List<Transaction> crunchifyTransactions = new ArrayList<Transaction>();
            crunchifyTransactions.add(crunchifyTransaction);

            // Add Payment details
            Payment crunchifyPayment = new Payment();

            // Set Payment intent to authorize
            crunchifyPayment.setIntent("authorize");
            crunchifyPayment.setPayer(crunchifyPayer);
            crunchifyPayment.setTransactions(crunchifyTransactions);
            crunchifyPayment.setRedirectUrls(crunchifyRedirectUrls);

            // Pass the clientID, secret and mode. The easiest, and most widely used option.
            APIContext crunchifyapiContext = new APIContext(crunchifyID, crunchifySecret, executionMode);

            try {

                Payment myPayment = crunchifyPayment.create(crunchifyapiContext);

                System.out.println("createdPayment Obejct Details ==> " + myPayment.toString());

                // Identifier of the payment resource created
                crunchifyPayment.setId(myPayment.getId());

                PaymentExecution crunchifyPaymentExecution = new PaymentExecution();

                // Set your PayerID. The ID of the Payer, passed in the `return_url` by PayPal.
                crunchifyPaymentExecution.setPayerId("<!---- Add your PayerID here ---->");

                // This call will fail as user has to access Payment on UI. Programmatically
                // there is no way you can get Payer's consent.
                Payment createdAuthPayment = crunchifyPayment.execute(crunchifyapiContext, crunchifyPaymentExecution);

                // Transactional details including the amount and item details.
                Authorization crunchifyAuthorization = createdAuthPayment.getTransactions().get(0).getRelatedResources().get(0).getAuthorization();

                log("Here is your Authorization ID" + crunchifyAuthorization.getId());

            } catch (PayPalRESTException e) {

                // The "standard" error output stream. This stream is already open and ready to
                // accept output data.
                System.err.println(e.getDetails());
            }
        }

        private void log(String string) {
            System.out.println(string);

        }
    }


    */




}//end

