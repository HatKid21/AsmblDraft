package org.example.components;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.tag.Tag;
import org.example.CustomPlayer;
import org.example.utils.Ray;
import org.example.utils.Scheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AsmblComponent {

    private final CustomPlayer player;

    private final List<Entity> prevHolo;

    private Pos prevPos;
    private Scheme prevScheme;

    public AsmblComponent(CustomPlayer player){
        this.player = player;
        prevHolo = new ArrayList<>();
        prevPos = new Pos(0,0,0);
        prevScheme = new Scheme();
    }

    public void drawHologram(){
        if (player.getSelectComponent().isDone()){
            Pos playerPos = player.getPosition().add(0,player.getEyeHeight(),0);
            Vec direction = player.getPosition().direction();
            Pos blockPos = Ray.rayCast(player.getInstance(),playerPos,direction);
            if (blockPos == null){
                return;
            }
            blockPos = blockPos.add(0,1,0);
            if (prevPos.equals(blockPos)) {
                return;
            }

            Scheme scheme = player.getSelectComponent().getScheme();

            if (!prevScheme.equals(scheme)){
                clearHolo();
                createHoloBlocks(player.getInstance(),blockPos,scheme);
            } else{
                teleportHoloBlocks(blockPos);
            }

            prevPos = blockPos;
        }
    }

    private void teleportHoloBlocks(Pos blockPos){
       for (Entity entity : prevHolo){
           Pos relativePos = getRelativePos(entity);
           entity.teleport(blockPos.add(relativePos));
       }
    }

    private void createHoloBlocks(Instance instance, Pos blockPos,Scheme scheme){
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

            prevHolo.add(entity);
        }
    }


    public void clearHolo(){
        for (Entity entity : prevHolo){
            entity.remove();
        }
        prevHolo.clear();
    }

    public void assembly(){
        for (Entity entity : prevHolo){
            Pos relativePos = getRelativePos(entity);
            BlockDisplayMeta meta = (BlockDisplayMeta) entity.getEntityMeta();
            Block block = meta.getBlockStateId();
            player.getInstance().setBlock(relativePos.add(prevPos),block);
        }
        clearHolo();
    }

    private Pos getRelativePos(Entity entity){
       int relativeX = entity.getTag(Tag.Integer("RelativeX"));
       int relativeY = entity.getTag(Tag.Integer("RelativeY"));
       int relativeZ = entity.getTag(Tag.Integer("RelativeZ"));
       return new Pos(relativeX,relativeY,relativeZ);
    }

}
