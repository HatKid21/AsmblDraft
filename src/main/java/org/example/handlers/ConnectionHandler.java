package org.example.handlers;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.Instance;

public class ConnectionHandler extends AbstractHandler {

    private final Instance instance;

    public ConnectionHandler(Instance instance){
        this.instance = instance;

        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event ->{
            final Player player = event.getPlayer();
            event.setSpawningInstance(instance);
            player.setRespawnPoint(new Pos(0,42,0));
        });

    }

}
