package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedAbilities;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Assassin.AssassinMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marauder.MarauderMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier.SoldierMenu;
import io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleAbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.NoOne.menuSystem.Menu;
import io.github.NoOne.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ExpertiseMenu extends Menu {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private SelectedAbilities selectedAbilities;

    public ExpertiseMenu(ExpertiseStylePlugin expertiseStylePlugin, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.expertiseStylePlugin = expertiseStylePlugin;
        selectedAbilities = expertiseStylePlugin.getSelectedManager().getPlayerProfile(playerMenuUtility.getOwner().getUniqueId()).getSelectedAbilities();
    }

    @Override
    public String getMenuName() {
        return "§6§lCHOOSE YOUR EXPERTISE!!!";
    }

    @Override
    public int getSlots() {
        return 9 * 5;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        switch (slot) {
            case 10 -> new SoldierMenu(expertiseStylePlugin, playerMenuUtility).open();
            case 11 -> new AssassinMenu(expertiseStylePlugin, playerMenuUtility).open();
            case 12 -> new MarauderMenu(expertiseStylePlugin, playerMenuUtility).open();
            case 44 -> {
                player.getInventory().setItem(0, StyleAbilityItemTemplate.emptyStyleAbilityItem());
                player.getInventory().setItem(1, StyleAbilityItemTemplate.emptyStyleAbilityItem());
                player.getInventory().setItem(2, ExpertiseItemTemplate.emptyExpertiseAbilityItem());
                player.getInventory().setItem(3, ExpertiseItemTemplate.emptyExpertiseAbilityItem());
                selectedAbilities.setStyle1(StyleAbilityItemTemplate.emptyStyleAbilityItem().getItemMeta().getDisplayName());
                selectedAbilities.setExpertise1(StyleAbilityItemTemplate.emptyStyleAbilityItem().getItemMeta().getDisplayName());
                selectedAbilities.setExpertise1(ExpertiseItemTemplate.emptyExpertiseAbilityItem().getItemMeta().getDisplayName());
                selectedAbilities.setExpertise1(ExpertiseItemTemplate.emptyExpertiseAbilityItem().getItemMeta().getDisplayName());
            }
        }
    }

    @Override
    public void handlePlayerMenu(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(10, ExpertiseMenuItems.soldier());
        inventory.setItem(11, ExpertiseMenuItems.assassin());
        inventory.setItem(12, ExpertiseMenuItems.marauder());
        inventory.setItem(16, ExpertiseMenuItems.shieldHero());
        inventory.setItem(14, ExpertiseMenuItems.cavalier());
        inventory.setItem(15, ExpertiseMenuItems.martialArtist());
        inventory.setItem(22, ExpertiseMenuItems.marksman());
        inventory.setItem(29, ExpertiseMenuItems.sorcerer());
        inventory.setItem(30, ExpertiseMenuItems.primordial());
        inventory.setItem(32, ExpertiseMenuItems.hallowed());
        inventory.setItem(33, ExpertiseMenuItems.annulled());
        inventory.setItem(44, ExpertiseMenuItems.resetAbilities());
    }
}
