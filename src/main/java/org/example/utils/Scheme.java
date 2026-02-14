package org.example.utils;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.Block;

import java.util.HashMap;
import java.util.Map;

public class Scheme {

    private final Map<Pos, Block> blocks;

    public Scheme(){
        this.blocks = new HashMap<>();
    }

    public void add(Pos pos, Block block){
        blocks.put(pos,block);
    }

    public void clear(){
        blocks.clear();
    }

    public Map<Pos, Block> getBlocks() {
        return blocks;
    }
}
