package org.teachingkidsprogramming.recipes.deepdives;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.Turtle;
import org.teachingextensions.logo.utils.TortoiseUtils;

@Ignore
public class DeepDive01ForLoops
{
  //  How to do deep dive:
  //  Step 1: Select the method name (numbersDoNotNeedQuotes on line 24) Press the Run Button
  //            PC: Ctrl+F11 
  //            Mac: Command+fn+F11
  //  Step 2: Read the name of the method that failed
  //  Step 3: Fill in the blank (___) to make it pass
  //  Step 4: Consider at least one thing you just learned
  //  Step 5: Advance to the next method
  //  Do not change anything except the blank (___)
  //
  @Test
  public void numbersDoNotNeedQuotes()
  {
    Assert.assertEquals(42, ____);
  }
  @Test
  public void defaultWidthForTheTortoise() throws Exception
  {
    Assert.assertEquals(Tortoise.getPenWidth(), ____);
  }
  @Test
  public void stringsNeedQuotes() throws Exception
  {
    Assert.assertEquals("Green", ___);
  }
  @Test
  public void stringsCanIncludeSpaces() throws Exception
  {
    Assert.assertEquals("This is a string", ___);
  }
  @Test
  public void changingThePenWidthTo5() throws Exception
  {
    Tortoise.setPenWidth(____);
    Assert.assertEquals(5, Tortoise.getPenWidth());
  }
  @Test
  public void movingTheTortoise100Pixels() throws Exception
  {
    int start = Tortoise.getY();
    Tortoise.move(____);
    Assert.assertEquals(Tortoise.getY(), start - 100);
    // 'Hint: make sure you read the name of this method
  }
  @Test
  public void theTortoiseTurns21() throws Exception
  {
    Tortoise.turn(____);
    Assert.assertEquals(21.0, Tortoise.getAngle(), 0.01);
  }
  @Test
  public void theTortoiseTurns15Twice() throws Exception
  {
    Tortoise.turn(____);
    Tortoise.turn(____);
    Assert.assertEquals(30.0, Tortoise.getAngle(), 0.01);
  }
  @Test
  public void howFastCanTheTortoiseGo() throws Exception
  {
    Tortoise.setSpeed(____);
    Assert.assertEquals(Tortoise.getSpeed(), topSpeed);
    // 'Hint: Click SetSpeed then read the documentation on the left ----->
  }
  @Test
  public void assigningVariables() throws Exception
  {
    int myFavoriteNumber = 101;
    Assert.assertEquals(myFavoriteNumber, ____);
  }
  @Test
  public void combiningNumbers() throws Exception
  {
    int age = 3 + 4;
    Assert.assertEquals(age, ____);
  }
  @Test
  public void combiningText() throws Exception
  {
    String name = "Peter" + " " + "Pan";
    Assert.assertEquals(name, ___);
  }
  @Test
  public void combiningTextAndNumbers() throws Exception
  {
    String name = "Henry The " + 8;
    Assert.assertEquals(name, ___);
  }
  @Test
  public void textIsTextEvenWhenItsNumbers() throws Exception
  {
    String age = "3" + "4";
    Assert.assertEquals(age, ___);
  }
  @Test
  public void combiningTextInALoop() throws Exception
  {
    String sound = "A";
    for (int i = 1; i <= 3; i++)
    {
      sound += "H";
    }
    Assert.assertEquals(sound, ___);
  }
  @Test
  public void forLoopsEndAtTheEnd() throws Exception
  {
    String numbers = "# ";
    for (int i = 1; i <= ____; i++)
    {
      numbers += i;
      preventInfiniteLoops();
    }
    Assert.assertEquals("# 12345", numbers);
  }
  @Test
  public void forLoopsCanStartAnywhere() throws Exception
  {
    String answer = "Because ";
    for (int i = ____; i <= 9; i++)
    {
      answer += i;
      preventInfiniteLoops();
    }
    // 'Question: Why is 7 the most feared number?
    Assert.assertEquals("Because 789", answer);
  }
  @Test
  public void forLoopsCanSkip() throws Exception
  {
    String numbers = "# ";
    for (int i = 1; i <= 20; i += ____)
    {
      numbers = numbers + i + ",";
      preventInfiniteLoops();
    }
    Assert.assertEquals("# 1,3,5,7,9,11,13,15,17,19,", numbers);
  }
  @Test
  public void forLoopsCanSkipUpAndDown() throws Exception
  {
    String numbers = "# ";
    for (int i = 20; 0 < i && i <= 40; i += ____)
    {
      numbers = numbers + i + ",";
      preventInfiniteLoops();
    }
    Assert.assertEquals("# 20,17,14,11,8,5,2,", numbers);
  }
  @Test
  public void forLoopsCanGoBackwards() throws Exception
  {
    String numbers = "Countdown: ";
    for (int i = 9; i >= 1; i += ____)
    {
      numbers += i;
      preventInfiniteLoops();
    }
    Assert.assertEquals("Countdown: 987654321", numbers);
  }
  @Test
  public void semicolonsMessUpForLoops() throws Exception
  {
    String sound = "A";
    for (int i = 1; i <= 13; i++);
    {
      sound += "H";
    }
    Assert.assertEquals(sound, ___);
  }
  /**
   * Ignore the following, it's needed to run the homework
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
  public String ___      = "You need to fill in the blank ___";
  public int    ____     = 1;
  int           topSpeed = 10;
  int           counter  = 0;
  @Before
  public void init()
  {
    TortoiseUtils.resetTurtle();
    Tortoise.setSpeed(Turtle.TEST_SPEED);
  }
  private void preventInfiniteLoops()
  {
    if (counter++ > 100) { throw new RuntimeException("You have created an infinite loop"); }
  }
}
