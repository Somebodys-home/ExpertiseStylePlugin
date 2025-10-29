package io.github.NoOne.expertiseStylePlugin.expertiseSystem.primordial;

import io.github.NoOne.damagePlugin.customDamage.CustomDamageEvent;
import io.github.NoOne.damagePlugin.customDamage.DamageConverter;
import io.github.NoOne.damagePlugin.customDamage.DamageType;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.CooldownSystem.CooldownManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLPlayerStats.profileSystem.ProfileManager;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class PrimordialAbilityEffects {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static ProfileManager profileManager;

    public PrimordialAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        profileManager = expertiseStylePlugin.getProfileManager();
    }

    public static void chuckRock(Player user, int hotbarSlot) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

        HashSet<UUID> hitEntityUUIDs = new HashSet<>();
        HashMap<DamageType, Double> physicalDamage = DamageConverter.multiplyDamageMap(DamageConverter.convertPlayerStat2Damage(
                                            profileManager.getPlayerProfile(user.getUniqueId()).getStats(), "physicaldamage"), 1.5);

        FallingBlock stone = user.getWorld().spawnFallingBlock(user.getLocation().add(0, 1.5, 0), Bukkit.createBlockData(Material.STONE_BUTTON));

        EnergyManager.useEnergy(user, 10);
        CooldownManager.putAllOtherAbilitiesOnCooldown(user, 1, hotbarSlot);

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

    public static void pumpkinBomb(Player user, int hotbarSlot) {
        HashSet<UUID> hitEntityUUIDs = new HashSet<>();
        HashMap<DamageType, Double> earth = DamageConverter.multiplyDamageMap(DamageConverter.convertPlayerStat2Damage(profileManager.getPlayerProfile(user.getUniqueId()).getStats(), "earthdamage"), 1.5);
        HashMap<DamageType, Double> fire = DamageConverter.multiplyDamageMap(DamageConverter.convertPlayerStat2Damage(profileManager.getPlayerProfile(user.getUniqueId()).getStats(), "firedamage"), 1.5);
        HashMap<DamageType, Double> totalDamage = DamageConverter.multiplyDamageMap(DamageConverter.convertPlayerStats2Damage(profileManager.getPlayerProfile(user.getUniqueId()).getStats()), .5);

        totalDamage.remove("earthdamage");
        totalDamage.remove("firedamage");
        totalDamage.putAll(earth);
        totalDamage.putAll(fire);

        BlockFace face = yawToFace(user.getLocation().getYaw());
        Directional data = (Directional) Bukkit.createBlockData(Material.JACK_O_LANTERN);
        data.setFacing(face);
        FallingBlock pumpkinBomb = user.getWorld().spawnFallingBlock(user.getLocation().add(0, 1, 0), data);

        // sweep particle
        Location baseLocation = user.getEyeLocation().clone().subtract(0, .5, 0);
        Vector forward = baseLocation.getDirection().normalize().multiply(1.2);
        Location swing = baseLocation.clone().add(forward);
        user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, swing, 1);

        EnergyManager.useEnergy(user, 10);
        CooldownManager.putAllOtherAbilitiesOnCooldown(user, 1.25, hotbarSlot);
        pumpkinBomb.setCancelDrop(true);
        pumpkinBomb.setVelocity(user.getLocation().getDirection().multiply(.5).add(new Vector(0, 1, 0)));
        user.playSound(user.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 1f, 1f);
        user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);

        new BukkitRunnable() {
            boolean kaboom = false;

            @Override
            public void run() {
                Location pumpkinBombLocation = pumpkinBomb.getLocation();
                Collection<Entity> nearbyEntities = user.getWorld().getNearbyEntities(pumpkinBombLocation, 1, 1, 1);
                Particle.DustOptions stem = new Particle.DustOptions(Color.fromRGB(98, 143, 64), 2F);

                nearbyEntities.remove(pumpkinBomb);
                nearbyEntities.remove(user);
                user.getWorld().spawnParticle(Particle.DUST, pumpkinBombLocation.add(0, 1, 0), 1, 0, 0, 0, stem);

                // trigger
                if (!nearbyEntities.isEmpty() || (!pumpkinBomb.isValid() || pumpkinBomb.isDead())) kaboom = true;

                // explosion
                if (kaboom) {
                    pumpkinBomb.remove();
                    user.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, pumpkinBombLocation, 1);
                    user.playSound(pumpkinBombLocation, Sound.ENTITY_GENERIC_EXPLODE, 2f, 1f);
                    user.playSound(pumpkinBombLocation, Sound.ENTITY_WITHER_DEATH, 1.5f, 1f);

                    // fireworks
                    new BukkitRunnable() {
                        int times = 0;

                        @Override
                        public void run() {
                            times++;

                            for (int i = 0; i < 3; i++) {
                                Location fireworkLocation = pumpkinBombLocation.clone();
                                fireworkLocation.add((Math.random() - .5) * 12, (Math.random() - .5) * 12, (Math.random() - .5) * 12);

                                Firework firework = (Firework) user.getWorld().spawnEntity(fireworkLocation, EntityType.FIREWORK_ROCKET);
                                FireworkMeta fireworkMeta = firework.getFireworkMeta();

                                fireworkMeta.addEffect(FireworkEffect.builder()
                                        .withColor(Color.ORANGE)
                                        .withFade(Color.YELLOW)
                                        .with(FireworkEffect.Type.BALL)
                                        .flicker(true)
                                        .build());
                                fireworkMeta.setPower(0);
                                firework.setFireworkMeta(fireworkMeta);
                                firework.setMetadata("ability_firework", new FixedMetadataValue(expertiseStylePlugin, true));
                                firework.detonate();
                            }


                            if (times == 6) cancel();
                        }
                    }.runTaskTimer(expertiseStylePlugin,  6L, 2L);

                    // damage
                    user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

                    for (Entity entity : user.getWorld().getNearbyEntities(pumpkinBombLocation, 4, 4, 4)) {
                        if (entity instanceof LivingEntity livingEntity && entity != user) {
                            hitEntityUUIDs.add(livingEntity.getUniqueId());
                        }
                    }

                    for (UUID uuid : hitEntityUUIDs) {
                        Bukkit.getPluginManager().callEvent(new CustomDamageEvent((LivingEntity) Bukkit.getEntity(uuid), user, totalDamage));
                    }

                    user.removeMetadata("using ability", expertiseStylePlugin);
                    cancel();
                }

            }
        }.runTaskTimer(expertiseStylePlugin, 0L, 1L);

        user.removeMetadata("using ability", expertiseStylePlugin);
    }

    private static BlockFace yawToFace(float yaw) {
        yaw = (yaw % 360 + 360) % 360;
        if (yaw < 45 || yaw >= 315) return BlockFace.NORTH;
        if (yaw < 135) return BlockFace.EAST;
        if (yaw < 225) return BlockFace.SOUTH;
        return BlockFace.WEST;
    }
}