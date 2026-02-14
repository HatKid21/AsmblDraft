package org.example.components;

import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import org.example.CustomPlayer;
import org.example.utils.Scheme;

public class SelectComponent {

    private Point first;
    private Point second;

    private boolean done;

    private final CustomPlayer player;
    private final Scheme scheme;

    public SelectComponent(CustomPlayer player){
        this.player = player;
        this.scheme = new Scheme();
        done = false;
    }

    public void select(Point pos){
        if (first == null){
            first = pos;
        } else if (second == null){
            second = pos;
            done = true;
        } else{
            first = second;
            second = pos;
            done = true;
        }
        player.sendMessage(Component.text("Pos1: " + first + " Pos2:" + second));
        updateMap();
    }

    private void updateMap(){
        if (first == null || second == null){
            return;
        }
        scheme.clear();
        Instance instance = player.getInstance();

        int minX = Math.min(first.blockX(),second.blockX());
        int maxX = Math.max(first.blockX(),second.blockX());

        int minY = Math.min(first.blockY(),second.blockY());
        int maxY = Math.max(first.blockY(),second.blockY());

        int minZ = Math.min(first.blockZ(),second.blockZ());
        int maxZ = Math.max(first.blockZ(),second.blockZ());

        for (int x = minX; x <= maxX;x++){
            for (int y = minY; y <= maxY;y++){
                for (int z = minZ; z <= maxZ;z++){
                    Pos blockPos = new Pos(x,y,z);
                    Block block = instance.getBlock(blockPos);
                    if (block.equals(Block.AIR)){
                        continue;
                    }

                    scheme.add(blockPos.sub(first),block);
                }
            }
        }
    }

    public Scheme getScheme() {
        return scheme;
    }

    public boolean isDone() {
        return done;
    }
}
