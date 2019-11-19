package edu.cmu.andrew.karim.server.http.interfaces;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.client.MongoCollection;
import edu.cmu.andrew.karim.server.http.responses.AppResponse;
import edu.cmu.andrew.karim.server.managers.ParentManager;
import edu.cmu.andrew.karim.server.models.Parent;
import org.bson.Document;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/parentData")
public class LoadParentDataHttpInterface extends HttpInterface{
    private ObjectWriter ow;
    private MongoCollection<Document> parentCollection = null;

    public LoadParentDataHttpInterface() {
        ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    }
    @POST
    //@Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public AppResponse postParent(){
        try{

            for (int i = 0; i < 10; i++) {
                if(i==0) {
                    Parent newparent = new Parent(
                            null,
                            "PR01",
                            "Scott",
                            "Lohr",
                            "609-949-5647",
                            "ScottJLohr@dayrep.com",
                            "2497 Whiteman Street",
                            "Apt 1",
                            "Camden",
                            "NJ",
                            "08102",
                            "US",
                            "11",
                            "Camden",
                            "Fitness"
                    );
                    ParentManager.getInstance().createParent(newparent);
                }
                if(i==1) {
                    Parent newparent = new Parent(
                            null,
                            "PR02",
                            "Alice",
                            "Moore",
                            "804-398-1406",
                            "AliceJMoore@armyspy.com",
                            "4577 Melody Lane",
                            "Apt 2",
                            "Richmond",
                            "VA",
                            "23222",
                            "US",
                            "5",
                            "Richmond",
                            "Swimming"
                    );
                    ParentManager.getInstance().createParent(newparent);
                }

                if(i==2) {
                    Parent newparent = new Parent(
                            null,
                            "PR03",
                            "Ila",
                            "Grant",
                            "516-624-8026",
                            "IlaBGrant@teleworm.us",
                            "3873 Southern Street",
                            "Apt 3",
                            "Oyster Bay",
                            "NY",
                            "11771",
                            "US",
                            "15",
                            "Oyster Bay",
                            "Soccer"
                    );
                    ParentManager.getInstance().createParent(newparent);
                }

                if(i==3) {
                    Parent newparent = new Parent(
                            null,
                            "PR04",
                            "Minerva",
                            "Berger",
                            "203-633-1187",
                            "MinervaJBerger@jourrapide.com",
                            "4793 Whitman Court",
                            "Apt 4",
                            "Stamford",
                            "CT",
                            "06902",
                            "US",
                            "10",
                            "Stamford",
                            "Painting"
                    );
                    ParentManager.getInstance().createParent(newparent);
                }

                if(i==4) {
                    Parent newparent = new Parent(
                            null,
                            "PR05",
                            "William",
                            "Patel",
                            "303-793-1092",
                            "WilliamSPatel@jourrapide.com",
                            "2324 McKinley Avenue",
                            "Apt 5",
                            "Centennial",
                            "CO",
                            "80111",
                            "US",
                            "11",
                            "Centennial",
                            "Personality Development"
                    );
                    ParentManager.getInstance().createParent(newparent);
                }

                if(i==5) {
                    Parent newparent = new Parent(
                            null,
                            "PR06",
                            "Maria",
                            "Hicks",
                            "302-293-0436",
                            "MariaGHicks@teleworm.us",
                            "2593 Argonne Street",
                            "Apt 6",
                            "Eagleville",
                            "DE",
                            "19403",
                            "US",
                            "7",
                            "Eagleville",
                            "Knowledge"
                    );
                    ParentManager.getInstance().createParent(newparent);
                }

                if(i==6) {
                    Parent newparent = new Parent(
                            null,
                            "PR07",
                            "Sandra",
                            "Trimble",
                            "917-680-4225",
                            "SandraCTrimble@teleworm.us",
                            "732 Hanover Street",
                            "Apt 7",
                            "Huntington",
                            "NY",
                            "11743",
                            "US",
                            "3",
                            "Huntington",
                            "Personality Development"
                    );
                    ParentManager.getInstance().createParent(newparent);
                }

                if(i==7) {
                    Parent newparent = new Parent(
                            null,
                            "PR08",
                            "Gerald",
                            "Carpenter",
                            "918-799-4539",
                            "GeraldLCarpenter@rhyta.com",
                            "423 Bridge Street",
                            "Apt 8",
                            "Enterprise",
                            "OK",
                            "74436",
                            "US",
                            "9",
                            "Enterprise",
                            "Knowledge"
                    );
                    ParentManager.getInstance().createParent(newparent);
                }

                if(i==8) {
                    Parent newparent = new Parent(
                            null,
                            "PR09",
                            "Gloria",
                            "Garcia",
                            "541-937-5446",
                            "GloriaDGarcia@rhyta.com",
                            "3956 Center Street",
                            "Apt 9",
                            "Lowell",
                            "OR",
                            "97452",
                            "US",
                            "16",
                            "Lowell",
                            "Painting"
                    );
                    ParentManager.getInstance().createParent(newparent);
                }

                if(i==9) {
                    Parent newparent = new Parent(
                            null,
                            "PR10",
                            "Charles",
                            "Flowers",
                            "646-837-6914",
                            "CharlesWFlowers@dayrep.com",
                            "4116 Forest Avenue",
                            "Apt 10",
                            "New York",
                            "NY",
                            "10013",
                            "US",
                            "4",
                            "New York",
                            "Swimming"
                    );
                    ParentManager.getInstance().createParent(newparent);
                }

            }
            return new AppResponse("Insert Successful");

        }catch (Exception e){
            throw handleException("POST parents", e);
        }
    }
}
