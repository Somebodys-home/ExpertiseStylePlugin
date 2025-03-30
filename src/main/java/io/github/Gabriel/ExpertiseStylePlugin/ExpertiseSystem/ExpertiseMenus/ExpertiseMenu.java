package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier.SoldierMenu;
import io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleAbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.Gabriel.menuSystem.Menu;
import io.github.Gabriel.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ExpertiseMenu extends Menu {
    PlayerMenuUtility playerMenuUtility;

    public ExpertiseMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&6&lCHOOSE YOUR EXPERTISE!!!");
    }

    @Override
    public int getSlots() {
        return 9 * 5;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item != null) {
            if (item.equals(ExpertiseMenuItems.soldier())) {
                new SoldierMenu(playerMenuUtility).
            } else if (item.equals(ExpertiseMenuItems.resetAbilities())) {
                player.getInventory().setItem(0, StyleAbilityItemTemplate.emptyStyleAbilityItem());
                player.getInventory().setItem(1, StyleAbilityItemTemplate.emptyStyleAbilityItem());
                player.getInventory().setItem(2, ExpertiseItemTemplate.emptyExpertiseAbilityItem());
                player.getInventory().setItem(3, ExpertiseItemTemplate.emptyExpertiseAbilityItem());
            } else {
                player.sendMessage("nothing works other than swordsman, sorry.");
            }
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(10, ExpertiseMenuItems.soldier());
        inventory.setItem(11, ExpertiseMenuItems.ninja());
        inventory.setItem(12, ExpertiseMenuItems.marauder());
        inventory.setItem(16, ExpertiseMenuItems.shieldHero());
        inventory.setItem(14, ExpertiseMenuItems.cavalier());
        inventory.setItem(15, ExpertiseMenuItems.martialArtist());
        inventory.setItem(22, ExpertiseMenuItems.archer());
        inventory.setItem(29, ExpertiseMenuItems.sorcerer());
        inventory.setItem(30, ExpertiseMenuItems.druid());
        inventory.setItem(32, ExpertiseMenuItems.hallowed());
        inventory.setItem(33, ExpertiseMenuItems.annulled());
        inventory.setItem(44, ExpertiseMenuItems.resetAbilities());
    }
}
