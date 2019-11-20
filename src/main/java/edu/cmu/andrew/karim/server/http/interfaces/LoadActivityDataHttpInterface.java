package edu.cmu.andrew.karim.server.http.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.exceptions.AppUnauthorizedException;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.managers.ActivityManager;
import edu.cmu.andrew.karim.server.managers.SessionManager;
import edu.cmu.andrew.karim.server.managers.UserManager;
import edu.cmu.andrew.karim.server.models.Activity;
import edu.cmu.andrew.karim.server.models.Session;
import edu.cmu.andrew.karim.server.models.User;
import org.bson.Document;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/activityData")
public class LoadActivityDataHttpInterface extends HttpInterface {
    private ObjectWriter ow;
    private MongoCollection<Document>  activityCollection = null;

    public LoadActivityDataHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }
    @POST
    //@Consumes({MediaType.APPLICATION_JSON})
   @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postActivity(@Context HttpHeaders headers){
        try{
            Session session = SessionManager.getInstance().getSessionForToken(headers);
            ArrayList<User> user = UserManager.getInstance().getUserById(session.getUserId());
            if(!session.getUserId().equals(user.get(0).getId()))
                throw new AppUnauthorizedException(70,"Invalid user id");
            for (int i = 0; i < 10; i++) {
                if(i==0) {
                    Activity newactivity = new Activity(
                            "A100",
                            "Crossfit for Kids",
                            "10",
                            "10/01/2019",
                            "",
                            "Fitness",
                            "Healthy beginnings for your kids",
                            "crossfit.jpg",
                            150,
                            "USD",
                            "Review",
                            "3"
                    );
                    ActivityManager.getInstance().createActivity(headers,newactivity);
                }
                if(i==1) {
                    Activity newactivity = new Activity(
                            "A101",
                            "Swim with Dolphins",
                            "5",
                            "10/11/2019",
                            "",
                            "Swimming",
                            "A fun activity to teach your kid to swim",
                            "dolphin.jpg",
                            100,
                            "USD",
                            "Draft",
                            "3"
                    );
                    ActivityManager.getInstance().createActivity(headers,newactivity);
                }

                if(i==2) {
                    Activity newactivity = new Activity(
                            "A102",
                            "Messi & Carla - U15 Soccer",
                            "3",
                            "10/15/2019",
                            "",
                            "Soccer",
                            "Make your kid the future Messi",
                            "soccer.jpg",
                            80,
                            "USD",
                            "Draft",
                            "3"
                    );
                    ActivityManager.getInstance().createActivity(headers,newactivity);
                }
                if(i==3) {
                    Activity newactivity = new Activity(
                            "A103",
                            "Express with colors",
                            "4",
                            "10/27/2019",
                            "",
                            "Painting",
                            "Fill your kid's life with colors",
                            "colors.jpg",
                            30,
                            "USD",
                            "Review",
                            "3"
                    );
                    ActivityManager.getInstance().createActivity(headers,newactivity);
                }

                if(i==4) {
                    Activity newactivity = new Activity(
                            "A104",
                            "Public Speaking",
                            "3",
                            "11/01/2019",
                            "",
                            "Personality Development",
                            "Develop the leadership skills in your kid",
                            "publicSpeaking.jpg",
                            25,
                            "USD",
                            "Publish",
                            "3"
                    );
                    ActivityManager.getInstance().createActivity(headers,newactivity);
                }
                if(i==5) {
                    Activity newactivity = new Activity(
                            "A105",
                            "Fun with numbers",
                            "6",
                            "10/15/2019",
                            "",
                            "Knowledge",
                            "Get comfortable with numbers",
                            "numbers.jpg",
                            35,
                            "USD",
                            "Publish",
                            "3"
                    );
                    ActivityManager.getInstance().createActivity(headers,newactivity);
                }
                if(i==6) {
                    Activity newactivity = new Activity(
                            "A106",
                            "Dance",
                            "8",
                            "10/01/2019",
                            "",
                            "Personality Development",
                            "Dance for fun",
                            "dance.jpg",
                            80,
                            "USD",
                            "Publish",
                            "3"
                    );
                    ActivityManager.getInstance().createActivity(headers,newactivity);
                }
                if(i==7) {
                    Activity newactivity = new Activity(
                            "A107",
                            "Space Kids",
                            "3",
                            "10/15/2019",
                            "",
                            "Knowledge",
                            "Explore the world",
                            "space.jpg",
                            75,
                            "USD",
                            "Publish",
                            "3"
                    );
                    ActivityManager.getInstance().createActivity(headers,newactivity);
                }
                if(i==8) {
                    Activity newactivity = new Activity(
                            "A108",
                            "Oil painting for beginners",
                            "4",
                            "09/10/2019",
                            "",
                            "Painting",
                            "Introduce your kid to oil painting",
                            "oilpaint.jpg",
                            50,
                            "USD",
                            "Publish",
                            "3"
                    );
                    ActivityManager.getInstance().createActivity(headers,newactivity);
                }

                if(i==9) {
                    Activity newactivity = new Activity(
                            "A109",
                            "Pink's swimming",
                            "7",
                            "10/15/2019",
                            "",
                            "Swimming",
                            "Swim like champion",
                            "space.jpg",
                            60,
                            "USD",
                            "Draft",
                            "3"
                    );
                    ActivityManager.getInstance().createActivity(headers,newactivity);
                }

            }
            return new AppResponse("Insert Successful");

        }catch (Exception e){
            throw handleException("POST users", e);
        }
    }
}
