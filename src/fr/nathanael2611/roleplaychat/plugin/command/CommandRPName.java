package fr.nathanael2611.roleplaychat.plugin.command;

import fr.nathanael2611.roleplaychat.plugin.RolePlayChat;
import fr.nathanael2611.roleplaychat.plugin.core.EnumMessages;
import fr.nathanael2611.roleplaychat.plugin.core.RolePlayNames;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandRPName implements CommandExecutor {
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args) {
        if (args.length >= 1) {
            List<String> players = new ArrayList<>();
            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                players.add(online.getName());
            }



            if (players.contains(args[0]) && args.length > 1) {
                if (sender.hasPermission("roleplaychat.setrpname.other")) {
                    StringBuilder builder = new StringBuilder();
                    for(int i = 1; i < args.length; i++){
                        builder.append(" ").append(args[i]);
                    }
                    String rpName = builder.toString().substring(1);
                    RolePlayNames.setRPName(args[0], rpName);
                    String result = RolePlayChat.getChatConfig().getMessageTemplate(EnumMessages.SETRPNAMEOTHER);
                    if (result.contains("{player}")) result = result.replace("{player}", args[0]);
                    if (result.contains("{rpname}")) result = result.replace("{rpname}", rpName);
                    sender.sendMessage(result);
                } else {
                    sender.sendMessage(RolePlayChat.getChatConfig().getMessageTemplate(EnumMessages.NOPERM));
                }
            }else{
                StringBuilder builder = new StringBuilder();
                for(String str : args){
                    builder.append(" ").append(str);
                }
                String rpName = builder.toString().substring(1);
                RolePlayNames.setRPName(sender.getName(), rpName);
                String result = RolePlayChat.getChatConfig().getMessageTemplate(EnumMessages.SETRPNAME);
                if (result.contains("{player}")) result = result.replace("{player}", sender.getName());
                if (result.contains("{rpname}")) result = result.replace("{rpname}", rpName);
                sender.sendMessage(result);
            }
        } else {
            sender.sendMessage("§cCorrect usage: /rpname <rpname> OR /rpname <player> <rpname>");
        }
        return false;
    }

}
