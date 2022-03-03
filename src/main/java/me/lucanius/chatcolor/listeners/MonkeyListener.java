package me.lucanius.chatcolor.listeners;

import lombok.AllArgsConstructor;
import me.lucanius.chatcolor.MonkeyColor;
import me.lucanius.chatcolor.monkey.MonkeyData;
import me.lucanius.chatcolor.utils.CC;
import me.lucanius.chatcolor.utils.menu.Button;
import me.lucanius.chatcolor.utils.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by Lucanius
 * March 03, 2022
 */
@AllArgsConstructor
public class MonkeyListener implements Listener {

    private final MonkeyColor plugin;

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID uniqueId = event.getUniqueId();
        Player player = this.plugin.getServer().getPlayer(uniqueId);
        if (player != null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CC.translate("&cPlease wait before re-logging!"));
            this.plugin.getServer().getScheduler().runTask(this.plugin, () -> player.kickPlayer(ChatColor.RED + "Duplicated login!"));
            return;
        }

        MonkeyData monkeyData = this.plugin.getMonkeyManager().createAndGetMonkey(uniqueId);
        this.plugin.getMonkeyManager().loadMonkey(monkeyData);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.plugin.getMonkeyManager().removeMonkey(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        this.plugin.getMonkeyManager().removeMonkey(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        MonkeyData monkeyData = this.plugin.getMonkeyManager().getMonkey(event.getPlayer().getUniqueId());
        if (monkeyData.getChatColor() != null) {
            event.setFormat(event.getFormat().replace(event.getMessage(), monkeyData.getChatColor() + event.getMessage()));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMenuClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu openedMenu = this.plugin.getMenuManager().getMenuFromOpenedMenus(player.getUniqueId());
        if (openedMenu == null) {
            return;
        }

        int slot = event.getSlot();
        ClickType clickType = event.getClick();
        if (slot != event.getRawSlot()) {
            if ((clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT)) {
                event.setCancelled(true);
            }

            return;
        }
        Button button = openedMenu.getButtonMap().get(slot);
        if (button != null) {
            boolean cancel = button.shouldCancel(player, clickType);
            if (!cancel && (clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT)) {
                event.setCancelled(true);
                ItemStack currentItem = event.getCurrentItem();
                if (currentItem != null) {
                    player.getInventory().addItem(currentItem);
                }
            } else {
                event.setCancelled(cancel);
            }

            button.onClick(player, clickType);

            Menu newMenu = this.plugin.getMenuManager().getMenuFromOpenedMenus(player.getUniqueId());
            if (newMenu != null) {
                if (newMenu == openedMenu && openedMenu.isUpdateAfterClick()) {
                    newMenu.openMenu(player);
                }
            } else if (button.shouldUpdate(player, clickType)) {
                openedMenu.update(player);
            }

            if (event.isCancelled()) {
                this.plugin.getServer().getScheduler().runTaskLater(this.plugin, player::updateInventory, 1L);
            }
        } else {
            InventoryAction action = event.getAction();
            if ((clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT || action == InventoryAction.MOVE_TO_OTHER_INVENTORY || action == InventoryAction.HOTBAR_MOVE_AND_READD || action == InventoryAction.HOTBAR_SWAP)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMenuClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Menu openedMenu = this.plugin.getMenuManager().getMenuFromOpenedMenus(player.getUniqueId());
        if (openedMenu != null) {
            openedMenu.onClose(player);
        }
    }
}
