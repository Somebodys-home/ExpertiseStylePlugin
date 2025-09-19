package io.github.Gabriel.expertiseStylePlugin.AbilitySystem;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class AbilityItemTemplate {
    private static NamespacedKey abilityKey;
    private static NamespacedKey cooldownKey;
    private static NamespacedKey toggleKey;
    private static NamespacedKey originalItemKey;
    private static NamespacedKey energyKey;

    public AbilityItemTemplate(ExpertiseStylePlugin expertiseStylePlugin) {
        abilityKey = new NamespacedKey(expertiseStylePlugin, "ability");
        cooldownKey = new NamespacedKey(expertiseStylePlugin, "cooldown");
        toggleKey = new NamespacedKey(expertiseStylePlugin, "toggle");
        originalItemKey = new NamespacedKey(expertiseStylePlugin, "originalItem");
        energyKey = new NamespacedKey(expertiseStylePlugin, "energy");
    }

    public static ItemStack emptyStyleAbilityItem() {
        ItemStack style = new ItemStack(Material.LIGHT_BLUE_DYE);
        ItemMeta meta = style.getItemMeta();
        List<String> lore = new ArrayList<>();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(abilityKey, PersistentDataType.INTEGER, 1);
        meta.setDisplayName(ChatColor.AQUA + "Empty Style Ability");
        lore.add(ChatColor.GRAY + "An empty ability slot. Dunno why you'd put nothing here.");
        meta.setLore(lore);
        style.setItemMeta(meta);

        return style;
    }
    
    public static ItemStack cooldownItem() {
        ItemStack cooldown = new ItemStack(Material.GRAY_DYE);
        ItemMeta meta = cooldown.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(abilityKey, PersistentDataType.INTEGER, 1);
        meta.setDisplayName(ChatColor.GRAY + "This ability is on cooldown!");
        cooldown.setItemMeta(meta);

        return cooldown;
    }

    public static boolean isAnAbility(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

            return pdc.has(abilityKey);
        }

        return false;
    }

    public static boolean isToggleable(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

            return pdc.has(toggleKey);
        }

        return false;
    }

    public static boolean getToggleState(ItemStack item) {
        if (isToggleable(item)) return item.getItemMeta().getPersistentDataContainer().get(toggleKey, PersistentDataType.BOOLEAN);

        return false;
    }

    public static void toggleAbility(ItemStack item, boolean onOff) {
        if (item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();

            if (pdc.has(toggleKey)) {
                pdc.set(toggleKey, PersistentDataType.BOOLEAN, onOff);

                if (onOff) { // turning it on
                    item.setType(Material.LIME_DYE);
                } else { // turning it off
                    Material original = getOriginalItemMaterial(item);
                    if (original != null) {
                        item.setType(original);
                    }
                }

                item.setItemMeta(meta);
            }
        }
    }

    public static int getCooldown(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

            if (pdc.has(cooldownKey)) {
                return pdc.get(cooldownKey, PersistentDataType.INTEGER);
            }
        }

        return -1;
    }

    public static int getRequiredEnergy(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

            if (pdc.has(energyKey)) {
                return pdc.get(energyKey, PersistentDataType.INTEGER);
            }
        }

        return -1;
    }

    public static Material getOriginalItemMaterial(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

        if (pdc.has(originalItemKey)) {
            String materialName = pdc.get(originalItemKey, PersistentDataType.STRING);
            if (materialName != null) {
                Material material = Material.matchMaterial(materialName);

                return material;
            }
        }

        return null;
    }

    public static NamespacedKey getAbilityKey() {
        return abilityKey;
    }

    public static NamespacedKey getCooldownKey() {
        return cooldownKey;
    }

    public static NamespacedKey getToggleKey() {
        return toggleKey;
    }

    public static NamespacedKey getOriginalItemKey() {
        return originalItemKey;
    }

    public static NamespacedKey getEnergyKey() {
        return energyKey;
    }
}