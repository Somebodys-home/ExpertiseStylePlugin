package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import io.github.Gabriel.damagePlugin.customDamage.CustomDamageEvent;
import io.github.Gabriel.damagePlugin.customDamage.DamageConverter;
import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLItems.ItemSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.*;

public class SoldierAbilityEffects {
    private ExpertiseStylePlugin expertiseStylePlugin;
    private Set<UUID> hitEntityUUIDs = new HashSet<>();
    private Player user;
    private int hotbarSlot;

    public SoldierAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin, Player user, int hotbarSlot) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        this.user = user;
        this.hotbarSlot = hotbarSlot;
    }

    public void slash(ItemStack weapon) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

        Location location = user.getLocation();
        HashMap<DamageType, Double> multipliedDamageMap = DamageConverter.convertStatMap2DamageTypes(ItemSystem.multiplyAllDamageStats(weapon, 1.2));

        CooldownManager.putAllOtherAbilitesOnCooldown(user, 1, hotbarSlot);
        EnergyManager.useEnergy(user, 15);
        user.playSound(user.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1f, 1f);

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
                Bukkit.getPluginManager().callEvent(new CustomDamageEvent(livingEntity, user, multipliedDamageMap));
                hitEntityUUIDs.remove(uuid);
            }
        }

        user.removeMetadata("using ability", expertiseStylePlugin);
    }
}