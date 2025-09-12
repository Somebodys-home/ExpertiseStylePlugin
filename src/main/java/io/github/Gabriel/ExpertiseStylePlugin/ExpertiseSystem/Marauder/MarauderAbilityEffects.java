package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marauder;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLItems.ItemSystem;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class MarauderAbilityEffects {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static NMLPlayerStats nmlPlayerStats;
    private static Set<UUID> hitEntityUUIDs = new HashSet<>();

    public MarauderAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        nmlPlayerStats = expertiseStylePlugin.getNmlPlayerStats();
    }

    public static void bladeTornado(Player user, int hotbarSlot) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

        HashMap<DamageType, Double> damageStats = DamageConverter.multiplyDamageMap(DamageConverter.convertPlayerStats2Damage(
                nmlPlayerStats.getProfileManager().getPlayerProfile(user.getUniqueId()).getStats()), .5);

        EnergyManager.useEnergy(user, 30);
        CooldownManager.putAllOtherAbilitesOnCooldown(user, 6, hotbarSlot);
        user.getAttribute(Attribute.STEP_HEIGHT).setBaseValue(1);

        new BukkitRunnable() {
            int tornadoTicks = 100;

            @Override
            public void run() {

                // tiny dash
                Vector knockback = user.getLocation().getDirection().multiply(.5);
                knockback.setY(-2);
                user.setVelocity(knockback);

                // particles
                if (tornadoTicks % 2 == 0) {
                    double radius = 1;
                    double radius2 = 1.5;
                    double raidus3 = 2;
                    int biggestParticleCount = 8;

                    for (int i = 0; i < biggestParticleCount; i++) {
                        double angle = 2 * Math.PI * i / biggestParticleCount;
                        double x2 = radius2 * Math.cos(angle);
                        double z2 = radius2 * Math.sin(angle);
                        double x3 = raidus3 * Math.cos(angle);
                        double z3 = raidus3 * Math.sin(angle);

                        if (i % (biggestParticleCount / 4) == 0) {
                            double x = radius * Math.cos(angle);
                            double z = radius * Math.sin(angle);

                            Location particleLocation = user.getLocation().clone().add(x, 0.5, z);
                            user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLocation, 1, 0, 0, 0, 0);
                        }

                        Location particleLocation2 = user.getLocation().clone().add(x2, 1.25, z2);
                        Location particleLocation3 = user.getLocation().clone().add(x3, 2, z3);
                        user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLocation2, 1, 0, 0, 0, 0);
                        user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLocation3, 1, 0, 0, 0, 0);
                    }
                }

                if (tornadoTicks % 3 == 0) user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, .5f);

                if (tornadoTicks % 5 == 0) {
                    for (Entity entity : user.getWorld().getNearbyEntities(user.getLocation(), 2.25, 2, 2.25)) {
                        if (entity instanceof LivingEntity livingEntity && !entity.equals(user)) {
                            Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, damageStats));
                            livingEntity.setNoDamageTicks(5);
                        }
                    }
                }

                tornadoTicks--;
                if (tornadoTicks == 0) {
                    this.cancel();
                    user.removeMetadata("using ability", expertiseStylePlugin);
                    user.getAttribute(Attribute.STEP_HEIGHT).setBaseValue(.6);
                }
            }
        }.runTaskTimer(expertiseStylePlugin, 0L, 1L);
    }
}