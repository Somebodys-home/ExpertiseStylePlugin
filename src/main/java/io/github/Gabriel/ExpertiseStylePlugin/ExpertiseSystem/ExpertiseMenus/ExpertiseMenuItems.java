package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ExpertiseMenuItems {
    public static ItemStack soldier() {
        ItemStack swordsman = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = swordsman.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lSoldier"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Me when military proletarianism is kinda based actually"));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCLICK TO SELECT!"));
        meta.setLore(lore);
        swordsman.setItemMeta(meta);

        return swordsman;
    }

    public static ItemStack ninja() {
        ItemStack ninja = new ItemStack(Material.BLACK_WOOL, 1);
        ItemMeta meta = ninja.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&0&lNinja"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7\"Hey what do you got there?\""));
        lore.add(ChatColor.translateAlternateColorCodes('&',  "&7\"A knife!\""));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7\"NO!\""));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCLICK TO SELECT!"));
        meta.setLore(lore);
        ninja.setItemMeta(meta);

        return ninja;
    }

    public static ItemStack marauder() {
        ItemStack marauder = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta meta = marauder.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lMarauder"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Marauder? I barely know her!"));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCLICK TO SELECT!"));
        meta.setLore(lore);
        marauder.setItemMeta(meta);

        return marauder;
    }


    public static ItemStack shieldHero() {
        ItemStack shieldHero = new ItemStack(Material.SHIELD, 1);
        ItemMeta meta = shieldHero.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3&lShield Hero"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7WARNING: If you play this expertise, you will be hate crimed."));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCLICK TO SELECT!"));
        meta.setLore(lore);
        shieldHero.setItemMeta(meta);

        return shieldHero;
    }

    public static ItemStack cavalier() {
        ItemStack blunt = new ItemStack(Material.IRON_SHOVEL, 1);
        ItemMeta meta = blunt.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&1&lCavalier"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7[TBD]"));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCLICK TO SELECT!"));
        meta.setLore(lore);
        blunt.setItemMeta(meta);

        return blunt;
    }

    public static ItemStack martialArtist() {
        ItemStack martialArtist = new ItemStack(Material.RED_GLAZED_TERRACOTTA, 1);
        ItemMeta meta = martialArtist.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lMartial Artist"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Beating up homeless people in the back alley of an Arby's™ pro max"));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCLICK TO SELECT!"));
        meta.setLore(lore);
        martialArtist.setItemMeta(meta);

        return martialArtist;
    }

    public static ItemStack archer() {
        ItemStack archer = new ItemStack(Material.BOW, 1);
        ItemMeta meta = archer.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lArcher"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7SPOILER ALERT: This is the closest thing you're getting to a gun."));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCLICK TO SELECT!"));
        meta.setLore(lore);
        archer.setItemMeta(meta);

        return archer;
    }

    public static ItemStack sorcerer() {
        ItemStack sorcerer = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta meta = sorcerer.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lSorcerer"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7There's no I in team, but there's 6 Is in"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7&o\"F&kuck&r&7&o it, I don't care how big the room is, I cast fireball.\""));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCLICK TO SELECT!"));
        meta.setLore(lore);
        sorcerer.setItemMeta(meta);

        return sorcerer;
    }

    public static ItemStack primordial() {
        ItemStack primordial = new ItemStack(Material.OAK_SAPLING, 1);
        ItemMeta meta = primordial.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lPrimordial"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7F&kuck&r&7in hippie."));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCLICK TO SELECT!"));
        meta.setLore(lore);
        primordial.setItemMeta(meta);

        return primordial;
    }

    public static ItemStack hallowed() {
        ItemStack hallowed = new ItemStack(Material.OXEYE_DAISY, 1);
        ItemMeta meta = hallowed.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lHallowed"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7\"Say Drake, I hear you like 'em young.\""));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCLICK TO SELECT!"));
        meta.setLore(lore);
        hallowed.setItemMeta(meta);

        return hallowed;
    }

    public static ItemStack annulled() {
        ItemStack annulled = new ItemStack(Material.CRYING_OBSIDIAN, 1);
        ItemMeta meta = annulled.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lAnnulled"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Welcome home."));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCLICK TO SELECT!"));
        meta.setLore(lore);
        annulled.setItemMeta(meta);

        return annulled;
    }

    public static ItemStack resetAbilities() {
        ItemStack reset = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = reset.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lReset Abilities"));
        reset.setItemMeta(meta);

        return reset;
    }
}
