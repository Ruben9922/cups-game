class Cup {
  Cup() {

  }

  void draw() { // Would make static but not allowed as Cup class is nested inside PApplet Java class
    shape(createConicalFrustum(50, 150, 50, 75, false, true));
  }
}
