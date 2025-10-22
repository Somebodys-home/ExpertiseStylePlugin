package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem.SelectedAbilities;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.menuSystem.Menu;
import io.github.NoOne.menuSystem.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;

public class ExpertiseConfirmMenu extends Menu {
    private final ItemStack expertise1;
    private final ItemStack expertise2;
    private final ItemStack expertise3;
    private final ItemStack selected;
    private final Menu previous;
    private SelectedAbilities selectedAbilities;

    public ExpertiseConfirmMenu(ExpertiseStylePlugin expertiseStylePlugin, PlayerMenuUtility playerMenuUtility, ItemStack selected, Menu previous) {
        super(playerMenuUtility);
        this.expertise1 = playerMenuUtility.getOwner().getInventory().getItem(1);
        this.expertise2 = playerMenuUtility.getOwner().getInventory().getItem(2);
        this.expertise3 = playerMenuUtility.getOwner().getInventory().getItem(3);
        this.selected = selected;
        this.previous = previous;
        selectedAbilities = expertiseStylePlugin.getSelectedManager().getPlayerProfile(playerMenuUtility.getOwner().getUniqueId()).getSelectedAbilities();
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', "&6&lYou sure?");
    }

    @Override
    public int getSlots() {
        return 9 * 3;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        String[] playersAbilities = selectedAbilities.getSelectedAbilitiesArray();
        int slot = event.getSlot();

        switch (slot) {
            case 11 -> {
                if (expertise1.isSimilar(AbilityItemTemplate.cooldownItem())) {
                    player.sendMessage("§c⚠ §nWait for this ability to go off cooldown.§r§c ⚠");
                    return;
                }
                if (Arrays.stream(playersAbilities).anyMatch(element -> element.equals(Objects.requireNonNull(selected.getItemMeta()).getDisplayName()))) {
                    player.sendMessage("§c⚠ §nYou already have this ability selected.§r§c ⚠");
                    return;
                }

                player.getInventory().setItem(1, selected);
                selectedAbilities.setExpertise1(selected.getItemMeta().getDisplayName());
            }
            case 13 -> {
                if (expertise2.isSimilar(AbilityItemTemplate.cooldownItem())) {
                    player.sendMessage("§c⚠ §nWait for this ability to go off cooldown.§r§c ⚠");
                    return;
                }
                if (Arrays.stream(playersAbilities).anyMatch(element -> element.equals(Objects.requireNonNull(selected.getItemMeta()).getDisplayName()))) {
                    player.sendMessage("§c⚠ §nYou already have this ability selected.§r§c ⚠");
                    return;
                }

                player.getInventory().setItem(2, selected);
                selectedAbilities.setExpertise2(selected.getItemMeta().getDisplayName());
            }
            case 15 -> {
                if (expertise3.isSimilar(AbilityItemTemplate.cooldownItem())) {
                    player.sendMessage("§c⚠ §nWait for this ability to go off cooldown.§r§c ⚠");
                    return;
                }
                if (Arrays.stream(playersAbilities).anyMatch(element -> element.equals(Objects.requireNonNull(selected.getItemMeta()).getDisplayName()))) {
                    player.sendMessage("§c⚠ §nYou already have this ability selected.§r§c ⚠");
                    return;
                }

                player.getInventory().setItem(3, selected);
                selectedAbilities.setExpertise3(selected.getItemMeta().getDisplayName());
            }
            case 22 -> previous.open();
        }

        previous.open();
    }

    @Override
    public void handlePlayerMenu(InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(4, selected);
        inventory.setItem(11, expertise1);
        inventory.setItem(13, expertise2);
        inventory.setItem(15, expertise3);

        // Backout button
        ItemStack nvm = new ItemStack(Material.RED_DYE);
        ItemMeta meta = nvm.getItemMeta();
        assert meta != null;

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&l<= Go back"));
        nvm.setItemMeta(meta);

        inventory.setItem(22, nvm);
    }
}
