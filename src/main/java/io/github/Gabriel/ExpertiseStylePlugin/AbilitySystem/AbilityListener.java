package io.github.Gabriel.expertiseStylePlugin.AbilitySystem;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class AbilityListener implements Listener {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private final ItemStack expertiseAbilityItem = ExpertiseItemTemplate.emptyExpertiseAbilityItem();
    private final ItemStack styleAbilityItem = AbilityItemTemplate.emptyStyleAbilityItem();
    private final ArrayList<UUID> triggeringPlayer = new ArrayList<>();

    public AbilityListener(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
    }

    // put items on cooldown when selecting them (using the ability)
    @EventHandler
    public void onUseAbility(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        double currentEnergy = expertiseStylePlugin.getNmlPlayerStats().getProfileManager().getPlayerProfile(uuid).getStats().getCurrentEnergy();
        int newSlot = event.getNewSlot();
        ItemStack abilityItem = player.getInventory().getItem(newSlot);
        ItemStack weapon = player.getInventory().getItem(event.getPreviousSlot());

        if (triggeringPlayer.contains(uuid)) { // double trigger guard
            triggeringPlayer.remove(uuid);
            return;
        }


        if (AbilityItemTemplate.isImmovable(abilityItem)) { // if it's an ability item
            event.setCancelled(true);

            if (abilityItem.isSimilar(AbilityItemTemplate.cooldownItem())) return;
            if (!ExpertiseManager.isHoldingWeaponForAbility(player, abilityItem, weapon)) {
                event.getPlayer().sendMessage("§c⚠ §nRequirements not met!§r§c ⚠");
            } else {
                triggeringPlayer.add(uuid);

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
    public void onPlayerFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            player.getInventory().setItem(0, expertiseAbilityItem);
            player.getInventory().setItem(1, expertiseAbilityItem);
            player.getInventory().setItem(2, styleAbilityItem);
            player.getInventory().setItem(3, styleAbilityItem);
        }
    }

    @EventHandler
    public void onPlayerDropAbilityItem(PlayerDropItemEvent event) {
        if (AbilityItemTemplate.isImmovable(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClickAbilityItem(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();

        if (AbilityItemTemplate.isImmovable(clickedItem)) {
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
    public void onPlayerSwapHandAbilityItem(PlayerSwapHandItemsEvent event) {
        if (AbilityItemTemplate.isImmovable(Objects.requireNonNull(event.getOffHandItem()))) {
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
    public void onHotkeyAbilityItemIntoContainers(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClick() == ClickType.NUMBER_KEY) {
            ItemStack item = player.getInventory().getItem(event.getHotbarButton());

            assert item != null;
            if (AbilityItemTemplate.isImmovable(item)) {
                event.setCancelled(true);
            }
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
