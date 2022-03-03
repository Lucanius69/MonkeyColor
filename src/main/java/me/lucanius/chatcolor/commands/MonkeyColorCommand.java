package me.lucanius.chatcolor.commands;

import me.lucanius.chatcolor.menu.ChatColorMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * Created by Lucanius
 * March 03, 2022
 */
public class MonkeyColorCommand extends Command {

    public MonkeyColorCommand() {
        super("monkeycolor");
        this.setAliases(Arrays.asList("chatcolor", "color"));
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        new ChatColorMenu().openMenu((Player) sender);
        return true;
    }
}
