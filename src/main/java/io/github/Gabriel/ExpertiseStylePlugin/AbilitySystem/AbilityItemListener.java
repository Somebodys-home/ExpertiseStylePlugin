package io.github.Gabriel.expertiseStylePlugin.AbilitySystem;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.StyleSystem.StyleAbilityItemTemplate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class AbilityItemListener implements Listener {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private final ItemStack expertiseAbilityItem = ExpertiseItemTemplate.emptyExpertiseAbilityItem();
    private final ItemStack styleAbilityItem = StyleAbilityItemTemplate.emptyStyleAbilityItem();
    private final ItemStack cooldownItem = AbilityItemTemplate.abilityCooldownItem();
    private final ArrayList<UUID> triggeringPlayer = new ArrayList<>();

    public AbilityItemListener(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
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
        if (event.getClickedInventory() instanceof PlayerInventory playerInventory) {
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

    // put items on cooldown when selecting them (using the ability)
    @EventHandler
    public void onUseAbility(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (triggeringPlayer.contains(uuid)) { // double trigger guard
            triggeringPlayer.remove(uuid);
            return;
        }

        int newSlot = event.getNewSlot();
        ItemStack abilityItem = player.getInventory().getItem(newSlot);
        ItemStack weapon = player.getInventory().getItem(event.getPreviousSlot());

        if (AbilityItemTemplate.isImmovable(abilityItem)) {
            event.setCancelled(true);

            if (player.hasCooldown(abilityItem.getType())) return;

            triggeringPlayer.add(uuid);
            Bukkit.getPluginManager().callEvent(new UseAbilityEvent(player, weapon, abilityItem));

            if (AbilityItemTemplate.getCooldown(abilityItem) != -1) { // if the item has a cooldown timer
                player.getInventory().setItem(newSlot, cooldownItem);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.getInventory().setItem(newSlot, abilityItem);
                    }
                }.runTaskLater(expertiseStylePlugin, 20L * AbilityItemTemplate.getCooldown(abilityItem));
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
