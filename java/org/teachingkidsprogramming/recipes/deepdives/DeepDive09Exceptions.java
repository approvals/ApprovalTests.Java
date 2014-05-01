package org.teachingkidsprogramming.recipes.deepdives;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.spun.util.FormattedException;

@Ignore
public class DeepDive09Exceptions
{
  //  How to do deep dive:
  //  Step 1: Select the method name (doesABear on line 20) Press the Run Button
  //            PC: Ctrl+F11 
  //            Mac: Command+fn+F11
  //  Step 2: Read the name of the method that failed
  //  Step 3: Fill in the blank (___) to make it pass
  //  Step 4: Consider at least one thing you just learned
  //  Step 5: Advance to the next method
  //  Do not change anything except the blank (___)
  // THERE ARE NO BLANKS!
  //
  @Test
  public void exceptionsShouldProvideInformation() throws Exception
  {
    Chain c = createChain();
    int answer = c.get("a").get("b").get("c").get("d").get("e").value; /* Fix This Line */
    Assert.assertEquals(2048, answer);
  }
  @Test
  public void exceptionsShouldExplainPreconditions() throws Exception
  {
    Game game = new Game();
    /* Add needed line here */
    int fun = game.play();
    Assert.assertEquals(11, fun);
  }
  @Test
  public void creatingYourOwnExceptions() throws Exception
  {
    Exception yourException = null;
    try
    {
      int[] indexes = {5, 83, 14, 20, 2, 6, 7};
      String[] values = new String[8];
      for (int i : indexes)
      {
        if (i < 0 || values.length <= i)
        {
          int length = ____;
          throw new FormattedException("Index out of Boands [%s] for Array of length %s", ___, length);
        }
        String e = values[i];
      }
    }
    catch (Exception e)
    {
      yourException = e;
    }
    Assert.assertEquals("Index out of Boands [83] for Array of length 8", yourException.getMessage());
  }
  @Test(expected = RuntimeException.class)
  public void youCanAugmentExceptions() throws Exception
  {
    int[] indexes = {5, 83, 14, 20, 2, 6, 7};
    String[] values = new String[8];
    for (int i : indexes)
    {
      try
      {
        String e = values[i];
      }
      catch (Exception originalException)
      {
        String message = String.format("Index out of Boands [%s] for Array of length %s", i, values.length);
        throw new _____(message, originalException);
      }
    }
  }
  @Test
  public void exceptionsShouldShowAllInputs() throws Exception
  {
    int s = 0;
    s += call(1, 3, 7);
    s += call(2, 3, 8);
    s += call(-6, 3, 9);
    s += call(-11, 3, 17);
    s += call(-10, 3, 31);
    /* ^^^ Change 1 of the 3's of the above to a 4 ^^^ */
    Assert.assertEquals(64, s);
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
  private int call(int a, int b, int c)
  {
    if (((a + c) / 2) == b) { throw new FormattedException("%s is not a valid input for (%s, %s, %s)", b, a, b, c); }
    return a + b + c;
  }
  private static class Game
  {
    boolean on = false;
    public void turnOn()
    {
      on = true;
    }
    public int play()
    {
      if (!on) { throw new FormattedException("Before you can play a game you need to turn it on.\n game.turnOn()"); }
      return 11;
    }
  }
  private static class Chain
  {
    private String label;
    private Chain  chain;
    public int     value;
    public Chain(String label, Chain chain)
    {
      this.label = label;
      this.chain = chain;
    }
    public Chain(int value)
    {
      this.value = value;
    }
    public Chain get(String string)
    {
      if (!label.equals(string)) { throw new FormattedException("There is no value for '%s', please use '%s'",
          string, label); }
      return chain;
    }
  }
  private Chain createChain()
  {
    return new Chain("a", new Chain("b", new Chain("surprise", new Chain("d", new Chain("e", new Chain(2048))))));
  }
  private static class _____ extends Exception
  {
    public _____(String message, Exception originalException)
    {
      super(message, originalException);
    }
  }
  public String  ___  = "You need to fill in the blank ___";
  public Integer ____ = -99;
}
