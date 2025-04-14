package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import io.github.Gabriel.damagePlugin.DamagePlugin;
import io.github.Gabriel.damagePlugin.customDamage.DamageType;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class SoldierAbilityEffects {
    private DamagePlugin damagePlugin;
    private Player user;

    public SoldierAbilityEffects(ExpertiseStylePlugin plugin, Player user) {
        damagePlugin = plugin.getDamagePlugin();
        this.user = user;
    }

    public void slash(ItemStack weapon) { // Todo: eventually make a custom slash model
        Location location = user.getLocation();

        if (damagePlugin.getDamageKeys().checkForDamageKey(weapon, DamageType.PHYSICAL)) {
            int damage = (int) (damagePlugin.getDamageKeys().getDamageKeyValue(weapon, DamageType.PHYSICAL) * 1.2);

            for (double i = -Math.PI / 2; i <= Math.PI / 2; i += Math.PI / 10) {
                double x = Math.sin(i) * 2;
                double z = Math.cos(i) * 2;
                Vector offset = new Vector(x, 1, z).rotateAroundY(-Math.toRadians(location.getYaw()));
                Location particleLocation = location.clone().add(offset);

                user.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLocation, 1);

                for (Entity entity : user.getWorld().getNearbyEntities(particleLocation, 1.5, 1.5, 1.5)) {
                    if (entity instanceof LivingEntity target && !entity.equals(user)) {
                        damagePlugin.getCustomDamager().doCustomDamage(target, user, DamageType.PHYSICAL, damage);
                    }
                }
            }
        }
    }
}
