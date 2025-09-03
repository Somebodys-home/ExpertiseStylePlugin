package io.github.Gabriel.expertiseStylePlugin.AbilitySystem;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class AbilityItemTemplate {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static NamespacedKey immovableKey;
    private static NamespacedKey cooldownKey;

    public AbilityItemTemplate(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        immovableKey = new NamespacedKey(expertiseStylePlugin, "immovable");
        cooldownKey = new NamespacedKey(expertiseStylePlugin, "cooldown");
    }

    public static ItemStack abilityCooldownItem() {
        ItemStack cooldown = new ItemStack(Material.GRAY_DYE);
        ItemMeta meta = cooldown.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(immovableKey, PersistentDataType.INTEGER, 1);
        meta.setDisplayName(ChatColor.GRAY + "This ability is on cooldown!");
        cooldown.setItemMeta(meta);

        return cooldown;
    }

    public static boolean isImmovable(ItemStack item) {
        if (item != null && item.getItemMeta() != null) {
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

            return pdc.has(immovableKey, PersistentDataType.INTEGER);
        }
        return false;
    }

    public static int getCooldown(ItemStack item) {
        if (item != null && item.getItemMeta() != null) {
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

            if (pdc.has(cooldownKey, PersistentDataType.INTEGER)) {
                return pdc.get(cooldownKey, PersistentDataType.INTEGER);
            }
        }
        return -1;
    }

    public static NamespacedKey getImmovableKey() {
        return immovableKey;
    }

    public static NamespacedKey getCooldownKey() {
        return cooldownKey;
    }

}