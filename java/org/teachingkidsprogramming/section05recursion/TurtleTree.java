package org.teachingkidsprogramming.section05recursion;


public class TurtleTree
{
  public static void main(String[] args)
  {
    //    Make the tortoise go as fast as possible --#11
    //    Turn the background black --#22
    //    The current branch length = 60 --#1.2
    //    drawBranch(recipe below) --#2
    //    ------------- Recipe for drawBranch --#2
    //        If the current branch length is greater than zero, do the rest of this recipe --#6
    //        adjustColor --#16
    //        ------------- Recipe for adjustColor --#16
    //            A 10 pixel long branch is lime --#21
    //            A 20 pixel long branch is forest green --#20
    //            A 30 pixel long branch is dark green --#19
    //            A 40 pixel long branch is olive --#18
    //            A 50 pixel long branch is sienna --#15
    //            A 60 pixel long branch is saddle brown --#14
    //        ------------- End of adjustColor --#16
    //        Move the tortoise the length of the current branch --#1.1
    //        drawLowerBranches (recipe below) --#7
    //        ------------- Recipe for drawLowerBranches --#7
    //            Turn the Tortoise 30 degrees to the right --#3
    //            drawShorterBranch (recipe below) --#9
    //            ------------- Recipe for drawShorterBranch --#9
    //                drawBranch (10 pixels shorter) --#4
    //            ------------- End of drawShorterBranch recipe --#9
    //            Turn the Tortoise 60 degrees to the left --#8
    //            drawShorterBranch --#10
    //            Turn the Tortoise 30 degrees to the right --#13
    //            adjustColor --#17
    //            Move the tortoise backward the length of the current branch --#12
    //        ------------- End of drawLowerBranches recipe --#7
    //    ------------- End of drawBranch recipe --#2
  }
}