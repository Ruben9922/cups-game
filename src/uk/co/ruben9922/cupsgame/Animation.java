package uk.co.ruben9922.cupsgame;

import java.util.function.Consumer;
import java.util.function.Supplier;

class Animation {
    private EasingFunction easingFunction;
    private Supplier<Float> getBeginningFunction;
    private Consumer<Float> setValueFunction;
    private int time = 0;
    private float beginning;
    private float change;
    private int duration;

    public Animation(EasingFunction easingFunction, Supplier<Float> getBeginningFunction, Consumer<Float> setValueFunction, float change, int duration) {
        this.easingFunction = easingFunction;
        this.getBeginningFunction = getBeginningFunction;
        this.setValueFunction = setValueFunction;
        this.change = change;
        this.duration = duration;
    }

    public EasingFunction getEasingFunction() {
        return easingFunction;
    }

    public Supplier<Float> getGetBeginningFunction() {
        return getBeginningFunction;
    }

    public Consumer<Float> getSetValueFunction() {
        return setValueFunction;
    }

    public int getTime() {
        return time;
    }

    public float getChange() {
        return change;
    }

    public float getDuration() {
        return duration;
    }

    public void update() {
        // If the animation is just beginning, set the beginning value using getBeginningFunction
        if (time == 0) {
            beginning = getBeginningFunction.get();
        }

        // Get current value using easing function then set the current value using the setValueFunction
        float currentValue = easingFunction.getValue(time, beginning, change, duration);
        setValueFunction.accept(currentValue);

        // If not finished increment time
        if (!isFinished()) {
            time++;
        }
    }

    public boolean isFinished() {
        return time >= duration + 1;
    }
}
