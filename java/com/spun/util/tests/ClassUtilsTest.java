package com.spun.util.tests;

import java.io.File;
import java.io.Serializable;

import junit.framework.TestCase;

import com.spun.util.ClassUtils;

public class ClassUtilsTest extends TestCase
{
  public void testHasMethod() throws Exception
  {
    assertFalse(ClassUtils.hasMethod(Serializable.class, "getClass"));
    assertTrue(ClassUtils.hasMethod(Object.class, "getClass"));
  }
  public void testSourceDirectory() throws Exception
  {
    File dir = ClassUtils.getSourceDirectory(getClass());
    assertNotNull(dir);
    assertTrue(dir.isDirectory());
  }
}
