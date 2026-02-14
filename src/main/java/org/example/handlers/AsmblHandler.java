package org.example.handlers;

import net.minestom.server.entity.PlayerHand;
import net.minestom.server.event.player.PlayerTickEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import org.example.CustomPlayer;
import org.example.components.AsmblComponent;
import org.example.utils.Tools;

public class AsmblHandler extends AbstractHandler{

    public AsmblHandler(){

        globalEventHandler.addListener(PlayerTickEvent.class, event ->{
            if (isOn) {
                final CustomPlayer player = (CustomPlayer) event.getPlayer();
                if (player.getItemInMainHand().equals(Tools.ASMBL_TOOL)){
                    AsmblComponent asmblComponent = player.getAsmblComponent();
                    asmblComponent.drawHologram();
                }
            }
        });

        globalEventHandler.addListener(PlayerUseItemEvent.class,event ->{
            if (isOn){
                final CustomPlayer player = (CustomPlayer) event.getPlayer();
                if (event.getHand() == PlayerHand.MAIN && event.getItemStack().equals(Tools.ASMBL_TOOL)){
                    //TODO
                }
            }
        });

    }

}
