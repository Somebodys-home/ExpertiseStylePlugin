package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedAbilities;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Annulled.AnnulledMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Assassin.AssassinMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Cavalier.CavalierMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Hallowed.HallowedMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marauder.MarauderMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marksman.MarksmanMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.MartialArtist.MartialArtistMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Primordial.PrimordialMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ShieldHero.ShieldHeroMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier.SoldierMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Sorcerer.SorcererMenu;
import io.github.NoOne.menuSystem.Menu;
import io.github.NoOne.menuSystem.PlayerMenuUtility;
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
            case 14 -> new CavalierMenu(expertiseStylePlugin, playerMenuUtility).open();
            case 15 -> new MartialArtistMenu(expertiseStylePlugin, playerMenuUtility).open();
            case 16 -> new ShieldHeroMenu(expertiseStylePlugin, playerMenuUtility).open();
            case 22 -> new MarksmanMenu(expertiseStylePlugin, playerMenuUtility).open();
            case 29 -> new SorcererMenu(expertiseStylePlugin, playerMenuUtility).open();
            case 30 -> new PrimordialMenu(expertiseStylePlugin, playerMenuUtility).open();
            case 32 -> new HallowedMenu(expertiseStylePlugin, playerMenuUtility).open();
            case 33 -> new AnnulledMenu(expertiseStylePlugin, playerMenuUtility).open();
            case 44 -> {
                CooldownManager.resetAllCooldowns(player);
                player.getInventory().setItem(0, AbilityItemTemplate.emptyStyleAbilityItem());
                player.getInventory().setItem(1, AbilityItemTemplate.emptyStyleAbilityItem());
                player.getInventory().setItem(2, ExpertiseAbilityItemTemplate.emptyExpertiseAbilityItem());
                player.getInventory().setItem(3, ExpertiseAbilityItemTemplate.emptyExpertiseAbilityItem());
                selectedAbilities.resetSelectedAbilities();
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
        inventory.setItem(14, ExpertiseMenuItems.cavalier());
        inventory.setItem(15, ExpertiseMenuItems.martialArtist());
        inventory.setItem(16, ExpertiseMenuItems.shieldHero());
        inventory.setItem(22, ExpertiseMenuItems.marksman());
        inventory.setItem(29, ExpertiseMenuItems.sorcerer());
        inventory.setItem(30, ExpertiseMenuItems.primordial());
        inventory.setItem(32, ExpertiseMenuItems.hallowed());
        inventory.setItem(33, ExpertiseMenuItems.annulled());
        inventory.setItem(44, ExpertiseMenuItems.resetAbilities());
    }
}
