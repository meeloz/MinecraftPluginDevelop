package com.minecraft.plugin;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class VillagerProfession {
    private final CustomVillagerTrade instance = CustomVillagerTrade.getInstance();

    private final String filesName;
    private final File files;
    private final FileConfiguration content;

    private ArrayList<ArrayList<String>> recipe = new ArrayList<>();

    public VillagerProfession(String fileName) throws IOException, InvalidConfigurationException {
        this.filesName = fileName;
        this.files = new File(instance.getDataFolder(),"Profession/"+fileName+".yml");
        this.content = new YamlConfiguration();
        createFile();
    }

    public void createFile() throws IOException, InvalidConfigurationException {
        if(!files.exists()){
            files.createNewFile();
            content.load(files);
            for (int i = 1; i < 6; i++) {
                String level = "Level_" + i;
                ArrayList<String> ingredient = new ArrayList<>(Arrays.asList("DIAMOND,1", "DIAMOND,1"));
                content.set(level + ".Custom.Exp", 10);
                content.set(level + ".Custom.Result", "WHEAT,1");
                content.set(level + ".Custom.Require", ingredient);
            }
            content.save(files);
        }
    }

    public void loadFile() throws IOException, InvalidConfigurationException {
        content.load(files);
        for(int i=1;i < 6;i++) {
            ArrayList<String> level = new ArrayList<>();
            ConfigurationSection section = content.getConfigurationSection("Level_"+i);
            for (String key : section.getKeys(false)) {
                level.add(key);
            }
            recipe.add(level);
        }
    }

    public ArrayList<ArrayList<String>> getRecipe(){
        return recipe;
    }

    public String getFileName(){
        return filesName;
    }

    public FileConfiguration getFile(){
        return content;
    }
}
