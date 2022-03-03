package me.lucanius.chatcolor.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Lucanius
 * March 03, 2022
 */
public class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(CC.translate(name));
        this.item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setData(int data) {
        this.item.setDurability((short) data);

        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder hideEnchants() {
        ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.item.setItemMeta(meta);

        return this;
    }

    public ItemStack create() {
        return this.item;
    }
}
