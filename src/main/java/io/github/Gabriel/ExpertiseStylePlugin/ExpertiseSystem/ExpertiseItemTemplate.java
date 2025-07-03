package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem;

import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
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
import java.util.Map;

public class ExpertiseItemTemplate extends AbilityItemTemplate {
    private static NamespacedKey expertiseKey;

    public ExpertiseItemTemplate(ExpertiseStylePlugin expertiseStylePlugin) {
        super(expertiseStylePlugin);
        expertiseKey = new NamespacedKey(expertiseStylePlugin, "expertise");
    }

    public static ItemStack emptyExpertiseAbilityItem() {
        ItemStack expertise = new ItemStack(Material.MAGENTA_DYE);
        ItemMeta meta = expertise.getItemMeta();
        assert meta != null;
        List<String> lore = new ArrayList<>();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(immovableKey, PersistentDataType.INTEGER, 1);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Empty Expertise Ability");
        lore.add(ChatColor.GRAY + "An empty ability slot. Dunno why you'd put nothing here.");
        meta.setLore(lore);
        expertise.setItemMeta(meta);

        return expertise;
    }

    public static ItemStack makeExpertiseAbilityItem(String expertise, String name, String description, String targeting, int range, int duration, int cooldown, int cost, int weaponDamageMultiplier, Map<DamageType, Double> damageStats) {
        ItemStack expertiseItem = new ItemStack(Material.CRYING_OBSIDIAN);
        ChatColor color = ChatColor.DARK_PURPLE;
        ItemMeta meta = expertiseItem.getItemMeta();
        List<String> lore = new ArrayList<>();
        assert meta != null;
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(immovableKey, PersistentDataType.INTEGER, 1);
        pdc.set(expertiseKey, PersistentDataType.INTEGER, 1);
        pdc.set(cooldownKey, PersistentDataType.INTEGER, cooldown);

        // color and itemstack of ability
        switch (expertise) {
            case "swordsman" -> {
                expertiseItem = new ItemStack(Material.IRON_SWORD);
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
            case "jouster" -> {
                expertiseItem = new ItemStack(Material.STICK);
                color = ChatColor.DARK_GRAY;
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
                expertiseItem = new ItemStack(Material.ENCHANTED_BOOK);
                color = ChatColor.GOLD;
            }
            case "druid" -> {
                expertiseItem = new ItemStack(Material.OAK_SAPLING);
                color = ChatColor.DARK_GREEN;
            }
            case "hallowed" -> {
                expertiseItem = new ItemStack(Material.OXEYE_DAISY);
                color = ChatColor.WHITE;
            }
        }

        meta.setDisplayName(color + ChatColor.translateAlternateColorCodes('&', "&l" + name));
        lore.add(ChatColor.GRAY + description);
        lore.add("");

        // misc stats
        lore.add(ChatColor.WHITE + "Targeting: " + ChatColor.BLUE + targeting);
        lore.add(ChatColor.WHITE + "Range: " + ChatColor.GREEN + range + "m");

        if (duration != 0) {
            lore.add(ChatColor.WHITE + "Duration: " + ChatColor.DARK_AQUA + duration + "s");
        }

        lore.add(ChatColor.WHITE + "Cooldown: " + ChatColor.AQUA + cooldown + "s");
        lore.add(ChatColor.WHITE + "Cost: " + ChatColor.GOLD + cost + "⚡");

        // damage stats
        lore.add("§b§l--------Damage--------");

        // elemental multipliers
        if (weaponDamageMultiplier != 0) {
            lore.add("§f§n" + weaponDamageMultiplier + "%" + "§r§f" + " Weapon Damage \uD83D\uDDE1");
        }

        if (damageStats != null) {
            for (Map.Entry<DamageType, Double> damageStat : damageStats.entrySet()) {
                DamageType damageType = damageStat.getKey();
                String icon = "";

                switch (damageType) {
                    case FIRE -> {
                        icon = "\uD83D\uDD25";
                    }
                    case COLD -> {
                        icon = "❄";
                    }
                    case EARTH -> {
                        icon = "\uD83E\uDEA8";
                    }
                    case LIGHTNING -> {
                        icon = "\uD83D\uDDF2";
                    }
                    case AIR -> {
                        icon = "☁";
                    }
                    case LIGHT -> {
                        icon = "✦";
                    }
                    case DARK -> {
                        icon = "\uD83C\uDF00";
                    }
                    case PURE -> {
                        icon = "\uD83D\uDCA2";
                    }
                }

                lore.add(DamageType.getDamageColor(damageType) + "" + damageStat.getValue() + " % " + damageType + " " + icon);
            }
        }

        // todo: add stats effects (when i eventually get to that)
        lore.add("§b§l--------Status--------");

        // todo: add useable weapons (when i eventually get to that)
        lore.add("§b§l--------Weapons--------");

        meta.setLore(lore);
        expertiseItem.setItemMeta(meta);

        return expertiseItem;
    }

}
