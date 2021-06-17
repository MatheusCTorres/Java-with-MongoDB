package com.room;

import com.mongodb.ConnectionString;
import com.mongodb.client.*;
import com.mongodb.MongoClientSettings;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Updates.set;

public class MongoConnector {
    public static void main(String[] args) {
        ConnectionString connString = new ConnectionString("mongodb+srv://Admin:admin123@java-diurno.qs5vu.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Java-Diurno");
        MongoCollection users = database.getCollection("users");

        // create

        Map<String, Object> newUser = new HashMap<>();

        newUser.put("username", "Example");
        newUser.put("password", "123456789");
        newUser.put("email", "example@hotmail.com");
        newUser.put("contact", 963852741);

        Document doc = new Document(newUser);

        users.insertOne(doc);

        // Read one result

        Map<String, Object> findUser = new HashMap<>();
        findUser.put("username", "Example");

        doc = new Document(findUser);

        Document read = (Document) users.find(doc).first();

        System.out.println(read.toJson());

        // Read multiple results

        MongoCursor<Document> cursor = users.find().iterator();

        try{
            while(cursor.hasNext()){
                System.out.println(cursor.next().toJson());
            }
        }finally{
            cursor.close();
        }

        // update

        Bson filter = gt("username", "Example");
        Bson update = set("username", "Another");

        // update one
        users.updateOne(filter, update);
/*
        // update many
       // users.updateMany(filter, update);
   */

        // delete

        users.deleteMany(eq("email", "testing@hotmail.com"));
        // delete one
        users.deleteOne(eq("email","aaa@hotmail.com"));

    }

}