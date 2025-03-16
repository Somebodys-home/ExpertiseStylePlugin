package io.github.Gabriel.expertiseStylePlugin.StyleStuff.StyleMenus;

import io.github.Gabriel.expertiseStylePlugin.MenuSystem.Menu;
import io.github.Gabriel.expertiseStylePlugin.MenuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static org.bukkit.Material.BLACK_TERRACOTTA;
import static org.bukkit.Material.YELLOW_TERRACOTTA;

public class StyleMenu extends Menu {

    public StyleMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&b&lSTYLE ON 'EM!!");
    }

    @Override
    public int getSlots() {
        return 9 * 6;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

    }

    @Override
    public void setMenuItems() {
        ItemStack wip = new ItemStack(Material.OAK_SIGN, 1);
        ItemMeta wipMeta = wip.getItemMeta();
        wipMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eMENU CURRENTLY UNDER CONSTRUCTION"));
        wip.setItemMeta(wipMeta);

        inventory.setItem(18, new ItemStack(YELLOW_TERRACOTTA));
        inventory.setItem(19, new ItemStack(BLACK_TERRACOTTA));
        inventory.setItem(20, new ItemStack(YELLOW_TERRACOTTA));
        inventory.setItem(21, new ItemStack(BLACK_TERRACOTTA));
        inventory.setItem(22, wip);
        inventory.setItem(23, new ItemStack(YELLOW_TERRACOTTA));
        inventory.setItem(24, new ItemStack(BLACK_TERRACOTTA));
        inventory.setItem(25, new ItemStack(YELLOW_TERRACOTTA));
        inventory.setItem(26, new ItemStack(BLACK_TERRACOTTA));

        inventory.setItem(27, new ItemStack(YELLOW_TERRACOTTA));
        inventory.setItem(28, new ItemStack(BLACK_TERRACOTTA));
        inventory.setItem(29, new ItemStack(YELLOW_TERRACOTTA));
        inventory.setItem(30, new ItemStack(BLACK_TERRACOTTA));
        inventory.setItem(31, wip);
        inventory.setItem(32, new ItemStack(YELLOW_TERRACOTTA));
        inventory.setItem(33, new ItemStack(BLACK_TERRACOTTA));
        inventory.setItem(34, new ItemStack(YELLOW_TERRACOTTA));
        inventory.setItem(35, new ItemStack(BLACK_TERRACOTTA));
    }
}
