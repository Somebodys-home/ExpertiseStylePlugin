package io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleMenus;


import io.github.Gabriel.menuSystem.Menu;
import io.github.Gabriel.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;

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
