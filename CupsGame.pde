void setup() {
  size(800, 600, P3D);
}

void draw() {
  final int CUP_COUNT = 3;
  final int CUP_SPACING = 200; // Distance between centre of top of each cup

  background(50);
  lights();

  noStroke();
  fill(200);
  translate(width / 2, height / 2, 0);

  pushMatrix();
  rotateX(-9 * PI / 16);
  ArrayList<Cup> cups = new ArrayList<Cup>(3);
  translate(-(CUP_COUNT - 1) * CUP_SPACING / 2, 0, 0);
  for (int i = 0; i < CUP_COUNT; i++) {
    Cup cup = new Cup();
    cup.draw();
    cups.add(cup);
    translate(CUP_SPACING, 0, 0);
  }
  popMatrix();
}

void drawConicalFrustum(int sides, float height, float topRadius, float bottomRadius, boolean topOpen, boolean bottomOpen) {
  float centralAngle = 360 / (float)sides;
  float[][] topVertices = new float[sides][2]; // Store vertices in array to save recalculating them
  float[][] bottomVertices = new float[sides][2];

  pushMatrix();

  // Store coordinates of vertices in array and draw body
  beginShape(QUAD_STRIP);
  for (int i = 0; i < sides; i++) {
    float topX = cos(radians(i * centralAngle)) * topRadius;
    float topY = sin(radians(i * centralAngle)) * topRadius;
    float bottomX = cos(radians(i * centralAngle)) * bottomRadius;
    float bottomY = sin(radians(i * centralAngle)) * bottomRadius;
    vertex(topX, topY, 0);
    vertex(bottomX, bottomY, height);
    topVertices[i][0] = topX;
    topVertices[i][1] = topY;
    bottomVertices[i][0] = bottomX;
    bottomVertices[i][1] = bottomY;
  }
  vertex(topVertices[0][0], topVertices[0][1], 0);
  vertex(bottomVertices[0][0], bottomVertices[0][1], height);
  endShape();

  // Draw top
  if (!topOpen) {
    beginShape();
    for (int i = 0; i < topVertices.length; i++) {
      float x = topVertices[i][0];
      float y = topVertices[i][1];
      vertex(x, y, 0);
    }
    endShape();
  }

  // Draw bottom
  if (!bottomOpen) {
    beginShape();
    translate(0, 0, height);
    for (int i = 0; i < bottomVertices.length; i++) {
      float x = bottomVertices[i][0];
      float y = bottomVertices[i][1];
      vertex(x, y, 0);
    }
    endShape();
  }

  popMatrix();
}
