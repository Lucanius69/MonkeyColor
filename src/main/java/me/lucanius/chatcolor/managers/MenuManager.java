package me.lucanius.chatcolor.managers;

import me.lucanius.chatcolor.utils.menu.Menu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Lucanius
 * March 03, 2022
 */
public class MenuManager {

    private final Map<UUID, Menu> openedMenus = new HashMap<>();

    public Map<UUID, Menu> getOpenedMenus() {
        return this.openedMenus;
    }

    public void addToOpenedMenus(UUID uniqueId, Menu menu) {
        this.openedMenus.put(uniqueId, menu);
    }

    public void removeFromOpenedMenus(UUID uniqueId) {
        this.openedMenus.remove(uniqueId);
    }

    public Menu getMenuFromOpenedMenus(UUID uniqueId) {
        return this.openedMenus.get(uniqueId);
    }
}
