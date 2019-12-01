package edu.cmu.andrew.karim.server.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.exceptions.AppUnauthorizedException;
import edu.cmu.andrew.karim.server.http.exceptions.HttpBadRequestException;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.http.utils.PATCH;
import edu.cmu.andrew.karim.server.managers.ActivityProviderManager;
import edu.cmu.andrew.karim.server.managers.SessionManager;
import edu.cmu.andrew.karim.server.managers.UserManager;
import edu.cmu.andrew.karim.server.models.ActivityProvider;
import edu.cmu.andrew.karim.server.models.Session;
import edu.cmu.andrew.karim.server.models.User;
import edu.cmu.andrew.karim.server.utils.*;
import org.bson.Document;
import org.json.JSONObject;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


@Path("/activityProviderData")
public class LoadActivityProviderDataHttpInterface extends HttpInterface {
    private ObjectWriter ow;
    private MongoCollection<Document> activityProviderCollection = null;

    public LoadActivityProviderDataHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }
    @POST
    //@Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postActivityProvider(@Context HttpHeaders headers){
        try{
            Session session = SessionManager.getInstance().getSessionForToken(headers);
            ArrayList<User> user = UserManager.getInstance().getUserById(session.getUserId());
            if(!session.getUserId().equals(user.get(0).getId()))
                throw new AppUnauthorizedException(70,"Invalid user id");
            for (int i = 0; i < 10; i++) {
                if(i==0) {
                    ActivityProvider newactivityprovider = new ActivityProvider(
                            null,
                            "0",
                            "William Weinman",
                            "Techo Solutions",
                            "09-6161234",
                            "096-16-1234",
                            "663 Patterson Road",
                            "Apt 0",
                            "Brooklyn",
                            "NY",
                            "718-840-1913",
                            "WilliamKWeinman@dayrep.com",
                            "0.2",
                            "Visa",
                            "4539 1282 2152 8459",
                            "578",
                            ""
                    );
                    ActivityProviderManager.getInstance().createActivityProvider(headers, newactivityprovider);
                }
                if(i==1) {
                    ActivityProvider newactivityprovider = new ActivityProvider(
                            null,
                            "1",
                            "Robert Womack",
                            "Piccolo Mondo",
                            "54-3101234",
                            "543-10-1234",
                            "2082 Illinois Avenue",
                            "Apt 1",
                            "Portland",
                            "OR",
                            "503-709-2953",
                            "RobertZWomack@dayrep.com",
                            "0.2",
                            "MasterCard",
                            "5209 3070 4056 5936",
                            "451",
                            ""
                    );
                    ActivityProviderManager.getInstance().createActivityProvider(headers, newactivityprovider);
                }

                if(i==2) {
                    ActivityProvider newactivityprovider = new ActivityProvider(
                            null,
                            "2",
                            "Joseph Alvarado",
                            "National Shirt Shop",
                            "77-1301234",
                            "771-30-1234",
                            "28 Wyatt Street",
                            "APT 2",
                            "West Palm Beach",
                            "FL",
                            "561-534-2507",
                            "JosephIAlvarado@rhyta.com",
                            "0.2",
                            "Master Card",
                            "5353 4988 4821 0561",
                            "488",
                            ""
                    );
                    ActivityProviderManager.getInstance().createActivityProvider(headers, newactivityprovider);
                }
                if(i==3) {
                    ActivityProvider newactivityprovider = new ActivityProvider(
                            null,
                            "3",
                            "Shawn Scott",
                            "Star Interior Design",
                            "36-0201234",
                            "360-20-1234",
                            "416 Lewis Street",
                            "APT 3",
                            "Chicago",
                            "IL",
                            "630-923-8353",
                            "ShawnSScott@jourrapide.com",
                            "0.2",
                            "Visa",
                            "5360 9913 3483 4317",
                            "218",
                            ""
                    );
                    ActivityProviderManager.getInstance().createActivityProvider(headers, newactivityprovider);
                }

                if(i==4) {
                    ActivityProvider newactivityprovider = new ActivityProvider(
                            null,
                            "4",
                            "Shirley Hughes",
                            "Al's Auto Parts",
                            "62-8381234",
                            "628-38-1234",
                            "4112 Adams Drive",
                            "APT 4",
                            "Houston",
                            "TX",
                            "979-487-8901",
                            "ShirleyRHughes@armyspy.com",
                            "0.2",
                            "Visa",
                            "5147 2751 8677 2860",
                            "238",
                            ""
                    );
                    ActivityProviderManager.getInstance().createActivityProvider(headers, newactivityprovider);
                }
                if(i==5) {
                    ActivityProvider newactivityprovider = new ActivityProvider(
                            null,
                            "5",
                            "Brandi Feldt",
                            "Square Circle",
                            "44-8201234",
                            "448-20-1234",
                            "3446 Grove Avenue",
                            "APT 5",
                            "Clinton",
                            "OK",
                            "580-330-1405",
                            "BrandiDFeldt@jourrapide.com",
                            "0.2",
                            "Visa",
                            "4929 1636 1112 6596",
                            "093",
                             ""
                    );
                    ActivityProviderManager.getInstance().createActivityProvider(headers, newactivityprovider);
                }
                if(i==6) {
                    ActivityProvider newactivityprovider = new ActivityProvider(
                            null,
                            "6",
                            "Louise Smalley",
                            "Monk Home Loans",
                            "32-4761234",
                            "324-76-1234",
                            "2509 Poplar Street",
                            "APT 6",
                            "Burr Ridge",
                            "IL",
                            "708-703-2128",
                            "LouiseRSmalley@armyspy.com",
                            "0.2",
                            "Visa",
                            "4539 2774 0520 9640",
                            "487",
                            ""
                    );
                    ActivityProviderManager.getInstance().createActivityProvider(headers, newactivityprovider);
                }
                if(i==7) {
                    ActivityProvider newactivityprovider = new ActivityProvider(
                            null,
                            "7",
                            "Michael Levine",
                            "Sunny's Surplus",
                            "21-9261234",
                            "219-26-1234",
                            "940 Bluff Street",
                            "APT 7",
                            "Mount Airy",
                            "MD",
                            "301-799-5178",
                            "MichaelELevine@armyspy.com",
                            "0.2",
                            "Visa",
                            "4532 0637 6391 9716",
                            "456",
                            ""
                    );
                    ActivityProviderManager.getInstance().createActivityProvider(headers, newactivityprovider);
                }
                if(i==8) {
                    ActivityProvider newactivityprovider = new ActivityProvider(
                            null,
                            "8",
                            "Ignacio Burnham",
                            "Burstein-Applebee",
                            "34-5901234",
                            "345-90-1234",
                            "1646 Emeral Dreams Drive",
                            "APT 8",
                            "Arlington Heights",
                            "IL",
                            "815-317-6136",
                            "IgnacioPBurnham@jourrapide.com",
                            "0.2",
                            "Master Card",
                            "4916 3752 9317 4901",
                            "052",
                            ""
                    );
                    ActivityProviderManager.getInstance().createActivityProvider(headers, newactivityprovider);
                }

                if(i==9) {
                    ActivityProvider newactivityprovider = new ActivityProvider(
                            null,
                            "9",
                            "Shirley Blaine",
                            "The Great Train Stores",
                            "49-9211234",
                            "499-21-1234",
                            "2065 Oak Lane",
                            "APT 9",
                            "Maryville",
                            "MO",
                            "660-671-7139",
                            "ShirleyTBlaine@armyspy.com",
                            "0.2",
                            "Master Card",
                            "4929 9695 6552 9100",
                            "244",
                            ""
                    );
                    ActivityProviderManager.getInstance().createActivityProvider(headers, newactivityprovider);
                }

            }
            return new AppResponse("Insert Successful");

        }catch (Exception e){
            throw handleException("POST activity providers", e);
        }
    }
}
