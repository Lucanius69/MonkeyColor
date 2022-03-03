package me.lucanius.chatcolor.monkey;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.ChatColor;

import java.util.UUID;

/**
 * Created by Lucanius
 * March 03, 2022
 */
@Getter
@Setter
@RequiredArgsConstructor
public class MonkeyData {

    private final UUID uniqueId;
    private ChatColor chatColor;
}
