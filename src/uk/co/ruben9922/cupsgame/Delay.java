package uk.co.ruben9922.cupsgame;

class Delay implements AnimationItem {
    private VoidNullaryFunction onFinish;
    private int time = 0;
    private int duration;
    private boolean hasFinished = false;

    public Delay(VoidNullaryFunction onFinish, int duration) {
        this.onFinish = onFinish;
        this.duration = duration;
    }

    public VoidNullaryFunction getOnFinish() {
        return onFinish;
    }

    public int getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    public void update() {
        // Increment time if not finished
        if (!isFinished()) {
            time++;
        }

        // Run onFinish function if finished and not already finished
        boolean isCurrentlyFinished = isFinished();
        if (isCurrentlyFinished && !hasFinished) {
            onFinish.run();
        }
        hasFinished = isCurrentlyFinished;
    }

    public boolean isFinished() {
        return time >= duration;
    }
}
