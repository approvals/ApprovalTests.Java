package com.spun.util.tests;

import java.util.Arrays;
import java.util.List;

import org.approvaltests.Approvals;

import com.spun.util.StringUtils;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase
{
  ReplaceUseCase[] replaceUseCases = {new ReplaceUseCase("quick brown fox", "brown", "white", "quick white fox"),
                                      new ReplaceUseCase("quick brown quick fox brown", "quick", "slow",
                                          "slow brown slow fox brown"),};
  
  public void testJoin() throws Exception
  {
    assertEquals("Falco,Llewellyn", StringUtils.join("Falco", ",", "Llewellyn"));
    assertEquals("Falco", StringUtils.join("Falco", ",", null));
    assertEquals("Falco", StringUtils.join("Falco", ",", ""));
  }
  
  public void testToNameCase()
  {
    assertEquals("Name changed", "Mr. Frank M Peter", StringUtils.toNameUpperCase("mr. frank m peter"));
  }
  
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
  public void testJavaScript()
  {
    String[] strings = {"\"\n", "this is a note \"\'\nanother liner"};
    Approvals.verifyAll(strings, a -> multiline(a, StringUtils.toJavaScriptEncode(a)));
  }
  public static String multiline(String from, String to)
  {
    return (from + "\n => \n" + to + "\n" + "------------------------------------");
  }
  
  public void testJoinCollection()
  {
    // begin-snippet: join_collection
    List<Integer> number = Arrays.asList(1, 2, 3, 4, 5);
    String text = StringUtils.join(number, ", ");
    // end-snippet
    Approvals.verify(text);
  }
  public void testJoinCollectionWithFunction()
  {
    // begin-snippet: join_collection_with_lambda
    List<Integer> number = Arrays.asList(1, 2, 3, 4, 5);
    String text = StringUtils.join(number, ", ", n -> StringUtils.padNumber(n, 3));
    // end-snippet
    Approvals.verify(text);
  }
  public void testReplace()
  {
    for (int i = 0; i < replaceUseCases.length; i++)
    {
      assertEquals("Replace failed", StringUtils.replace(replaceUseCases[i].startingString,
          replaceUseCases[i].find, replaceUseCases[i].replace), replaceUseCases[i].expectedString);
    }
  }
  public void testWriteToString() throws Exception
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
  /************************************************************************/
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
