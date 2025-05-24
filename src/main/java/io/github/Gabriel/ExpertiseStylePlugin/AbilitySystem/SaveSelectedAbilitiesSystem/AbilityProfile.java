package io.github.Gabriel.expertiseStylePlugin.AbilitySystem.SaveSelectedAbilitiesSystem;

public class AbilityProfile {
    private SelectedAbilities selectedAbilities;

    public AbilityProfile(SelectedAbilities selectedAbilities) {
        this.selectedAbilities = selectedAbilities;
    }

    public SelectedAbilities getSelectedAbilities() {
        return selectedAbilities;
    }

    public void setSelectedAbilities(SelectedAbilities selectedAbilities) {
        this.selectedAbilities = selectedAbilities;
    }

}
