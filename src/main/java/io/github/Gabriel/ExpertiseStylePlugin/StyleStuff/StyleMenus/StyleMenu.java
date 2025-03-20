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
        underConstruction();
    }
}
