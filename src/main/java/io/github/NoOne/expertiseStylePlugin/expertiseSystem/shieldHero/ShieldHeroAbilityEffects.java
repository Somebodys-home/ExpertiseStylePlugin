package io.github.NoOne.expertiseStylePlugin.expertiseSystem.shieldHero;

import io.github.NoOne.expertiseStylePlugin.abilitySystem.AbilityEffects;
import io.github.NoOne.expertiseStylePlugin.abilitySystem.cooldownSystem.CooldownManager;
import io.github.NoOne.expertiseStylePlugin.ExpertiseStylePlugin;
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

public class ShieldHeroAbilityEffects {
    private static ExpertiseStylePlugin expertiseStylePlugin;
    private static GuardingSystem guardingSystem;

    public ShieldHeroAbilityEffects(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
        guardingSystem = expertiseStylePlugin.getGuardingSystem();
    }

    public static void secondWind(Player user, int hotbarSlot) {
        user.setMetadata("using ability", new FixedMetadataValue(expertiseStylePlugin, true));

        EnergyManager.useEnergy(user, 20);
        CooldownManager.putAllOtherAbilitiesOnCooldown(user, 2, hotbarSlot);
        user.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 30, 10, false, false, false));
        user.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 30, 255, false, false, false));
        user.playSound(user, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1f, 1f);

        new BukkitRunnable() {
            int timer = 0;

            @Override
            public void run() {
                timer++;

                /// charge up
                double radius = 5 * (1 - (timer / 30.0));
                int particleCount = 75;
                Location center = user.getLocation().clone().add(0, 0.15, 0);

                AbilityEffects.verticalParticleCircle(Particle.END_ROD, center, radius, particleCount);

                if (timer % 10 == 0 && timer != 30) {
                    user.playSound(user, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1f, 1f);
                }

                /// explosion
                if (timer == 30) {
                    AbilityEffects.expandingParticleSphere(Particle.END_ROD, user.getLocation(), 4, 30, .3);
                    user.playSound(user, Sound.ITEM_TOTEM_USE, 1f, 1f);
                    guardingSystem.fullyRegenerateGuard(user);
                    user.removeMetadata("using ability", expertiseStylePlugin);
                    cancel();
                }
            }
        }.runTaskTimer(expertiseStylePlugin, 0L, 1L);
    }
}