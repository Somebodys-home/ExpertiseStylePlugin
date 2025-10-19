package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Annulled;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class AnnulledAbilityEffects {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static ProfileManager profileManager;
    private static Set<UUID> hitEntityUUIDs = new HashSet<>();

    public AnnulledAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        profileManager = expertiseStylePlugin.getProfileManager();
    }

    public static void blackHole(Player user, int hotbarSlot) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

        HashMap<DamageType, Double> darkDamage = DamageConverter.multiplyDamageMap(DamageConverter.convertPlayerStat2Damage(
                profileManager.getPlayerProfile(user.getUniqueId()).getStats(), "darkdamage"), 3) ;

        EnergyManager.useEnergy(user, 50);
        CooldownManager.putAllOtherAbilitiesOnCooldown(user, 1.5, hotbarSlot);
        user.playSound(user, Sound.ENTITY_WITHER_SHOOT, 1f, 1f);

        new BukkitRunnable() {
            int duration = 0;
            Location center = user.getLocation().clone().add(0, 1, 0);
            Vector velocity = user.getLocation().getDirection().normalize().multiply(.15);

            @Override
            public void run() {
                duration++;

                center.add(velocity);

                // tiny black hole
                for (double i = 0; i <= Math.PI; i += Math.PI / 5) { // vertical circles
                    double radius = Math.sin(i) / 2; // .5 block
                    double y = Math.cos(i) / 2; // .5 block

                    for (double a = 0; a < Math.PI * 2; a+= Math.PI / 5) { // horizontal circles
                        double x = Math.cos(a) * radius;
                        double z = Math.sin(a) * radius;
                        Location particleLocation = center.clone().add(x, y, z);
                        Particle.DustOptions black = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 1.0F);

                        user.getWorld().spawnParticle(Particle.DUST, particleLocation, 1, 0, 0, 0, black);
                        particleLocation.subtract(x, y, z); // reset location
                    }
                }

                // triggering big black hole
                Collection<Entity> triggeringEntities = user.getWorld().getNearbyEntities(center, .5, .5, .5);
                triggeringEntities.remove(user);

                // big / collapsing black hole
                if (duration == 100 || !center.getBlock().isPassable() || !triggeringEntities.isEmpty()) {
                    cancel();
                    user.playSound(user, Sound.ITEM_ELYTRA_FLYING, 2f, 1f);
                    user.playSound(user, Sound.ENTITY_WITHER_DEATH, 1f, 1f);
                    user.removeMetadata("using ability", expertiseStylePlugin);

                    new BukkitRunnable() {
                        int duration = 0;
                        double size = 0;
                        double particleStep;
                        int pullradius = 15;

                        @Override
                        public void run() {
                            duration++;

                            if (duration < 100 && size < 8) {
                                size = Math.min(8, size + 2.5);
                            } else if (duration == 100) {
                                size = Math.max(1, size - 2);
                                user.playSound(user, Sound.ENTITY_WITHER_HURT, 1f, 1f);
                            } else if (duration == 115) {
                                size = Math.max(1, size - 2);
                                user.playSound(user, Sound.ENTITY_WITHER_HURT, 1f, 1.5f);
                            } else if (duration == 130) {
                                size = Math.max(1, size - 2);
                                user.playSound(user, Sound.ENTITY_WITHER_HURT, 1f, 2f);
                            }

                            particleStep = Math.PI / (size * 2);

                            // big black hole
                            for (double i = 0; i <= Math.PI; i += particleStep) {
                                for (double a = 0; a < Math.PI * 2; a+= particleStep) {
                                    double x = Math.cos(a) * Math.sin(i) * size;
                                    double y = Math.cos(i) * size;
                                    double z = Math.sin(a) * Math.sin(i) * size;
                                    Location particleLocation = center.clone().add(x, y, z);

                                    user.getWorld().spawnParticle(Particle.SQUID_INK, particleLocation, 3, 0, 0, 0);
                                }
                            }

                            // pull
                            for (Entity entity : center.getWorld().getNearbyEntities(center, pullradius, pullradius, pullradius)) {
                                if (entity instanceof LivingEntity && entity != user) {
                                    Vector toCenter = center.clone().subtract(entity.getLocation()).toVector();
                                    double distance = toCenter.length();
                                    Vector pull = toCenter.normalize().multiply(.03 * distance);

                                    entity.setVelocity(entity.getVelocity().add(pull));
                                }
                            }

                            // explosion
                            if (duration == 155) {
                                cancel();
                                user.stopSound(Sound.ITEM_ELYTRA_FLYING);
                                user.playSound(user, Sound.ENTITY_WITHER_SPAWN, 2f, 1f);
                                user.playSound(user, Sound.ENTITY_WITHER_DEATH, .5f, 1f);

                                double explosionRadius = 8;
                                for (double i = 0; i <= Math.PI; i += Math.PI / 60) { // vertical circles
                                    double radius = Math.sin(i) * 2; // 2 block
                                    double y = Math.cos(i) * 2; // 2 block

                                    for (double a = 0; a < Math.PI * 2; a+= Math.PI / 60) { // horizontal circles
                                        double x = Math.cos(a) * radius;
                                        double z = Math.sin(a) * radius;
                                        Location particleLocation = center.clone().add(x, y, z);
                                        Vector velocity = particleLocation.toVector().subtract(center.toVector()).normalize().multiply(0.3);

                                        if (Math.random() < .3) { // random chance to spawn explosion for every expanding particle
                                            double finalA = a;
                                            double finalI = i;
                                            new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    double explosionX = Math.cos(finalA) * explosionRadius;
                                                    double explosionY = Math.cos(finalI) * explosionRadius;
                                                    double explosionZ = Math.sin(finalA) * explosionRadius;
                                                    Location explosionLocation = center.clone().add(explosionX, explosionY, explosionZ);

                                                    user.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, explosionLocation, 1);
                                                }
                                            }.runTaskLater(expertiseStylePlugin, 3L);
                                        }

                                        user.getWorld().spawnParticle(Particle.SQUID_INK, particleLocation, 0,
                                                                        velocity.getX(), velocity.getY(), velocity.getZ(), 7);
                                        particleLocation.subtract(x, y, z); // reset location
                                    }
                                }

                                for (Entity entity : user.getWorld().getNearbyEntities(center, explosionRadius, explosionRadius, explosionRadius)) {
                                    if (entity instanceof LivingEntity livingEntity && entity != user) {
                                        hitEntityUUIDs.add(livingEntity.getUniqueId());
                                    }
                                }

                                if (!hitEntityUUIDs.isEmpty()) {
                                    user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

                                    for (UUID uuid : hitEntityUUIDs) {
                                        Bukkit.getPluginManager().callEvent(new CustomDamageEvent((LivingEntity) Bukkit.getEntity(uuid), user, darkDamage));
                                        ((LivingEntity) Bukkit.getEntity(uuid)).setNoDamageTicks(5);
                                    }
                                }

                                user.removeMetadata("using ability", expertiseStylePlugin);
                                hitEntityUUIDs.clear();
                            }
                        }
                    }.runTaskTimer(expertiseStylePlugin, 0L, 1L);
                }
            }
        }.runTaskTimer(expertiseStylePlugin, 0L, 1L);
    }
}