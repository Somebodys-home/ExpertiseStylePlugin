package io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem;

import io.github.Gabriel.expertiseStylePlugin.ExpertiseStylePlugin;
import io.github.Gabriel.expertiseStylePlugin.ExpertiseSystem.ExpertiseMenus.ExpertiseMenu;
import io.github.NoOne.menuSystem.MenuSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChooseExpertiseCommand implements CommandExecutor {
    private ExpertiseStylePlugin expertiseStylePlugin;

    public ChooseExpertiseCommand(ExpertiseStylePlugin expertiseStylePlugin) {
        this.expertiseStylePlugin = expertiseStylePlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            new ExpertiseMenu(expertiseStylePlugin, MenuSystem.getPlayerMenuUtility(player)).open();
        }
        return true;
    }
}