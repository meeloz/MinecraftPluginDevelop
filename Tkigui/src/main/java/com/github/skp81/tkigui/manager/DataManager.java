package com.github.skp81.tkigui.manager;

import com.github.skp81.tkigui.TkiguiPlugin;
import com.github.skp81.tkigui.data.ShowcaseData;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//将ShowcaseData的内容保存至data.yml
public class DataManager {
    private final TkiguiPlugin instance;
    private final YamlConfiguration config = new YamlConfiguration();
    private final File file;

    public DataManager() throws IOException {
        instance = TkiguiPlugin.getInstance();
        file = new File(instance.getDataFolder(),"data.yml");
        if(!file.exists()){
            file.createNewFile();
        }
    }

    public void writeFile(String key) throws IOException, InvalidConfigurationException {
        config.load(file);
        ShowcaseData data = TkiguiPlugin.getInstance().getGUIManager().getShowcases().get(key);
        config.set(key+".Host",data.getHost().toString());
        config.set(key+".Location",data.getLocation());
        config.set(key+".Items",data.getItems());
        config.save(file);
    }

    public void deleteFile(String key) throws IOException, InvalidConfigurationException {
        config.load(file);
        config.set(key,null);
        config.save(file);
    }

    public void loadFile() throws IOException, InvalidConfigurationException {
        config.load(file);
        HashMap<String, ShowcaseData> showcases = instance.getGUIManager().getShowcases();
        Set<String> keys = config.getKeys(false);
        for(String key:keys){
            UUID uuid = UUID.fromString(config.getString(key+".Host"));
            Location location = config.getLocation(key+".Location");
            List<ItemStack> items = (List<ItemStack>) config.getList(key+".Items");
            ShowcaseData data = new ShowcaseData(uuid,location,items);
            showcases.put(key,data);
        }

    }

}
