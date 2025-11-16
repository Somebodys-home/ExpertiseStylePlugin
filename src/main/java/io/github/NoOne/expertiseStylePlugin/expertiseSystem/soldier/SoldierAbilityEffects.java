package io.github.NoOne.expertiseStylePlugin.expertiseSystem.soldier;

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
import org.bukkit.util.Vector;

import java.util.*;

public class SoldierAbilityEffects {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static ProfileManager profileManager;
    public SoldierAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        profileManager = expertiseStylePlugin.getProfileManager();
    }

    public static void slash(Player user, int hotbarSlot) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

        HashSet<UUID> hitEntityUUIDs = new HashSet<>();
        Location location = user.getLocation();
        HashMap<DamageType, Double> damageStats = DamageConverter.multiplyDamageMap(DamageConverter.convertPlayerStats2Damage(
                profileManager.getPlayerStats(user.getUniqueId())), 1.2);

        CooldownManager.putAllOtherAbilitiesOnCooldown(user, 1, hotbarSlot);
        EnergyManager.useEnergy(user, 15);
        user.playSound(location, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);

        for (double i = -Math.PI / 2; i <= Math.PI / 2; i += Math.PI / 10) {
            double x = Math.sin(i) * 2;
            double z = Math.cos(i) * 2;
            Vector offset = new Vector(x, 1, z).rotateAroundY(-Math.toRadians(location.getYaw()));
            Location particleLocation = location.clone().add(offset);

            user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLocation, 1);

            for (Entity entity : user.getWorld().getNearbyEntities(particleLocation, 1.5, 1.5, 1.5)) {
                if (!entity.equals(user)) {
                    hitEntityUUIDs.add(entity.getUniqueId());
                }
            }
        }

        for (UUID uuid : hitEntityUUIDs) {
            if (Bukkit.getEntity(uuid) instanceof LivingEntity livingEntity) {
                Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, damageStats));
                hitEntityUUIDs.remove(uuid);
            }
        }

        user.removeMetadata("using ability", expertiseStylePlugin);
    }
}