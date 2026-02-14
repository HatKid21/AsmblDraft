package org.example;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import org.example.components.AsmblComponent;
import org.example.handlers.AsmblHandler;
import org.example.handlers.ConnectionHandler;
import org.example.handlers.SelectHandler;
import org.example.handlers.SpawnHandler;

public class Main {
    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();
        MinecraftServer.getConnectionManager().setPlayerProvider(CustomPlayer::new);

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instance = instanceManager.createInstanceContainer();

        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));

        ConnectionHandler connectionHandler = new ConnectionHandler(instance);
        connectionHandler.turnOn();

        SpawnHandler spawnHandler = new SpawnHandler();
        spawnHandler.turnOn();

        SelectHandler selectHandler = new SelectHandler();
        selectHandler.turnOn();

        AsmblHandler asmblHandler = new AsmblHandler();
        asmblHandler.turnOn();

        instance.setChunkSupplier(LightingChunk::new);

        server.start("localhost",25565);
    }
}