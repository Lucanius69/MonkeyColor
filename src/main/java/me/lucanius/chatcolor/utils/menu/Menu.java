package me.lucanius.chatcolor.utils.menu;

import lombok.Getter;
import lombok.Setter;
import me.lucanius.chatcolor.MonkeyColor;
import me.lucanius.chatcolor.utils.CC;
import me.lucanius.chatcolor.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucanius
 * March 03, 2022
 */
@Getter
@Setter
public abstract class Menu {

    protected final MonkeyColor plugin = MonkeyColor.getInstance();

    private final ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE).setData(15).setName("&b").create();

    private Map<Integer, Button> buttons = new HashMap<>();

    private boolean updateAfterClick = true;

    public abstract String getTitle(Player player);

    public abstract Map<Integer, Button> getButtons(Player player);

    public void onOpen(Player player) {
        this.plugin.getMenuManager().addToOpenedMenus(player.getUniqueId(), this);
    }

    public void onClose(Player player) {
        this.plugin.getMenuManager().removeFromOpenedMenus(player.getUniqueId());
    }

    public int getSize() {
        return this.buttonSize();
    }

    private ItemStack createItemStack(Player player, Button button) {
        ItemStack item = button.getItem(player);
        if (item.getType() != Material.SKULL) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                meta.setDisplayName(meta.getDisplayName() + "§b§c§d§e");
            }
            item.setItemMeta(meta);
        }

        return item;
    }

    public void openMenu(Player player) {
        this.buttons = this.getButtons(player);
        Menu previousMenu = this.plugin.getMenuManager().getMenuFromOpenedMenus(player.getUniqueId());

        Inventory inventory = null;
        int size = this.getSize();
        boolean update = false;

        String title = CC.translate(this.getTitle(player));
        if (title.length() > 32) {
            title = title.substring(0, 32);
        }

        if (player.getOpenInventory() != null) {
            if (previousMenu != null) {
                int previousSize = player.getOpenInventory().getTopInventory().getSize();
                if (previousSize == size && player.getOpenInventory().getTopInventory().getTitle().equals(title)) {
                    inventory = player.getOpenInventory().getTopInventory();
                    update = true;
                } else {
                    previousMenu.onClose(player);
                }
            }
        }

        if (inventory == null) {
            inventory = this.plugin.getServer().createInventory(player, size, title);
        }

        inventory.setContents(new ItemStack[inventory.getSize()]);
        for (Map.Entry<Integer, Button> buttonEntry : this.buttons.entrySet()) {
            inventory.setItem(buttonEntry.getKey(), this.createItemStack(player, buttonEntry.getValue()));
        }

        if (update) {
            player.updateInventory();
        } else {
            player.openInventory(inventory);
        }

        this.onOpen(player);
    }

    public int buttonSize() {
        int highest = 0;
        for (int buttonValue : this.buttons.keySet()) {
            if (buttonValue > highest) {
                highest = buttonValue;
            }
        }

        return (int) (Math.ceil((highest + 1) / 9d) * 9d);
    }

    public void update(Player player) {
        Inventory inventory = player.getOpenInventory().getTopInventory();
        if (inventory == null) {
            return;
        }

        inventory.setContents(new ItemStack[inventory.getSize()]);
        this.plugin.getMenuManager().addToOpenedMenus(player.getUniqueId(), this);

        for (Map.Entry<Integer, Button> buttonEntry : this.buttons.entrySet()) {
            inventory.setItem(buttonEntry.getKey(), this.createItemStack(player, buttonEntry.getValue()));
        }

        player.updateInventory();
    }

    public Map<Integer, Button> getButtonMap() {
        return this.buttons;
    }

    public Button getGlassButton() {
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                return glass;
            }
        };
    }
}
