package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Primordial;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLPlayerStats.NMLPlayerStats;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class PrimordialAbilityEffects {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static NMLPlayerStats nmlPlayerStats;
    private static Set<UUID> hitEntityUUIDs = new HashSet<>();

    public PrimordialAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        nmlPlayerStats = expertiseStylePlugin.getNmlPlayerStats();
    }

    public static void chuckRock(Player user, int hotbarSlot) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

        HashMap<DamageType, Double> physicalDamage = DamageConverter.multiplyDamageMap(DamageConverter.convertPlayerStat2Damage(
                                            nmlPlayerStats.getProfileManager().getPlayerProfile(user.getUniqueId()).getStats(), "physicaldamage"), 1.5);

        FallingBlock stone = user.getWorld().spawnFallingBlock(user.getLocation().add(0, 1.5, 0), Bukkit.createBlockData(Material.STONE_BUTTON));

        EnergyManager.useEnergy(user, 10);
        CooldownManager.putAllOtherAbilitiesOnCooldown(user, 2.5, hotbarSlot);

        stone.setCancelDrop(true);
        stone.setVelocity(user.getLocation().getDirection().multiply(2).add(new Vector(0, .3, 0)));
        user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, .5f, 2f);

        // sweep particle
        Location baseLocation = user.getEyeLocation().clone().subtract(0, .5, 0);
        Vector forward = baseLocation.getDirection().normalize().multiply(1.2);
        Location swing = baseLocation.clone().add(forward);
        user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, swing, 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                Location stoneLocation = stone.getLocation();

                for (Entity entity : user.getWorld().getNearbyEntities(stoneLocation, 1, 1, 1)) {
                    if (entity instanceof LivingEntity livingEntity && entity != user) {
                        hitEntityUUIDs.add(livingEntity.getUniqueId());
                    }
                }

                if (!hitEntityUUIDs.isEmpty()) {
                    user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

                    for (UUID uuid : hitEntityUUIDs) {
                        Bukkit.getPluginManager().callEvent(new CustomDamageEvent((LivingEntity) Bukkit.getEntity(uuid), user, physicalDamage));
                        hitEntityUUIDs.remove(uuid);
                    }

                    user.getWorld().spawnParticle(Particle.BLOCK, stoneLocation, 100, 0, 0 ,0, 0, Bukkit.createBlockData(Material.STONE));
                    user.playSound(stoneLocation, Sound.BLOCK_STONE_BREAK, 2f, 2f);
                    user.removeMetadata("using ability", expertiseStylePlugin);
                    cancel();
                }

                if (!stone.isValid() || stone.isDead()) {
                    user.getWorld().spawnParticle(Particle.BLOCK, stoneLocation, 100, 0, 0 ,0, 0, Bukkit.createBlockData(Material.STONE));
                    user.playSound(stoneLocation, Sound.BLOCK_STONE_BREAK, 2f, 2f);
                    cancel();
                }
            }
        }.runTaskTimer(expertiseStylePlugin, 0L, 1L);

        user.removeMetadata("using ability", expertiseStylePlugin);
    }
}