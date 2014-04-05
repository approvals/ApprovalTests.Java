package org.teachingkidsprogramming.recipes.quizzes.graders;

public class PentagonQuizAdapter
{
  public PentagonCrazyQuizGrader grader;
  public void callThread()
  {
    HousesQuizAdapter.call(this, "thread");
  }
  public void question2()
  {
  }
  public void question3()
  {
  }
  public void question4()
  {
  }
  public void stitch()
  {
    grader.stitch();
  }
}
