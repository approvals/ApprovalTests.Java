package org.approvaltests;

public class Circular
{
  Circular parent;
  String   label;
  public Circular(Circular parent, String label)
  {
    this.parent = parent;
    this.label = label;
  }

  public static Circular getIndirectCircularReference()
  {
    Circular level1 = new Circular(null, "middle");
    Circular level2 = new Circular(level1, "child");
    level1.parent = level2;
    return level1;
  }
}
