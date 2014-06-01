package org.extensionmethods.tests;

import org.extensionmethods.ExtendableUtilities;

import junit.framework.TestCase;

public class ExtendableTest extends TestCase
{
  public static class MyString extends ExtendableBase<String>
  {
    public String removeVowels()
    {
      return caller.toUpperCase();
    }
  }
  public void testname() throws Exception
  {
    if (ExtendableUtilities.isNotConfigured()) {
      return;
    }
    String i = "hello".use(MyString.class).removeVowels();
    assertEquals("HELLO", i);
  }
}