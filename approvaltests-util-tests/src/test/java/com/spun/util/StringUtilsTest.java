package com.spun.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class StringUtilsTest
{
  ReplaceUseCase[] replaceUseCases = {new ReplaceUseCase("quick brown fox", "brown", "white", "quick white fox"),
                                      new ReplaceUseCase("quick brown quick fox brown", "quick", "slow",
                                          "slow brown slow fox brown"),};
  @Test
  public void testJoin()
  {
    assertEquals("Falco,Llewellyn", StringUtils.join("Falco", ",", "Llewellyn"));
    assertEquals("Falco", StringUtils.join("Falco", ",", null));
    assertEquals("Falco", StringUtils.join("Falco", ",", ""));
  }
  @Test
  public void testToNameCase()
  {
    assertEquals("Mr. Frank M Peter", StringUtils.toNameUpperCase("mr. frank m peter"),
        "Name changed");
  }
  @Test
  public void testSplit()
  {
    SplitUseCase[] split = {new SplitUseCase("quick brown fox", " "),
                            new SplitUseCase("quick/brown/ fox", "/"),
                            new SplitUseCase("quick**brown**fox", "**"),
                            new SplitUseCase(" quick   brown fox ", " "),
                            new SplitUseCase("quick brown fox", "brown"),};
    Approvals.verifyAll(split, a -> String.format("'%s'.split(%s) => %s", a.start, a.splitOn,
        Arrays.toString(StringUtils.split(a.start, a.splitOn))));
  }
  @Test
  public void testJavaScript()
  {
    String[] strings = {"\"\n", "this is a note \"\'\nanother liner"};
    Approvals.verifyAll(strings, a -> multiline(a, StringUtils.toJavaScriptEncode(a)));
  }
  public static String multiline(String from, String to)
  {
    return (from + "\n => \n" + to + "\n" + "------------------------------------");
  }
  @Test
  public void testJoinCollection()
  {
    // begin-snippet: join_collection
    List<Integer> number = Arrays.asList(1, 2, 3, 4, 5);
    String text = StringUtils.join(number, ", ");
    // end-snippet
    Approvals.verify(text);
  }
  @Test
  public void testJoinCollectionWithFunction()
  {
    // begin-snippet: join_collection_with_lambda
    List<Integer> number = Arrays.asList(1, 2, 3, 4, 5);
    String text = StringUtils.join(number, ", ", n -> StringUtils.padNumber(n, 3));
    // end-snippet
    Approvals.verify(text);
  }
  @Test
  public void testReplace()
  {
    for (int i = 0; i < replaceUseCases.length; i++)
    {
      assertEquals(StringUtils.replace(replaceUseCases[i].startingString,
            replaceUseCases[i].find, replaceUseCases[i].replace), replaceUseCases[i].expectedString,
          "Replace failed");
    }
  }
  @Test
  public void testWriteToString()
  {
    Approvals.verify(StringUtils.toString("things", new Object[]{1, null, "hi"}));
  }
  public class SplitUseCase
  {
    String   start;
    String   splitOn;
    String[] expectedArray;
    public SplitUseCase(String startingString, String splitOn, String... expectedArray)
    {
      this.start = startingString;
      this.splitOn = splitOn;
      this.expectedArray = expectedArray;
    }
  }
  public class ReplaceUseCase
  {
    String startingString;
    String find;
    String replace;
    String expectedString;
    public ReplaceUseCase(String startingString, String find, String replace, String expectedString)
    {
      this.startingString = startingString;
      this.find = find;
      this.replace = replace;
      this.expectedString = expectedString;
    }
  }
}
