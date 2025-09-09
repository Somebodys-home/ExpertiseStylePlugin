package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.ItemType;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class ExpertiseManager {
    private static ExpertiseStylePlugin expertiseStylePlugin;

    public ExpertiseManager(ExpertiseStylePlugin expertiseStylePlugin) {
        ExpertiseManager.expertiseStylePlugin = expertiseStylePlugin;
    }

    public static List<ItemType> getWeaponsForAbility(ItemStack item) {
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        List<ItemType> weapons = new ArrayList<>(List.of(SWORD, DAGGER, AXE, HAMMER, SPEAR, GLOVE, BOW, WAND, STAFF, CATALYST, SHIELD));
        List<ItemType> weaponsToRemove = new ArrayList<>();

        for (ItemType weapon : weapons) {
            NamespacedKey weaponKey = new NamespacedKey(expertiseStylePlugin, ItemType.getItemTypeString(weapon));
            if (!pdc.has(weaponKey)) {
                weaponsToRemove.add(weapon);
            }
        }

        weapons.removeAll(weaponsToRemove);
        return weapons;
    }

    public static boolean isHoldingWeaponForAbility(Player player, ItemStack abilityItem, ItemStack mainhand) {
        List<ItemType> requiredWeapons = getWeaponsForAbility(abilityItem);
        ItemStack offhand = player.getInventory().getItemInOffHand();

        if (requiredWeapons.contains(SWORD) && requiredWeapons.contains(DAGGER) && requiredWeapons.contains(AXE) && requiredWeapons.contains(HAMMER) &&
            requiredWeapons.contains(SPEAR) && requiredWeapons.contains(GLOVE) && requiredWeapons.contains(BOW) && requiredWeapons.contains(WAND) &&
            requiredWeapons.contains(STAFF) && requiredWeapons.contains(CATALYST)) {

            return (requiredWeapons.contains(ItemSystem.getItemType(mainhand)));
        }

        if (requiredWeapons.contains(SHIELD)) {
            return (ItemSystem.getItemType(offhand) == SHIELD);
        } else if (requiredWeapons.contains(GLOVE)) {
            return (ItemSystem.getItemType(mainhand) == GLOVE && (ItemSystem.getItemType(offhand) == GLOVE));
        } else if (requiredWeapons.contains(BOW)) {
            return (ItemSystem.getItemType(mainhand) == BOW && (ItemSystem.getItemType(offhand) == QUIVER));
        } else {
            return (requiredWeapons.contains(ItemSystem.getItemType(mainhand)));
        }
    }
}
