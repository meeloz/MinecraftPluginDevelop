package com.github.skp81.tkigui.gui;

import com.github.skp81.tkigui.manager.GUIManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

//create gui and button
public class GUI {

    private final Inventory menu;
    private final String name;

    public GUI(Block block, GUIManager guiManager){
        this.name = setName(block);
        Inventory menu = CreateInventory();
        this.menu = menu;
        ItemStack button = CreateButton();
        ItemStack buttons = CreateButtons();
        menu.setItem(8,button);
        menu.setItem(7,buttons);
    }

    public Inventory CreateInventory() {
        String title = "§r§b[CustomDisplayGUI]";
        int size = 9;
        return Bukkit.createInventory(null, size, title);
    }


    public ItemStack CreateButton(){
        ItemStack button = new ItemStack(Material.ANVIL,1);
        ItemMeta meta = button.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("§r§9Click to show item!");
        lore.add(ChatColor.BLUE+"§r"+name);
        meta.setLore(lore);
        meta.setDisplayName("§a§lCreate");
        button.setItemMeta(meta);
        return button;
    }

    public ItemStack CreateButtons(){
        ItemStack button = new ItemStack(Material.SHEARS,1);
        ItemMeta meta = button.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add("§r§9Click to delete showcase!");
        meta.setLore(lore);
        meta.setDisplayName("§a§lDelete");
        button.setItemMeta(meta);
        return button;
    }

    public String setName(Block block){
        String world = block.getWorld().getName();
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        String name = String.format("%s,%d,%d,%d",world,x,y,z);
        return name;
    }

    public Inventory getMenu(){
        return menu;
    }

    public List<ItemStack> getItems(){
        List<ItemStack> items = new ArrayList<>();
        for(int i = 0 ; i < 7 ; i++){
            if(menu.getItem(i) != null){
                items.add(menu.getItem(i));
            }
        }
        return items;
    }
}
