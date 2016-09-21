package uk.co.ruben9922.cupsgame;

import processing.core.PApplet;
import processing.core.PShape;

class Cup {
    private PApplet parent;
    private PShape shape;

    Cup(PApplet parent, PShape shape) {
        this.parent = parent;
        this.shape = shape;
    }

    void draw() {
        parent.shape(shape);
    }
}
