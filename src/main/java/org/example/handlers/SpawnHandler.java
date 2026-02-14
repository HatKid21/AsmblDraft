package org.example.handlers;

import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerSpawnEvent;
import org.example.utils.Tools;

public class SpawnHandler extends AbstractHandler {

    public SpawnHandler(){
        globalEventHandler.addListener(PlayerSpawnEvent.class, event ->{
            if (isOn){
                final Player player = event.getPlayer();
                player.setGameMode(GameMode.CREATIVE);
                player.getInventory().addItemStack(Tools.SELECT_TOOL);
                player.getInventory().addItemStack(Tools.ASMBL_TOOL);
            }
        });
    }

}
