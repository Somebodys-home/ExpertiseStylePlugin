package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.MartialArtist;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class MartialArtistAbilityItems extends AbilityItemTemplate {
    public MartialArtistAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack tenHitCombo() {
        return ExpertiseItemTemplate.makeExpertiseAbilityItem(
                "martial",
                "10-Hit Combo",
                "Perform a devastating 10 hit combo, ending with an uppercut. WARNING: Prone to failure if the target's kneecaps are unbroken.",
                "Single",
                5,
                0,
                15,
                115,
                List.of("§f§n300%" + "§r§f" + " Weapon Damage \uD83D\uDDE1"),
                null,
                List.of(GLOVE));
    }
}