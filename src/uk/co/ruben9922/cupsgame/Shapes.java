package uk.co.ruben9922.cupsgame;

import processing.core.PApplet;
import processing.core.PShape;

class Shapes {
    private PApplet parent;

    Shapes(PApplet parent) {
        this.parent = parent;
    }

    PShape createConicalFrustum(int sides, float height, float topRadius, float bottomRadius, boolean topOpen, boolean bottomOpen) {
        float centralAngle = 360 / (float)sides;
        float[][] topVertices = new float[sides][2]; // Store vertices in arrays to save recalculating them
        float[][] bottomVertices = new float[sides][2];
        PShape conicalFrustum = parent.createShape(PApplet.GROUP);

        // Store coordinates of vertices in array and draw body
        PShape body = parent.createShape();
        body.beginShape(PApplet.QUAD_STRIP);
        for (int i = 0; i < sides; i++) {
            float topX = PApplet.cos(PApplet.radians(i * centralAngle)) * topRadius;
            float topY = PApplet.sin(PApplet.radians(i * centralAngle)) * topRadius;
            float bottomX = PApplet.cos(PApplet.radians(i * centralAngle)) * bottomRadius;
            float bottomY = PApplet.sin(PApplet.radians(i * centralAngle)) * bottomRadius;
            body.vertex(topX, topY, 0);
            body.vertex(bottomX, bottomY, height);
            topVertices[i][0] = topX;
            topVertices[i][1] = topY;
            bottomVertices[i][0] = bottomX;
            bottomVertices[i][1] = bottomY;
        }
        body.vertex(topVertices[0][0], topVertices[0][1], 0);
        body.vertex(bottomVertices[0][0], bottomVertices[0][1], height);
        body.endShape();
        conicalFrustum.addChild(body);

        // Draw top
        if (!topOpen) {
            PShape top = parent.createShape();
            top.beginShape();
            for (int i = 0; i < topVertices.length; i++) {
                float x = topVertices[i][0];
                float y = topVertices[i][1];
                top.vertex(x, y, 0);
            }
            top.endShape();
            conicalFrustum.addChild(top);
        }

        // Draw bottom
        if (!bottomOpen) {
            PShape bottom = parent.createShape();
            bottom.beginShape();
            bottom.translate(0, 0, height);
            for (int i = 0; i < bottomVertices.length; i++) {
                float x = bottomVertices[i][0];
                float y = bottomVertices[i][1];
                bottom.vertex(x, y, 0);
            }
            bottom.endShape();
            conicalFrustum.addChild(bottom);
        }

        return conicalFrustum;
    }
}
