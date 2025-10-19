package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLItems.ItemType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.ArrayList;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class ExpertiseAbilityItemTemplate extends AbilityItemTemplate {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static NamespacedKey expertiseKey;

    public ExpertiseAbilityItemTemplate(ExpertiseStylePlugin expertiseStylePlugin) {
        super(expertiseStylePlugin);
        this.expertiseStylePlugin = expertiseStylePlugin;
        expertiseKey = new NamespacedKey(expertiseStylePlugin, "expertise");
    }

    public static ItemStack emptyExpertiseAbilityItem() {
        ItemStack expertise = new ItemStack(Material.MAGENTA_DYE);
        ItemMeta meta = expertise.getItemMeta();
        List<String> lore = new ArrayList<>();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(AbilityItemTemplate.getAbilityKey(), PersistentDataType.INTEGER, 1);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Empty Expertise Ability");
        lore.add(ChatColor.GRAY + "An empty ability slot. Dunno why you'd put nothing here.");
        meta.setLore(lore);
        expertise.setItemMeta(meta);

        return expertise;
    }

    public static ItemStack makeExpertiseAbilityItem(String expertise, String name, String description, boolean toggleable, String targeting, int range, int duration,
                                                     int cooldown, int cost, List<String> damage, List<String> effects, List<ItemType> weapons) {

        ItemStack expertiseItem = new ItemStack(Material.BARRIER);
        ChatColor color = null;
        ItemMeta meta = expertiseItem.getItemMeta();
        List<String> lore = new ArrayList<>();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(expertiseKey, PersistentDataType.INTEGER, 1);
        pdc.set(AbilityItemTemplate.getAbilityKey(), PersistentDataType.INTEGER, 1);
        pdc.set(AbilityItemTemplate.getCooldownKey(), PersistentDataType.INTEGER, cooldown);
        pdc.set(AbilityItemTemplate.getEnergyKey(), PersistentDataType.INTEGER, cost);

        // color and itemstack of ability
        switch (expertise) {
            case "swordsman" -> {
                expertiseItem = new ItemStack(Material.DIAMOND_SWORD);
                color = ChatColor.RED;
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            case "assassin" -> {
                expertiseItem = new ItemStack(Material.BLACK_WOOL);
                color = ChatColor.DARK_GRAY;
            }
            case "marauder" -> {
                expertiseItem = new ItemStack(Material.GOLDEN_AXE);
                color = ChatColor.DARK_RED;
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            case "shield" -> {
                expertiseItem = new ItemStack(Material.SHIELD);
                color = ChatColor.DARK_AQUA;
            }
            case "cavalier" -> {
                expertiseItem = new ItemStack(Material.DIAMOND_SHOVEL);
                color = ChatColor.BLUE;
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            case "martial" -> {
                expertiseItem = new ItemStack(Material.RED_GLAZED_TERRACOTTA);
                color = ChatColor.DARK_RED;
            }
            case "marksman" -> {
                expertiseItem = new ItemStack(Material.BOW);
                color = ChatColor.GREEN;
            }
            case "sorcerer" -> {
                expertiseItem = new ItemStack(Material.BOOK);
                color = ChatColor.GOLD;
            }
            case "primordial" -> {
                expertiseItem = new ItemStack(Material.OAK_SAPLING);
                color = ChatColor.DARK_GREEN;
            }
            case "hallowed" -> {
                expertiseItem = new ItemStack(Material.OXEYE_DAISY);
                color = ChatColor.WHITE;
            }
            case "annulled" -> {
                expertiseItem = new ItemStack(Material.CRYING_OBSIDIAN);
                color = ChatColor.DARK_PURPLE;
            }
        }

        meta.setDisplayName(color + ChatColor.translateAlternateColorCodes('&', "&l" + name));
        lore.add("");
        for (String line : linebreak(description, 33)) lore.add(ChatColor.GRAY + line);
        lore.add("");

        // misc stats
        if (toggleable) {
            lore.add(ChatColor.WHITE + "Target: " + ChatColor.BLUE + targeting + ", Toggle");
            pdc.set(AbilityItemTemplate.getToggleKey(), PersistentDataType.BOOLEAN, false);
            pdc.set(AbilityItemTemplate.getOriginalItemKey(), PersistentDataType.STRING, expertiseItem.getType().toString());
        } else {
            lore.add(ChatColor.WHITE + "Target: " + ChatColor.BLUE + targeting);
        }

        if (range != 0) lore.add(ChatColor.WHITE + "Range: " + ChatColor.GREEN + range + "m");
        if (duration != 0) lore.add(ChatColor.WHITE + "Duration: " + ChatColor.DARK_AQUA + duration + "s");
        lore.add(ChatColor.WHITE + "Cooldown: " + ChatColor.AQUA + cooldown + "s");
        lore.add(ChatColor.WHITE + "Cost: " + ChatColor.GOLD + cost + "⚡");

        // damage stats
        if (damage != null) {
            lore.add("§b§l----------Damage----------");
            for (String damageString : damage) { lore.add(damageString); }
        }

        if (effects != null) {
            lore.add("§b§l----------Effects----------");
            for (String statusString : effects) { lore.add(statusString); }
        }

        if (weapons != null) {
            lore.add("§b§l----------Weapons---------");
            if (weapons.isEmpty()) {
                lore.add("§e- Any");

                for (ItemType type : ItemType.getAllWeaponTypes()) {
                    pdc.set(new NamespacedKey(expertiseStylePlugin, ItemType.getItemTypeString(type)),
                            PersistentDataType.BOOLEAN, true);
                }

            } else {
                // Specific subset of weapons
                for (ItemType weapon : weapons) {
                    if (weapon == SHIELD) {
                        lore.add("§e- " + ItemType.getItemTypeString(weapon) + " (in offhand)");
                    } else if (weapon == BOW) {
                        lore.add("§e- " + ItemType.getItemTypeString(weapon) + " and quiver");
                    } else if (weapon == GLOVE) {
                        lore.add("§e- " + ItemType.getItemTypeString(weapon) + "s (both)");
                    } else if (weapon == STAFF) {
                        lore.add("§e- Staves");
                    } else {
                        lore.add("§e- " + ItemType.getItemTypeString(weapon) + "s");
                    }

                    pdc.set(new NamespacedKey(expertiseStylePlugin, ItemType.getItemTypeString(weapon)),
                            PersistentDataType.BOOLEAN, true);
                }
            }
        }

        meta.setLore(lore);
        expertiseItem.setItemMeta(meta);

        return expertiseItem;
    }

    private static List<String> linebreak(String string, int size) {
        List<String> breaks = new ArrayList<>();
        int i = 0;

        while (i < string.length()) {
            int end = Math.min(string.length(), i + size);

            if (end < string.length() && string.charAt(end) != ' ') {
                int lastSpace = string.lastIndexOf(' ', end);
                if (lastSpace > i) {
                    end = lastSpace; // move break point to last space
                }
            }

            String chunk = string.substring(i, end).trim();
            if (!chunk.isEmpty()) {
                breaks.add(chunk);
            }

            i = end;
            while (i < string.length() && string.charAt(i) == ' ') {
                i++;
            }
        }

        return breaks;
    }
}
