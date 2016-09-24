package uk.co.ruben9922.cupsgame;

import java.util.ArrayList;

class AnimationGroup extends ArrayList<Animation> {
    public AnimationGroup(Animation animation) {
        super(1);
        this.add(animation);
    }

    public AnimationGroup(Animation[] animations) {
        super(animations.length);
        for (Animation animation : animations) {
            this.add(animation);
        }
    }

    public void updateAnimations() {
        for (int i = 0; i < size(); i++) {
            Animation animation = get(i);
            animation.updateVectorComponents();
            if (animation.isFinished()) {
                remove(i);
            }
        }
    }

    public boolean isFinished() {
        for (Animation animation : this) {
            if (!animation.isFinished()) {
                return false;
            }
        }
        return true;
    }
}
