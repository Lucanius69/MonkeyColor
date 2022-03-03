package me.lucanius.chatcolor.menu.buttons;

import lombok.AllArgsConstructor;
import me.lucanius.chatcolor.monkey.MonkeyData;
import me.lucanius.chatcolor.utils.CC;
import me.lucanius.chatcolor.utils.ItemBuilder;
import me.lucanius.chatcolor.utils.menu.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Lucanius
 * March 03, 2022
 */
@AllArgsConstructor
public class ChatColorButton extends Button {

    private final MonkeyData monkeyData;
    private final ChatColor color;

    @Override
    public ItemStack getItem(Player player) {
        if (this.monkeyData.getChatColor() == this.color) {
            return new ItemBuilder(Material.WOOL).setData(CC.getWoolColorFromChatColor(this.color)).setName(this.color + CC.getSexyChatColor(this.color, true)).addEnchant(Enchantment.DURABILITY, 1).hideEnchants().create();
        } else {
            return new ItemBuilder(Material.WOOL).setData(CC.getWoolColorFromChatColor(this.color)).setName(this.color + CC.getSexyChatColor(this.color, true)).create();
        }
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        if (!player.hasPermission("monkeycolor." + CC.getChatColor(this.color).toLowerCase())) {
            player.sendMessage(CC.translate("&cYou don't have permission to the " + this.color + CC.getSexyChatColor(this.color, true) + " &cchat color!"));
            playFail(player);
            player.closeInventory();
            return;
        }

        if (this.monkeyData.getChatColor() != this.color) {
            this.monkeyData.setChatColor(this.color);
            player.sendMessage(CC.translate("&aSuccessfully set your chat color to " + this.color + CC.getSexyChatColor(this.color, true) + "&a!"));
            playSuccess(player);
            player.closeInventory();
        } else {
            player.sendMessage(CC.translate("&cYou already have that chat color selected!"));
            playFail(player);
            player.closeInventory();
        }
    }
}
