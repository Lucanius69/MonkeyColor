package me.lucanius.chatcolor;

import lombok.Getter;
import me.lucanius.chatcolor.commands.MonkeyColorCommand;
import me.lucanius.chatcolor.listeners.MonkeyListener;
import me.lucanius.chatcolor.managers.MenuManager;
import me.lucanius.chatcolor.managers.MongoManager;
import me.lucanius.chatcolor.managers.MonkeyManager;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MonkeyColor extends JavaPlugin {

    @Getter private static MonkeyColor instance;

    private MongoManager mongoManager;
    private MonkeyManager monkeyManager;
    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();

        if (!this.getDescription().getAuthors().contains("Lucanius") || !this.getDescription().getName().equals("MonkeyColor")) {
            this.getServer().getPluginManager().disablePlugin(this);
            this.getServer().shutdown();
            return;
        }

        this.mongoManager = new MongoManager(this);
        this.monkeyManager = new MonkeyManager(this);
        this.menuManager = new MenuManager();

        this.getServer().getPluginManager().registerEvents(new MonkeyListener(this), this);

        Command command = new MonkeyColorCommand();
        ((CraftServer) this.getServer()).getCommandMap().register(command.getName(), this.getName(), command);
    }

    @Override
    public void onDisable() {
        this.monkeyManager.getAllMonkeys().forEach(playerData -> this.monkeyManager.saveMonkey(playerData));
        this.mongoManager.disconnect();
    }
}
