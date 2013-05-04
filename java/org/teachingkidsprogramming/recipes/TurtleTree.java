package org.teachingkidsprogramming.recipes;

import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;

public class TurtleTree
{
  public static void main(String[] args)
  {
    //    Make the tortoise go as fast as possible --#11
    Tortoise.setSpeed(10);
    //    Turn the background black --#22
    Tortoise.getBackgroundWindow().setBackground(Colors.Grays.Black);
    //    The current branch length = 60 --#1.2
    int branchLength = 60;
    //    drawBranch(recipe below) --#2
    drawBranch(branchLength);
  }
  public static void drawBranch(int branchLength)
  {
    //    ------------- Recipe for drawBranch --#2
    //          If the current branch length is greater than zero --#6
    if (branchLength > 0)
    {
      //                          adjustColor --#16
      adjustColor(branchLength);
      //                  Move the tortoise the length of the current branch --#1.1
      Tortoise.move(branchLength);
      //                  drawLowerBranches (recipe below) --#7
      drawLowerBranches(branchLength);
    }
    //    ------------- End of drawBranch recipe --#2
  }
  public static void adjustColor(int branchLength)
  {
    //                          ------------- Recipe for adjustColor --#16
    //                                  A 10 pixel long branch is lime --#21
    if (branchLength == 10)
    {
      Tortoise.setPenColor(Colors.Greens.Lime);
    }
    //                                  A 20 pixel long branch is forest green --#20
    if (branchLength == 20)
    {
      Tortoise.setPenColor(Colors.Greens.ForestGreen);
    }
    //                                  A 30 pixel long branch is dark green --#19
    if (branchLength == 30)
    {
      Tortoise.setPenColor(Colors.Greens.DarkGreen);
    }
    //                                  A 40 pixel long branch is olive --#18
    if (branchLength == 40)
    {
      Tortoise.setPenColor(Colors.Greens.Olive);
    }
    //                                  A 50 pixel long branch is sienna --#15
    if (branchLength == 50)
    {
      Tortoise.setPenColor(Colors.Browns.Sienna);
    }
    //                                  A 60 pixel long branch is saddle brown --#14
    if (branchLength == 60)
    {
      Tortoise.setPenColor(Colors.Browns.SaddleBrown);
    }
    //                          ------------- End of adjustColor --#16
  }
  public static void drawLowerBranches(int branchLength)
  {
    //                  ------------- Recipe for drawLowerBranches --#7
    //                          Turn the Tortoise 30 degrees to the right --#3
    Tortoise.turn(30);
    //                          drawShorterBranch (recipe below) --#9
    drawShorterBranches(branchLength);
    //                          Turn the Tortoise 60 degrees to the left --#8
    Tortoise.turn(-60);
    //                          drawShorterBranch --#10
    drawShorterBranches(branchLength);
    //                          Turn the Tortoise 30 degrees to the right --#13
    Tortoise.turn(30);
    //adjustColor --#17
    adjustColor(branchLength);
    //                  Move the tortoise backward the length of the current branch --#12
    Tortoise.move(-branchLength);
    //                  ------------- End of drawLowerBranches recipe --#7
  }
  public static void drawShorterBranches(int branchLength)
  {
    //                          ------------- Recipe for drawShorterBranch --#9
    //                                  drawBranch (10 pixels shorter) --#4
    drawBranch(branchLength - 10);
    //                          ------------- End of drawShorterBranch recipe --#9
  }
}