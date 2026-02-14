package org.example.animations;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.display.BlockDisplayMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.timer.SchedulerManager;
import net.minestom.server.utils.time.TimeUnit;

import java.util.ArrayList;
import java.util.List;

public class Animation {

    private static final SchedulerManager scheduler = MinecraftServer.getSchedulerManager();
    private final List<KeyFrame> keyFrames;
    private int delay = 1;

    public Animation(int delay){
        keyFrames = new ArrayList<>();
        this.delay = delay;
    }

    public void addKeyFrame(KeyFrame keyFrame){
       keyFrames.add(keyFrame);
    }

    public void playAimation(Instance instance,Block block) {
        Entity bd = new Entity(EntityType.BLOCK_DISPLAY);
        BlockDisplayMeta meta = (BlockDisplayMeta) bd.getEntityMeta();
        bd.setNoGravity(true);
        meta.setBlockState(block);
        meta.setScale(keyFrames.get(0).endScale);
        bd.setInstance(instance,keyFrames.get(0).endPos);
        for (KeyFrame keyFrame : keyFrames){
            scheduler.buildTask(() -> {
                meta.setTransformationInterpolationDuration(keyFrame.duration);
                meta.setTransformationInterpolationStartDelta(0);

                meta.setPosRotInterpolationDuration(keyFrame.duration);

                meta.setScale(keyFrame.endScale);
                meta.setTranslation(keyFrame.endScale.mul(-0.5));

                bd.teleport(keyFrame.endPos);


            }).delay(delay, TimeUnit.SERVER_TICK).schedule();
            delay += keyFrame.duration;
        }
        scheduler.buildTask(bd::remove).delay(delay,TimeUnit.SERVER_TICK).schedule();
    }
}
