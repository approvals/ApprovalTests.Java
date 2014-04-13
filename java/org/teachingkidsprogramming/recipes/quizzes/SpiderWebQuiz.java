package org.teachingkidsprogramming.recipes.quizzes;

import org.teachingkidsprogramming.recipes.quizzes.graders.SpiderQuiz;
import org.teachingkidsprogramming.recipes.quizzes.graders.SpiderWebQuizGrader;

public class SpiderWebQuiz extends SpiderQuiz
{
  //       Question1
public void question1()
  {
    //        Do the following the current number of times
    //        Call circle
    //        Repeat
  }
    //   Question2
    //   circleAround (recipe below) 
    //   ------------- Recipe for circleAround 
    //      Does the following 3 times
    //      Call adjust()
    //      Call question1
    //      Repeat
    //   ------------- End of circleAround
    //
    //      Question3
    //      grow (recipe below) 
    //   ------------- Recipe for grow 
    //       Set the current length so it is multiplied by 2.5
    //   ------------- End of grow
    //
    //      Question4
    //      shrink (recipe below) 
    //   ------------- Recipe for shrink 
    //       Decrease the current length by 9 pixels
    //   ------------- End of shrink
    //
    //      Question5
    //      expand (recipe below) 
    //   ------------- Recipe for expand 
    //       Increase the current number by 12
    //   ------------- End of expand

  
  public static void main(String[] args)
  {
    new SpiderWebQuizGrader().grade(new SpiderWebQuiz());
  }
}
