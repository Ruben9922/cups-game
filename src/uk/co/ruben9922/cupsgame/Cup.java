package uk.co.ruben9922.cupsgame;

import penner.easing.Bounce;
import penner.easing.Quad;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Supplier;

class Cup {
    private PApplet parent;
    private PShape shape;
    private PVector normalPosition; // TODO: Should be able to merge `normalPosition` and `position`
    private PVector position = new PVector(0, 0, 0);
    private Queue<AnimationGroup> animationQueue = new ArrayDeque<>();
    private Ball ball;
    private int number;

    Cup(PApplet parent, PShape shape, PVector normalPosition, int number, Ball ball) {
        this.parent = parent;
        this.shape = shape;
        this.normalPosition = normalPosition;
        this.ball = ball;
        this.number = number;
    }

    Cup(PApplet parent, PShape shape, PVector normalPosition, int number) {
        this(parent, shape, normalPosition, number, null);
    }

    public int getNumber() {
        return number;
    }

    void draw() {
        parent.pushMatrix();
        parent.translate(normalPosition.x, normalPosition.y, normalPosition.z);
        if (ball != null) {
            ball.draw();
        }
        parent.translate(position.x, position.y, position.z);
        parent.rotateX(-PApplet.PI / 2);
        parent.shape(shape);
        parent.popMatrix();
    }

    void updatePosition() {
//        position.add(0, revealAnimation.getCurrentValue(), 0);

        AnimationGroup currentAnimationGroup = animationQueue.peek();
        if (currentAnimationGroup != null) {
            currentAnimationGroup.updateAnimations();

            if (currentAnimationGroup.isFinished()) {
                animationQueue.poll();
            }
        }
    }

    void reveal() {
        Supplier<Float> getPositionY = () -> position.y;
        Consumer<Float> setPositionY = (y) -> position.y = y;
        animationQueue.offer(new AnimationGroup(new Animation(Quad::easeInOut, getPositionY, setPositionY, -150, 50)));
        animationQueue.offer(new AnimationGroup(new Animation(Bounce::easeOut, getPositionY, setPositionY, 150, 60)));
    }

    void swap(Cup cup) {
        final int duration = 10;
        final float positionXChange = (cup.normalPosition.x - normalPosition.x) / 2;

        int tempNumber = this.number;
        this.number = cup.number;
        cup.number = tempNumber;

        Supplier<Float> getThisNormalPositionX = () -> normalPosition.x;
        Supplier<Float> getThisNormalPositionZ = () -> normalPosition.z;
        Consumer<Float> setThisNormalPositionX = (x) -> normalPosition.x = x;
        Consumer<Float> setThisNormalPositionZ = (z) -> normalPosition.z = z;
        animationQueue.offer(new AnimationGroup(new Animation[] {
                new Animation(Quad::easeIn, getThisNormalPositionX, setThisNormalPositionX, positionXChange, duration),
                new Animation(Quad::easeInOut, getThisNormalPositionZ, setThisNormalPositionZ, -100, duration)
        }));
        animationQueue.offer(new AnimationGroup(new Animation[] {
                new Animation(Quad::easeOut, getThisNormalPositionX, setThisNormalPositionX, positionXChange, duration),
                new Animation(Quad::easeInOut, getThisNormalPositionZ, setThisNormalPositionZ, 100, duration)
        }));

        Supplier<Float> getCupNormalPositionX = () -> cup.normalPosition.x;
        Supplier<Float> getCupNormalPositionZ = () -> cup.normalPosition.z;
        Consumer<Float> setCupNormalPositionX = (x) -> cup.normalPosition.x = x;
        Consumer<Float> setCupNormalPositionZ = (z) -> cup.normalPosition.z = z;
        cup.animationQueue.offer(new AnimationGroup(new Animation[] {
                new Animation(Quad::easeIn, getCupNormalPositionX, setCupNormalPositionX, -positionXChange, duration),
                new Animation(Quad::easeInOut, getCupNormalPositionZ, setCupNormalPositionZ, 100, duration)
        }));
        cup.animationQueue.offer(new AnimationGroup(new Animation[] {
                new Animation(Quad::easeOut, getCupNormalPositionX, setCupNormalPositionX, -positionXChange, duration),
                new Animation(Quad::easeInOut, getCupNormalPositionZ, setCupNormalPositionZ, -100, duration)
        }));
    }
}
