package io.github.Gabriel.expertiseStylePlugin;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.UseAbilityEvent;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLItems.ItemType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
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

    public static boolean isHoldingWeaponForAbility(UseAbilityEvent event) {
        List<ItemType> requiredWeapons = getWeaponsForAbility(event.getAbility());
        Player player = event.getPlayer();
        ItemStack mainhand = event.getWeapon();
        ItemStack offhand = player.getInventory().getItemInOffHand();

        if (!requiredWeapons.contains(SHIELD)) {
            return (requiredWeapons.contains(ItemSystem.getItemType(mainhand)));
        } else {
            return (requiredWeapons.contains(ItemSystem.getItemType(offhand)));
        }
    }
}
