package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Assassin;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLItems.ItemSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class AssassinAbilityEffects {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private Set<UUID> hitEntityUUIDs = new HashSet<>();
    private Player user;
    private int hotbarSlot;

    public AssassinAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin, Player user, int hotbarSlot) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        this.user = user;
        this.hotbarSlot = hotbarSlot;
    }

    public void slashAndDash(ItemStack weapon) {
        HashMap<DamageType, Double> multipliedDamageMap = DamageConverter.convertStatMap2DamageTypes(ItemSystem.multiplyAllDamageStats(weapon, 1.5));

        EnergyManager.useEnergy(user, 20);
        CooldownManager.putAllOtherAbilitesOnCooldown(user, 1, hotbarSlot);
        user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);

        // dash
        Vector knockback = user.getLocation().getDirection().multiply(4);
        knockback.setY(-2);
        user.setVelocity(knockback);
        user.setInvulnerable(true);

        // slash
        new BukkitRunnable() {
            int dashTicks = 6;

            @Override
            public void run() {
                user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

                Location particleLocation = user.getLocation().add(0, 1, 0);
                Vector direction = particleLocation.getDirection().multiply(1.2); // distance in blocks of particle from player

                particleLocation.add(direction);
                user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLocation, 0, 0, 0, 0, 0);

                for (Entity entity : user.getWorld().getNearbyEntities(user.getLocation(), 2, 1, 2)) {
                    if (entity instanceof LivingEntity livingEntity && !entity.equals(user)) {
                        if (!hitEntityUUIDs.contains(entity.getUniqueId())) {
                            Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));
                            hitEntityUUIDs.add(entity.getUniqueId());
                        }
                    }
                }

                dashTicks--;
                if (dashTicks == 0) {
                    this.cancel();
                    user.removeMetadata("using ability", expertiseStylePlugin);
                }

            }
        }.runTaskTimer(expertiseStylePlugin, 0L, 1L);

        hitEntityUUIDs.clear();

        Bukkit.getScheduler().runTaskLater(expertiseStylePlugin, () -> {
            user.setInvulnerable(false);
        }, 6L);
    }
}