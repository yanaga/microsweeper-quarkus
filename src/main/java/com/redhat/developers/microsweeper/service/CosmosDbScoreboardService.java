package com.redhat.developers.microsweeper.service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.redhat.developers.microsweeper.model.Score;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CosmosDbScoreboardService implements ScoreboardService {

    MongoClient mongoClient;

    final Logger logger = LoggerFactory.getLogger(getClass());

    String uri;

    public CosmosDbScoreboardService(String uri) {
        this.uri = uri;
    }

    private MongoClient getMongoClient() {
        if (mongoClient == null) {
            return this.mongoClient = new MongoClient(new MongoClientURI(uri));
        }
        return mongoClient;
    }

    @Override
    public List<Score> getScoreboard() {
        List<Score> scores = new ArrayList<>();

        for (Document document : getScoresCollection().find()) {
            scores.add(Score.fromDocument(document));
        }
        logger.info("Fetched scores from AzureDB: " + scores);
        return scores;
    }

    @Override
    public void addScore(Score score) {
        createScoreItem(score);
        logger.info("Stored score in AzureDB: " + score);
    }

    @Override
    public void clearScores() {
        getScoresCollection().drop();
        collectionCache = null;
        logger.info("Cleared scores in AzureDB");
    }

    // Cache for the database object, so we don't have to query for it to
    // retrieve self links.
    private static MongoDatabase databaseCache;

    // Cache for the collection object, so we don't have to query for it to
    // retrieve self links.
    private static MongoCollection<Document> collectionCache;

    // The name of our database.
    private static final String DATABASE_ID = "ScoresDB";

    // The name of our collection.
    private static final String COLLECTION_ID = "ScoresCollection";

    private void createScoreItem(Score score) {
        Document scoreItemDocument = new Document(score.toMap());
        // Persist the document using the DocumentClient.
        getScoresCollection().insertOne(scoreItemDocument);
    }

    private MongoDatabase getScoreDatabase() {
        if (databaseCache != null) {
            return databaseCache;
        } else {
            databaseCache = getMongoClient().getDatabase(DATABASE_ID);
            return databaseCache;
        }
    }

    private MongoCollection<Document> getScoresCollection() {
        if (collectionCache != null) {
            return collectionCache;
        } else {
            collectionCache = getScoreDatabase().getCollection(COLLECTION_ID);
            return collectionCache;
        }
    }

}