package com.spun.util;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.AutoApproveReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.utils.parseinput.ParseInput;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    assertEquals("Mr. Frank M Peter", StringUtils.toNameUpperCase("mr. frank m peter"), "Name changed");
  }
  @Test
  public void testSplit()
  {
    var expected = """
        'quick brown fox'.split( ) => [quick, brown, fox]
        'quick/brown/ fox'.split(/) => [quick, brown, fox]
        'quick**brown**fox'.split(\\*\\*) => [quick, brown, fox]
        ' quick   brown fox '.split( ) => [, quick, , , brown, fox]
        'quick brown fox'.split(brown) => [quick, fox]
        """;
    SplitUseCase[] split = {new SplitUseCase("quick brown fox", " "),
                            new SplitUseCase("quick/brown/ fox", "/"),
                            new SplitUseCase("quick**brown**fox", "\\*\\*"),
                            new SplitUseCase(" quick   brown fox ", " "),
                            new SplitUseCase("quick brown fox", "brown"),};
    Approvals.verifyAll(split, a -> String.format("'%s'.split(%s) => %s", a.start, a.splitOn,
        Arrays.toString(StringUtils.split(a.start, a.splitOn, true))), new Options().inline(expected));
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
      assertEquals(StringUtils.replace(replaceUseCases[i].startingString, replaceUseCases[i].find,
          replaceUseCases[i].replace), replaceUseCases[i].expectedString, "Replace failed");
    }
  }
  @Test
  public void testWriteToString()
  {
    Approvals.verify(StringUtils.toString("things", new Object[]{1, null, "hi"}));
  }
  @Test
  void testSortedMap()
  {
    Map<String, String> sortedMap = new TreeMap<>((s1, s2) -> s1.length() - s2.length());
    sortedMap.put("c", "1");
    sortedMap.put("mmmm", "1");
    sortedMap.put("aaa", "1");
    sortedMap.put("zz", "1");
    Approvals.verify(sortedMap);
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
  @Test
  @UseReporter(AutoApproveReporter.class)
  public void testSplitting()
  {
    var expected = """
        1a2aa3, a -> [1, 2, , 3]
        1ś2śś3, ś -> [1, 2, , 3]
        1a2aa3a, a -> [1, 2, , 3]
        1ś2śś3ś, ś -> [1, 2, , 3]
        1a2aa3aa, a -> [1, 2, , 3, ]
        1ś2śś3śś, ś -> [1, 2, , 3, ]
        """;
    ParseInput.from(expected).withTypes(String.class, String.class)
        .verifyAll((i, p) -> Arrays.toString(StringUtils.split(i, p)));
  }
  @Test
  @UseReporter(AutoApproveReporter.class)
  public void testRepeating()
  {
    var expected = """
        Hello, 3 -> HelloHelloHello
        -, 56 -> --------------------------------------------------------
        """;
    ParseInput.from(expected).withTypes(String.class, Integer.class).verifyAll((s, i) -> StringUtils.repeat(s, i));
  }
  @Test
  public void testEnsureEnding()
  {
    String first = "hello";
    String second = "hello\n";
    assertEquals(StringUtils.ensureEnding(first, "\n"), second);
    assertEquals(StringUtils.ensureEnding(second, "\n"), second);
  }
}