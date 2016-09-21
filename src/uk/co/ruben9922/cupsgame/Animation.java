package uk.co.ruben9922.cupsgame;

import processing.core.PVector;

class Animation {
    private EasingFunction easingFunction;
    private PVector vector;
    private int time = 0;
    private float beginning;
    private float change;
    private int duration;

    Animation(EasingFunction easingFunction, PVector vector, float beginning, float change, int duration) {
        this.easingFunction = easingFunction;
        this.vector = vector;
        this.beginning = beginning;
        this.change = change;
        this.duration = duration;
    }

    public EasingFunction getEasingFunction() {
        return easingFunction;
    }

    public PVector getVector() {
        return vector;
    }

    public int getTime() {
        return time;
    }

    public float getBeginning() {
        return beginning;
    }

    public float getChange() {
        return change;
    }

    public float getDuration() {
        return duration;
    }

    public void updateVectorComponents() {
        easingFunction.setVectorComponents(vector, time, beginning, change, duration);
        if (!hasFinished()) {
            time++;
        }
    }

    public boolean hasFinished() {
        return time >= duration;
    }
}
