package org.teachingkidsprogramming.recipes.quizzes;

import org.teachingkidsprogramming.recipes.quizzes.graders.PentagonCrazyQuizGrader;
import org.teachingkidsprogramming.recipes.quizzes.graders.PentagonQuizAdapter;

public class PentagonCrazyQuiz extends PentagonQuizAdapter
{
  public void question1()
  {
    // Question 1 
    //   thread (recipe below) 
    //   ------------- Recipe for thread
    //       that moves the tortoise 6 pixels
    //   ------------- End of thread recipe
  }
  public void question2()
  {
    //        Do the following 76 times
    //           call stitch
    //        Repeat
  }
  public void question3()
  {
    //        Add lime to the color wheel
  }
  public void question4()
  {
    //        Add red to the color wheel
  }
  public static void main(String[] args)
  {
    new PentagonCrazyQuizGrader().grade(new PentagonCrazyQuiz());
  }
}
