package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import io.github.Gabriel.damagePlugin.DamagePlugin;
import io.github.Gabriel.damagePlugin.customDamage.DamageKey;
import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.util.Vector;

import java.util.*;

public class SoldierAbilityEffects { // todo: make the damage calc work
    private Player user;

    public SoldierAbilityEffects(Player user) {
        this.user = user;
    }

    public void slash(ItemStack weapon) {
        DamageKey damageKey = new DamageKey(weapon);
        Location location = user.getLocation();
        Set<UUID> hitEntityUUIDs = new HashSet<>();
        HashMap<DamageType, Double> damage = damageKey.multiplyAllDamageStats(1.2);

        int count = 0;
        for (Map.Entry<DamageType, Double> entry : damage.entrySet()) {
            count++;
        }
        System.out.println(count);

        for (double i = -Math.PI / 2; i <= Math.PI / 2; i += Math.PI / 10) {
            double x = Math.sin(i) * 2;
            double z = Math.cos(i) * 2;
            Vector offset = new Vector(x, 1, z).rotateAroundY(-Math.toRadians(location.getYaw()));
            Location particleLocation = location.clone().add(offset);

            user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLocation, 1);

            for (Entity entity : user.getWorld().getNearbyEntities(particleLocation, 1.5, 1.5, 1.5)) {
                if (!entity.equals(user)) {
                    UUID uuid = entity.getUniqueId();
                    if (!hitEntityUUIDs.contains(uuid)) {
                        entity.setMetadata("gothit", new FixedMetadataValue(ExpertiseStylePlugin.getInstance(), true));
                        hitEntityUUIDs.add(uuid);
                    }
                }
            }

        }

        for (UUID uuid : hitEntityUUIDs) {
            Entity entity = Bukkit.getEntity(uuid);
            if (entity instanceof LivingEntity living && entity.hasMetadata("gothit")) {
                DamagePlugin.getInstance().getCustomDamager().doDamage(living, user, damage);
                entity.removeMetadata("gothit", ExpertiseStylePlugin.getInstance());
            }
        }
    }
}