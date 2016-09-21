package uk.co.ruben9922.cupsgame;

import penner.easing.Quad;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayDeque;
import java.util.Queue;

class Cup {
    private PApplet parent;
    private PShape shape;
    private int number;
    private PVector position = new PVector(0, 0, 0);
    private Queue<Animation> animationQueue = new ArrayDeque<>();

    Cup(PApplet parent, PShape shape, int number) {
        this.parent = parent;
        this.shape = shape;
        this.number = number;
    }

    void draw() {
        parent.pushMatrix();
        parent.translate(position.x, position.y, position.z);
        parent.rotateX(-PApplet.PI / 2);
        parent.shape(shape);
        parent.popMatrix();
    }

    void updatePosition() {
//        position.add(0, revealAnimation.getCurrentValue(), 0);

        Animation currentAnimation = animationQueue.peek();
        if (currentAnimation != null) {
            currentAnimation.updateVectorComponents();

            if (currentAnimation.hasFinished()) {
                animationQueue.poll();
            }
        }
    }

    void reveal() {
        animationQueue.offer(new Animation((v, t, b, c, d) -> v.set(0, Quad.easeInOut(t, b, c, d), 0), position, position.y, -100, 50));
        animationQueue.offer(new Animation((v, t, b, c, d) -> v.set(0, Quad.easeInOut(t, b, c, d), 0), position, -100, 100, 50));
    }
}
