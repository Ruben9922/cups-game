package uk.co.ruben9922.cupsgame;

import processing.core.PApplet;
import processing.core.PShape;

import java.util.ArrayList;

public class CupsGame extends PApplet {
    private final int CUP_COUNT = 3;
    private final int CUP_SPACING = 200; // Distance between centre of top of each cup

    private ArrayList<Cup> cups;

    public static void main(String[] args) {
        PApplet.main("uk.co.ruben9922.cupsgame.CupsGame");
    }

    public void settings() {
        size(800, 600, P3D);
    }

    public void setup() {
        Shapes shapes = new Shapes(this);

        cups = new ArrayList<>(CUP_COUNT);
        PShape cupShape = shapes.createConicalFrustum(50, 150, 50, 75, false, true);
        cupShape.rotateX(-PApplet.PI / 16);
        cupShape.setStroke(false);
        cupShape.setFill(color(179, 0, 0));
        for (int i = 0; i < CUP_COUNT; i++) {
            Cup cup = new Cup(this, cupShape, i);
            cups.add(cup);
        }
        revealAllCups();
    }

    public void draw() {
        background(50);
        lights();

        translate(width / 2, height / 2, 0);

        pushMatrix();
        translate(-(CUP_COUNT - 1) * CUP_SPACING / 2, 0, 0);
        for (int i = 0; i < cups.size(); i++) {
            if (i != 0) {
                translate(CUP_SPACING, 0, 0);
            }
            Cup cup = cups.get(i);
            cup.updatePosition();
            cup.draw();
        }
        popMatrix();
    }

    private void revealAllCups() {
        for (Cup cup : cups) {
            cup.reveal();
        }
    }
}
