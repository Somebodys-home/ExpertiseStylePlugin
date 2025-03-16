package io.github.Gabriel.expertiseStylePlugin.StyleStuff.StyleMenus.AbilityItems;

import io.github.Gabriel.expertiseStylePlugin.AbilityStuff.AbilityItems;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.MenuSystem.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class StyleAbilityItems extends AbilityItems {
    private static NamespacedKey styleKey;

    public StyleAbilityItems(ExpertiseStylePlugin plugin) {
        super(ExpertiseStylePlugin.getInstance());
        styleKey = new NamespacedKey(expertiseStylePlugin, "style");
    }

    public static ItemStack emptyStyleAbilityItem() {
        ItemStack style = new ItemStack(Material.LIGHT_BLUE_DYE);
        ItemMeta meta = style.getItemMeta();
        assert meta != null;
        List<String> lore = new ArrayList<>();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(immovableKey, PersistentDataType.INTEGER, 1);
        meta.setDisplayName(ChatColor.AQUA + "Empty Style Ability");
        lore.add(ChatColor.GRAY + "An empty ability slot. Dunno why you'd put nothing here.");
        meta.setLore(lore);
        style.setItemMeta(meta);

        return style;
    }
}
