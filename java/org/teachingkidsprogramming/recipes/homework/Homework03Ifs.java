package org.teachingkidsprogramming.recipes.homework;

import junit.framework.Assert;

import org.junit.Test;

//@Ignore
public class Homework03Ifs
{
  //  'How to do homework:
  //  'Step 1: Press the Run Button
  //            PC: Ctrl+F11 
  //            Mac: Command+fn+F11
  //  'Step 2: Read the name of the Method (Sub) that Failed
  //  'Step 3: Fill in the blank (___) to make it pass
  //  'Step 4: Repeat Until Enlightenment
  //  ' Do not change anything except the blank (___)
  //
  @Test
  public void doesABear() throws Exception
  {
    String bearPoopPlace = "";
    if (true)
    {
      bearPoopPlace = ___;
    }
    Assert.assertEquals("woods", bearPoopPlace);
  }
  @Test
  public void neverEverEver() throws Exception
  {
    String dessert = "chocolate";
    if (false)
    {
      dessert = "ketchup";
    }
    Assert.assertEquals(___, dessert);
  }
  @Test
  public void isThePopeCatholic() throws Exception
  {
    String pope = "";
    if (_____)
    {
      pope = "Catholic";
    }
    Assert.assertEquals("Catholic", pope);
  }
  @Test
  public void trueOrFalse() throws Exception
  {
    String animal = "cat";
    boolean elephant = _____;
    if (elephant)
    {
      animal = "flat " + animal;
    }
    Assert.assertEquals("flat cat", animal);
  }
  @Test
  public void letSleepingBabiesLie() throws Exception
  {
    String babySounds = "";
    boolean sleeping = ______;
    if (sleeping)
    {
      babySounds = "zzzzzzzzzzzz";
    }
    else
    {
      babySounds = "waaaaaahhh!";
    }
    Assert.assertEquals("waaaaaahhh!", babySounds);
  }
  /**
   * Ignore the following, It's needed to run the homework
   * 
   * 
   * 
   * 
   * 
   * 
   * 
   * 
   * 
   * 
   */
  public boolean _____  = false;
  public boolean ______ = true;
  public String  ___    = "You need to fill in the blank ___";
  public int     ____   = 10000;
  public String ___()
  {
    return ___;
  }
}
