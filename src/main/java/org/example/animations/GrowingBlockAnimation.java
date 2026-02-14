package org.example.animations;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;

public class GrowingBlockAnimation {

    public static Animation getAnim(int delay,int duration,Pos startPos, Pos endPos){
        Animation animation = new Animation(delay);
        startPos = startPos.add(0.5,0.5,0.5);
        endPos = endPos.add(0.5,0.5,0.5);
        KeyFrame st = new KeyFrame();
        st.endPos = startPos;
        st.endScale = new Vec(0.1);
        st.duration = 0;
        animation.addKeyFrame(st);
        KeyFrame end = new KeyFrame();
        end.endPos = endPos;
        end.endScale = new Vec(1);
        end.duration = duration;
        animation.addKeyFrame(end);
        return animation;
    }

}
