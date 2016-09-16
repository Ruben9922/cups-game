void setup() {
  size(800, 600, P3D);
}

void draw() {
  background(50);
  lights();

  noStroke();
  fill(200);
  translate(width / 2, height / 2, 0);
  rotateX(-9 * PI / 16);
  drawConicalFrustum(50, 150, 50, 75);
}

void drawConicalFrustum(int sides, float height, float topRadius, float bottomRadius) {
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
  beginShape();
  for (int i = 0; i < topVertices.length; i++) {
    float x = topVertices[i][0];
    float y = topVertices[i][1];
    vertex(x, y, 0);
  }
  endShape();

  // Draw bottom
  beginShape();
  translate(0, 0, height);
  for (int i = 0; i < bottomVertices.length; i++) {
    float x = bottomVertices[i][0];
    float y = bottomVertices[i][1];
    vertex(x, y, 0);
  }
  endShape();

  popMatrix();
}
