package io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SelectedAbilities {
    private String style1;
    private String style2;
    private String expertise1;
    private String expertise2;

    public SelectedAbilities(String style1, String style2, String expertise1, String expertise2) {
        this.style1 = style1;
        this.style2 = style2;
        this.expertise1 = expertise1;
        this.expertise2 = expertise2;
    }

    public String[] getSelectedAbilitiesArray() {
        return new String[]{style1, style2, expertise1, expertise2};
    }

    public void resetSelectedAbilities() {
        style1 = ChatColor.AQUA + "Empty Style Ability";
        style2 = ChatColor.AQUA + "Empty Style Ability";
        expertise1 = ChatColor.LIGHT_PURPLE + "Empty Expertise Ability";
        expertise2 = ChatColor.LIGHT_PURPLE + "Empty Expertise Ability";
    }

    public String getStyle1() {
        return style1;
    }

    public String getStyle2() {
        return style2;
    }

    public String getExpertise1() {
        return expertise1;
    }

    public String getExpertise2() {
        return expertise2;
    }

    public void setStyle1(String style1) {
        this.style1 = style1;
    }

    public void setStyle2(String style2) {
        this.style2 = style2;
    }

    public void setExpertise1(String expertise1) {
        this.expertise1 = expertise1;
    }

    public void setExpertise2(String expertise2) {
        this.expertise2 = expertise2;
    }
}