package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Hallowed;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class HallowedAbilityEffects {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static NMLPlayerStats nmlPlayerStats;
    private static Set<UUID> hitEntityUUIDs = new HashSet<>();

    public HallowedAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        nmlPlayerStats = expertiseStylePlugin.getNmlPlayerStats();
    }

    public static void halo(Player user, int hotbarSlot) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

        HashMap<DamageType, Double> lightDamage = DamageConverter.multiplyDamageMap(DamageConverter.convertPlayerStat2Damage(
                nmlPlayerStats.getProfileManager().getPlayerProfile(user.getUniqueId()).getStats(), "lightdamage"), .5) ;

        EnergyManager.useEnergy(user, 25);
        CooldownManager.putAllOtherAbilitesOnCooldown(user, 1.5, hotbarSlot);
        user.playSound(user, Sound.ITEM_TRIDENT_RIPTIDE_1, 1f, 1f);
        user.playSound(user, Sound.ITEM_ELYTRA_FLYING, .5f, 1f);

        new BukkitRunnable() {
            int ticks = 0;
            int maxTicks = 80;
            boolean hitPlayer = false;
            Location center = user.getLocation().clone().add(0, 1, 0);
            Vector baseVelocity = user.getLocation().getDirection().normalize().multiply(.4);

            @Override
            public void run() {
                ticks++;

                double progress = (double) ticks / ((double) maxTicks / 2);
                double speedFactor;

                if (progress <= 1) { // slowdown going forwards
                    speedFactor = 1 - (progress * progress);
                    center.add(baseVelocity.clone().multiply(speedFactor * 1.5));
                } else { // coming back, tracking the player
                    double t = progress - 1;
                    speedFactor = t * t;

                    Location playerCenter = user.getLocation().clone().add(0, 2, 0);
                    Vector toPlayer = playerCenter.toVector().subtract(center.toVector()).normalize();
                    Vector blended = baseVelocity.clone().normalize().multiply(1 - t).add(toPlayer.multiply(t)).normalize();
                    Vector step = blended.multiply(speedFactor * 3);

                    if (step.lengthSquared() > center.distanceSquared(playerCenter)) {
                        hitPlayer = true;
                        user.removeMetadata("using ability", expertiseStylePlugin);
                        user.playSound(user, Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1f, 1f);
                        user.stopSound(Sound.ITEM_ELYTRA_FLYING);
                        center = playerCenter.clone(); // snap to player
                        this.cancel(); // stop the main loop right away

                        // mini halo
                        new BukkitRunnable() {
                            int timer = 0;

                            @Override
                            public void run() {
                                timer++;

                                Location center = user.getLocation();
                                double radius = .5; // <- max radius
                                int particleCount = 100;

                                if (timer != 40) {
                                    for (int i = 0; i < particleCount; i++) {
                                        double angle = 2 * Math.PI * i / particleCount;
                                        double x = radius * Math.cos(angle);
                                        double y = 2;
                                        double z = radius * Math.sin(angle);
                                        Location particleLocation = center.clone().add(x, y, z);

                                        user.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, particleLocation, 1, 0, 0, 0, 0);
                                    }
                                }

                                if (timer == 40) { // burst
                                    user.playSound(user, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 1f, 1f);

                                    for (int i = 0; i < particleCount; i++) {
                                        double angle = 2 * Math.PI * i / particleCount;
                                        double x = radius * Math.cos(angle);
                                        double y = 2;
                                        double z = radius * Math.sin(angle);
                                        Location particleLocation = center.clone().add(x, y, z);
                                        Vector velocity = particleLocation.toVector().subtract(user.getLocation().toVector()).normalize().multiply(0.3);

                                        user.getWorld().spawnParticle(Particle.END_ROD, particleLocation,0, velocity.getX(), 0, velocity.getZ(), 3);
                                    }

                                    cancel();
                                }
                            }
                        }.runTaskTimer(expertiseStylePlugin, 0L, 1L);
                        return;
                    } else {
                        center.add(step);
                    }
                }

                // halo
                Location playerCenter = user.getLocation().clone().add(0, 2, 0);
                double distance = center.distance(playerCenter);

                double shrinkStart = 12; // what distance from the player to start shrinking
                double currentRadius;
                if (progress <= 1 || distance > shrinkStart) {
                    currentRadius = 4; // outbound
                } else {
                    double shrinkProgress = Math.min(1.0, (shrinkStart - distance) / shrinkStart);
                    currentRadius = 4 - (3.5 * shrinkProgress); // 4 → 0.5
                }

                int particleCount = 100;
                for (int i = 0; i < particleCount; i++) {
                    double angle = 2 * Math.PI * i / particleCount;
                    double x = currentRadius * Math.cos(angle);
                    double z = currentRadius * Math.sin(angle);
                    double x2 = (currentRadius - .1) * Math.cos(angle);
                    double z2 = (currentRadius - .1) * Math.sin(angle);
                    Location particleLocation = center.clone().add(x, 0, z);
                    Location particleLocation2 = center.clone().add(x2, 0, z2);

                    user.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, particleLocation2, 1, 0, 0, 0, 0);
                    user.getWorld().spawnParticle(Particle.END_ROD, particleLocation, 5, 0, 0, 0, 0);
                }

                // damage
                if (!hitPlayer && ticks % 5 == 0) {
                    for (Entity entity : user.getWorld().getNearbyEntities(center, 4, .5, 4)) {
                        if (entity instanceof LivingEntity livingEntity && entity != user) {
                            hitEntityUUIDs.add(livingEntity.getUniqueId());
                        }
                    }

                    for (UUID uuid : hitEntityUUIDs) {
                        Bukkit.getPluginManager().callEvent(new CustomDamageEvent((LivingEntity) Bukkit.getEntity(uuid), user, lightDamage));
                        ((LivingEntity) Bukkit.getEntity(uuid)).setNoDamageTicks(5);
                    }

                    hitEntityUUIDs.clear();
                }
            }
        }.runTaskTimer(expertiseStylePlugin, 0L, 1L);
    }
}