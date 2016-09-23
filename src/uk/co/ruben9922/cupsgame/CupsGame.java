package uk.co.ruben9922.cupsgame;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

public class CupsGame extends PApplet {
    private final int CUP_COUNT = 3;
    private final int CUP_SPACING = 200; // Distance between centre of top of each cup

    private ArrayList<Cup> cups = new ArrayList<>(CUP_COUNT);
    private ArrayList<Delay> delays = new ArrayList<>(CUP_COUNT);

    public static void main(String[] args) {
        PApplet.main("uk.co.ruben9922.cupsgame.CupsGame");
    }

    public void settings() {
        size(800, 600, P3D);
    }

    public void setup() {
        Shapes shapes = new Shapes(this);

        PShape cupShape = shapes.createConicalFrustum(50, 150, 50, 75, false, true);
        cupShape.rotateX(-PApplet.PI / 16);
        cupShape.setStroke(false);
        cupShape.setFill(color(179, 0, 0));
        for (int i = 0; i < CUP_COUNT; i++) {
            Cup cup = new Cup(this, cupShape, new PVector(CUP_SPACING * i, 0, 0), i);
            cups.add(cup);
        }
//        revealAllCups();

        cups.get(1).swap(cups.get(2));
    }

    public void draw() {
        background(50);
        lights();

        translate(width / 2, height / 2, 0);

        pushMatrix();
        translate(-(CUP_COUNT - 1) * CUP_SPACING / 2, 0, 0);
        for (Cup cup : cups) {
            cup.updatePosition();
            cup.draw();
        }
        popMatrix();

        updateDelays();
    }

    private void revealAllCups() {
        final int CUP_DELAY = 20;
        for (int i = 0; i < cups.size(); i++) {
            Cup cup = cups.get(i);
            delays.add(new Delay(cup::reveal, CUP_DELAY * i));
        }
    }

    private void updateDelays() {
        for (int i = 0; i < delays.size(); i++) {
            Delay delay = delays.get(i);
            delay.updateTime();
            if (delay.isFinished()) {
                delays.remove(i);
            }
        }
    }
}
