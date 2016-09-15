void setup() {
  size(800, 600, P3D);
}

void draw() {
  translate(width / 2, height / 2);
  drawPolygon(10, 100);
}

void drawPolygon(int sides, float radius) {
  float angle = 360 / (float)sides;
  beginShape();
  for (int i = 0; i < sides; i++) {
    float x = cos(radians(i * angle)) * radius;
    float y = sin(radians(i * angle)) * radius;
    vertex(x, y);
  }
  endShape(CLOSE);
}
