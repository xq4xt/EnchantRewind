package com.blockanomics.psychnodelic;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class EnchantRewind extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent e) {
        int cost = e.getExpLevelCost();
        int button = e.whichButton() + 1;
        int adjustedCost = cost - button;
        int playerLevel = e.getEnchanter().getLevel();

        if (adjustedCost > 0 && adjustedCost <= playerLevel) {
            int newLevel = playerLevel - adjustedCost;
            e.getEnchanter().setLevel(newLevel);
            e.setExpLevelCost(1);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getType() == InventoryType.ENCHANTING) {
            ItemStack lapis = event.getInventory().getItem(1);
            if (lapis != null && lapis.getType() == Material.LAPIS_LAZULI) {
                event.getPlayer().getInventory().addItem(lapis);
                event.getInventory().setItem(1, null);
            }
        }
    }
}