package com.github.skp81.tkigui.listener;

import com.github.skp81.tkigui.TkiguiPlugin;
import com.github.skp81.tkigui.manager.GUIManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class CommandHandler implements CommandExecutor {
    private TkiguiPlugin instance;
    private GUIManager guiManager;

    public CommandHandler(){
        instance = TkiguiPlugin.getInstance();
        guiManager = TkiguiPlugin.getInstance().getGUIManager();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(!player.hasPermission("tkigui.command")){
                player.sendMessage(instance.getConfig().getString("Message.PermissionMessage"));
                return false;
            }
            if (args[0].equals("reload")) {
                instance.reloadConfig();
                player.sendMessage(instance.getConfig().getString("Message.ReloadMessage"));
                return true;
            }
            if (args[0].equals("list")) {
                Set<String> keys = guiManager.getShowcases().keySet();
                player.sendMessage(String.format(instance.getConfig().getString("Message.ListMessage"),keys.size()));
                for(String key:keys){
                    Location loc = guiManager.getShowcases().get(key).getLocation();
                    TextComponent tp = new TextComponent();
                    tp.setText(String.format("§6[§3Location§6] §e[%d,%d,%d] §8- §l§2[Click]",loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()));
                    tp.setColor(ChatColor.GREEN);
                    tp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,String.format("/tp %d %d %d",loc.getBlockX(),loc.getBlockY(),loc.getBlockZ())));
                    player.spigot().sendMessage(tp);
                }
                return true;
            }
        }
        return false;
    }
}
