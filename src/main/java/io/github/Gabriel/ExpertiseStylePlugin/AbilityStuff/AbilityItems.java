package io.github.Gabriel.expertiseStylePlugin.AbilityStuff;

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

public class AbilityItems {
    protected ExpertiseStylePlugin expertiseStylePlugin;
    protected static NamespacedKey immovableKey;
    protected static NamespacedKey cooldownKey;

    public AbilityItems(ExpertiseStylePlugin plugin){
        expertiseStylePlugin = plugin;
        immovableKey = new NamespacedKey(plugin, "immovable");
        cooldownKey = new NamespacedKey(plugin, "cooldown");
    }

    public static ItemStack abilityCooldownItem() {
        ItemStack cooldown = new ItemStack(Material.GRAY_DYE);
        ItemMeta meta = cooldown.getItemMeta();
        assert meta != null;
        List<String> lore = new ArrayList<>();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(immovableKey, PersistentDataType.INTEGER, 1);
        meta.setDisplayName(ChatColor.GRAY + "Ability On Cooldown!");
        lore.add(ChatColor.GRAY + "This ability is on cooldown!");
        meta.setLore(lore);
        cooldown.setItemMeta(meta);

        return cooldown;
    }

    public static boolean isImmovable(ItemStack item) {
        if (item != null &&  item.getItemMeta() != null) {
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

            return pdc.has(immovableKey, PersistentDataType.INTEGER);
        }
        return false;
    }

    public static int getCooldown(ItemStack item) {
        if (item != null &&  item.getItemMeta() != null) {
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

            if (pdc.has(cooldownKey, PersistentDataType.INTEGER)) {
                return pdc.get(cooldownKey, PersistentDataType.INTEGER);
            }
        }
        return -1;
    }
}