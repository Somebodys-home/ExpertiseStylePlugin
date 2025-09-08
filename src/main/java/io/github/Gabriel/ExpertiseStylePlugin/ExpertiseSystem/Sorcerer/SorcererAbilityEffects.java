package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Sorcerer;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.*;

public class SorcererAbilityEffects {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static NMLPlayerStats nmlPlayerStats;
    private static Set<UUID> hitEntityUUIDs = new HashSet<>();

    public SorcererAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        nmlPlayerStats = expertiseStylePlugin.getNmlPlayerStats();
    }

    public static void magicMissileEX(Player user, int hotbarSlot) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

        HashMap<DamageType, Double> damageStats = DamageConverter.convertPlayerStats2Damage(
                nmlPlayerStats.getProfileManager().getPlayerProfile(user.getUniqueId()).getStats());

        EnergyManager.useEnergy(user, 20);
        CooldownManager.putAllOtherAbilitesOnCooldown(user, 2.5, hotbarSlot);

        new BukkitRunnable() {
            int missiles = 0;
            int activeMissiles = 0;

            // actual missile
            @Override
            public void run() {
                missiles++;
                activeMissiles++;

                user.playSound(user.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, .6f, 1f);

                Set<UUID> hitEntityUUIDs = new HashSet<>();
                Random random = new Random();
                Vector direction = user.getEyeLocation().getDirection().normalize();

                Vector randomVec = new Vector(random.nextDouble() - 0.5, random.nextDouble() - 0.5, random.nextDouble() - 0.5);
                if (randomVec.lengthSquared() < 1e-6) randomVec = new Vector(0.001, 0.001, 0.001);
                randomVec.normalize();

                Vector curveAxis = direction.clone().crossProduct(randomVec);
                if (curveAxis.lengthSquared() < 1e-6) {
                    double yaw = Math.toRadians(user.getEyeLocation().getYaw());
                    curveAxis = new Vector(-Math.sin(yaw), 0, Math.cos(yaw));
                }
                curveAxis.normalize();

                // start on the player's right
                Vector littleBitRight = direction.clone().crossProduct(new Vector(0, 1, 0));
                if (littleBitRight.lengthSquared() < 1e-6) {
                    double yaw = Math.toRadians(user.getEyeLocation().getYaw());
                    littleBitRight = new Vector(-Math.sin(yaw), 0, Math.cos(yaw));
                }
                littleBitRight.normalize();

                Location start = user.getLocation().add(0, 1.2, 0).add(littleBitRight.multiply(0.4));

                // where to end
                Location end;
                RayTraceResult rayTraceResult = user.getWorld().rayTraceEntities(
                        user.getEyeLocation(),
                        user.getLocation().getDirection(),
                        16,
                        entity -> entity instanceof LivingEntity && !entity.equals(user)
                );

                if (rayTraceResult != null && rayTraceResult.getHitEntity() instanceof LivingEntity livingEntity) { // successfully traced a target
                    end = livingEntity.getLocation().add(0, 0.5, 0);
                } else {
                    Location startLocation = user.getLocation().add(0, 1, 0);
                    Vector forward = startLocation.getDirection().normalize().multiply(16); // max range
                    end = startLocation.clone().add(forward);
                }

                double curveDirection = random.nextBoolean() ? 1 : -1;
                double verticalCurveDirection = random.nextBoolean() ? 1 : -1;
                double curveAmount = 1.5 + random.nextDouble() * 3.0;
                double minHeight = 0.2 + random.nextDouble();
                double maxHeight = 1.0 + random.nextDouble() * 1.5;
                int particleInstances = 10;
                Vector finalCurveAxis = curveAxis;

                // particles
                new BukkitRunnable() {
                    int i = 0;

                    @Override
                    public void run() {
                        if (i > particleInstances) {
                            activeMissiles--;

                            if (missiles == 5 && activeMissiles == 0) user.removeMetadata("using ability", expertiseStylePlugin);

                            user.playSound(user.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, .8f, 1f);
                            user.getWorld().spawnParticle(Particle.EXPLOSION, end, 1, 0, 1, 0, 0);
                            cancel();
                            return;
                        }

                        double progress = (double) i / particleInstances;

                        if (i == 0) {
                            user.getWorld().spawnParticle(Particle.GLOW, start, 50, 0.1, 0.075, 0.1, 0);
                            i++;
                            return;
                        } else if (i == particleInstances) {
                            user.getWorld().spawnParticle(Particle.GLOW, end, 60, 0.1, 0.1, 0.1, 0);
                            i++;
                            return;
                        }

                        double baseX = start.getX() + (end.getX() - start.getX()) * progress;
                        double baseY = start.getY() + (end.getY() - start.getY()) * progress;
                        double baseZ = start.getZ() + (end.getZ() - start.getZ()) * progress;
                        double curveOffset = curveDirection * curveAmount * Math.sin(progress * Math.PI);
                        double heightFactor = minHeight + (maxHeight - minHeight) * Math.sin(progress * Math.PI);

                        double finalX = baseX + finalCurveAxis.getX() * curveOffset;
                        double finalY = baseY + heightFactor * verticalCurveDirection;
                        double finalZ = baseZ + finalCurveAxis.getZ() * curveOffset;

                        int worldMinY = user.getWorld().getMinHeight();
                        if (Double.isNaN(finalY) || finalY < worldMinY + 0.1) finalY = worldMinY + 0.1;

                        Location particleLocation = new Location(user.getWorld(), finalX, finalY, finalZ);
                        Collection<Entity> nearbyEntities = user.getWorld().getNearbyEntities(particleLocation, 2, 2, 2);

                        // entity collision
                        for (Entity entity : nearbyEntities) {
                            if (entity instanceof LivingEntity livingEntity && entity != user) {
                                if (hitEntityUUIDs.add(entity.getUniqueId())) { // still works
                                    Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, damageStats));
                                    livingEntity.setNoDamageTicks(0);
                                }
                            }
                        }

                        user.getWorld().spawnParticle(Particle.GLOW, particleLocation, 50, 0.1, 0.075, 0.1, 0);
                        i++;
                    }
                }.runTaskTimer(expertiseStylePlugin, 0L, 1L);

                if (missiles == 5) cancel();
            }
        }.runTaskTimer(expertiseStylePlugin, 0L, 5L);
    }
}