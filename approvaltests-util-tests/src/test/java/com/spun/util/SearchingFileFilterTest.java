package com.spun.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchingFileFilterTest
{
  @Test
  public void testStar() throws Exception
  {
    assertFalse(new SearchingFileFilter("*", "something").accept(".anything", true));
    assertFalse(new SearchingFileFilter("*", "something").accept("anythingFile", false));
    assertTrue(new SearchingFileFilter("*", "something").accept("anythingDirectory", true));
    assertFalse(new SearchingFileFilter("something").accept("anything", true));
    assertTrue(new SearchingFileFilter("*", "temp.htm").accept("temp.htm", false));
  }
  @Test
  public void testFinding() throws Exception
  {
    assertEquals("[*, temp.htm]", new SearchingFileFilter("*", "temp.htm").getSubset("a").toString());
    assertEquals("[b]", new SearchingFileFilter("*", "a", "b").getSubset("a").toString());
  }
}
