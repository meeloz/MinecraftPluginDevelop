package com.minecraft.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Villager;
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

public class CustomVillagerTrade extends JavaPlugin implements Listener {
    private static CustomVillagerTrade instance;
    private final List<String> list = Arrays.asList("ARMORER", "BUTCHER", "FARMER","CARTOGRAPHER","CLERIC","FISHERMAN","FLETCHER","LEATHERWORKER","LIBRARIAN","MASON","TOOLSMITH","WEAPONSMITH","SHEPHERD");
    public ArrayList<VillagerProfession> config = new ArrayList<>();

    public static CustomVillagerTrade getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        try {
            fileProfession();
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
                reloadConfig();
                config.clear();
                try {
                    fileProfession();
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

    public void fileProfession() throws IOException, InvalidConfigurationException {
        final File dataFolder = this.getDataFolder();
        final File files = new File(dataFolder, "Profession");
        if (!files.exists()) {
            files.mkdirs();
        } else {
            for(String fileName : list) {
                config.add(new VillagerProfession(fileName));
            }
        }
        for(int i = 0;i < config.size();i++){
            config.get(i).loadFile();
        }
    }

    public VillagerProfession getConfig(String fileName){
        for(VillagerProfession VillagerProfession:config){
            if(VillagerProfession.getFileName() == fileName){
                return VillagerProfession;
            }
        }
        return null;
    }
}
