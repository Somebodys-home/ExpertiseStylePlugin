package io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveAbilitiesSystem;

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

    public String[] getSelectedAbilitesArray() {
        return new String[]{style1, style2, expertise1, expertise2};
    }

    public String[] getSelectedAbilitesFromPlayerInventory(Player player) {
        return new String[]{player.getInventory().getItem(0).getItemMeta().getDisplayName(),
                            player.getInventory().getItem(1).getItemMeta().getDisplayName(),
                            player.getInventory().getItem(2).getItemMeta().getDisplayName(),
                            player.getInventory().getItem(3).getItemMeta().getDisplayName()};
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