package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.Marksman;

import io.github.Gabriel.expertiseStylePlugin.AbilitySystem.AbilityItemTemplate;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseItemTemplate;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import static io.github.NoOne.nMLItems.ItemType.*;

public class MarksmanAbilityItems extends AbilityItemTemplate {
    public MarksmanAbilityItems() {
        super(ExpertiseStylePlugin.getInstance());
    }

    public static ItemStack rapidShot() {
        return ExpertiseItemTemplate.makeExpertiseAbilityItem(
                "marksman",
                "Rapid Shot",
                "Nock an arrow every .5 second this ability is toggled on. After untoggling, or nocking 10 arrows, shoot all prepared arrows rapidly",
                true,
                "Single",
                70,
                0,
                10,
                25,
                List.of("§f§n100%" + "§r§f" + " Weapon Damage \uD83D\uDDE1 §7§o(per arrow)"),
                null,
                List.of(BOW));
    }
}