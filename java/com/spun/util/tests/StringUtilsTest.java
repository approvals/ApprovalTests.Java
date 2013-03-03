package com.spun.util.tests;

import java.util.Arrays;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.approvaltests.strings.Printer;

import com.spun.util.StringUtils;

public class StringUtilsTest extends TestCase
{

  ReplaceUseCase[] replaceUseCases = {new ReplaceUseCase("quick brown fox", "brown", "white", "quick white fox"),
      new ReplaceUseCase("quick brown quick fox brown", "quick", "slow", "slow brown slow fox brown"),};
  /***********************************************************************/
  public void testJoin() throws Exception
  {
    assertEquals("Falco,Llewellyn", StringUtils.join("Falco", ",", "Llewellyn"));
    assertEquals("Falco", StringUtils.join("Falco", ",", null));
    assertEquals("Falco", StringUtils.join("Falco", ",", ""));
  }
  /***********************************************************************/
  public void testToNameCase()
  {
    assertEquals("Name changed", "Mr. Frank M Peter", StringUtils.toNameUpperCase("mr. frank m peter"));
  }
  /***********************************************************************/
  public void testSplit()
  {
    SplitUseCase[] split = {new SplitUseCase("quick brown fox", " "),
        new SplitUseCase("quick/brown/ fox", "/"),
        new SplitUseCase("quick**brown**fox", "**"),
        new SplitUseCase(" quick   brown fox ", " "),
        new SplitUseCase("quick brown fox", "brown"),};

    Approvals.verifyAll(split, new Printer<SplitUseCase>(split[0]){{format("'%s'.split(%s) => %s", a.start,a.splitOn, Arrays.toString(StringUtils.split(a.start, a.splitOn)));}});
  }
 
  public void testJavaScript()
  {
    String[] strings = {"\"\r\n", "this is a note \"\'\r\nanother liner"};
    Approvals.verifyAll(strings, new Printer<String>(""){{multiline(a,StringUtils.toJavaScriptEncode(a));}});
  }
  /***********************************************************************/
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
  /***********************************************************************/
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
  /***********************************************************************/
  /***********************************************************************/
}
