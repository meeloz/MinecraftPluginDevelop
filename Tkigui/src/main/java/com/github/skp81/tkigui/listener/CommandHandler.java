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


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Set<String> keys = guiManager.getShowcases().keySet();
            player.sendMessage("§6You have set "+keys.size()+" showcases.");
            for(String key:keys){
                Location loc = guiManager.getShowcases().get(key).getLocation();
                TextComponent tp = new TextComponent();
                tp.setText(String.format("§e%d,%d,%d §l§2[Click]",loc.getBlockX(),loc.getBlockY(),loc.getBlockZ()));
                tp.setColor(ChatColor.GREEN);
                tp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,String.format("/tp %d %d %d",loc.getBlockX(),loc.getBlockY(),loc.getBlockZ())));
                player.spigot().sendMessage(tp);
            }
        }
        return true;
    }
}
