package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Sorcerer;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class SorcererAbilityItems extends AbilityItemTemplate {
    public SorcererAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack magicMissileEX() {
        return ExpertiseItemTemplate.makeExpertiseAbilityItem(
                "sorcerer",
                "Magic Missile EX",
                "Shoot your basic attack 5 times. (Don't get it twisted, this is still the lamest ability you could muster.)",
                false,
                "Single",
                16,
                0,
                5,
                20,
                List.of("§f§n100%" + "§r§f" + " Weapon Damage \uD83D\uDDE1 §7§o(per missile)"),
                null,
                List.of(WAND, STAFF, CATALYST));
    }
}