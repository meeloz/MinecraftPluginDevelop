package com.github.skp81.tkigui;

import com.github.skp81.tkigui.listener.BlockClickHandler;
import com.github.skp81.tkigui.listener.CommandHandler;
import com.github.skp81.tkigui.listener.InventoryEventHandler;
import com.github.skp81.tkigui.listener.ItemBreakHandler;
import com.github.skp81.tkigui.manager.DataManager;
import com.github.skp81.tkigui.manager.GUIManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class TkiguiPlugin extends JavaPlugin {
    private static TkiguiPlugin instance;
    private GUIManager guiManager;
    private DataManager dataManager;

    public static TkiguiPlugin getInstance(){
        return instance;
    }

    public GUIManager getGUIManager(){
        return guiManager;
    }

    public DataManager getDataManager(){
        return dataManager;
    }

    @Override
    public void onEnable() {
        instance = this;

        File file = new File(getDataFolder(),"config.yml");
        File dir = getDataFolder();
        if(!dir.exists())
            dir.mkdir();
        if(!file.exists()){
            saveResource("config.yml",true);
        }else{
            reloadConfig();
        }

        try {
            dataManager = new DataManager();
            guiManager = new GUIManager();
            dataManager.loadFile();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        Bukkit.getPluginManager().registerEvents(new BlockClickHandler(),this);
        Bukkit.getPluginManager().registerEvents(new InventoryEventHandler(),this);
        Bukkit.getPluginManager().registerEvents(new ItemBreakHandler(),this);
        this.getCommand("tki").setExecutor(new CommandHandler());
    }

    @Override
    public void onDisable() {
    }

}
