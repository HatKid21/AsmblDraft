package org.example;

import net.minestom.server.entity.Player;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;
import org.example.components.AsmblComponent;
import org.example.components.SelectComponent;

public class CustomPlayer extends Player {

    private SelectComponent selectComponent;
    private AsmblComponent asmblComponent;

    public CustomPlayer(PlayerConnection playerConnection, GameProfile gameProfile) {
        super(playerConnection, gameProfile);
        this.selectComponent = new SelectComponent(this);
        this.asmblComponent = new AsmblComponent(this);
    }

    public SelectComponent getSelectComponent() {
        return selectComponent;
    }

    public AsmblComponent getAsmblComponent() {
        return asmblComponent;
    }

}
