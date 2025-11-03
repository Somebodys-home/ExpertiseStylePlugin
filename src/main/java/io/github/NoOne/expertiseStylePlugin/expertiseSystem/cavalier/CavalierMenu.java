package io.github.NoOne.expertiseStylePlugin.expertiseSystem.cavalier;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemManager;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.saveAbilitiesSystem.SelectedAbilities;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.expertiseMenus.ExpertiseConfirmMenu;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.expertiseMenus.ExpertiseMenu;
import io.github.NoOne.menuSystem.Menu;
import io.github.NoOne.menuSystem.PlayerMenuUtility;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public class CavalierMenu extends Menu {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private SelectedAbilities selectedAbilities;
    private Skills skills;

    public CavalierMenu(ExpertiseStylePlugin expertiseStylePlugin, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.expertiseStylePlugin = expertiseStylePlugin;
        selectedAbilities = expertiseStylePlugin.getSelectedManager().getPlayerProfile(playerMenuUtility.getOwner().getUniqueId()).getSelectedAbilities();
        skills = expertiseStylePlugin.getSkillSetManager().getSkillSet(playerMenuUtility.getOwner().getUniqueId()).getSkills();
    }

    @Override
    public String getMenuName() {
        return "§1§lCavalier Abilities";
    }

    @Override
    public int getSlots() {
        return 9 * 4;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        ItemStack selected = event.getCurrentItem();
        assert selected != null;

        if (event.getSlot() == 35) {
            new ExpertiseMenu(expertiseStylePlugin, playerMenuUtility).open();
        } else {
            if (Arrays.stream(selectedAbilities.getSelectedAbilitiesArray()).anyMatch(element -> element.equals(Objects.requireNonNull(selected.getItemMeta()).getDisplayName()))) {
                playerMenuUtility.getOwner().sendMessage("§c⚠ §nYou already have this ability selected!§r§c ⚠");
                playerMenuUtility.getOwner().playSound(playerMenuUtility.getOwner(), Sound.BLOCK_NOTE_BLOCK_BASS, 2f, .5f);
                return;
            }
            if (selected.getItemMeta().getPersistentDataContainer().has(AbilityItemManager.getUnusableKey())) {
                playerMenuUtility.getOwner().sendMessage("§c⚠ §nYou are too inexperienced for this ability!§r§c ⚠");
                playerMenuUtility.getOwner().playSound(playerMenuUtility.getOwner(), Sound.BLOCK_NOTE_BLOCK_BASS, 2f, .5f);
                return;
            }

            new ExpertiseConfirmMenu(expertiseStylePlugin, playerMenuUtility, selected, this).open();
        }
    }

    @Override
    public void handlePlayerMenu(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(10, CavalierAbilityItems.seismicSlam(skills));

        // Backout button
        ItemStack nvm = new ItemStack(Material.BARRIER);
        ItemMeta meta = nvm.getItemMeta();
        assert meta != null;

        meta.setDisplayName("§4§l<= Go back");
        nvm.setItemMeta(meta);

        inventory.setItem(35, nvm);
    }
}
