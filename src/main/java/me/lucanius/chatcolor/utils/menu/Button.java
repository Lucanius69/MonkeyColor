package me.lucanius.chatcolor.utils.menu;

import me.lucanius.chatcolor.MonkeyColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Lucanius
 * March 03, 2022
 */
public abstract class Button {

    protected final MonkeyColor plugin = MonkeyColor.getInstance();

    public abstract ItemStack getItem(Player player);

    public void onClick(Player player, ClickType clickType) {

    }

    public boolean shouldCancel(Player player, ClickType clickType) {
        return true;
    }

    public boolean shouldUpdate(Player player, ClickType clickType) {
        return false;
    }

    public static Button getPlaceholder(ItemStack item) {
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                return item;
            }
        };
    }

    public static void playFail(Player player) {
        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1f, (float) (Math.random() / 2) + 1f);
    }

    public static void playSuccess(Player player) {
        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1f, (float) (Math.random() / 2) + 1f);
    }
}
