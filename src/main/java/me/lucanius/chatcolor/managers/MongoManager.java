package me.lucanius.chatcolor.managers;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.lucanius.chatcolor.MonkeyColor;
import org.bson.Document;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;

/**
 * Created by Lucanius
 * March 03, 2022
 */
@Getter
public class MongoManager {

    private final MonkeyColor plugin;
    private final FileConfiguration config;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private final String host;
    private final int port;
    private final String database;
    private final boolean auth;
    private final String user;
    private final String password;
    private final String authDb;

    private MongoCollection<Document> monkeys;

    public MongoManager(MonkeyColor plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();

        this.host = this.config.getString("MONGO.HOST");
        this.port = this.config.getInt("MONGO.PORT");
        this.database = this.config.getString("MONGO.DATABASE");
        this.auth = this.config.getBoolean("MONGO.AUTH.ENABLED");
        this.user = this.config.getString("MONGO.AUTH.USER");
        this.password = this.config.getString("MONGO.AUTH.PASS");
        this.authDb = this.config.getString("MONGO.AUTH.AUTH-DB");

        this.connect();
    }

    private void connect() {
        try {
            if (this.auth) {
                final MongoCredential credential = MongoCredential.createCredential(this.user, this.authDb, this.password.toCharArray());
                this.mongoClient = new MongoClient(new ServerAddress(this.host, this.port), Collections.singletonList(credential));
            } else {
                this.mongoClient = new MongoClient(this.host, this.port);
            }
            this.mongoDatabase = this.mongoClient.getDatabase(this.database);
            this.monkeys = this.mongoDatabase.getCollection("monkeys");
        } catch (Exception exception) {
            exception.printStackTrace();
            this.plugin.getPluginLoader().disablePlugin(this.plugin);
        }
    }

    public void disconnect() {
        if (this.mongoClient != null) {
            this.mongoClient.close();
        }
    }
}
