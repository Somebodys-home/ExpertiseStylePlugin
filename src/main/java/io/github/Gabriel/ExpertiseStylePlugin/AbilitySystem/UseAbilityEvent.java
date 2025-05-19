package io.github.Gabriel.expertiseStylePlugin.AbilitySystem;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class UseAbilityEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final int previousSlot;
    private final int newSlot;
    private final ItemStack weapon;

    public UseAbilityEvent(@NotNull Player player, int previousSlot, int newSlot, ItemStack weapon) {
        this.player = player;
        this.previousSlot = previousSlot;
        this.newSlot = newSlot;
        this.weapon = weapon;
    }

    @Override
    public HandlerList getHandlers() { return handlers; }
    
    public static HandlerList getHandlerList() { return handlers; }

    public Player getPlayer() { return player; }

    public ItemStack getWeapon() { return weapon; }

    public int getPreviousSlot() {
        return previousSlot;
    }

    public int getNewSlot() {
        return newSlot;
    }
}