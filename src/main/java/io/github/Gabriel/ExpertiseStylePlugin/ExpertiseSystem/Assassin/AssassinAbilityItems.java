package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Assassin;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class AssassinAbilityItems extends AbilityItemTemplate {
    public AssassinAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack slashandDash() {
        return ExpertiseItemTemplate.makeExpertiseAbilityItem(
                "assassin",
                "Slash & Dash",
                "Dash forwards, dealing damage to anyone in your way",
                "Area",
                10,
                0,
                5,
                20,
                List.of("§f§n150%" + "§r§f" + " Weapon Damage \uD83D\uDDE1"),
                null,
                List.of(DAGGER),
                false);
    }
}