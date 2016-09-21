final int CUP_COUNT = 3;
final int CUP_SPACING = 200; // Distance between centre of top of each cup

ArrayList<Cup> cups = new ArrayList<Cup>(CUP_COUNT);

void setup() {
  size(800, 600, P3D);

  Shapes shapes = new Shapes();

  PShape cupShape = shapes.createConicalFrustum(50, 150, 50, 75, false, true);
  cupShape.setStroke(false);
  cupShape.setFill(color(200));
  for (int i = 0; i < CUP_COUNT; i++) {
    Cup cup = new Cup(cupShape);
    cups.add(cup);
  }
}

void draw() {
  background(50);
  lights();

  translate(width / 2, height / 2, 0);

  pushMatrix();
  rotateX(-9 * PI / 16);
  translate(-(CUP_COUNT - 1) * CUP_SPACING / 2, 0, 0);
  for (Cup cup : cups) {
    cup.draw();
    translate(CUP_SPACING, 0, 0);
  }

  popMatrix();
}
