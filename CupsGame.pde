void setup() {
  size(800, 600, P3D);
}

void draw() {
  final int CUP_COUNT = 3;
  final int CUP_SPACING = 200; // Distance between centre of top of each cup

  Shapes shapes = new Shapes();

  background(50);
  lights();

  translate(width / 2, height / 2, 0);

  pushMatrix();
  rotateX(-9 * PI / 16);
  ArrayList<Cup> cups = new ArrayList<Cup>(3);
  PShape cupShape = shapes.createConicalFrustum(50, 150, 50, 75, false, true);
  cupShape.setStroke(false);
  cupShape.setFill(color(200));
  translate(-(CUP_COUNT - 1) * CUP_SPACING / 2, 0, 0);
  for (int i = 0; i < CUP_COUNT; i++) {
    Cup cup = new Cup(cupShape);
    cup.draw();
    cups.add(cup);
    translate(CUP_SPACING, 0, 0);
  }

  popMatrix();
}
