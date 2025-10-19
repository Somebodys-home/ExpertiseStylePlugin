package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Cavalier;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseAbilityItemTemplate;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class CavalierAbilityItems extends AbilityItemTemplate {
    public CavalierAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack seismicSlam() {
        return ExpertiseAbilityItemTemplate.makeExpertiseAbilityItem(
                "cavalier",
                "Seismic Slam",
                "Jump into the air, then crash into the ground, launching anyone nearby away from you",
                false,
                "Area",
                16,
                0,
                10,
                30,
                List.of("§f§n200%" + "§r§f" + " Weapon Damage \uD83D\uDDE1"),
                null,
                List.of(SPEAR, HAMMER));
    }
}