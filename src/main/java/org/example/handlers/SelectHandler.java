package org.example.handlers;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerHand;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import org.example.CustomPlayer;
import org.example.utils.Tools;

public class SelectHandler extends AbstractHandler{

    public SelectHandler(){
        globalEventHandler.addListener(PlayerUseItemOnBlockEvent.class, event ->{
            if (isOn){
                if (event.getHand() == PlayerHand.MAIN && event.getItemStack().equals(Tools.SELECT_TOOL)) {
                    final CustomPlayer player = (CustomPlayer) event.getPlayer();
                    player.getSelectComponent().select(event.getPosition());
                }
            }
        });
    }

}
