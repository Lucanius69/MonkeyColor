package me.lucanius.chatcolor.menu;

import me.lucanius.chatcolor.menu.buttons.ChatColorButton;
import me.lucanius.chatcolor.menu.buttons.ResetChatColorButton;
import me.lucanius.chatcolor.monkey.MonkeyData;
import me.lucanius.chatcolor.utils.menu.Button;
import me.lucanius.chatcolor.utils.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Lucanius
 * March 03, 2022
 */
public class ChatColorMenu extends Menu {

    @Override
    public String getTitle(Player player) {
        return "&eChat Colors";
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        MonkeyData monkeyData = this.plugin.getMonkeyManager().getMonkey(player.getUniqueId());

        for (int i = 0; i < 9; i++) {
            buttons.put(i, this.getGlassButton());
        }

        buttons.put(4, new ResetChatColorButton(monkeyData));

        AtomicInteger value = new AtomicInteger(9);
        for (ChatColor color : ChatColor.values()) {
            if (color.isColor()) {
                buttons.put(value.getAndIncrement(), new ChatColorButton(monkeyData, color));
            }
        }

        return buttons;
    }

    @Override
    public int getSize() {
        return 3 * 9;
    }
}
