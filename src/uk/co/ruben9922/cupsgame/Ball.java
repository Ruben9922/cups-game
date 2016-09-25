package uk.co.ruben9922.cupsgame;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

class Ball {
    private PApplet parent;
    private PShape shape;
    private PVector position;

    public Ball(PApplet parent, PShape shape, PVector position) {
        this.parent = parent;
        this.shape = shape;
        this.position = position;
    }

    public void draw() {
        parent.pushMatrix();
        parent.translate(position.x, position.y, position.z);
        parent.shape(shape);
        parent.popMatrix();
    }
}
