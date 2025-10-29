package io.github.NoOne.expertiseStylePlugin.expertiseSystem;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityItemTemplate;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLItems.ItemType;
import io.github.NoOne.nMLSkills.skillSystem.Skills;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

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
        meta.setDisplayName("§dEmpty Expertise Ability");
        lore.add("§7An empty ability slot. Dunno why you'd put nothing here.");
        meta.setLore(lore);
        expertise.setItemMeta(meta);

        return expertise;
    }

    public static ItemStack makeExpertiseAbilityItem(String name, Map<String, Integer> requirements, String description, boolean toggleable, String targeting,
                                                     int range, int duration, int cooldown, int cost, List<String> damage, List<String> effects, List<ItemType> weapons,
                                                    Skills playerSkills) {

        ItemStack expertiseItem = new ItemStack(Material.BARRIER);
        String color = "";
        ItemMeta meta = expertiseItem.getItemMeta();
        List<String> lore = new ArrayList<>();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        // setting keys
        pdc.set(expertiseKey, PersistentDataType.INTEGER, 1);
        pdc.set(AbilityItemTemplate.getAbilityKey(), PersistentDataType.INTEGER, 1);
        pdc.set(AbilityItemTemplate.getCooldownKey(), PersistentDataType.INTEGER, cooldown);
        pdc.set(AbilityItemTemplate.getEnergyKey(), PersistentDataType.INTEGER, cost);

        for (Map.Entry<String, Integer> entry : requirements.entrySet()) {
            pdc.set(new NamespacedKey(expertiseStylePlugin, entry.getKey()), PersistentDataType.INTEGER, entry.getValue());
        }

        // color and itemstack of ability
        switch (requirements.entrySet().iterator().next().getKey()) {
            case "soldier" -> {
                expertiseItem = new ItemStack(Material.DIAMOND_SWORD);
                color = "§c";
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            case "assassin" -> {
                expertiseItem = new ItemStack(Material.BLACK_WOOL);
                color = "§8";
            }
            case "marauder" -> {
                expertiseItem = new ItemStack(Material.GOLDEN_AXE);
                color = "§4";
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            case "cavalier" -> {
                expertiseItem = new ItemStack(Material.MACE);
                color = "§9";
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            case "martialartist" -> {
                expertiseItem = new ItemStack(Material.RED_GLAZED_TERRACOTTA);
                color = "§4";
            }
            case "shieldhero" -> {
                expertiseItem = new ItemStack(Material.SHIELD);
                color = "§3";
            }
            case "marksman" -> {
                expertiseItem = new ItemStack(Material.BOW);
                color = "§a";
            }
            case "sorcerer" -> {
                expertiseItem = new ItemStack(Material.BOOK);
                color = "§6";
            }
            case "primordial" -> {
                expertiseItem = new ItemStack(Material.OAK_SAPLING);
                color = "§2";
            }
            case "hallowed" -> {
                expertiseItem = new ItemStack(Material.OXEYE_DAISY);
                color = "§f";
            }
            case "annulled" -> {
                expertiseItem = new ItemStack(Material.CRYING_OBSIDIAN);
                color = "§5";
            }
        }

        meta.setDisplayName(color + "§l" + name);

        // requirements
        for (Map.Entry<String, Integer> requirementEntry : requirements.entrySet()) {
            String string = requirementEntry.getKey();

            if (Objects.equals(string, "shieldhero")) string = "Shield Hero";
            if (Objects.equals(string, "martialartist")) string = "Martial Artist";

            String requirementString = "§8Lv. " + requirementEntry.getValue() + " " + string.substring(0, 1).toUpperCase() + string.substring(1);

            if (AbilityItemTemplate.meetsRequirement(playerSkills, string, requirementEntry.getValue())) {
                requirementString += " §a✔";
            } else {
                requirementString += " §c✖";
                pdc.set(AbilityItemTemplate.getUnusableKey(), PersistentDataType.BOOLEAN, true);
            }

            lore.add(requirementString);
        }
        lore.add("");

        // description
        for (String line : linebreak(description, 33)) lore.add("§7" + line);
        lore.add("");

        // misc stats
        if (toggleable) {
            lore.add("§fTarget: §9" + targeting + ", Toggle");
            pdc.set(AbilityItemTemplate.getToggleKey(), PersistentDataType.BOOLEAN, false);
            pdc.set(AbilityItemTemplate.getOriginalItemKey(), PersistentDataType.STRING, expertiseItem.getType().toString());
        } else {
            lore.add("§fTarget: §9" + targeting);
        }

        if (range != 0) lore.add("§fRange: §a" + range + "m");
        if (duration != 0) lore.add("§fDuration: §3" + duration + "s");
        lore.add("§fCooldown: §b" + cooldown + "s");
        lore.add("§fCost: §6" + cost + "⚡");

        // damage stats
        if (damage != null) {
            lore.add("§b§l----------Damage----------");
            lore.addAll(damage);
        }

        if (effects != null) {
            lore.add("§b§l----------Effects----------");
            lore.addAll(effects);
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
