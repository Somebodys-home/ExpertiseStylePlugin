package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Cavalier;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLItems.ItemSystem;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;

public class CavalierAbilityEffects {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private Set<UUID> hitEntityUUIDs = new HashSet<>();
    private Player user;
    private int hotbarSlot;

    public CavalierAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin, Player user, int hotbarSlot) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        this.user = user;
        this.hotbarSlot = hotbarSlot;
    }

    public void seismicSlam(ItemStack weapon) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));
        user.setMetadata("falling", new FixedMetadataValue(expertiseStylePlugin, true));

        HashMap<DamageType, Double> multipliedDamageMap = DamageConverter.convertStatMap2DamageTypes(ItemSystem.multiplyAllDamageStats(weapon, 2));
        EnergyManager.useEnergy(user, 30);
        CooldownManager.putAllOtherAbilitesOnCooldown(user, 4, hotbarSlot);

        // jump
        Vector jump = user.getLocation().getDirection().multiply(.5);
        jump.setY(1.5);
        user.setVelocity(jump);
        user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 2f);

        // trail particles
        BukkitTask flyingParticles = new BukkitRunnable() {
            @Override
            public void run() {
                user.getWorld().spawnParticle(Particle.SNOWFLAKE, user.getLocation(), 50, .25, 1, .25, 0);
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
                        flyingParticles.cancel();
                        user.stopSound(Sound.ENTITY_PLAYER_ATTACK_SWEEP);
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
                                Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));

                                // knockback
                                Vector knockback = livingEntity.getLocation().toVector().subtract(user.getLocation().toVector()).normalize().multiply(1.2);
                                knockback.setY(.75);
                                livingEntity.setVelocity(knockback);
                            }
                        }

                        user.removeMetadata("using ability", expertiseStylePlugin);
                        user.removeMetadata("falling", expertiseStylePlugin);
                        cancel();
                    }
                }
            }.runTaskTimer(expertiseStylePlugin, 0L, 1L);
        }, 20L);
    }
}