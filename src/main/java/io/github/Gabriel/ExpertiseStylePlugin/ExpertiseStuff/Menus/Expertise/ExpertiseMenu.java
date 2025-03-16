package io.github.Gabriel.expertiseStylePlugin.ExpertiseStuff.Menus.Expertise;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseStuff.Menus.Swordsman.SwordsmanMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.MenuSystem.Menu;
import io.github.Gabriel.expertiseStylePlugin.MenuSystem.PlayerMenuUtility;
import io.github.Gabriel.expertiseStylePlugin.StyleStuff.StyleMenus.AbilityItems.StyleAbilityItems;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ExpertiseMenu extends Menu {
    public ExpertiseMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&6&lCHOOSE YOUR EXPERTISE!!!");
    }

    @Override
    public int getSlots() {
        return 9 * 4;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item != null) {
            if (item.equals(ExpertiseMenuItems.swordsman())) {
                new SwordsmanMenu(ExpertiseStylePlugin.getPlayerMenuUtility(player)).open();
            } else if (item.equals(ExpertiseMenuItems.resetAbilities())) {
                player.getInventory().setItem(0, StyleAbilityItems.emptyStyleAbilityItem());
                player.getInventory().setItem(1, StyleAbilityItems.emptyStyleAbilityItem());
                player.getInventory().setItem(2, ExpertiseAbilityItems.emptyExpertiseAbilityItem());
                player.getInventory().setItem(3, ExpertiseAbilityItems.emptyExpertiseAbilityItem());
            } else {
                player.sendMessage("nothing works other than swordsman, sorry.");
            }
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(10, ExpertiseMenuItems.swordsman());
        inventory.setItem(11, ExpertiseMenuItems.ninja());
        inventory.setItem(12, ExpertiseMenuItems.marauder());
        inventory.setItem(13, ExpertiseMenuItems.jouster());
        inventory.setItem(14, ExpertiseMenuItems.shieldHero());
        inventory.setItem(15, ExpertiseMenuItems.blunt());
        inventory.setItem(16, ExpertiseMenuItems.martialArtist());
        inventory.setItem(20, ExpertiseMenuItems.archer());
        inventory.setItem(21, ExpertiseMenuItems.sorcerer());
        inventory.setItem(22, ExpertiseMenuItems.druid());
        inventory.setItem(23, ExpertiseMenuItems.hallowed());
        inventory.setItem(24, ExpertiseMenuItems.annulled());
        inventory.setItem(27, ExpertiseMenuItems.resetAbilities());
    }
}
