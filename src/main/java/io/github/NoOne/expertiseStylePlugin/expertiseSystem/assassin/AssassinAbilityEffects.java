package io.github.NoOne.expertiseStylePlugin.expertiseSystem.assassin;

import io.github.NoOne.damagePlugin.customDamage.CustomDamageEvent;
import io.github.NoOne.damagePlugin.customDamage.DamageConverter;
import io.github.NoOne.damagePlugin.customDamage.DamageType;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.cooldownSystem.CooldownManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class AssassinAbilityEffects {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static ProfileManager profileManager;

    public AssassinAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        profileManager = expertiseStylePlugin.getProfileManager();
    }

    public static void slashAndDash(Player user, int hotbarSlot) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

        HashSet<UUID> hitEntityUUIDs = new HashSet<>();
        HashMap<DamageType, Double> damageStats = DamageConverter.multiplyDamageMap(DamageConverter.convertPlayerStats2Damage(
                profileManager.getPlayerProfile(user.getUniqueId()).getStats()), 1.5);

        EnergyManager.useEnergy(user, 20);
        CooldownManager.putAllOtherAbilitiesOnCooldown(user, 1, hotbarSlot);
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
                Location particleLocation = user.getLocation().add(0, 1, 0);
                Vector direction = particleLocation.getDirection().multiply(1.2); // distance in blocks of particle from player

                particleLocation.add(direction);
                user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLocation, 0, 0, 0, 0, 0);

                for (Entity entity : user.getWorld().getNearbyEntities(user.getLocation(), 2, 1, 2)) {
                    if (entity instanceof LivingEntity livingEntity && !entity.equals(user)) {
                        if (!hitEntityUUIDs.contains(entity.getUniqueId())) {
                            Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, damageStats));
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