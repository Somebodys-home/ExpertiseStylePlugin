package io.github.Gabriel.expertiseStylePlugin.MenuSystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Material.BLACK_TERRACOTTA;
import static org.bukkit.Material.YELLOW_TERRACOTTA;


public abstract class Menu implements InventoryHolder {
    protected Inventory inventory;
    protected PlayerMenuUtility playerMenuUtility;

    public Menu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent event);

    public abstract void setMenuItems();

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        this.setMenuItems();
        playerMenuUtility.getOwner().openInventory(inventory);
    }

    public void underConstruction() {
        int rows = getSlots() / 9;
        ItemStack wip = new ItemStack(Material.OAK_SIGN, 1);
        ItemMeta wipMeta = wip.getItemMeta();
        wipMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e!! MENU CURRENTLY UNDER CONSTRUCTION !!"));
        wip.setItemMeta(wipMeta);

        if (rows % 2 == 0) { // no simple middle >:T
            int topMiddleRow = (rows / 2) - 1;
            int bottomMiddleRow = topMiddleRow + 1;

            inventory.setItem((topMiddleRow * 9), new ItemStack(BLACK_TERRACOTTA));
            inventory.setItem((topMiddleRow * 9) + 1, new ItemStack(YELLOW_TERRACOTTA));
            inventory.setItem((topMiddleRow * 9) + 2, new ItemStack(YELLOW_TERRACOTTA));
            inventory.setItem((topMiddleRow * 9) + 3, new ItemStack(BLACK_TERRACOTTA));
            inventory.setItem((topMiddleRow * 9) + 4, wip);
            inventory.setItem((topMiddleRow * 9) + 5, new ItemStack(YELLOW_TERRACOTTA));
            inventory.setItem((topMiddleRow * 9) + 6, new ItemStack(YELLOW_TERRACOTTA));
            inventory.setItem((topMiddleRow * 9) + 7, new ItemStack(BLACK_TERRACOTTA));
            inventory.setItem((topMiddleRow * 9) + 8, new ItemStack(BLACK_TERRACOTTA));
            inventory.setItem((bottomMiddleRow * 9), new ItemStack(YELLOW_TERRACOTTA));
            inventory.setItem((bottomMiddleRow * 9) + 1, new ItemStack(YELLOW_TERRACOTTA));
            inventory.setItem((bottomMiddleRow * 9) + 2, new ItemStack(BLACK_TERRACOTTA));
            inventory.setItem((bottomMiddleRow * 9) + 3, new ItemStack(BLACK_TERRACOTTA));
            inventory.setItem((bottomMiddleRow * 9) + 4, wip);
            inventory.setItem((bottomMiddleRow * 9) + 5, new ItemStack(YELLOW_TERRACOTTA));
            inventory.setItem((bottomMiddleRow * 9) + 6, new ItemStack(BLACK_TERRACOTTA));
            inventory.setItem((bottomMiddleRow * 9) + 7, new ItemStack(BLACK_TERRACOTTA));
            inventory.setItem((bottomMiddleRow * 9) + 8, new ItemStack(YELLOW_TERRACOTTA));
        } else { // yes simple middle :D
            int middleRow = rows / 2;

            inventory.setItem((middleRow * 9), new ItemStack(BLACK_TERRACOTTA));
            inventory.setItem((middleRow * 9) + 1, new ItemStack(YELLOW_TERRACOTTA));
            inventory.setItem((middleRow * 9) + 2, new ItemStack(YELLOW_TERRACOTTA));
            inventory.setItem((middleRow * 9) + 3, new ItemStack(BLACK_TERRACOTTA));
            inventory.setItem((middleRow * 9) + 4, wip);
            inventory.setItem((middleRow * 9) + 5, new ItemStack(YELLOW_TERRACOTTA));
            inventory.setItem((middleRow * 9) + 6, new ItemStack(YELLOW_TERRACOTTA));
            inventory.setItem((middleRow * 9) + 7, new ItemStack(BLACK_TERRACOTTA));
            inventory.setItem((middleRow * 9) + 8, new ItemStack(BLACK_TERRACOTTA));
        }
    }
}
