package com.minecraft.plugin;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.*;

public class BasicFile {

    public List<FILETYPE> types = new ArrayList<>();

    public void load() throws IOException, InvalidConfigurationException {
        types.clear();
        for (FILETYPE type : FILETYPE.values()) {
            type.load();
            types.add(type);
        }
    }

    public FILETYPE getType(String work){
        for(BasicFile.FILETYPE type:types){
            if(type.getType().equals(work)){
                return type;
            }
        }
        return null;
    }

    public enum FILETYPE {
        ARMORER("ARMORER"), BUTCHER("BUTCHER"), CARTOGRAPHER("CARTOGRAPHER"), CLERIC("CLERIC"), FARMER("FARMER"), FISHERMAN("FISHERMAN"), FLETCHER("FLETCHER"), LEATHERWORKER("LEATHERWORKER"), LIBRARIAN("LIBRARIAN"), MASON("MASON"), SHEPHERD("SHEPHERD"), TOOLSMITH("TOOLSMITH"), WEAPONSMITH("WEAPONSMITH");

        private final String fileName;
        private final String type;
        private final YamlConfiguration config = new YamlConfiguration();
        private final File file;

        FILETYPE(String str) {
            this.type = str;
            this.fileName = str.substring(0,1)+str.substring(1).toLowerCase() + ".yml";
            this.file = new File(CustomVillagerTrade.getInstance().getDataFolder(), "works/"+fileName);
        }

        public String getType() {
            return type;
        }

        public int getInt(String path) {
            return config.getInt(path);
        }

        public List<String> getStringList(String path) {
            if (config.isList(path))
                return config.getStringList(path);
            return new ArrayList<>();
        }

        public String getRandomSection(String path) {
            Object[] Section = config.getConfigurationSection(path).getKeys(false).toArray();
            int rnd = new Random().nextInt(Section.length);
            return Section[rnd].toString();
        }

        private void load() throws IOException, InvalidConfigurationException {
            File dir = new File(CustomVillagerTrade.getInstance().getDataFolder(), "works");
            if(!dir.exists()){
                dir.mkdir();
            }
            if(!file.exists()){
                file.createNewFile();
                config.load(file);
                for (int i = 1; i < 6; i++) {
                    ArrayList<String> ingredient = new ArrayList<>(Arrays.asList("DIAMOND_SWORD,1","DIAMOND,1", "DIAMOND,1"));
                    String level = "Level_" + i;
                    config.set(level + ".Custom.Exp", 10);
                    config.set(level + ".Custom.Info", ingredient);
                }
                config.save(file);
            } else {
                try {
                    config.load(file);
                    final InputStream in = new FileInputStream(file);
                    if (in != null) {
                        config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(in)));
                        config.options().copyDefaults(true);
                        in.close();
                    }
                    config.save(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
