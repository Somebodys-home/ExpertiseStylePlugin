package io.github.Gabriel.expertiseStylePlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class test implements CommandExecutor {
    private ExpertiseStylePlugin expertiseStylePlugin;

    public test(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (expertiseStylePlugin.getSelectedManager().getPlayerProfile(player.getUniqueId()) != null) {
                player.sendMessage("you DO have an ability profile");
            } else {
                player.sendMessage("you DO NOT have an ability profile");
            }
        }
        return true;
    }
}
