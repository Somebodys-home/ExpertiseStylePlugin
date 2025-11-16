package io.github.NoOne.expertiseStylePlugin.expertiseSystem.cavalier;

import io.github.NoOne.damagePlugin.customDamage.CustomDamageEvent;
import io.github.NoOne.damagePlugin.customDamage.DamageConverter;
import io.github.NoOne.damagePlugin.customDamage.DamageType;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.cooldownSystem.CooldownManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public class CavalierAbilityEffects {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static ProfileManager profileManager;

    public CavalierAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        profileManager = expertiseStylePlugin.getProfileManager();
    }

    public static void seismicSlam(Player user, int hotbarSlot) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));
        user.setMetadata("falling", new FixedMetadataValue(expertiseStylePlugin, true));

        HashSet<UUID> hitEntityUUIDs = new HashSet<>();
        HashMap<DamageType, Double> damageStats = DamageConverter.multiplyDamageMap(DamageConverter.convertPlayerStats2Damage(
                profileManager.getPlayerStats(user.getUniqueId())), 2.5);

        EnergyManager.useEnergy(user, 30);
        CooldownManager.putAllOtherAbilitiesOnCooldown(user, 4, hotbarSlot);

        // jump
        Vector jump = user.getLocation().getDirection().multiply(.5);
        jump.setY(1.5);
        user.setVelocity(jump);
        user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 2f);

        // trail particles
        BukkitTask flyingParticles = new BukkitRunnable() {
            @Override
            public void run() {
                user.getWorld().spawnParticle(Particle.SNOWFLAKE, user.getLocation(), 75, .15, 1, .15, 0);
            }
        }.runTaskTimer(expertiseStylePlugin, 0L, 1L);

        // slam
        Bukkit.getScheduler().runTaskLater(expertiseStylePlugin, () -> {
            Vector slam = user.getLocation().getDirection().multiply(1.5);
            slam.setY(-2.2);
            user.setVelocity(slam);
            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, .3f);
            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, .3f);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (user.isOnGround()) {
                        user.removeMetadata("falling", expertiseStylePlugin);

                        flyingParticles.cancel();
                        user.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, user.getLocation().add(0, .5, 0), 3, .25, 0, .25, 0);
                        user.playSound(user.getLocation(), Sound.ITEM_MACE_SMASH_GROUND_HEAVY, 3f, 1f);
                        user.playSound(user.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, .8f, 1f);

                        for (Entity entity : user.getWorld().getNearbyEntities(user.getLocation(), 4, 2, 4)) {
                            if (!entity.equals(user)) {
                                hitEntityUUIDs.add(entity.getUniqueId());
                            }
                        }

                        for (UUID uuid : hitEntityUUIDs) {
                            if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                                Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, damageStats));

                                // knockback
                                Vector knockback = livingEntity.getLocation().toVector().subtract(user.getLocation().toVector()).normalize().multiply(1.2);
                                knockback.setY(.75);
                                livingEntity.setVelocity(knockback);
                            }
                        }

                        user.removeMetadata("using ability", expertiseStylePlugin);
                        cancel();
                    }
                }
            }.runTaskTimer(expertiseStylePlugin, 0L, 1L);
        }, 20L);
    }
}