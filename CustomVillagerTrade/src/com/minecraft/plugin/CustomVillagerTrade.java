package com.minecraft.plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class CustomVillagerTrade extends JavaPlugin implements Listener {
    public static CustomVillagerTrade instance;
    public final BasicFile basic = new BasicFile();

    public static CustomVillagerTrade getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        try {
            this.basic.load();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(this, this);
        manager.registerEvents(new PlayerEvent(), this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equals("cv")) {
            if(args.length == 0) {
                s.sendMessage(ChatColor.GREEN + "/cv reload");
                return false;
            } else if(args[0].equalsIgnoreCase("reload")) {
                try {
                    this.basic.load();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                }
                s.sendMessage(ChatColor.GREEN + "CustomVillagerTrade has been reloaded!");
                return true;
            }
        }
        return false;
    }

}
