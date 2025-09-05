package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.MartialArtist;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLItems.ItemSystem;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class MartialArtistAbilityEffects {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private Set<UUID> hitEntityUUIDs = new HashSet<>();
    private Player user;
    private int hotbarSlot;

    public MartialArtistAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin, Player user, int hotbarSlot) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        this.user = user;
        this.hotbarSlot = hotbarSlot;
    }

    public void tenHitCombo(ItemStack weapon) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));
        user.setMetadata("falling", new FixedMetadataValue(expertiseStylePlugin, true));

        HashMap<DamageType, Double> multipliedDamageMap = DamageConverter.convertStatMap2DamageTypes(ItemSystem.multiplyAllDamageStats(weapon, .25));
        CooldownManager.putAllOtherAbilitesOnCooldown(user, 2, hotbarSlot);
        EnergyManager.useEnergy(user, 5);

        // punch 1
        dashUntilCollision(2, 5, new BukkitRunnable() {
            @Override
            public void run() {
                Location baseLocation = user.getLocation().add(0, 1.5, 0);
                Vector forward = user.getLocation().getDirection().normalize().multiply(2);
                Location punch = baseLocation.clone().add(forward);

                user.getWorld().spawnParticle(Particle.CRIT, punch, 100, 0.25, 0.25, 0.25);
                user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f);

                for (Entity entity : user.getWorld().getNearbyEntities(punch, 1.5, 2, 1.5)) {
                    if (entity != user) {
                        hitEntityUUIDs.add(entity.getUniqueId());
                    }
                }

                for (UUID uuid : hitEntityUUIDs) {
                    if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                        Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));
                        livingEntity.setNoDamageTicks(0);
                    }
                }

                if (hitEntityUUIDs.isEmpty()) {
                    user.removeMetadata("using ability", expertiseStylePlugin);
                } else {
                    user.swingMainHand();
                }
            }
        });

        // punch 2
        new BukkitRunnable() {
            @Override
            public void run() {
                if (user.hasMetadata("using ability")) {
                    hitEntityUUIDs.clear();
                    CooldownManager.putAllOtherAbilitesOnCooldown(user, .5, hotbarSlot);

                    dashUntilCollision(2, 5, new BukkitRunnable() {
                        @Override
                        public void run() {
                            EnergyManager.useEnergy(user, 5);

                            Location baseLocation = user.getLocation().add(0, 1.5, 0);
                            Vector forward = user.getLocation().getDirection().normalize().multiply(2);
                            Location punch = baseLocation.clone().add(forward);

                            user.getWorld().spawnParticle(Particle.CRIT, punch, 100, 0.25, 0.25, 0.25);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f);
                            user.swingOffHand();

                            for (Entity entity : user.getWorld().getNearbyEntities(punch, 1.5, 2, 1.5)) {
                                if (entity != user) {
                                    hitEntityUUIDs.add(entity.getUniqueId());
                                }
                            }

                            for (UUID uuid : hitEntityUUIDs) {
                                if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                                    Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));
                                    livingEntity.setNoDamageTicks(0);
                                }
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (hitEntityUUIDs.isEmpty()) user.removeMetadata("using ability", expertiseStylePlugin);
                                }
                            }.runTaskLater(expertiseStylePlugin, 1L);
                        }
                    });
                }
            }
        }.runTaskLater(expertiseStylePlugin, 10L);

        // punch 3
        new BukkitRunnable() {
            @Override
            public void run() {
                if (user.hasMetadata("using ability")) {
                    hitEntityUUIDs.clear();
                    CooldownManager.putAllOtherAbilitesOnCooldown(user, .5, hotbarSlot);

                    dashUntilCollision(2, 5, new BukkitRunnable() {
                        @Override
                        public void run() {
                            EnergyManager.useEnergy(user, 5);

                            Location baseLocation = user.getLocation().add(0, 1.5, 0);
                            Vector forward = user.getLocation().getDirection().normalize().multiply(2);
                            Location punch = baseLocation.clone().add(forward);

                            user.getWorld().spawnParticle(Particle.CRIT, punch, 100, 0.25, 0.25, 0.25);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f);
                            user.swingMainHand();

                            for (Entity entity : user.getWorld().getNearbyEntities(punch, 1.5, 2, 1.5)) {
                                if (entity != user) {
                                    hitEntityUUIDs.add(entity.getUniqueId());
                                }
                            }

                            for (UUID uuid : hitEntityUUIDs) {
                                if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                                    Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));
                                    livingEntity.setNoDamageTicks(0);
                                }
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (hitEntityUUIDs.isEmpty()) user.removeMetadata("using ability", expertiseStylePlugin);
                                }
                            }.runTaskLater(expertiseStylePlugin, 1L);
                        }
                    });
                }
            }
        }.runTaskLater(expertiseStylePlugin, 20L);

        // kick 4
        new BukkitRunnable() {
            @Override
            public void run() {
                if (user.hasMetadata("using ability")) {
                    hitEntityUUIDs.clear();
                    CooldownManager.putAllOtherAbilitesOnCooldown(user, .5, hotbarSlot);

                    dashUntilCollision(2, 5, new BukkitRunnable() {
                        @Override
                        public void run() {
                            EnergyManager.useEnergy(user, 10);

                            Vector jump = new Vector(0, 1, 0);
                            user.setVelocity(jump);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f);

                            Location baseLocation = user.getLocation().add(0, 4, 0);
                            Vector forward = user.getLocation().getDirection().normalize().multiply(2);
                            Location punch = baseLocation.clone().add(forward);

                            user.getWorld().spawnParticle(Particle.CRIT, punch, 150, 0.15, 1, 0.15);

                            for (Entity entity : user.getWorld().getNearbyEntities(punch, 1.5, 2, 1.5)) {
                                if (entity != user) {
                                    hitEntityUUIDs.add(entity.getUniqueId());
                                }
                            }

                            for (UUID uuid : hitEntityUUIDs) {
                                if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                                    Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));
                                    livingEntity.setNoDamageTicks(0);
                                    livingEntity.setVelocity(jump); // knockback
                                }
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (hitEntityUUIDs.isEmpty()) user.removeMetadata("using ability", expertiseStylePlugin);
                                }
                            }.runTaskLater(expertiseStylePlugin, 1L);
                        }
                    });
                }
            }
        }.runTaskLater(expertiseStylePlugin, 30L);

        // kick 5
        new BukkitRunnable() {
            @Override
            public void run() {
                if (user.hasMetadata("using ability")) {
                    hitEntityUUIDs.clear();
                    CooldownManager.putAllOtherAbilitesOnCooldown(user, .5, hotbarSlot);

                    dashUntilCollision(2, 5, new BukkitRunnable() {
                        @Override
                        public void run() {
                            EnergyManager.useEnergy(user, 10);

                            Vector slam = new Vector(0, -1, 0);
                            user.setVelocity(slam);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f);

                            Location baseLocation = user.getLocation().add(0, 4, 0);
                            Vector forward = user.getLocation().getDirection().normalize().multiply(2);
                            Location punch = baseLocation.clone().add(forward);

                            user.getWorld().spawnParticle(Particle.CRIT, punch, 150, 0.15, 4, 0.15);

                            for (Entity entity : user.getWorld().getNearbyEntities(user.getLocation(), 2, 4, 2)) {
                                if (entity != user) {
                                    hitEntityUUIDs.add(entity.getUniqueId());
                                }
                            }

                            for (UUID uuid : hitEntityUUIDs) {
                                if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                                    Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));
                                    livingEntity.setNoDamageTicks(0);
                                    livingEntity.setVelocity(slam); // knockback
                                }
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (hitEntityUUIDs.isEmpty()) user.removeMetadata("using ability", expertiseStylePlugin);
                                }
                            }.runTaskLater(expertiseStylePlugin, 1L);
                        }
                    });
                }
            }
        }.runTaskLater(expertiseStylePlugin, 40L);

        // kick 6
        new BukkitRunnable() {
            @Override
            public void run() {
                if (user.hasMetadata("using ability")) {
                    hitEntityUUIDs.clear();
                    CooldownManager.putAllOtherAbilitesOnCooldown(user, .5, hotbarSlot);

                    dashUntilCollision(2, 5, new BukkitRunnable() {
                        @Override
                        public void run() {
                            EnergyManager.useEnergy(user, 10);

                            Location baseLocation = user.getLocation().add(0, 1.5, 0);
                            Vector forward = user.getLocation().getDirection().normalize().multiply(2);
                            Location punch = baseLocation.clone().add(forward);

                            user.getWorld().spawnParticle(Particle.CRIT, punch, 100, 0.15, 0.15, 0.15);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f);

                            for (Entity entity : user.getWorld().getNearbyEntities(punch, 1.5, 2, 1.5)) {
                                if (entity != user) {
                                    hitEntityUUIDs.add(entity.getUniqueId());
                                }
                            }

                            for (UUID uuid : hitEntityUUIDs) {
                                if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                                    Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));
                                    livingEntity.setNoDamageTicks(0);

                                    Vector knockback = livingEntity.getLocation().toVector().subtract(user.getLocation().toVector()).normalize().setY(.2);
                                    livingEntity.setVelocity(knockback);
                                }
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (hitEntityUUIDs.isEmpty()) user.removeMetadata("using ability", expertiseStylePlugin);
                                }
                            }.runTaskLater(expertiseStylePlugin, 1L);
                        }
                    });
                }
            }
        }.runTaskLater(expertiseStylePlugin, 50L);

        // punch 7
        new BukkitRunnable() {
            @Override
            public void run() {
                if (user.hasMetadata("using ability")) {
                    hitEntityUUIDs.clear();
                    CooldownManager.putAllOtherAbilitesOnCooldown(user, .8, hotbarSlot);

                    dashUntilCollision(2, 5, new BukkitRunnable() {
                        @Override
                        public void run() {
                            EnergyManager.useEnergy(user, 10);

                            Location baseLocation = user.getLocation().add(0, 1.5, 0);
                            Vector forward = user.getLocation().getDirection().normalize().multiply(2);
                            Location punch = baseLocation.clone().add(forward);

                            user.swingMainHand();
                            user.getWorld().spawnParticle(Particle.CRIT, punch, 100, 0.25, 0.25, 0.25);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);

                            for (int i = 0; i < 8; i++) {
                                double angle = 2 * Math.PI * i / 8;
                                double x = Math.cos(angle);
                                double z = Math.sin(angle);

                                Location particleLocation = user.getLocation().clone().add(x, 1, z);
                                user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLocation, 1, 0, 0, 0, 0);
                            }

                            for (Entity entity : user.getWorld().getNearbyEntities(punch, 1.5, 2, 1.5)) {
                                if (entity != user) {
                                    hitEntityUUIDs.add(entity.getUniqueId());
                                }
                            }

                            for (UUID uuid : hitEntityUUIDs) {
                                if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                                    Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));
                                    livingEntity.setNoDamageTicks(0);

                                    Vector knockback = livingEntity.getLocation().toVector().subtract(user.getLocation().toVector()).normalize().setY(.1);
                                    livingEntity.setVelocity(knockback);
                                }
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (hitEntityUUIDs.isEmpty()) user.removeMetadata("using ability", expertiseStylePlugin);
                                }
                            }.runTaskLater(expertiseStylePlugin, 1L);
                        }
                    });
                }
            }
        }.runTaskLater(expertiseStylePlugin, 60L);

        // punch 8
        new BukkitRunnable() {
            @Override
            public void run() {
                if (user.hasMetadata("using ability")) {
                    hitEntityUUIDs.clear();
                    CooldownManager.putAllOtherAbilitesOnCooldown(user, .8, hotbarSlot);

                    dashUntilCollision(2, 5, new BukkitRunnable() {
                        @Override
                        public void run() {
                            EnergyManager.useEnergy(user, 10);

                            Location baseLocation = user.getLocation().add(0, 1.5, 0);
                            Vector forward = user.getLocation().getDirection().normalize().multiply(2);
                            Location punch = baseLocation.clone().add(forward);

                            user.swingOffHand();
                            user.getWorld().spawnParticle(Particle.CRIT, punch, 100, 0.25, 0.25, 0.25);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);

                            for (int i = 0; i < 8; i++) {
                                double angle = 2 * Math.PI * i / 8;
                                double x = Math.cos(angle);
                                double z = Math.sin(angle);

                                Location particleLocation = user.getLocation().clone().add(x, 1, z);
                                user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLocation, 1, 0, 0, 0, 0);
                            }

                            for (Entity entity : user.getWorld().getNearbyEntities(punch, 1.5, 2, 1.5)) {
                                if (entity != user) {
                                    hitEntityUUIDs.add(entity.getUniqueId());
                                }
                            }

                            for (UUID uuid : hitEntityUUIDs) {
                                if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                                    Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));
                                    livingEntity.setNoDamageTicks(0);

                                    Vector knockback = livingEntity.getLocation().toVector().subtract(user.getLocation().toVector()).normalize().setY(.1);
                                    livingEntity.setVelocity(knockback);
                                }
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (hitEntityUUIDs.isEmpty()) user.removeMetadata("using ability", expertiseStylePlugin);
                                }
                            }.runTaskLater(expertiseStylePlugin, 1L);
                        }
                    });
                }
            }
        }.runTaskLater(expertiseStylePlugin, 75L);

        // punch 9
        new BukkitRunnable() {
            @Override
            public void run() {
                if (user.hasMetadata("using ability")) {
                    hitEntityUUIDs.clear();
                    CooldownManager.putAllOtherAbilitesOnCooldown(user, .8, hotbarSlot);

                    dashUntilCollision(2, 5, new BukkitRunnable() {
                        @Override
                        public void run() {
                            EnergyManager.useEnergy(user, 10);

                            Location baseLocation = user.getLocation().add(0, 1.5, 0);
                            Vector forward = user.getLocation().getDirection().normalize().multiply(2);
                            Location punch = baseLocation.clone().add(forward);

                            user.swingMainHand();
                            user.getWorld().spawnParticle(Particle.CRIT, punch, 100, 0.25, 0.25, 0.25);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);

                            for (int i = 0; i < 8; i++) {
                                double angle = 2 * Math.PI * i / 8;
                                double x = Math.cos(angle);
                                double z = Math.sin(angle);

                                Location particleLocation = user.getLocation().clone().add(x, 1, z);
                                user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLocation, 1, 0, 0, 0, 0);
                            }

                            for (Entity entity : user.getWorld().getNearbyEntities(punch, 1.5, 2, 1.5)) {
                                if (entity != user) {
                                    hitEntityUUIDs.add(entity.getUniqueId());
                                }
                            }

                            for (UUID uuid : hitEntityUUIDs) {
                                if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                                    Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));
                                    livingEntity.setNoDamageTicks(0);

                                    Vector knockback = livingEntity.getLocation().toVector().subtract(user.getLocation().toVector()).normalize().setY(.1);
                                    livingEntity.setVelocity(knockback);
                                }
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (hitEntityUUIDs.isEmpty()) user.removeMetadata("using ability", expertiseStylePlugin);
                                }
                            }.runTaskLater(expertiseStylePlugin, 1L);
                        }
                    });
                }
            }
        }.runTaskLater(expertiseStylePlugin, 90L);

        // uppercut 10
        new BukkitRunnable() {
            HashMap<DamageType, Double> multipliedDamageMap = DamageConverter.convertStatMap2DamageTypes(ItemSystem.multiplyAllDamageStats(weapon, .75));

            @Override
            public void run() {
                if (user.hasMetadata("using ability")) {
                    user.setMetadata("falling", new FixedMetadataValue(expertiseStylePlugin, true));
                    hitEntityUUIDs.clear();
                    CooldownManager.putAllOtherAbilitesOnCooldown(user, .8, hotbarSlot);

                    dashUntilCollision(2, 5, new BukkitRunnable() {
                        @Override
                        public void run() {
                            EnergyManager.useEnergy(user, 25);

                            Vector jump = user.getLocation().getDirection().multiply(1.5).setY(1.5);
                            Location baseLocation = user.getLocation().add(0, 4, 0);
                            Vector forward = user.getLocation().getDirection().normalize().multiply(2);
                            Location punch = baseLocation.clone().add(forward);

                            user.setVelocity(jump);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);
                            user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 1f, 1f);
                            user.playSound(user.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.PLAYERS, 3f, 1f);

                            // uppercut particles
                            new BukkitRunnable() {
                                int particleticks = 20;
                                int i = 0;
                                double angleStep = Math.PI / 20;

                                @Override
                                public void run() {
                                    Location baseLocation = user.getLocation().clone().add(0, 1, 0);
                                    Location trail = baseLocation.clone().add(user.getLocation().getDirection().normalize().multiply(-1));

                                    for (double j = 0; j < 20; j++) {
                                        double radius = 1.5;
                                        double angle = (2 * (i * angleStep) + (j * 0.05)) - 180;
                                        double reverseAngle = angle - 180;
                                        double x = Math.cos(angle) * radius;
                                        double z = Math.sin(angle) * radius;
                                        double reverseX = Math.cos(reverseAngle) * radius;
                                        double reverseZ = Math.sin(reverseAngle) * radius;
                                        Location soulFireLocation = user.getLocation().clone().add(x, 2, z);
                                        Location fireLocation = user.getLocation().clone().add(reverseX,  2, reverseZ);

                                        user.getWorld().spawnParticle(Particle.SNOWFLAKE, trail, 10, .05, .05, .05, 0);
                                        user.getWorld().spawnParticle(Particle.FLAME, fireLocation, 30, 0, 0, 0, 0);
                                        user.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, soulFireLocation, 30, 0, 0, 0, 0);
                                    }

                                    particleticks--;
                                    i++;
                                    if (particleticks == 0) cancel();
                                }
                            }.runTaskTimer(expertiseStylePlugin, 0L, 1L);

                            for (Entity entity : user.getWorld().getNearbyEntities(punch, 1.5, 2, 1.5)) {
                                if (entity != user) {
                                    hitEntityUUIDs.add(entity.getUniqueId());
                                }
                            }

                            for (UUID uuid : hitEntityUUIDs) {
                                if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                                    Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));
                                    livingEntity.setNoDamageTicks(0);
                                    livingEntity.setVelocity(jump.multiply(2).setY(2)); // knockback
                                }
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    hitEntityUUIDs.clear();
                                    user.removeMetadata("using ability", expertiseStylePlugin);
                                }
                            }.runTaskLater(expertiseStylePlugin, 1L);
                        }
                    });
                }
            }
        }.runTaskLater(expertiseStylePlugin, 105L);
    }

    private void dashUntilCollision(double velocity, int fallbackTicks, BukkitRunnable onFinish) {
        Vector dashDirection = user.getLocation().getDirection().normalize();
        Vector punch1 = dashDirection.clone().multiply(velocity);
        punch1.setY(0);
        user.setVelocity(punch1);

        user.getAttribute(Attribute.GENERIC_STEP_HEIGHT).setBaseValue(1);
        new BukkitRunnable() {
            int ticks = 0;
            boolean triggered = false;

            @Override
            public void run() {
                Location baseLocation = user.getLocation().add(0, 1, 0);
                Location hitbox = baseLocation.clone().add(dashDirection);

                for (Entity entity : user.getWorld().getNearbyEntities(hitbox, 1.5, 2, 1.5)) {
                    if (entity != user && entity instanceof LivingEntity livingEntity) {
                        hitEntityUUIDs.add(entity.getUniqueId());
                        user.setVelocity(new Vector(0, 0, 0));
                        triggered = true;
                    }
                }

                if (!triggered && ticks >= fallbackTicks) {
                    user.setVelocity(new Vector(0, 0, 0));
                    triggered = true;
                }

                if (triggered) {
                    cancel();
                    onFinish.runTaskLater(expertiseStylePlugin, 1L);
                    user.getAttribute(Attribute.GENERIC_STEP_HEIGHT).setBaseValue(.6);
                }

                ticks++;
            }
        }.runTaskTimer(expertiseStylePlugin, 0L, 1L);
    }
}