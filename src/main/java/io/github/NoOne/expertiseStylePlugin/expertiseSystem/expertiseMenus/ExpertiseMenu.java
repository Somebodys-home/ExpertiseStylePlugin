package io.github.NoOne.expertiseStylePlugin.expertiseSystem.expertiseMenus;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemTemplate;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.CooldownSystem.CooldownManager;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.SaveAbilitiesSystem.SelectedAbilities;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.annulled.AnnulledMenu;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.assassin.AssassinMenu;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.cavalier.CavalierMenu;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.hallowed.HallowedMenu;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.marauder.MarauderMenu;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.marksman.MarksmanMenu;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.martialArtist.MartialArtistMenu;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.primordial.PrimordialMenu;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.shieldHero.ShieldHeroMenu;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.soldier.SoldierMenu;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.sorcerer.SorcererMenu;
import io.github.NoOne.menuSystem.Menu;
import io.github.NoOne.menuSystem.PlayerMenuUtility;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ExpertiseMenu extends Menu {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private Player player;
    private SelectedAbilities selectedAbilities;
    private Skills skills;
    private ExpertiseMenuItems expertiseMenuItems;

    public ExpertiseMenu(ExpertiseStylePlugin expertiseStylePlugin, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.expertiseStylePlugin = expertiseStylePlugin;
        player = playerMenuUtility.getOwner();
        selectedAbilities = expertiseStylePlugin.getSelectedManager().getPlayerProfile(player.getUniqueId()).getSelectedAbilities();
        skills = expertiseStylePlugin.getSkillSetManager().getSkillSet(player.getUniqueId()).getSkills();
        expertiseMenuItems = new ExpertiseMenuItems(skills);
    }

    @Override
    public String getMenuName() {
        return "§6§lCHOOSE YOUR EXPERTISE!";
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
                player.getInventory().setItem(1, ExpertiseAbilityItemTemplate.emptyExpertiseAbilityItem());
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
        inventory.setItem(10, expertiseMenuItems.soldier());
        inventory.setItem(11, expertiseMenuItems.assassin());
        inventory.setItem(12, expertiseMenuItems.marauder());
        inventory.setItem(14, expertiseMenuItems.cavalier());
        inventory.setItem(15, expertiseMenuItems.martialArtist());
        inventory.setItem(16, expertiseMenuItems.shieldHero());
        inventory.setItem(22, expertiseMenuItems.marksman());
        inventory.setItem(29, expertiseMenuItems.sorcerer());
        inventory.setItem(30, expertiseMenuItems.primordial());
        inventory.setItem(32, expertiseMenuItems.hallowed());
        inventory.setItem(33, expertiseMenuItems.annulled());
        inventory.setItem(44, expertiseMenuItems.resetAbilities());
    }
}
