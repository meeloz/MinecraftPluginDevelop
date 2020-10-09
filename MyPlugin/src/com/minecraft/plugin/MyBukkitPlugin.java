package com.minecraft.plugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyBukkitPlugin extends JavaPlugin implements Listener {
    private final List<String> list = Arrays.asList("ARMORER.yml", "BUTCHER.yml", "FARMER.yml","CARTOGRAPHER.yml","CLERIC.yml","FISHERMAN.yml","FLETCHER.yml","LEATHERWORKER.yml","LIBRARIAN.yml","MASON.yml","TOOLSMITH.yml","WEAPONSMITH.yml","SHEPHERD.yml");


    public void onEnable() {
        saveDefaultConfig();
        fileProfession();

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

    public void fileProfession(){
        final File dataFolder = this.getDataFolder();
        final File files = new File(dataFolder, "Profession");
        if (!files.exists()) {
            files.mkdirs();
        }
        for(String fileName:list) {
            final File file = new File(files, fileName);
            if (!file.exists()) {
                InputStream inputStream = this.getResource("Profession/" + fileName);
                try {
                    Files.copy(inputStream, file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
