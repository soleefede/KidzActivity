package edu.cmu.andrew.karim.server.http;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.cmu.andrew.karim.server.utils.MongoPool;
import org.bson.Document;
import org.glassfish.jersey.server.ResourceConfig;
import edu.cmu.andrew.karim.server.managers.ActivityProviderManager;
import edu.cmu.andrew.karim.server.models.ActivityProvider;


public class AppApplication extends ResourceConfig {



//    MongoDatabase db = new MongoClient().getDatabase(db);
//    MongoCollection<Document> collectionBook = db.getCollection("bookList");
//    MongoCollection<Document> collectionBorrower = db.getCollection("borrowerList");

    public static String upSince = "";

    static {

        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    public AppApplication() {

    }


}