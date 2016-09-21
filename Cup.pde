class Cup {
  PShape shape;

  Cup(PShape shape) {
    this.shape = shape;
  }

  void draw() { // Would make static but not allowed as Cup class is nested inside PApplet Java class
    shape(shape);
  }
}
