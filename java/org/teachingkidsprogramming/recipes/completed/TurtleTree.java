package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.logo.Colors;
import org.teachingextensions.logo.Tortoise;

public class TurtleTree
{
  public static void main(String[] args)
  {
    //    Make the tortoise go as fast as possible
    Tortoise.setSpeed(10);
    //    Turn the background black
    Tortoise.getBackgroundWindow().setBackground(Colors.Grays.Black);
    //    The current branch length = 60
    int length = 60;
    //    CreateBranchColors (recipe below)
    setBranchColors(length);
    //    DrawBranch(recipe below)
    drawBranch(length);
  }
  //    ------------- Recipe for DrawBranch
  private static void drawBranch(int length)
  {
    //    If the current branch length is greater than zero
    if (length > 0)
    {
      //         setBranchColors (recipe below)
      setBranchColors(length);
      //         Move the tortoise the length of the current branch
      Tortoise.move(length);
      //         DrawLowerBranches with length 10 pixels shorter (recipe below)
      drawLowerBranches(length - 10);
      //         setBranchColors (recipe below)
      setBranchColors(length);
      //         Move the tortoise backward the length of the current branch
      Tortoise.move(-length);
    }
  }
  //    ------------- End of DrawBranch recipe 
  //    ------------- Recipe for DrawLowerBranches
  private static void drawLowerBranches(int length)
  {
    //     Turn the Tortoise 30 degrees to the right
    Tortoise.turn(30);
    //     DrawBranch (recipe above)
    drawBranch(length);
    //     Turn the Tortoise 60 degrees to the left
    Tortoise.turn(-60);
    //     DrawBranch (recipe above)
    drawBranch(length);
    //     Turn the Tortoise 30 degrees to the right
    Tortoise.turn(30);
  }
  //    ------------- End of DrawLowerBranches recipe
  //    ------------- Recipe for CreateBranchColors
  public static void setBranchColors(int length)
  {
    //     A 10 pixel long branch is lime
    if (length == 10)
    {
      Tortoise.setPenColor(Colors.Greens.Lime);
    }
    //     A 20 pixel long branch is forest green
    if (length == 20)
    {
      Tortoise.setPenColor(Colors.Greens.ForestGreen);
    }
    //     A 30 pixel long branch is dark green
    if (length == 30)
    {
      Tortoise.setPenColor(Colors.Greens.DarkGreen);
    }
    //     A 40 pixel long branch is olive
    if (length == 40)
    {
      Tortoise.setPenColor(Colors.Greens.Olive);
    }
    //     A 50 pixel long branch is sienna
    if (length == 50)
    {
      Tortoise.setPenColor(Colors.Browns.Sienna);
    }
    //     A 60 pixel long branch is saddle brown
    if (length == 60)
    {
      Tortoise.setPenColor(Colors.Browns.SaddleBrown);
    }
  }
}
