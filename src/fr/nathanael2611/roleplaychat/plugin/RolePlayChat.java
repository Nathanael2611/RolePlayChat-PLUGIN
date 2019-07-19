package fr.nathanael2611.roleplaychat.plugin;

import fr.nathanael2611.roleplaychat.plugin.command.CommandRPName;
import fr.nathanael2611.roleplaychat.plugin.core.ChatConfig;
import fr.nathanael2611.roleplaychat.plugin.event.ChatHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RolePlayChat extends JavaPlugin {

    private static ChatConfig chatConfig;
    private static File       roleplayNames;

    @Override
    public void onEnable() {
        super.onEnable();
        new ChatHandler(this);
        getDataFolder().mkdir();
        File file = new File(getDataFolder(), "config.properties");
        roleplayNames = new File(getDataFolder(), "roleplaynames.json");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(!roleplayNames.exists()) {
            try {
                roleplayNames.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            chatConfig = new ChatConfig(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getCommand("rpname").setExecutor(new CommandRPName());
        this.getCommand("rpname").setTabCompleter((commandSender, command, s, strings) -> {
            List<String> list = new ArrayList<>();
            for(Player player : Bukkit.getServer().getOnlinePlayers()){
                list.add(player.getName());
            }
            return list;
        });
    }

    public static ChatConfig getChatConfig() {
        return chatConfig;
    }

    public static File getRoleplayNamesFile() {
        return roleplayNames;
    }
}
