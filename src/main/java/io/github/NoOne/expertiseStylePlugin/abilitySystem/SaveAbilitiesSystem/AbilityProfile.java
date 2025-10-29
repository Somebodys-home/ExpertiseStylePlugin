package io.github.NoOne.expertiseStylePlugin.abilitySystem.SaveAbilitiesSystem;

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
