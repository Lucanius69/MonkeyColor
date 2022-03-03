package me.lucanius.chatcolor.menu.buttons;

import lombok.AllArgsConstructor;
import me.lucanius.chatcolor.monkey.MonkeyData;
import me.lucanius.chatcolor.utils.CC;
import me.lucanius.chatcolor.utils.ItemBuilder;
import me.lucanius.chatcolor.utils.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Lucanius
 * March 03, 2022
 */
@AllArgsConstructor
public class ResetChatColorButton extends Button {

    private final MonkeyData monkeyData;

    @Override
    public ItemStack getItem(Player player) {
        return new ItemBuilder(Material.NETHER_STAR).setName("&cReset Chat Color").create();
    }

    @Override
    public void onClick(Player player, ClickType clickType) {
        if (this.monkeyData.getChatColor() != null) {
            this.monkeyData.setChatColor(null);
            player.sendMessage(CC.translate("&aSuccessfully reset your chat color!"));
            playSuccess(player);
            player.closeInventory();
        } else {
            player.sendMessage(CC.translate("&cYou don't have any chat color selected!"));
            playSuccess(player);
            player.closeInventory();
        }
    }
}
