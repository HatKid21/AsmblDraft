package org.example.utils;

import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

public class Ray {

    private static final double STEP = 0.1;

    public static Pos rayCast(Instance instance, Pos position, Vec direction){
        direction = direction.normalize().mul(STEP);
        for (int i = 0; i < 1000; i++) {
            if (!instance.getBlock(position).equals(Block.AIR)){
                Pos pos = new Pos(position.blockX(),position.blockY(),position.blockZ());
                return pos;
            }
            position = position.add(direction);
        }
        return null;
    }

}
