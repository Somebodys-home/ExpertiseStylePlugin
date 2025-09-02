package io.github.Gabriel.expertiseStylePlugin.StyleSystem;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.ArrayList;
import java.util.List;

public class StyleAbilityItemTemplate extends AbilityItemTemplate {
    private static NamespacedKey styleKey;

    public StyleAbilityItemTemplate(ExpertiseStylePlugin expertiseStylePlugin) {
        super(expertiseStylePlugin);
        styleKey = new NamespacedKey(expertiseStylePlugin, "style");
    }

    public static ItemStack emptyStyleAbilityItem() {
        ItemStack style = new ItemStack(Material.LIGHT_BLUE_DYE);
        ItemMeta meta = style.getItemMeta();
        assert meta != null;
        List<String> lore = new ArrayList<>();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(AbilityItemTemplate.getImmovableKey(), PersistentDataType.INTEGER, 1);
        meta.setDisplayName(ChatColor.AQUA + "Empty Style Ability");
        lore.add(ChatColor.GRAY + "An empty ability slot. Dunno why you'd put nothing here.");
        meta.setLore(lore);
        style.setItemMeta(meta);

        return style;
    }
}
