package com.spun.util.extensions.tests;

import junit.framework.TestCase;

public class ExtensionsTest extends TestCase
{
  public static class MyStringUtils extends ExtendableBase<String>
  {
    public String removeVowels()
    {
      StringBuffer b = new StringBuffer();
      for (Character c : caller.toCharArray())
      {
        switch (c)
        {
          case 'a' :
          case 'e' :
          case 'i' :
          case 'o' :
          case 'u' :
            break;
          default :
            b.append(c);
        }
      }
      return b.toString();
    }
  }
  public void testname() throws Exception
  {
    String name = "Hello World".use(MyStringUtils.class).removeVowels();
    assertEquals("Hll Wrld", name);
  }
}
