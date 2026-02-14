package org.example.handlers;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;

public interface Handler {

    GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();

    void turnOn();

    void turnOff();

    boolean isOn();

}
