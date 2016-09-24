package uk.co.ruben9922.cupsgame;

import penner.easing.Quad;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Cup {
    private PApplet parent;
    private PShape shape;
    private PVector normalPosition;
    private PVector position = new PVector(0, 0, 0);
    private int number;
    private Queue<AnimationGroup> animationQueue = new ArrayDeque<>();

    Cup(PApplet parent, PShape shape, PVector normalPosition, int number) {
        this.parent = parent;
        this.shape = shape;
        this.normalPosition = normalPosition;
        this.number = number;
    }

    void draw() {
        parent.pushMatrix();
        parent.translate(normalPosition.x, normalPosition.y, normalPosition.z);
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
        animationQueue.offer(new AnimationGroup(new Animation((v, t, b, c, d) -> v.set(0, Quad.easeInOut(t, b, c, d), 0), position, position.y, -100, 50)));
        animationQueue.offer(new AnimationGroup(new Animation((v, t, b, c, d) -> v.set(0, Quad.easeInOut(t, b, c, d), 0), position, -100, 100, 50)));
    }

    void swap(Cup cup) {
        animationQueue.offer(new AnimationGroup(new Animation[] {
                new Animation((v, t, b, c, d) -> v.set(Quad.easeInOut(t, b, c, d), 0, 0), normalPosition, normalPosition.x, (cup.normalPosition.x - normalPosition.x) / 2, 50),
                new Animation((v, t, b, c, d) -> v.set(0, 0, Quad.easeInOut(t, b, c, d)), normalPosition, normalPosition.z, 100, 50)
        }));
        animationQueue.offer(new AnimationGroup(new Animation[] {
                new Animation((v, t, b, c, d) -> v.set(Quad.easeInOut(t, b, c, d), 0, 0), normalPosition, normalPosition.x + ((cup.normalPosition.x - normalPosition.x) / 2), (cup.normalPosition.x - normalPosition.x) / 2, 50),
                new Animation((v, t, b, c, d) -> v.set(0, 0, Quad.easeInOut(t, b, c, d)), normalPosition, normalPosition.z + 100, -100, 50)
        }));

        cup.animationQueue.offer(new AnimationGroup(new Animation[] {
                new Animation((v, t, b, c, d) -> v.set(Quad.easeInOut(t, b, c, d), 0, 0), cup.normalPosition, cup.normalPosition.x, (normalPosition.x - cup.normalPosition.x) / 2, 50),
                new Animation((v, t, b, c, d) -> v.set(0, 0, Quad.easeInOut(t, b, c, d)), cup.normalPosition, cup.normalPosition.z, 100, 50)
        }));
        cup.animationQueue.offer(new AnimationGroup(new Animation[] {
                new Animation((v, t, b, c, d) -> v.set(Quad.easeInOut(t, b, c, d), 0, 0), cup.normalPosition, cup.normalPosition.x + (normalPosition.x - cup.normalPosition.x) / 2, (normalPosition.x - cup.normalPosition.x) / 2, 50),
                new Animation((v, t, b, c, d) -> v.set(0, 0, Quad.easeInOut(t, b, c, d)), cup.normalPosition, cup.normalPosition.z + 100, -100, 50)
        }));
    }
}
