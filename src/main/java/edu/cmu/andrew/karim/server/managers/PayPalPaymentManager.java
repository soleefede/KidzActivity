package edu.cmu.andrew.karim.server.managers;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import edu.cmu.andrew.karim.server.models.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class PayPalPaymentManager extends Manager {
    public static PayPalPaymentManager _self;
    //private MongoCollection<Document> paymentCollection;


    public PayPalPaymentManager() {
    }

    public static PayPalPaymentManager getInstance() {
        if (_self == null)
            _self = new PayPalPaymentManager();
        return _self;
    }
    private static final long serialVersionUID = 1L;
    private static final String CLIENT_ID = "ASA0IAYBxvfwyyc7gT-35X_VKrAvVcODmmXbzpKzr1ygfd6KtefDFx8dRLLz1kbofgpPiatCCavQE4l-";
    private static final String CLIENT_SECRET = "EOsA166qyvQVrh6D0DRNs6KLW9g_z_TEbu1zV-JUno7BzheMpHbAChn0Oq048RywtiLkMApmoOyjV3mB";
    private static final String MODE = "sandbox";

   public String processPayment(String product, String subtotal, String shipping, String tax, String total)
   {
       String approvalLink = "";
    OrderDetail orderDetail = new OrderDetail(product, subtotal, shipping, tax, total);

        try {
            //PaymentServices paymentServices = new PaymentServices();
           // String approvalLink = paymentServices.authorizePayment(orderDetail);
             approvalLink = authorizePayment(orderDetail);

            //response.sendRedirect(approvalLink);

        } catch (PayPalRESTException ex) {
            //request.setAttribute("errorMessage", ex.getMessage());
            ex.printStackTrace();
            //request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        return approvalLink;
    }


    public String authorizePayment(OrderDetail orderDetail)
            throws PayPalRESTException {

        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(orderDetail);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

        Payment approvedPayment = requestPayment.create(apiContext);

        return getApprovalLink(approvedPayment);

    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("William")
                .setLastName("Peterson")
                .setEmail("william.peterson@company.com");

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/PaypalTest/cancel.html");
        redirectUrls.setReturnUrl("http://localhost:8080/PaypalTest/review_payment");

        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(OrderDetail orderDetail) {
        Details details = new Details();
        details.setShipping(orderDetail.getShipping());
        details.setSubtotal(orderDetail.getSubtotal());
        details.setTax(orderDetail.getTax());

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(orderDetail.getTotal());
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(orderDetail.getProductName());

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setCurrency("USD");
        item.setName(orderDetail.getProductName());
        item.setPrice(orderDetail.getSubtotal());
        item.setTax(orderDetail.getTax());
        item.setQuantity("1");

        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }

}
