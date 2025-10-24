package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Sorcerer;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedAbilities;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus.ExpertiseConfirmMenu;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus.ExpertiseMenu;
import io.github.NoOne.menuSystem.Menu;
import io.github.NoOne.menuSystem.PlayerMenuUtility;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public class SorcererMenu extends Menu {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private SelectedAbilities selectedAbilities;
    private Skills skills;

    public SorcererMenu(ExpertiseStylePlugin expertiseStylePlugin, PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.expertiseStylePlugin = expertiseStylePlugin;
        selectedAbilities = expertiseStylePlugin.getSelectedManager().getPlayerProfile(playerMenuUtility.getOwner().getUniqueId()).getSelectedAbilities();
        skills = expertiseStylePlugin.getSkillSetManager().getSkillSet(playerMenuUtility.getOwner().getUniqueId()).getSkills();
    }

    @Override
    public String getMenuName() {
        return "§6§lSorcerer Abilities";
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
            if (selected.getItemMeta().getPersistentDataContainer().has(AbilityItemTemplate.getUnusableKey())) {
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
        inventory.setItem(10, SorcererAbilityItems.magicMissileEX(skills));

        // Backout button
        ItemStack nvm = new ItemStack(Material.BARRIER);
        ItemMeta meta = nvm.getItemMeta();
        assert meta != null;

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&l<= Go back"));
        nvm.setItemMeta(meta);

        inventory.setItem(35, nvm);
    }
}
