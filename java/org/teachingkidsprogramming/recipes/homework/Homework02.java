package org.teachingkidsprogramming.recipes.homework;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class Homework02
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
  public void youCanReadVariables() throws Exception
  {
    int numberOfDesserts = 5;
    Assert.assertEquals(numberOfDesserts, ____);
  }
  @Test
  public void youCanSaveVariables() throws Exception
  {
    int ickynessOfBrothers = ____;
    Assert.assertEquals(10, ickynessOfBrothers);
  }
  @Test
  public void youCanDoMathWithVariables() throws Exception
  {
    int ____ = 3 + 4;
    Assert.assertEquals(7, numberOfHarryPotterBooks);
  }
  @Test
  public void youCanChangeVariables() throws Exception
  {
    int milkTastiness = 6;
    addChocolate();
    milkTastiness = 10;
    Assert.assertEquals(milkTastiness, ____);
  }
  @Test
  public void variablesAreSnotStuck() throws Exception
  {
    int boogers = 4;
    blowNose();
    boogers = ____;
    Assert.assertEquals(0, boogers);
  }
  @Test
  public void youCanAddToAVariable() throws Exception
  {
    int age = 11;
    celebrateBirthday();
    age += ____;
    Assert.assertEquals(12, age);
  }
  @Test
  public void youCanAddInMultipleWays() throws Exception
  {
    int bakersDozen = 12;
    bakersDozen = bakersDozen + ____;
    Assert.assertEquals(13, bakersDozen);
  }
  @Test
  public void youCanAddOneInOneMoreWay() throws Exception
  {
    int bearsInABed = 3;
    andTheLittleOneSaid("I'm lonely, come back here");
    bearsInABed++;
    Assert.assertEquals(bearsInABed, ____);
  }
  @Test
  public void youCanSubtractFromAVariable() throws Exception
  {
    int amountOfHomework = 3;
    amountOfHomework -= ____;
    Assert.assertEquals(0, amountOfHomework);
  }
  @Test
  public void youCanSubtractOneInOneMoreWay() throws Exception
  {
    int bottlesOfBeerOnTheWall = 99;
    bottlesOfBeerOnTheWall--;
    Assert.assertEquals(bottlesOfBeerOnTheWall, ____);
  }
  @Test
  public void youCanMultiplyVariables() throws Exception
  {
    int volumeOfMyVoice = 2;
    int volumeMyMomHears = volumeOfMyVoice * 5;
    Assert.assertEquals(____, volumeMyMomHears);
  }
  @Test
  public void youCanDivideVariables() throws Exception
  {
    int inches = 36;
    int feet = ____ / 12;
    Assert.assertEquals(3, feet);
  }
  @Test
  public void variablesOnlyExistWithinTheMethod() throws Exception
  {
    String xmasList = "bike";
    dreamBigger(); //This method is directly below
    Assert.assertEquals(xmasList, ___);
  }
  private void dreamBigger()
  {
    String xmasList = "hippopotamus";
  }
  @Test
  public void methodsCanReturnValues() throws Exception
  {
    String bedPost = prepareForBed(); //This method is directly below
    Assert.assertEquals(bedPost, ___);
  }
  public String prepareForBed()
  {
    return "gum";
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
  public String ___  = "You need to fill in the blank ___";
  public int    ____ = 10000;
  public String ___()
  {
    return ___;
  }
  private void addChocolate()
  {
  }
  private void celebrateBirthday()
  {
  }
  private void blowNose()
  {
  }
  private void andTheLittleOneSaid(String string)
  {
  }
}
