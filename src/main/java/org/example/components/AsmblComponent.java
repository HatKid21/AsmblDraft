package org.example.components;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.tag.Tag;
import org.example.CustomPlayer;
import org.example.utils.Ray;
import org.example.utils.Scheme;

import java.util.*;

public class AsmblComponent {

    private final CustomPlayer player;

    private final List<Entity> holo;

    private Pos placePos;
    private Scheme scheme;

    private static final Pos[] directions = {
            new Pos (0,1,0),
            new Pos (1,0,0),
            new Pos (0,0,1),
            new Pos (-1,0,0),
            new Pos (0,0,-1),
            new Pos(0,-1,0)
    };


    public AsmblComponent(CustomPlayer player){
        this.player = player;
        holo = new ArrayList<>();
        placePos = new Pos(0,0,0);
        scheme = new Scheme();
    }

    public void drawHologram(){
        if (player.getSelectComponent().isDone()){
            Pos playerPos = player.getPosition().add(0,player.getEyeHeight(),0);
            Vec direction = player.getPosition().direction();
            Pos blockPos = Ray.rayCast(player.getInstance(),playerPos,direction);
            if (blockPos == null){
                return;
            }
            if (placePos.equals(blockPos)) {
                return;
            }

            Scheme scheme = player.getSelectComponent().getScheme();

            if (!this.scheme.equals(scheme)){
                clearHolo();
                this.scheme = scheme;
                blockPos = createHoloBlocks(player.getInstance(),blockPos,scheme);
            } else{
                blockPos = teleportHoloBlocks(blockPos);
            }

            placePos = blockPos;
        }
    }

    private Pos teleportHoloBlocks(Pos blockPos){
        blockPos = findValidPos(blockPos);
       for (Entity entity : holo){
           Pos relativePos = getRelativePos(entity);
           entity.teleport(blockPos.add(relativePos));
       }
       return blockPos;
    }

    private Pos createHoloBlocks(Instance instance, Pos blockPos,Scheme scheme){
        blockPos = findValidPos(blockPos);
        for (Map.Entry<Pos, Block> entry : scheme.getBlocks().entrySet()){
            Block block = entry.getValue();
            Pos relativePos = entry.getKey();

            Entity entity = new Entity(EntityType.BLOCK_DISPLAY);

            entity.setTag(Tag.Integer("RelativeX"),relativePos.blockX());
            entity.setTag(Tag.Integer("RelativeY"),relativePos.blockY());
            entity.setTag(Tag.Integer("RelativeZ"),relativePos.blockZ());

            BlockDisplayMeta displayMeta = (BlockDisplayMeta) entity.getEntityMeta();
            displayMeta.setBlockState(block);
            entity.setNoGravity(true);
            displayMeta.setScale(new Vec(1,1,1));

            entity.setInstance(instance,blockPos.add(relativePos));

            holo.add(entity);
        }
        return blockPos;
    }


    public void clearHolo(){
        for (Entity entity : holo){
            entity.remove();
        }
        holo.clear();
        placePos = new Pos(0,0,0);
        scheme = new Scheme();
    }

    public void assembly(){
        for (Entity entity : holo){
            Pos relativePos = getRelativePos(entity);
            BlockDisplayMeta meta = (BlockDisplayMeta) entity.getEntityMeta();
            Block block = meta.getBlockStateId();
            player.getInstance().setBlock(relativePos.add(placePos),block);
        }
        clearHolo();
    }

    private Pos getRelativePos(Entity entity){
       int relativeX = entity.getTag(Tag.Integer("RelativeX"));
       int relativeY = entity.getTag(Tag.Integer("RelativeY"));
       int relativeZ = entity.getTag(Tag.Integer("RelativeZ"));
       return new Pos(relativeX,relativeY,relativeZ);
    }

    private Pos findValidPos(Pos startPos){
        Queue<Pos> queue = new LinkedList<>();
        Set<Pos> visited = new HashSet<>();

        queue.add(startPos);
        visited.add(startPos);

        int maxIterations = 30;
        int iteration = 0;

        while (!queue.isEmpty() && iteration < maxIterations){
            Pos curPos = queue.poll();
            iteration++;

            if (canPlace(curPos)){
                return curPos;
            }

            for (Pos dir : directions){
                Pos next = curPos.add(dir);

                if (!visited.contains(next)){
                    visited.add(next);
                    queue.add(next);
                }
            }

        }

        return null;

    }

    private boolean canPlace(Pos pos){
        for (Map.Entry<Pos,Block> entry : scheme.getBlocks().entrySet()){
            Pos relativePos = entry.getKey();
            if (!player.getInstance().getBlock(pos.add(relativePos)).isAir()){
                return false;
            }
        }
        return true;
    }

}
