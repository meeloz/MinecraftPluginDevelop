package com.minecraft.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class Recipe{
    CustomVillagerTrade instance = CustomVillagerTrade.getInstance();

    private Villager villager;
    private String Recipe;
    private String level;
    private BasicFile.FILETYPE file;
    private int exp;

    public Recipe(Villager villager){
        this.villager = villager;
        this.file = instance.basic.getType(villager.getProfession().toString());
        this.level = String.format("Level_%d", villager.getVillagerLevel());
        this.Recipe = String.format("%s.%s", level,file.getRandomSection(level));
        this.exp = file.getInt(String.format("%s.Exp",Recipe));
    }

    public ArrayList<ItemStack> getInfo() {
        ArrayList<ItemStack> info = new ArrayList<>();
        List<String> section = file.getStringList(String.format("%s.Info",Recipe));
        for (String key : section) {
            int cutindex = key.indexOf(",");
            String item = key.substring(0, cutindex);
            String amount = key.substring(cutindex + 1);
            try {
                info.add(new ItemStack(Material.getMaterial(item), Integer.parseInt(amount)));
            }catch (IllegalArgumentException e){
                Bukkit.getLogger().info(ChatColor.RED + "[CustomVillagerTrade] ERROR! Please check format of setting file.");
            }
        }
        return info;
    }

    public MerchantRecipe getMerchant(){
        MerchantRecipe Merchant = new MerchantRecipe(getInfo().get(0),0,1,true,exp,(float)1.0);
        for(int i=1;i < getInfo().size();i++){
            Merchant.addIngredient(getInfo().get(i));
        }
        return Merchant;
    }
}
