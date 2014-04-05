package org.teachingkidsprogramming.recipes.deepdives;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
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
  public void notEverEverEver() throws Exception
  {
    String dessert = "chocolate";
    if (!_____)
    {
      dessert = "ketchup";
    }
    Assert.assertEquals("chocolate", dessert);
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
  @Test
  public void howCoachThinks() throws Exception
  {
    String coachSays = "try harder";
    int percentEffort = ____;
    if (percentEffort == 110)
    {
      coachSays = "good job";
    }
    Assert.assertEquals("good job", coachSays);
  }
  @Test
  public void lessThan() throws Exception
  {
    String modeOfTransportation = "";
    int age = ____;
    if (age < 16)
    {
      modeOfTransportation = "keep walking";
    }
    else
    {
      modeOfTransportation = "drive away";
    }
    Assert.assertEquals("keep walking", modeOfTransportation);
  }
  @Test
  public void greaterThan() throws Exception
  {
    String kidSays = "";
    int numberOfIceCreams = ____;
    if (numberOfIceCreams > 4)
    {
      kidSays = "I think I'm gonna barf";
    }
    else
    {
      kidSays = "More ice cream!";
    }
    Assert.assertEquals("I think I'm gonna barf", kidSays);
  }
  @Test
  public void notEqual() throws Exception
  {
    String playerSays = "";
    int cards = ____;
    if (cards != 52)
    {
      playerSays = "Not playing with a full deck!";
    }
    else
    {
      playerSays = "Game on!";
    }
    Assert.assertEquals("Game on!", playerSays);
  }
  @Test
  public void equalsForStrings() throws Exception
  {
    String knockKnock = "";
    String whosThere = ___;
    if (whosThere.equals("bananas"))
    {
      knockKnock = "Who's there?";
    }
    else if (whosThere.equals("orange"))
    {
      knockKnock = "Orange you glad I didn't say bananas?";
    }
    Assert.assertEquals("Who's there?", knockKnock);
  }
  @Test
  public void thisAndThat() throws Exception
  {
    String time = "";
    int score = ____;
    int years = ____;
    if (score == 4 && years == 7)
    {
      time = "Presidential";
    }
    Assert.assertEquals("Presidential", time);
  }
  @Test
  public void theBeginningOrEnd() throws Exception
  {
    String shoppingList = "";
    int age = ____;
    if (age <= 2 || 90 <= age)
    {
      shoppingList = "diapers";
    }
    Assert.assertEquals("diapers", shoppingList);
  }
  @Test
  public void ifInHighSchool() throws Exception
  {
    String status = "";
    int age = ____;
    if (age <= 15)
    {
      status = "smarty";
    }
    else if (age > 19)
    {
      status = "dummy";
    }
    else
    {
      status = "normal";
    }
    Assert.assertEquals("normal", status);
  }
  @Test
  public void nestedIfOrPigsInABlanket() throws Exception
  {
    String status = "";
    String animal = "PIG";
    boolean isWinningKarate = ______;
    if (animal.equalsIgnoreCase("pig"))
    {
      if (isWinningKarate)
      {
        status = "pork chop";
      }
      else
      {
        status = "hambulance";
      }
    }
    Assert.assertEquals("hambulance", status);
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
  public Integer ____   = null;
  public String ___()
  {
    return ___;
  }
}
