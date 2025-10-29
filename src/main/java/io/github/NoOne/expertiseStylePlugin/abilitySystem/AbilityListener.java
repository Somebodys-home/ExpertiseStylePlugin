package io.github.NoOne.expertiseStylePlugin.abilitySystem;

import io.github.NoOne.damagePlugin.customDamage.CustomDamageEvent;
import io.github.NoOne.damagePlugin.customDamage.DamageType;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.CooldownSystem.CooldownManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseAbilityItemTemplate;
import io.github.NoOne.expertiseStylePlugin.expertiseSystem.ExpertiseManager;
import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class AbilityListener implements Listener {
    private ProfileManager profileManager;
    private final ItemStack expertiseAbilityItem = ExpertiseAbilityItemTemplate.emptyExpertiseAbilityItem();
    private final ItemStack styleAbilityItem = AbilityItemTemplate.emptyStyleAbilityItem();

    public AbilityListener(ExpertiseStylePlugin expertiseStylePlugin) {
        this.profileManager = expertiseStylePlugin.getProfileManager();
    }

    @EventHandler
    public void onUseAbility(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        double currentEnergy = profileManager.getPlayerProfile(uuid).getStats().getCurrentEnergy();
        int newSlot = event.getNewSlot();
        ItemStack abilityItem = player.getInventory().getItem(newSlot);
        ItemStack weapon = player.getInventory().getItem(event.getPreviousSlot());

        if (AbilityItemTemplate.isAnAbility(abilityItem)) { // if it's an ability item
            event.setCancelled(true);

            if (abilityItem.isSimilar(AbilityItemTemplate.cooldownItem()) || abilityItem.isSimilar(AbilityItemTemplate.emptyStyleAbilityItem()) || abilityItem.isSimilar(ExpertiseAbilityItemTemplate.emptyExpertiseAbilityItem())) return;
            if (!ExpertiseManager.isHoldingWeaponForAbility(player, abilityItem, weapon)) {
                event.getPlayer().sendMessage("§c⚠ §nRequirements not met!§r§c ⚠");
            } else {
                if (AbilityItemTemplate.isToggleable(abilityItem)) { // if it's a toggleable ability
                    boolean toggle = !AbilityItemTemplate.getToggleState(abilityItem); // boolean of its inverse state

                    AbilityItemTemplate.toggleAbility(abilityItem, toggle);

                    if (!toggle) { // if were turning it off, put it on cooldown
                        Bukkit.getPluginManager().callEvent(new UseAbilityEvent(player, weapon, abilityItem, newSlot));
                        CooldownManager.putOnCooldown(player, newSlot, AbilityItemTemplate.getCooldown(abilityItem));
                    } else { // if turning on, energy check
                        if (AbilityItemTemplate.getRequiredEnergy(abilityItem) <= currentEnergy) {
                            Bukkit.getPluginManager().callEvent(new UseAbilityEvent(player, weapon, abilityItem, newSlot));
                        } else {
                            player.sendMessage("§c⚠ §nNot enough energy!§r§c ⚠");
                        }
                    }
                } else { // if it isnt a toggleable
                    if (AbilityItemTemplate.getRequiredEnergy(abilityItem) <= currentEnergy) { // energy check
                        Bukkit.getPluginManager().callEvent(new UseAbilityEvent(player, weapon, abilityItem, newSlot));
                        CooldownManager.putOnCooldown(player, newSlot, AbilityItemTemplate.getCooldown(abilityItem));
                    } else {
                        player.sendMessage("§c⚠ §nNot enough energy!§r§c ⚠");
                    }
                }
            }
        }
    }

    @EventHandler
    public void blankAbilitiesOnFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            player.getInventory().setItem(0, styleAbilityItem);
            player.getInventory().setItem(1, expertiseAbilityItem);
            player.getInventory().setItem(2, expertiseAbilityItem);
            player.getInventory().setItem(3, expertiseAbilityItem);
        }
    }

    @EventHandler
    public void dontDropAbilities(PlayerDropItemEvent event) {
        if (AbilityItemTemplate.isAnAbility(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void dontInventoryClickAbilities(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();

        if (AbilityItemTemplate.isAnAbility(clickedItem)) {
            if (event.getClick() == ClickType.SHIFT_LEFT ||
                    event.getClick() == ClickType.SHIFT_RIGHT ||
                    event.getClick() == ClickType.DOUBLE_CLICK ||
                    event.getClick() == ClickType.NUMBER_KEY ||
                    event.getClick() == ClickType.SWAP_OFFHAND) {

                event.setCancelled(true);
            }

            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY ||
                    event.getAction() == InventoryAction.PICKUP_ALL ||
                    event.getAction() == InventoryAction.PICKUP_HALF ||
                    event.getAction() == InventoryAction.PICKUP_SOME ||
                    event.getAction() == InventoryAction.PICKUP_ONE ||
                    event.getAction() == InventoryAction.PLACE_ALL ||
                    event.getAction() == InventoryAction.PLACE_SOME ||
                    event.getAction() == InventoryAction.PLACE_ONE ||
                    event.getAction() == InventoryAction.SWAP_WITH_CURSOR) {

                event.setCancelled(true);
            }

            if (isContainer(event.getClickedInventory().getType())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerSwapHandAbilities(PlayerSwapHandItemsEvent event) {
        if (AbilityItemTemplate.isAnAbility(Objects.requireNonNull(event.getOffHandItem()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        for (int slot : event.getRawSlots()) {
            Inventory inventory = event.getView().getInventory(slot);

            if (inventory != null && isContainer(inventory.getType())) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void dontHotkeyAbilitiesIntoContainers(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClick() == ClickType.NUMBER_KEY) {
            ItemStack item = player.getInventory().getItem(event.getHotbarButton());

            assert item != null;
            if (AbilityItemTemplate.isAnAbility(item)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void damageWithAbilityArrow(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow arrow && arrow.getShooter() instanceof Player player && arrow.hasMetadata("ability arrow")) {
            MetadataValue meta = arrow.getMetadata("ability arrow").get(0);
            HashMap<DamageType, Double> damageMap = (HashMap<DamageType, Double>) meta.value();

            event.setDamage(0);
            arrow.remove();
            if (event.getEntity() instanceof LivingEntity livingEntity) livingEntity.setNoDamageTicks(0);

            Bukkit.getPluginManager().callEvent(new CustomDamageEvent((LivingEntity) event.getEntity(), player, damageMap));
        }
    }

    @EventHandler
    public void fallingFromAbilityUse(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (player.hasMetadata("falling")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onFireworkDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Firework firework) {
            if (firework.hasMetadata("ability_firework")) {
                event.setCancelled(true);
            }
        }
    }


    private boolean isContainer(InventoryType type) {
        return type == InventoryType.CHEST ||
                type == InventoryType.HOPPER ||
                type == InventoryType.BARREL ||
                type == InventoryType.SHULKER_BOX ||
                type == InventoryType.DISPENSER ||
                type == InventoryType.DROPPER ||
                type == InventoryType.FURNACE ||
                type == InventoryType.BLAST_FURNACE ||
                type == InventoryType.SMOKER;
    }
}
