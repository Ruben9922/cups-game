package uk.co.ruben9922.cupsgame;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

public class CupsGame extends PApplet {
    private final int CUP_COUNT = 3;
    private final int CUP_SPACING = 200; // Distance between centre of top of each cup
    private final int SWAP_COUNT = 10;
    private final int SWAP_DELAY = 50;

    private ArrayList<Cup> cups = new ArrayList<>(CUP_COUNT);
    private ArrayList<Delay> delays = new ArrayList<>(CUP_COUNT); // TODO: Change so delays are stored with animations

    public static void main(String[] args) {
        PApplet.main("uk.co.ruben9922.cupsgame.CupsGame");
    }

    public void settings() {
        size(800, 600, P3D);
    }

    public void setup() {
        final float CUP_HEIGHT = 150;
        final float SPHERE_RADIUS = 30;

        Shapes shapes = new Shapes(this);

        PShape cupShape = shapes.createConicalFrustum(50, CUP_HEIGHT, 50, 75, false, true);
        cupShape.rotateX(-PApplet.PI / 16);
        cupShape.setStroke(false);
        cupShape.setFill(color(179, 0, 0));
        PShape ballShape = createShape(SPHERE, SPHERE_RADIUS);
        ballShape.setStroke(false);
        ballShape.setFill(color(200));
        int ballIndex = chooseCupIndex(); // Choose which cup to put ball under
        for (int i = 0; i < CUP_COUNT; i++) {
            float positionX = CUP_SPACING * i;
            Cup cup = new Cup(this, cupShape, new PVector(positionX, 0, 0), i,
                    i == ballIndex ? new Ball(this, ballShape, new PVector(0, CUP_HEIGHT - SPHERE_RADIUS, 0)) : null);
            cups.add(cup);
        }

        // TODO: Change so a queue is used instead to save calculating delays manually
        revealAllCups();

        delays.add(new Delay(this::performSwaps, 100));

        delays.add(new Delay(this::revealAllCups, (SWAP_DELAY * SWAP_COUNT) + 100));
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
        final int CUP_DELAY = 5;

        for (int i = 0; i < cups.size(); i++) {
            Cup cup = cups.get(i);
            delays.add(new Delay(cup::reveal, CUP_DELAY * cup.getNumber()));
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

    private void performSwaps() {
        for (int i = 0; i < SWAP_COUNT; i++) {
            int firstCupIndex = chooseCupIndex();
            boolean alreadyChosen;
            int secondCupIndex;
            do {
                secondCupIndex = chooseCupIndex();
                alreadyChosen = (firstCupIndex == secondCupIndex);
            } while (alreadyChosen); // Ensure indices are different - could change this

            int s = secondCupIndex;
            VoidNullaryFunction swapFunction = () -> cups.get(firstCupIndex).swap(cups.get(s));
            delays.add(new Delay(swapFunction, SWAP_DELAY * i));
        }
    }

    private int chooseCupIndex() {
        return (int)Math.floor(Math.random() * cups.size());
    }
}
