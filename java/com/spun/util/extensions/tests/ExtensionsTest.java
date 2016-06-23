package com.spun.util.extensions.tests;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ExtensionsTest
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
  @Test
  public void testname() throws Exception
  {
    String name = "Hello World".use(MyStringUtils.class).removeVowels();
    Assert.assertEquals("Hll Wrld", name);
  }
}
