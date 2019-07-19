package fr.nathanael2611.roleplaychat.plugin.event;

import fr.nathanael2611.roleplaychat.plugin.RolePlayChat;
import fr.nathanael2611.roleplaychat.plugin.core.ChatType;
import fr.nathanael2611.roleplaychat.plugin.core.EnumCHatTypes;
import fr.nathanael2611.roleplaychat.plugin.core.RolePlayNames;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatHandler implements Listener {

    public ChatHandler(final RolePlayChat plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void sendChatMessageByPlayer(Player player, ChatType type, String message){
        Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        List<Player> playersInRadius = new ArrayList<>();
        final int DISTANCE = type.getDistance();
        String format = type.getFormat();
        if(format.contains("{player}"))  format = format.replace("{player}", player.getName());
        if(format.contains("{rpname}"))  format = format.replace("{rpname}", RolePlayNames.getRPName(player.getName()));
        if(format.contains("{message}")) format = format.replace("{message}", message);
        if(DISTANCE == 0){
            playersInRadius.addAll(Arrays.asList(players));
        }else{
            for(Player entityPlayer : players){
                if(
                        player.getLocation().distance(
                                entityPlayer.getLocation()
                        ) <= type.getDistance()
                ){
                    playersInRadius.add(entityPlayer);
                }
            }
        }
        for(Player entityPlayer : playersInRadius){
            entityPlayer.sendMessage(
                    format
            );
        }
    }

    @EventHandler
    public static void onChat(AsyncPlayerChatEvent e){
        e.setCancelled(true);
        Player player = e.getPlayer();
        String entireMessage = e.getMessage();
        ChatType type = RolePlayChat.getChatConfig().getChatType(EnumCHatTypes.NORMAL);
        for(ChatType chatType : RolePlayChat.getChatConfig().getChatTypes()){
            if(!chatType.getPrefix().equals("")){
                if(entireMessage.startsWith(chatType.getPrefix())){
                    type = chatType;
                    break;
                }
            }else{
                type = chatType;
            }
        }
        entireMessage = entireMessage.substring(type.getPrefix().length());
        sendChatMessageByPlayer(player, type, entireMessage);
    }
}