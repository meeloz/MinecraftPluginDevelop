package com.minecraft.plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MyBukkitPlugin extends JavaPlugin implements Listener {

    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(this, this);
        manager.registerEvents(new PlayerEvent(this), this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equals("cv")) {
            if(args.length == 0) {
                s.sendMessage(ChatColor.GREEN + "/cv reload");
                return false;
            } else if(args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    s.sendMessage(ChatColor.GREEN + "HackerFound config has been reloaded!");
                return true;
            }
        }
        return false;
    }

}
