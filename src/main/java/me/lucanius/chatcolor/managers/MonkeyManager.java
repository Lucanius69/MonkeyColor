package me.lucanius.chatcolor.managers;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.AllArgsConstructor;
import me.lucanius.chatcolor.MonkeyColor;
import me.lucanius.chatcolor.monkey.MonkeyData;
import me.lucanius.chatcolor.utils.CC;
import org.bson.Document;
import org.bukkit.ChatColor;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lucanius
 * March 03, 2022
 */
@AllArgsConstructor
public class MonkeyManager {

    private final MonkeyColor plugin;
    private final Map<UUID, MonkeyData> monkeys = new ConcurrentHashMap<>();

    public MonkeyData createAndGetMonkey(UUID uniqueId) {
        MonkeyData monkeyData = new MonkeyData(uniqueId);
        this.monkeys.put(uniqueId, monkeyData);
        return monkeyData;
    }

    public MonkeyData getMonkey(UUID uniqueId) {
        return this.monkeys.get(uniqueId);
    }

    public Collection<MonkeyData> getAllMonkeys() {
        return this.monkeys.values();
    }

    public void removeMonkey(UUID uniqueId) {
        this.saveMonkey(this.monkeys.get(uniqueId));
        this.monkeys.remove(uniqueId);
    }

    public void loadMonkey(MonkeyData monkeyData) {
        Document document = this.plugin.getMongoManager().getMonkeys().find(Filters.eq("uniqueId", monkeyData.getUniqueId().toString())).first();
        if (document != null) {
            if (document.containsKey("chatColor")) {
                monkeyData.setChatColor(ChatColor.valueOf(document.getString("chatColor")));
            }
        }
    }

    public void saveMonkey(MonkeyData monkeyData) {
        if (monkeyData == null) {
            return;
        }

        Document document = new Document();
        document.put("uniqueId", monkeyData.getUniqueId().toString());
        if (monkeyData.getChatColor() != null) {
            document.put("chatColor", CC.getChatColor((monkeyData.getChatColor())));
        }

        this.plugin.getMongoManager().getMonkeys().replaceOne(Filters.eq("uniqueId", monkeyData.getUniqueId().toString()), document, new UpdateOptions().upsert(true));
    }
}
