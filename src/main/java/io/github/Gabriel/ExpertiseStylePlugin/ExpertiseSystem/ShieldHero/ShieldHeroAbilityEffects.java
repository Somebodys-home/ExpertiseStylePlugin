package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ShieldHero;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.CooldownSystem.CooldownManager;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.NoOne.nMLEnergySystem.EnergyManager;
import io.github.NoOne.nMLShields.GuardingSystem;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class ShieldHeroAbilityEffects {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static GuardingSystem guardingSystem;

    public ShieldHeroAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        guardingSystem = expertiseStylePlugin.getNmlShields().getGuardingSystem();
    }

    public static void secondWind(Player user, int hotbarSlot) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

        EnergyManager.useEnergy(user, 20);
        CooldownManager.putAllOtherAbilitesOnCooldown(user, 2, hotbarSlot);

        user.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 30, 10, false, false, false));
        user.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 30, 255, false, false, false));
        user.playSound(user, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1f, 1f);

        new BukkitRunnable() {
            int timer = 0;

            @Override
            public void run() {
                timer++;

                // particles
                double radius = 5 * (1 - (timer / 30.0));
                int particleCount = 75;
                Location base = user.getLocation().clone().add(0, 0.5, 0);

                for (int i = 0; i < particleCount; i++) {
                    double angle = 2 * Math.PI * i / particleCount;
                    double x = Math.cos(angle) * radius;
                    double z = Math.sin(angle) * radius;

                    Location particleLocation = base.clone().add(x, 0, z);
                    user.getWorld().spawnParticle(Particle.END_ROD, particleLocation, 1, 0, 0, 0, 0);
                }

                if (timer % 10 == 0 && timer != 30) {
                    user.playSound(user, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1f, 1f);
                }

                if (timer == 30) {
                    // particle hemi sphere
                    for (double i = 0; i <= Math.PI / 2; i += Math.PI / 30) { // vertical circles
                        double rad = Math.sin(i);
                        double y = Math.cos(i);

                        for (double a = 0; a < Math.PI * 2; a+= Math.PI / 30) { // horizontal circles
                            double x = Math.cos(a) * rad;
                            double z = Math.sin(a) * rad;
                            Location particleLocation = user.getLocation().add(x, y, z);
                            Vector velocity = particleLocation.toVector().subtract(user.getLocation().toVector()).normalize().multiply(0.3);

                            user.getWorld().spawnParticle(Particle.END_ROD, particleLocation,0, velocity.getX(), velocity.getY(), velocity.getZ(), 3);
                            particleLocation.subtract(x, y, z); // reset location
                        }
                    }

                    user.playSound(user, Sound.ITEM_TOTEM_USE, 1f, 1f);
                    guardingSystem.fullyRegenerateGuard(user);
                    user.removeMetadata("using ability", expertiseStylePlugin);
                    cancel();
                }
            }
        }.runTaskTimer(expertiseStylePlugin, 0L, 1L);
    }
}