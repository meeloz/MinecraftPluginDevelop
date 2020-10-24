package com.github.skp81.tkigui.data;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class ShowcaseData {
    private UUID host;
    private Location location;
    private List<ItemStack> items;

    public ShowcaseData(UUID host, Location location, List<ItemStack> items){
        this.host = host;
        this.location = location;
        this.items = items;
    }

    public UUID getHost() {
        return host;
    }

    public Location getLocation() {
        return location;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
