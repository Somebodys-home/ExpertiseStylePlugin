package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Soldier;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SoldierAbilityEffects {
    private Player caster;

    public SoldierAbilityEffects(Player caster) {
        this.caster = caster;
    }

    public void slash() { // Todo: eventually make a custom slash model
        Location location = caster.getLocation();

        for (double i = -Math.PI / 2; i <= Math.PI / 2; i += Math.PI / 10) {
            double x = Math.sin(i) * 2;
            double z = Math.cos(i) * 2;
            Vector offset = new Vector(x, 1, z).rotateAroundY(-Math.toRadians(location.getYaw()));
            Location particleLocation = location.clone().add(offset);

            caster.getWorld().spawnParticle(Particle.SWEEP_ATTACK, particleLocation, 1);

            for (Entity entity : caster.getWorld().getNearbyEntities(particleLocation, 1.5, 1.5, 1.5)) {
                if (entity instanceof LivingEntity && !entity.equals(caster)) {
                    LivingEntity mob = (LivingEntity) entity;
                    mob.damage(5, caster);
                }
            }
        }
    }
}
