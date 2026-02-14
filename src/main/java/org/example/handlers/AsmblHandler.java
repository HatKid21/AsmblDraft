package org.example.handlers;

import net.kyori.adventure.text.Component;
import net.minestom.server.event.player.PlayerTickEvent;
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

    }

}
