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
    private static NamespacedKey immovableKey;
    private static NamespacedKey cooldownKey;
    private static NamespacedKey toggleKey;
    private static NamespacedKey originalItemKey;

    public AbilityItemTemplate(ExpertiseStylePlugin expertiseStylePlugin) {
        immovableKey = new NamespacedKey(expertiseStylePlugin, "immovable");
        cooldownKey = new NamespacedKey(expertiseStylePlugin, "cooldown");
        toggleKey = new NamespacedKey(expertiseStylePlugin, "toggle");
        originalItemKey = new NamespacedKey(expertiseStylePlugin, "originalItem");
    }

    public static ItemStack emptyExpertiseAbilityItem() {
        ItemStack expertise = new ItemStack(Material.MAGENTA_DYE);
        ItemMeta meta = expertise.getItemMeta();
        List<String> lore = new ArrayList<>();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(immovableKey, PersistentDataType.INTEGER, 1);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Empty Expertise Ability");
        lore.add(ChatColor.GRAY + "An empty ability slot. Dunno why you'd put nothing here.");
        meta.setLore(lore);
        expertise.setItemMeta(meta);

        return expertise;
    }

    public static ItemStack emptyStyleAbilityItem() {
        ItemStack style = new ItemStack(Material.LIGHT_BLUE_DYE);
        ItemMeta meta = style.getItemMeta();
        List<String> lore = new ArrayList<>();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(immovableKey, PersistentDataType.INTEGER, 1);
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

        pdc.set(immovableKey, PersistentDataType.INTEGER, 1);
        meta.setDisplayName(ChatColor.GRAY + "This ability is on cooldown!");
        cooldown.setItemMeta(meta);

        return cooldown;
    }

    public static boolean isImmovable(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

            return pdc.has(immovableKey);
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

    public static void setOriginalItemKey(ItemStack item) {
        item.getItemMeta().getPersistentDataContainer().set(originalItemKey, PersistentDataType.STRING, item.getType().toString());
    }

    public static NamespacedKey getImmovableKey() {
        return immovableKey;
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
}