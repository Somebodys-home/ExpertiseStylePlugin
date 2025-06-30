package io.github.Gabriel.expertiseStylePlugin.AbilitySystem;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class UseAbilityEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final ItemStack weapon;
    private final ItemStack ability;

    public UseAbilityEvent(@NotNull Player player, ItemStack weapon, ItemStack ability) {
        this.player = player;
        this.weapon = weapon;
        this.ability = ability;
    }

    @Override
    public HandlerList getHandlers() { return handlers; }

    public static HandlerList getHandlerList() { return handlers; } // deleting this breaks things, apparently

    public Player getPlayer() { return player; }

    public ItemStack getWeapon() { return weapon; }

    public ItemStack getAbility() { return ability; }
}